package Server;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerThread extends Thread {
    private InetAddress addr;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private String clientMessage;
    private int counter;
    private DatabaseHandler mdbc;
    private Statement stmt;
    private UsersTable usersTable;
    private FlightsTable flightsTable;

    public ServerThread(Socket s, int counter) throws IOException, SQLException, ClassNotFoundException {
        this.counter = counter;
        this.os = new ObjectOutputStream(s.getOutputStream());
        this.is = new ObjectInputStream(s.getInputStream());
        this.addr = s.getInetAddress();
        this.mdbc = new DatabaseHandler();
        Connection conn = this.mdbc.getDbConnection();


        try {
            this.stmt = conn.createStatement();
            this.usersTable = new UsersTable(this.stmt, this.mdbc);
            this.flightsTable = new FlightsTable(this.stmt, this.mdbc);
        } catch (SQLException e5) {
            System.out.println(e5);
        }
    }

    public void writeObj(String str) {
        this.clientMessage = str;

        try {
            this.os.writeObject(this.clientMessage);
        } catch (IOException e3) {
            System.err.println("I/О thread error" + e3);
        }
    }

    public void save1 (Boolean bool, String text)
    {
        try(FileOutputStream fos=new FileOutputStream("D://KP//serverLog.txt", bool))
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void run()
    {
        boolean i = false;
        String messageToClient = "";
        String str;
        String ThreadStop = "";
        save1(false, "");
        try {
            System.out.println("Сервер ожидает действий от клиента");

            while (!ThreadStop.equals("Exit")) {
                str = (String) this.is.readObject();
                save1(true, str + " \r\n");
                String[] messageParts = str.split(",");
                switch (messageParts[0])
                {
                    case "checkLoginClient":
                        String UserLogin = messageParts[1];
                        String UserPassword = messageParts[2];
                        messageToClient = this.usersTable.CheckLoginClient(UserLogin, UserPassword);
                        this.writeObj(messageToClient);
                        break;
                    case "checkLoginAdmin":
                        String UserLogin1 = messageParts[1];
                        String UserPassword1 = messageParts[2];
                        messageToClient = this.usersTable.CheckLoginAdmin(UserLogin1, UserPassword1);
                        this.writeObj(messageToClient);
                        break;
                    case "addClient":
                        this.usersTable.AddClient(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5], messageParts[6]);
                        break;
                    case "addInfoToUserData":
                        this.flightsTable.addInfoToUserData(messageParts[1], messageParts[2], messageParts[3]);
                        break;
                    case "сheckAvFl":
                        messageToClient = this.flightsTable.сheckAvFl(messageParts[1], messageParts[2]);
                        this.writeObj(messageToClient);
                        break;
                    case "getAvFlID":
                        messageToClient = this.flightsTable.getAvFlID(messageParts[1], messageParts[2]);
                        this.writeObj(messageToClient);
                        break;
                    case "checkUserInDB":
                        messageToClient = this.usersTable.checkUserInDB(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "sendData":
                        messageToClient = this.flightsTable.sendData(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "senddata":
                        messageToClient = this.flightsTable.senddata(messageParts[1], messageParts[2], messageParts[3]);
                        this.writeObj(messageToClient);
                        break;
                    case "getAvFl":
                        messageToClient = this.flightsTable.getAvFl(messageParts[2], messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "allFl":

                        messageToClient = this.flightsTable.allFl();
                        this.writeObj(messageToClient);
                        break;
                    case "isBlocked":
                        messageToClient = this.usersTable.isBlocked(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "CountFreeSeatsAmount":
                        this.flightsTable.CountFreeSeatsAmount(messageParts[1]);
                        break;
                    case "addIDFtoUD":
                        this.flightsTable.addIDFtoUD(messageParts[1], messageParts[2]);
                        break;
                    case "getIDUserD":
                        messageToClient = this.flightsTable.getIDUserD(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "getTicketInfo":
                        messageToClient = this.flightsTable.getTicketInfo(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "getClientName":
                        messageToClient = this.flightsTable.getClientName(messageParts[1]);
                        this.writeObj(messageToClient);
                        break;
                    case "CalculateFullPrice":
                        messageToClient = this.flightsTable.CalculateFullPrice(messageParts[1], messageParts[2]);
                        this.writeObj(messageToClient);
                        break;
                    case "addInfoToTicket":
                        this.flightsTable.addInfoToTicket(messageParts[1], messageParts[2]);
                        break;
                    case "saveTicketToFile":
                        this.flightsTable.saveTicketToFile(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5]);
                        break;
                    case "deleteUserData":
                        this.flightsTable.deleteUserData(messageParts[1]);
                        break;
                    case "getDataForPie":
                        clientMessage = this.flightsTable.getDataForPie();
                        System.out.println(clientMessage);
                        this.writeObj(clientMessage);
                        break;
                    case "addFl":
                        this.flightsTable.addFl(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5], messageParts[6]);
                        break;
                    case "deleteFl":
                        this.flightsTable.deleteFl(messageParts[1], messageParts[2], messageParts[3]);
                        System.out.println("ВНИМАНИЕ!!!!!1 " + messageParts[1]+ messageParts[2]+ messageParts[3]);
                        break;
                    case "redFlAir":
                        this.flightsTable.redFlAir(messageParts[1], messageParts[2], messageParts[3], messageParts[4], messageParts[5]);
                        System.out.println("ВНИМАНИЕ тут уже изменения!!!!!1 " + messageParts[1]+ messageParts[2]+ messageParts[3]);
                        break;
                    case "redFlDate":
                        this.flightsTable.redFlDate(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "redFlOut":
                        this.flightsTable.redFlOut(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "redFlIn":
                        this.flightsTable.redFlIn(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "redFlCost":
                        this.flightsTable.redFlCost(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "redFlAmSeet":
                        this.flightsTable.redFlAmSeet(messageParts[1], messageParts[2], messageParts[3], messageParts[4]);
                        break;
                    case "addAdmin":
                        this.usersTable.addAdmin(messageParts[1], messageParts[2]);
                        break;
                    case "deleteAdmin":
                        this.usersTable.deleteAdmin(messageParts[1]);
                        break;
                    case "blockUser":
                        this.usersTable.blockUser(messageParts[1]);
                        break;
                }}}catch (Exception var17) {
            System.err.println("Соединение закрыто");
        } finally {
            this.disconnect();
        }}

    private void disconnect() {
        try {
            if (this.os != null) {
                this.os.close();
            }
            if (this.is != null) {
                this.is.close();
            }
            System.out.println(this.addr.getHostName() + " Закрытие соединения " + this.counter + "го клиента");
        } catch (IOException var5) {
            var5.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}


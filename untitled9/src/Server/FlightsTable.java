package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class FlightsTable extends DataTable implements ResultFromTable{
    public static int countMain;

    public FlightsTable (Statement stmt, DatabaseHandler mdbc) {
        super(stmt, mdbc);
    }

    public ResultSet getResultFromTable(String table) {
        ResultSet rs = null;
        try
        {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public void saveTicketToFile(String airport, String date, String outtime, String intime, String fullprice)
    {
        String text = "Аэропорт прибытия: " + airport + " \r\n";
        save(false, text);
        String text1 = "Дата вылета: " + date + " \r\n";
        save(true, text1);
        String text2 = "Время вылета:" + outtime + " \r\n";
        save(true, text2);
        String text3 = "Время прибытия:" + intime + " \r\n";
        save(true, text3);
        String text4 = "Цена:" + fullprice;
        save(true, text4);
    }

    public void save (Boolean bool, String text)
    {
        try(FileOutputStream fos=new FileOutputStream("D://KP//ticketAirport.txt", bool))
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
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

    public void deleteUserData(String idud)
    {
        int IDUD = Integer.parseInt(idud);
        try {
            stmt.executeUpdate("DELETE FROM " + Const.USERDATA_TABLE + " WHERE (" + Const.USERDATA_IDUD + " = '" + IDUD + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFl(String airport, String date, String outtime)
    {
        String idmd = getidmd(airport, date, outtime);
        ResultSet resultSet;
        String idmd1 = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_ID + " FROM " + Const.FLIGHT_TABLE + " WHERE (" + Const.FL_IDMD + " LIKE '" + idmd + "');");
            while (resultSet.next())
                idmd1 = resultSet.getString(Const.FL_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt.executeUpdate("DELETE FROM " + Const.USERDATA_TABLE + " WHERE (" + Const.USERDATA_IDF + " LIKE '" + idmd1 + "');");

            stmt.executeUpdate("DELETE FROM " + Const.FLIGHT_TABLE + " WHERE (" + Const.FL_IDMD + " LIKE '" + idmd + "');");
            stmt.executeUpdate("DELETE FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redFlAir(String airport, String date, String outtime, String newZ, String column)
    {
        String idmd = getidmd(airport, date, outtime);

        try {
            stmt.executeUpdate("UPDATE " + Const.MAINDATA_TABLE + " SET " + Const.MAINDATA_AIRPORT + " = '" + newZ + "' WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redFlDate(String airport, String date, String outtime, String newDate)
    {
        try {
            stmt.executeUpdate("UPDATE " + Const.MAINDATA_TABLE + " SET " + Const.MAINDATA_DATE + " = '" + newDate + "' WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redFlOut(String airport, String date, String outtime, String newOut)
    {
        try {
            stmt.executeUpdate("UPDATE " + Const.MAINDATA_TABLE + " SET " + Const.MAINDATA_OUTTIME + " = '" + newOut + "' WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redFlIn(String airport, String date, String outtime, String newIn)
    {
        try {
            stmt.executeUpdate("UPDATE " + Const.MAINDATA_TABLE + " SET " + Const.MAINDATA_INTIME + " = '" + newIn + "' WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redFlCost(String airport, String date, String outtime, String newCost)
    {

        String idmd = getidmd(airport, date, outtime);
        int idmd1 = Integer.valueOf(idmd);
        try {
            stmt.executeUpdate("UPDATE " + Const.FLIGHT_TABLE + " SET " + Const.FL_PRICE + " = '" + newCost + "' WHERE (" + Const.FL_IDMD + " LIKE '" + idmd1  +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void redFlAmSeet(String airport, String date, String outtime, String newSeet)
    {

        String idmd = getidmd(airport, date, outtime);
        int idmd1 = Integer.valueOf(idmd);
        try {
            stmt.executeUpdate("UPDATE " + Const.FLIGHT_TABLE + " SET " + Const.FL_SEATS + " = '" + newSeet + "' WHERE (" + Const.FL_IDMD + " LIKE '" + idmd1  +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addInfoToUserData(String age, String laggage, String userName)
    {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.USERDATA_TABLE + "(" + Const.USERDATA_IDU + "," + Const.USERDATA_A + "," + Const.USERDATA_l + ")" + "VALUES (" + getIdUser(userName) + "," + this.quotate(age) + "," + this.quotate(laggage) + ");" );
            String lprice = addLaPrice(laggage);
            stmt.executeUpdate("INSERT INTO " + Const.LA_TABLE + "(" + Const.LA_IDUD + "," + Const.LA_PRICE + ")" + "VALUES (" + getIDUD(getIdUser(userName)) + "," + this.quotate(lprice) + ");" );
            String asale = addSale(age);
            stmt.executeUpdate("INSERT INTO " + Const.A_TABLE + "(" + Const.A_IDUD + "," + Const.A_SALE + ")" + "VALUES (" + getIDUD(getIdUser(userName)) + "," + this.quotate(asale) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFl(String airport, String date, String outtime, String intime, String price, String seetAm)
    {
        int idmd1;
        idmd1 = Integer.valueOf(price);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.MAINDATA_TABLE + "(" + Const.MAINDATA_AIRPORT + "," + Const.MAINDATA_DATE + "," + Const.MAINDATA_OUTTIME + "," + Const.MAINDATA_INTIME + ") " + "VALUES (" + this.quotate(airport) + "," + this.quotate(date) + "," + this.quotate(outtime) + "," + this.quotate(intime) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String idmd = getidmd(airport, date, outtime);
        idmd1 = Integer.valueOf(idmd);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.FLIGHT_TABLE + "(" + Const.FL_PRICE+ "," + Const.FL_IDMD + "," + Const.FL_SEATS + ") " + "VALUES (" + this.quotate(price) + "," + idmd1 + "," + this.quotate(seetAm) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public String getidmd (String airport, String date, String outtime)
    {
        ResultSet resultSet;
        String idmd = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.MAINDATA_IDMD + " FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime + "');");
            while (resultSet.next())
                idmd = resultSet.getString(Const.MAINDATA_IDMD);
        } catch (SQLException e) {
            e.printStackTrace();
        }return idmd;
    }

    public void addInfoToTicket(String idud, String fullPrice)
    {
        String idu = getIdU(idud);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.TICKET_TABLE + "(" + Const.TICKET_IDUS + "," + Const.TICKET_FULLPRICE + ")" + "VALUES (" + this.quotate(idu) + "," + this.quotate(fullPrice) + ");" );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getClientName(String idud)
    {
        String idu = getIdU(idud);
        String name = getClName(idu);
        String surname = getClSurname(idu);
        String ns = name + "," + surname;
        return ns;
    }

    public String getClName(String idu)
    {
        ResultSet resultSet;
        String name = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.CLIENT_NAME + " FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENT_ID + " LIKE '" + idu + "';");
            while (resultSet.next())
                name = resultSet.getString(Const.CLIENT_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }return name;
    }

    public String getClSurname(String idu)
    {
        ResultSet resultSet;
        String surname = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.CLIENT_SURNAME + " FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENT_ID + " LIKE '" + idu + "';");
            while (resultSet.next())
                surname = resultSet.getString(Const.CLIENT_SURNAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }return surname;
    }

    public String getIdU(String idud)
    {
        ResultSet resultSet;
        String id = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDU + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next())
                id = resultSet.getString(Const.USERDATA_IDU);
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }

    public void addIDFtoUD(String id, String username)
    {
        int idu = getIdUser(username);
        try {
            stmt.executeUpdate("UPDATE " + Const.USERDATA_TABLE + " SET " + Const.USERDATA_IDF + " = '" + id + "' WHERE (" + Const.USERDATA_IDU + " = '" + idu + "')");}
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addSale (String age)
    {
        String sale = "";
        if (age.equals("Взpослые(18+ лет)")) sale = "0";
        else if (age.equals("Подpостки(14-17 лет)")) sale = "20";
        else if (age.equals("Дети(2-13 лет)")) sale = "50";
        else if (age.equals("Младенцы(0-2 года)")) sale = "100";
        return sale;
    }

    public String addLaPrice (String laggage)
    {
        String price = "";
        if (laggage.equals("Pучная кладь")) price = "0";
        else if (laggage.equals("Дополнительная pучная кладь")) price = "10";
        else if (laggage.equals("Основной багаж")) price = "20";
        else if (laggage.equals("Дополнительный основной багаж")) price = "30";
        else if (laggage.equals("Кpупногабаритный багаж")) price = "40";
        return price;
    }

    public int getIdUser(String username)
    {
        ResultSet resultSet;
        int id = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " LIKE '" + username + "';");
            while (resultSet.next())
                id = resultSet.getInt(Const.USERS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }

    public String getIDUserD (String idf)
    {
        ResultSet resultSet;
        String idU = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDUD + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDF + " = '" + idf + "';");
            while (resultSet.next())
                idU = resultSet.getString(Const.USERDATA_IDUD);
        } catch (SQLException e) {
            e.printStackTrace();
        }return idU;
    }

    public int getIDUD (int idu)
    {
        ResultSet resultSet;
        int idU = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDUD + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDU + " = '" + idu + "';");
            while (resultSet.next())
                idU = resultSet.getInt(Const.USERDATA_IDUD);
        } catch (SQLException e) {
            e.printStackTrace();
        }return idU;
    }

    public String sendData (String data){
        return data;
    }

    public String senddata (String data, String data1, String data2)
    {
        String fin = data + "," + data1 + "," + data2;
        System.out.println("Тут ФИИИИИИИН!!!  " + fin);
        return fin;
    }

    public int getIDmd (String idf)
    {
        ResultSet resultSet;
        int idmd = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_IDMD + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " LIKE '" + idf + "';");
            while (resultSet.next())
                idmd = resultSet.getInt(Const.FL_IDMD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save1(true, "idf = " + idf + " \r\n");
        save1(true, "idmd = " + idmd + " \r\n");
        return idmd;
    }

    public int getLaggagePrice (String idud)
    {
        ResultSet resultSet;
        int lprice = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.LA_PRICE + " FROM " + Const.LA_TABLE + " WHERE " + Const.LA_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next())
                lprice = resultSet.getInt(Const.LA_PRICE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save1(true, "lprice = " + lprice + " \r\n");
        return lprice;
    }

    public int getSale (String idud)
    {
        ResultSet resultSet;
        int sale = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.A_SALE + " FROM " + Const.A_TABLE + " WHERE " + Const.A_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next())
                sale = resultSet.getInt(Const.A_SALE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save1(true, "sale = " + sale + " \r\n");
        return sale;
    }

    public int getFlPrice (String idf)
    {
        ResultSet resultSet;
        int price = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_PRICE + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " LIKE '" + idf + "';");
            while (resultSet.next())
                price = resultSet.getInt(Const.FL_PRICE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save1(true, "flprice = " + price + " \r\n");
        return price;
    }

    public String CalculateFullPrice (String idud, String idf)
    {
        String fullprice = "";
        int laPrice = getLaggagePrice(idud);
        int flPrice = getFlPrice(idf);
        int sale = getSale(idud);
        int full = flPrice - flPrice * sale / 100 + laPrice;

        save1(true, "full price = " + full + " \r\n");
        fullprice = String.valueOf(full);
        return fullprice;
    }

    public String getTicketInfo (String idf)
    {
        String idmd = String.valueOf(getIDmd(idf));
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        String idMain = null;
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        try {
            while (rs.next()) {
                String idMD = rs.getString(Const.MAINDATA_IDMD);
                if (idmd.equals(idMD)) {
                    airport = rs.getString(Const.MAINDATA_AIRPORT);
                    date = rs.getString(Const.MAINDATA_DATE);
                    outtime = rs.getString(Const.MAINDATA_OUTTIME);
                    intime = rs.getString(Const.MAINDATA_INTIME);
                    idMain = idMD;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String tickInfo = airport + "," + date + "," + outtime + "," + intime;

        save1(true, tickInfo + " \r\n");
        return tickInfo;
    }


    public String allFl() throws SQLException {

        ResultSet resultSet;
        String count = null;
        try {
            resultSet = stmt.executeQuery("SELECT *" + " FROM " + Const.FLIGHT_TABLE + " ORDER BY " + Const.FL_ID + " DESC;");
            resultSet.next();
            count = resultSet.getString(Const.FL_ID);
        }catch (SQLException e){
            e.printStackTrace();
        }
        countMain = Integer.parseInt(count);
        System.out.println("Гланвый каунт !!! " + countMain);

        String AvFlData = count + ";";
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        String price = null;
        String seats = null;
        String idfl = null;
        String idMD = null;

        for (int i = 0; i < countMain + 1; i++) {

            ResultSet rs1 ;
            try {
                rs1 = stmt.executeQuery("SELECT *" + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " LIKE '" + i + "';");
                while (rs1.next()) {


                    price = rs1.getString(Const.FL_PRICE);
                    seats = rs1.getString(Const.FL_SEATS);
                    idfl = rs1.getString(Const.FL_ID);
                    idMD = rs1.getString(Const.FL_IDMD);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }



            ResultSet rs ;
            try {

                rs = stmt.executeQuery("SELECT *" + " FROM " + Const.MAINDATA_TABLE + " WHERE " + Const.MAINDATA_IDMD + " LIKE '" + idMD + "';");
                while (rs.next()) {

                        airport = rs.getString(Const.MAINDATA_AIRPORT);
                        date = rs.getString(Const.MAINDATA_DATE);
                        outtime = rs.getString(Const.MAINDATA_OUTTIME);
                        intime = rs.getString(Const.MAINDATA_INTIME);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            AvFlData += idfl + "," + airport + "," + date + "," + outtime + "," + intime + "," + seats + "," + price + ";";
            save1(true, "получение информации о полёте: " + AvFlData + " \r\n");
        }
        return AvFlData;
    }

    public String getAvFl(String id, String count)
    {
        String[] messParts = id.split(";");
        for (int j = 0; j < Integer.parseInt(count); j++)
        {

            save1(true, "Id полёта " + messParts[j] + " \r\n");
        }
        String AvFlData = "";
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        String price = null;
        String seats = null;
        String idfl = null;
        for (int i = 0; i < Integer.parseInt(count); i++) {
            ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
            try {
                while (rs.next()) {
                    String idmd = rs.getString(Const.MAINDATA_IDMD);

                    if (idmd.equals(messParts[i])) {
                        airport = rs.getString(Const.MAINDATA_AIRPORT);
                        date = rs.getString(Const.MAINDATA_DATE);
                        outtime = rs.getString(Const.MAINDATA_OUTTIME);
                        intime = rs.getString(Const.MAINDATA_INTIME);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet rs1 = this.getResultFromTable(Const.FLIGHT_TABLE);
            try {
                while (rs1.next()) {
                    String idMD = rs1.getString(Const.FL_IDMD);
                    if (idMD.equals(messParts[i])) {
                        price = rs1.getString(Const.FL_PRICE);
                        seats = rs1.getString(Const.FL_SEATS);
                        idfl = rs1.getString(Const.FL_ID);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            AvFlData +=idfl + "," + airport + "," + date + "," + outtime + "," + intime + "," + seats + "," + price + ";";
            save1(true, "получение информации о полёте: " + AvFlData + " \r\n");
        }

        return AvFlData;
    }

    public int getSeatsAmount(String id)
    {
        ResultSet resultSet;
        int seats = 0;
        int ID = Integer.parseInt(id);

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_SEATS + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " = '" + ID + "';");
            while (resultSet.next())
                seats = resultSet.getInt(Const.FL_SEATS);
        } catch (SQLException e) {
            e.printStackTrace();
        }return seats;
    }

    public void CountFreeSeatsAmount(String id)
    {
        int seats = getSeatsAmount(id) - 1;
        try {
            stmt.executeUpdate("UPDATE " + Const.FLIGHT_TABLE + " SET " + Const.FL_SEATS + " = '" + seats + "' WHERE (" + Const.FL_ID + " LIKE '" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String сheckAvFl(String airport, String date)
    {
        String hasAvFl = "fail";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int i = 0;
        try{
            while (rs.next())
            {
               String Airport = rs.getString(Const.MAINDATA_AIRPORT);
               String Date = rs.getString(Const.MAINDATA_DATE);

               if (Airport.equals(airport)&&Date.equals(date))
               {
                   i++;
               }
            }
            if(i>0) hasAvFl = "success";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        save1(true, "Проверка наличия рейса: " + hasAvFl + " \r\n");
        return hasAvFl;
    }

    public String getAvFlID (String airport, String date)
    {
        String mesToCl = "";
        String mesToCl1 = "";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int count = 0;
        try {
            while (rs.next())
            {
                String Airport = rs.getString(Const.MAINDATA_AIRPORT);
                String Date = rs.getString(Const.MAINDATA_DATE);

                if(Airport.equals(airport)&&Date.equals(date))
                {

                    String ID = rs.getString(Const.MAINDATA_IDMD);


                    mesToCl += ID + ";";
                    count ++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mesToCl1 = String.valueOf(count) + "," + mesToCl;

        save1(true, "id полетов " + mesToCl1 + " \r\n");
        return mesToCl1;
    }

    public String getDataForPie()
    {
        String data = "";
        ResultSet resultSet = this.getResultFromTable(Const.MAINDATA_TABLE);
        int countmorn = 0;
        int countday = 0;
        int counteve = 0;
        int countni = 0;
        try {
            while (resultSet.next())
            {
                String outtime = resultSet.getString(Const.MAINDATA_OUTTIME);
                String[] time = outtime.split(":");
                int  outTime = Integer.parseInt(time[0]);

                if (outTime >= 0 && outTime < 6) countni++;
                else if (outTime >= 6 && outTime < 12) countmorn++;
                else if (outTime >= 12 && outTime < 18) countday++;
                else if (outTime >= 18 && outTime < 24) counteve++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String CM = String.valueOf(countmorn);
        save1(true, "Количество вылетов утром: " + CM + " \r\n");
        String CD = String.valueOf(countday);
        save1(true, "Количество вылетов днём: " + CD + " \r\n");
        String CE  = String.valueOf(counteve);
        save1(true, "Количество вылетов вечером: " + CE + " \r\n");
        String CN = String.valueOf(countni);
        save1(true, "Количество вылетов ночью: " + CN + " \r\n");
        data = CM + "," + CD + "," + CE + "," + CN;
        return data;
    }
}

package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Server.Flight;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AvailableFlightsWindowController {

    public static String yAAAAA = null;
    public static String watchInf = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SowButton;


    @FXML
    private TableView<Flight> AvailableFlyTableView;

    @FXML
    private TableColumn<Flight, String> inAirport;

    @FXML
    private TableColumn<Flight, String> date;

    @FXML
    private TableColumn<Flight, String> outTime;

    @FXML
    private TableColumn<Flight, String> FlIDColumn;

    @FXML
    private TableColumn<Flight, String> inTime;

    @FXML
    private TableColumn<Flight, String> seatsAmount;

    @FXML
    private TableColumn<Flight, String> price;

    @FXML
    private TextField entertext1;

     @FXML
    private Button BuyTicketButton;

    @FXML
    private Button Back;

    private ObservableList<Flight> flData = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        entertext1.setEditable(true);
        String iduser = "";
        try {
            String id = (String)Client.is.readObject();
            String[] mp = id.split(",");
            iduser = mp[2];
            System.out.println("АЙДИ!!! " + iduser);
            yAAAAA = iduser;
            String id1 = mp[0] + "," + mp[1];
            System.out.println("аккаунт и айди на авфлокне " + id);
            String clm = "getAvFl," + id1;
            System.out.println("на окне полетов" + clm);
            Client.os.writeObject(clm);
            String data = (String)Client.is.readObject();
            System.out.println("на окне полетов после гетфлайтс" + data);
            //String[] count = id.split(",");
            String[] messParts = data.split(";");
            for (int i = 0; i < Integer.parseInt(mp[0]); i++)
            {
                String[] mesParts = messParts[i].split(",");
                if (!mesParts[5].equals("0"))
                flData.add(new Flight(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4], mesParts[5], mesParts[6]));
            }
            FlIDColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("id"));
            inAirport.setCellValueFactory(new PropertyValueFactory<Flight, String>("Airport"));
            date.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
            outTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("outTime"));
            inTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("inTime"));
            seatsAmount.setCellValueFactory(new PropertyValueFactory<Flight, String>("seatsAmount"));
            price.setCellValueFactory(new PropertyValueFactory<Flight, String>("price"));
            AvailableFlyTableView.setItems(flData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String finaliduser = iduser;


        BuyTicketButton.setOnAction(event -> {
            String FlID = entertext1.getText().trim();
            String idud = "";
            String clMess = "CountFreeSeatsAmount," + FlID;
            try {
                Client.os.writeObject(clMess);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String clm2 = "addIDFtoUD," + FlID + "," + finaliduser;
            System.out.println("ПРОВЕРКА!!! " + clm2);

            watchInf += "Юзер: " + finaliduser + "\n";

            try {
                Client.os.writeObject(clm2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String clm3 = "getIDUserD," + FlID;
            try {
                Client.os.writeObject(clm3);
                idud = (String)Client.is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String clm4 = "sendData," + idud + ";" + FlID;
            try {
                Client.os.writeObject(clm4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/TicketWindow.fxml");
        });

        Back.setOnAction(event -> {
            String clM = "sendData," + AvailableFlightsWindowController.yAAAAA;

            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/ClientMainWindow.fxml");
        });

    }

    public void openNewScene(String window)
    {
        BuyTicketButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}


package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class ClientMainWindowController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField FlyOutDateTF;

    @FXML
    private ComboBox<String> PassengerAgeComboBox;

    @FXML
    private TextField FlyToAirportField;

    @FXML
    public Button LetsFlyButton;

    @FXML
    public Button Exit;

    @FXML
    private ComboBox<String> LaggageComboBox;

    @FXML
    private Label UsernameTF;

    @FXML
    void initialize()
    {
        try {
            UsernameTF.setText((String) Client.is.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PassengerAgeComboBox.getItems().addAll("Взpослые(18+ лет)", "Подpостки(14-17 лет)", "Дети(2-13 лет)", "Младенцы(0-2 года)");
        LaggageComboBox.getItems().addAll("Pучная кладь", "Дополнительная pучная кладь", "Основной багаж", "Дополнительный основной багаж", "Кpупногабаритный багаж");

        LetsFlyButton.setOnAction(event -> {
            String airport = FlyToAirportField.getText().trim();
            String date = FlyOutDateTF.getText().trim();
            String age = PassengerAgeComboBox.getValue();
            String laggage = LaggageComboBox.getValue();
            String userName = UsernameTF.getText();

            String data = age + "," + laggage + "," + userName;
            String clientMessage1 = "addInfoToUserData," + data;
            try {
                Client.os.writeObject(clientMessage1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fl = airport + "," + date ;
            String clM = "сheckAvFl," + fl;
            System.out.println(clM);
            try {
                Client.os.writeObject(clM);
                String str = (String)Client.is.readObject();
                System.out.println("данные полученные на мейнклиентвиндоу с чекфл " + str);
                if (str.equals("success"))
                {
                  String clM1 = "getAvFlID," + fl;
                  Client.os.writeObject(clM1);
                    System.out.println("посылаем для получения айди " + clM1);
                  String ids = (String)Client.is.readObject();
                    System.out.println("полученные айди и аккаунт " + ids);
                  String clM2 = "senddata," + ids + "," + UsernameTF.getText();
                    System.out.println(clM2);
                  Client.os.writeObject(clM2);
                  openNewScene("/Client/AvailableFlightsWindow.fxml");
                }
                else if (str == "fail") System.out.println("no available flights");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Exit.setOnAction(event -> {
            openNewScene("/Client/LogInWindow.fxml");
        });

    }

    public void openNewScene(String window)
    {
        LetsFlyButton.getScene().getWindow().hide();

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

package Client;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TicketWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveButton;

    @FXML
    private Label inAirLab;

    @FXML
    private Label dateLab;

    @FXML
    private Label outTimeLab;

    @FXML
    private Label inTimeLab;

    @FXML
    private Label fullPriceLab;

    @FXML
    private Label ClName;

    @FXML
    private Label ClSurname;

    @FXML
    void initialize() {

        try {
            String ids = (String)Client.is.readObject();
            String[] mparts = ids.split(";");
            String idf = "getTicketInfo," + mparts[1];
            Client.os.writeObject(idf);
            String tickInfo = (String)Client.is.readObject();
            System.out.println("МЕГА ВАЖНО!!!1" + tickInfo);
            String[] mp = tickInfo.split(",");

            inAirLab.setText(mp[0]);
            dateLab.setText(mp[1]);
            outTimeLab.setText(mp[2]);
            inTimeLab.setText(mp[3]);

            AvailableFlightsWindowController.watchInf += "Город прибытия: " + mp[0] + "\nДата прибытия: " + mp[1] + "\nВремя вылета: " + mp[2] + "\n";

            String NS = "getClientName," + mparts[0];
            Client.os.writeObject(NS);
            String ns = (String)Client.is.readObject();
            String[] namesurname = ns.split(",");
            ClName.setText(namesurname[0]);
            ClSurname.setText(namesurname[1]);
            String IDs = "CalculateFullPrice," + mparts[0] + "," + mparts[1];
            Client.os.writeObject(IDs);
            String fullprice = (String)Client.is.readObject();
            AvailableFlightsWindowController.watchInf+="Цена: " + fullprice + "\n\n";

            try(FileWriter writer = new FileWriter("D://KP//Info.txt", true))
            {
                // запись всей строки

                writer.write(AvailableFlightsWindowController.watchInf);

            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
            AvailableFlightsWindowController.watchInf = "";
            fullPriceLab.setText(fullprice);
            String clm = "addInfoToTicket," + mparts[0] + "," + fullprice;
            Client.os.writeObject(clm);
            saveButton.setOnAction(event -> {
                String save = "saveTicketToFile," + tickInfo + "," + fullprice;

                try {
                    Client.os.writeObject(save);
                    String del = "deleteUserData," + mparts[0];
                    Client.os.writeObject(del);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                String clM = "sendData," + AvailableFlightsWindowController.yAAAAA;

                try {
                    Client.os.writeObject(clM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openNewScene("/Client/ClientMainWindow.fxml");
            });
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void openNewScene(String window)
    {
        saveButton.getScene().getWindow().hide();

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

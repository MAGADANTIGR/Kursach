package Client;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PieChartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart dayTimeStat;

    @FXML
    private Button naz;

    @FXML
    void initialize() {
        String clm = "getDataForPie,";
        int cm = 0;
        int cd = 0;
        int ce = 0;
        int cn = 0;
        try {
            Client.os.writeObject(clm);
            String data = (String) Client.is.readObject();
            String[] count = data.split(",");
            cm = Integer.parseInt(count[0]);
            cd = Integer.parseInt(count[1]);
            ce = Integer.parseInt(count[2]);
            cn = Integer.parseInt(count[3]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Утpо", cm),
                new PieChart.Data("День", cd),
                new PieChart.Data("Вечеp", ce),
                new PieChart.Data("Ночь", cn)
        );
        dayTimeStat.setData(pieChartData);

        naz.setOnAction(event -> {
            String clM = "sendData, admin";

            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/AdMainWin.fxml");
        });
    }

    public void openNewScene(String window)
    {
        naz.getScene().getWindow().hide();

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

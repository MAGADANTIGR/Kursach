package Client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import Server.Const;
import Server.Flight;
import Server.FlightsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdMainWinController {

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
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu pieStatDayTime;

    @FXML
    private Button addFlMenu;

    @FXML
    private Button addAdmin;

    @FXML
    private Button deleteFl;

    @FXML
    private Button deleteAdmin;

    @FXML
    private Button watchR;

    @FXML
    private TextField FirstTF;

    @FXML
    private TextField SecondTF;

    @FXML
    private TextField ThirdTf;

    @FXML
    private TextField SixthTF;

    @FXML
    private TextField FourthTF;

    @FXML
    private TextField FifthTF;

    @FXML
    private TextArea Area;

    @FXML
    private Label KeyLabel;

    @FXML
    private Button KeyButton;

    @FXML
    private Button statistics;

    @FXML
    private Label adminlabel;

    @FXML
    private Label label;

    @FXML
    private ScrollPane Prokr;

    @FXML
    private Button watchZ;

    @FXML
    private Button blockUser;

    @FXML
    private Button ex;

    @FXML
    private Button Back1;

    @FXML
    private Button redFl;

    private ObservableList<Flight> flData = FXCollections.observableArrayList();

    @FXML
    void initialize() throws IOException {


        try {
            adminlabel.setText((String) Client.is.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String str = "";
        Visibility(false, false, false, false, false, false, false, false);
        AvailableFlyTableView.setVisible(false);
        Prokr.setVisible(false);

        FileReader fr= new FileReader("D://KP//Info.txt");
        Scanner scan = new Scanner(fr);

        int i = 1;

        while (scan.hasNextLine()) {
            str+=scan.nextLine();
            str+="\n";
            i++;
        }

        fr.close();
String Ss = str;

        Back1.setVisible(false);
       addFlMenu.setOnAction(event -> {
           MainVis(false, true);
           Visibility(true, true, true, true, true, true, true, true);
           setText("Город прибытия", "Дата", "Время вылета", "Время прибытия", "Цена", "Количество мест", "Добавить", "Введите данные рейса", 203);
           KeyButton.setOnAction(event1 -> {

               String clm = "addFl," + FirstTF.getText() + "," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FifthTF.getText() + "," + SixthTF.getText();
               try {
                   Client.os.writeObject(clm);

               } catch (IOException e) {
                   e.printStackTrace();
               }
               Visibility(false, false, false, false, false, false, false, false);
               label.setLayoutX(203);
               label.setText("Рейс добавлен");
               label.setVisible(true);

           });

           Back1.setOnAction(event2 -> {
               MainVis(true, false);
               Visibility(false, false, false, false, false, false, false, false);
               label.setVisible(false);
           });

       });
       addAdmin.setOnAction(event -> {
           MainVis(false, true);
           Visibility(false, false, true, true, false, false, true, true);
           setText("", "", "Логин", "Пароль", "", "", "Добавить", "Введите данные администратора", 158);
           KeyButton.setOnAction(event1 -> {
               String clm1 = "addAdmin," + ThirdTf.getText() + "," + FourthTF.getText();
               try {
                   Client.os.writeObject(clm1);
               } catch (IOException e) {
                   e.printStackTrace();
               }

               Visibility(false, false, false, false, false, false, false, false);
               label.setLayoutX(178);
               label.setText("Администратор добавлен");
               label.setVisible(true);
           });

           Back1.setOnAction(event2 -> {
               MainVis(true, false);
               Visibility(false, false, false, false, false, false, false, false);
               label.setVisible(false);
           });
       });

       watchR.setOnAction(event -> {
            MainVis(false, true);
            Visibility(false, false, false, false, false, false, false, true);
           setText("", "", "", "", "", "", "", "Просмотр рейсов", 203);

           AvailableFlyTableView.setVisible(true);



           try {


               String clm = "allFl, 1";
               System.out.println("на окне полетов" + clm);
               Client.os.writeObject(clm);
               String data = (String)Client.is.readObject();
               System.out.println("на окне полетов после гетфлайтс" + data);
               //String[] count = id.split(",");
               String[] messParts = data.split(";");
               int count = Integer.parseInt(messParts[0]);
               System.out.println("КАУНТ " + count);
               System.out.println(messParts[1]);
               for (int j = 2; j < count + 2; j++)
               {
                   String[] mesParts = messParts[j].split(",");
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


            Back1.setOnAction(event2 -> {
                MainVis(true, false);
                Visibility(false, false, false, false, false, false, false, false);
                label.setVisible(false);
                AvailableFlyTableView.setVisible(false);
            });
        });

       deleteFl.setOnAction(event -> {
           MainVis(false, true);
           Visibility(false, true, true, true, false, false, true, true);
           setText("", "Город прибытия", "Дата", "Время вылета", "", "", "Удалить", "Введите данные рейса", 203);
           KeyButton.setOnAction(event1 -> {
               String clm2 = "deleteFl," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText();
               try {
                   Client.os.writeObject(clm2);
               } catch (IOException e) {
                   e.printStackTrace();
               }

               Visibility(false, false, false, false, false, false, false, false);
               label.setText("Рейс удален");
               label.setLayoutX(208);
               label.setVisible(true);
           });

           Back1.setOnAction(event2 -> {
               MainVis(true, false);
               Visibility(false, false, false, false, false, false, false, false);
               label.setVisible(false);
           });
       });
       deleteAdmin.setOnAction(event -> {
           MainVis(false, true);
           Visibility(false, false, true, false, false, false, true, true);
           setText("", "", "Логин", "", "", "", "Удалить", "Введите логин администратора", 158);
           KeyButton.setOnAction(event1 -> {
               String clm3 = "deleteAdmin," + ThirdTf.getText();
               try {
                   Client.os.writeObject(clm3);
               } catch (IOException e) {
                   e.printStackTrace();
               }

               Visibility(false, false, false, false, false, false, false, false);
               label.setText("Администратор удален");
               label.setLayoutX(178);
               label.setVisible(true);
           });

           Back1.setOnAction(event2 -> {
               MainVis(true, false);
               Visibility(false, false, false, false, false, false, false, false);
               label.setVisible(false);
           });
       });
       blockUser.setOnAction(event -> {
           MainVis(false, true);
           Visibility(false, false, true, false, false, false, true, true);
           setText("", "", "Логин", "", "", "", "Заблокиpовать", "Введите логин пользователя", 188);
           KeyButton.setOnAction(event1 -> {
               String clm4 = "blockUser," + ThirdTf.getText();
               try {
                   Client.os.writeObject(clm4);
               } catch (IOException e) {
                   e.printStackTrace();
               }

               Visibility(false, false, false, false, false, false, false, false);
               label.setText("Пользователь заблокирован");
               label.setLayoutX(178);
               label.setVisible(true);
           });

           Back1.setOnAction(event2 -> {
               MainVis(true, false);
               Visibility(false, false, false, false, false, false, false, false);
               label.setVisible(false);
           });

       });

       ex.setOnAction(event -> {
           openNewScene("/Client/LogInWindow.fxml");
        });

       statistics.setOnAction(event -> {
           openNewScene("/Client/PieChart.fxml");
       });

       redFl.setOnAction(event -> {
           MainVis(false, true);
           Visibility(false, true, true, true, false, false, true, true);
           setText("", "Город прибытия", "Дата", "Время вылета", "", "", "Изменить", "Введите данные рейса, который нужно изменить", 93);
           KeyButton.setOnAction(event1 -> {
              addFlMenu.setVisible(true);
              addAdmin.setVisible(true);
              deleteFl.setVisible(true);
              deleteAdmin.setVisible(true);
              blockUser.setVisible(true);
              redFl.setVisible(true);
              ex.setVisible(false);
              Back1.setVisible(true);

               addFlMenu.setText("Город прибытия");
               addAdmin.setText("Дата");
               deleteFl.setText("Время вылета");
               deleteAdmin.setText("Время прибытия");
               blockUser.setText("Цена");
               redFl.setText("Количество мест");

               Visibility(false, false, false, false, false, false, false, true);
               setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);

               addFlMenu.setOnAction(event3 -> {
                   MainVis(false, true);
                   Visibility(true, false, false, false, false, false, true, true);
                   setText("Город", "", "", "", "", "", "Изменить", "Введите новое название города прибытия", 125);

                   KeyButton.setOnAction(event4 -> {
                       String clm2 = "redFlAir," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FirstTF.getText() + "," + Const.MAINDATA_AIRPORT;
                       try {
                           Client.os.writeObject(clm2);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Visibility(false, false, false, false, false, false, false, false);
                       label.setText("Рейс изменен");
                       label.setLayoutX(208);
                       label.setVisible(true);
                   });

                   Back1.setOnAction(event31 -> {
                       Visibility(false, false, false, false, false, false, false, true);
                       setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);
                       addFlMenu.setVisible(true);
                       addAdmin.setVisible(true);
                       deleteFl.setVisible(true);
                       deleteAdmin.setVisible(true);
                       blockUser.setVisible(true);
                       redFl.setVisible(true);
                       ex.setVisible(false);
                       Back1.setVisible(true);
                       addFlMenu.setText("Город прибытия");
                       addAdmin.setText("Дата");
                       deleteFl.setText("Время вылета");
                       deleteAdmin.setText("Время прибытия");
                       blockUser.setText("Цена");
                       redFl.setText("Количество мест");
                       label.setVisible(false);

                   });
               });

               addAdmin.setOnAction(event5 -> {
                   MainVis(false, true);
                   Visibility(true, false, false, false, false, false, true, true);
                   setText("Дата", "", "", "", "", "", "Изменить", "Введите новую дату прибытия", 160);

                   KeyButton.setOnAction(event6 -> {
                       String clm2 = "redFlDate," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FirstTF.getText();
                       try {
                           Client.os.writeObject(clm2);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Visibility(false, false, false, false, false, false, false, false);
                       label.setText("Рейс изменен");
                       label.setLayoutX(208);
                       label.setVisible(true);
                   });

                   Back1.setOnAction(event51 -> {
                       addFlMenu.setVisible(true);
                       addAdmin.setVisible(true);
                       deleteFl.setVisible(true);
                       deleteAdmin.setVisible(true);
                       blockUser.setVisible(true);
                       redFl.setVisible(true);
                       ex.setVisible(false);
                       Back1.setVisible(true);

                       addFlMenu.setText("Город прибытия");
                       addAdmin.setText("Дата");
                       deleteFl.setText("Время вылета");
                       deleteAdmin.setText("Время прибытия");
                       blockUser.setText("Цена");
                       redFl.setText("Количество мест");
                       Visibility(false, false, false, false, false, false, false, true);
                       setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);
                       label.setVisible(false);
                   });
               });

               deleteAdmin.setOnAction(event7 -> {
                   MainVis(false, true);
                   Visibility(true, false, false, false, false, false, true, true);
                   setText("Время прибытия", "", "", "", "", "", "Изменить", "Введите новое время прибытия", 150);

                   KeyButton.setOnAction(event8 -> {
                       String clm2 = "redFlIn," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FirstTF.getText();
                       try {
                           Client.os.writeObject(clm2);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Visibility(false, false, false, false, false, false, false, false);
                       label.setText("Рейс изменен");
                       label.setLayoutX(208);
                       label.setVisible(true);
                   });

                   Back1.setOnAction(event71 -> {
                       addFlMenu.setVisible(true);
                       addAdmin.setVisible(true);
                       deleteFl.setVisible(true);
                       deleteAdmin.setVisible(true);
                       blockUser.setVisible(true);
                       redFl.setVisible(true);
                       ex.setVisible(false);
                       Back1.setVisible(true);

                       addFlMenu.setText("Город прибытия");
                       addAdmin.setText("Дата");
                       deleteFl.setText("Время вылета");
                       deleteAdmin.setText("Время прибытия");
                       blockUser.setText("Цена");
                       redFl.setText("Количество мест");
                       Visibility(false, false, false, false, false, false, false, true);
                       setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);
                       label.setVisible(false);
                   });
               });

               deleteFl.setOnAction(event9 -> {
                   MainVis(false, true);
                   Visibility(true, false, false, false, false, false, true, true);
                   setText("Время вылета", "", "", "", "", "", "Изменить", "Введите новое время вылета", 165);

                   KeyButton.setOnAction(event10 -> {
                       String clm2 = "redFlOut," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FirstTF.getText();
                       try {
                           Client.os.writeObject(clm2);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Visibility(false, false, false, false, false, false, false, false);
                       label.setText("Рейс изменен");
                       label.setLayoutX(208);
                       label.setVisible(true);
                   });

                   Back1.setOnAction(event91 -> {
                       addFlMenu.setVisible(true);
                       addAdmin.setVisible(true);
                       deleteFl.setVisible(true);
                       deleteAdmin.setVisible(true);
                       blockUser.setVisible(true);
                       redFl.setVisible(true);
                       ex.setVisible(false);
                       Back1.setVisible(true);

                       addFlMenu.setText("Город прибытия");
                       addAdmin.setText("Дата");
                       deleteFl.setText("Время вылета");
                       deleteAdmin.setText("Время прибытия");
                       blockUser.setText("Цена");
                       redFl.setText("Количество мест");
                       Visibility(false, false, false, false, false, false, false, true);
                       setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);
                       label.setVisible(false);
                   });
               });

               blockUser.setOnAction(event10 -> {
                   MainVis(false, true);
                   Visibility(true, false, false, false, false, false, true, true);
                   setText("Цена", "", "", "", "", "", "Изменить", "Введите новую цену билета", 160);

                   KeyButton.setOnAction(event11 -> {
                       String clm2 = "redFlCost," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FirstTF.getText();
                       try {
                           Client.os.writeObject(clm2);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Visibility(false, false, false, false, false, false, false, false);
                       label.setText("Рейс изменен");
                       label.setLayoutX(208);
                       label.setVisible(true);
                   });

                   Back1.setOnAction(event12 -> {
                       addFlMenu.setVisible(true);
                       addAdmin.setVisible(true);
                       deleteFl.setVisible(true);
                       deleteAdmin.setVisible(true);
                       blockUser.setVisible(true);
                       redFl.setVisible(true);
                       ex.setVisible(false);
                       Back1.setVisible(true);

                       addFlMenu.setText("Город прибытия");
                       addAdmin.setText("Дата");
                       deleteFl.setText("Время вылета");
                       deleteAdmin.setText("Время прибытия");
                       blockUser.setText("Цена");
                       redFl.setText("Количество мест");
                       Visibility(false, false, false, false, false, false, false, true);
                       setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);
                       label.setVisible(false);
                   });
               });

               redFl.setOnAction(event13 -> {
                   MainVis(false, true);
                   Visibility(true, false, false, false, false, false, true, true);
                   setText("Количество мест", "", "", "", "", "", "Изменить", "Введите новое колиество мест", 150);

                   KeyButton.setOnAction(event14 -> {
                       String clm2 = "redFlAmSeet," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FirstTF.getText();
                       try {
                           Client.os.writeObject(clm2);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Visibility(false, false, false, false, false, false, false, false);
                       label.setText("Рейс изменен");
                       label.setLayoutX(208);
                       label.setVisible(true);
                   });

                   Back1.setOnAction(event91 -> {
                       addFlMenu.setVisible(true);
                       addAdmin.setVisible(true);
                       deleteFl.setVisible(true);
                       deleteAdmin.setVisible(true);
                       blockUser.setVisible(true);
                       redFl.setVisible(true);
                       ex.setVisible(false);
                       Back1.setVisible(true);

                       addFlMenu.setText("Город прибытия");
                       addAdmin.setText("Дата");
                       deleteFl.setText("Время вылета");
                       deleteAdmin.setText("Время прибытия");
                       blockUser.setText("Цена");
                       redFl.setText("Количество мест");
                       Visibility(false, false, false, false, false, false, false, true);
                       setText("", "", "", "", "", "", "", "Выберите, что нужно изменить", 173);
                       label.setVisible(false);
                   });
               });

               Back1.setOnAction(event99 -> {

                   String clM = "sendData, admin";

                   try {
                       Client.os.writeObject(clM);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   openNewScene("/Client/AdMainWin.fxml");
                   MainVis(true, false);
                   addFlMenu.setText("Добавить рейс");
                   addAdmin.setText("Добавить администратора");
                   deleteFl.setText("Удалить рейс");
                   deleteAdmin.setText("Удалить администратора");
                   blockUser.setText("Заблокировать пользователя");
                   redFl.setText("Изменить рейс");
                   Visibility(false, false, false, false, false, false, false, false);
                   label.setVisible(false);
               });

           });

            Back1.setOnAction(event2 -> {
                String clM = "sendData, admin";

                try {
                    Client.os.writeObject(clM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openNewScene("/Client/AdMainWin.fxml");
                MainVis(true, false);
                addFlMenu.setText("Добавить рейс");
                addAdmin.setText("Добавить администратора");
                deleteFl.setText("Удалить рейс");
                deleteAdmin.setText("Удалить администратора");
                blockUser.setText("Заблокировать пользователя");
                redFl.setText("Изменить рейс");
                Visibility(false, false, false, false, false, false, false, false);
                label.setVisible(false);
            });

        });

        watchZ.setOnAction(event -> {
            MainVis(false, true);
            Visibility(false, false, false, false, false, false, false, true);
            setText("", "", "", "", "", "", "", "Список купленных билетов", 188);
            Area.setText(Ss);
            Prokr.setVisible(true);
            Area.setVisible(true);

            Back1.setOnAction(event2 -> {
                MainVis(true, false);
                Prokr.setVisible(false);
                Visibility(false, false, false, false, false, false, false, false);
                label.setVisible(false);

            });

        });
    }

    public void Visibility (Boolean one, Boolean two, Boolean three, Boolean four, Boolean five, Boolean six, Boolean butt, Boolean lab)
    {
        SixthTF.setVisible(six);
        FifthTF.setVisible(five);
        FourthTF.setVisible(four);
        ThirdTf.setVisible(three);
        SecondTF.setVisible(two);
        FirstTF.setVisible(one);
        KeyButton.setVisible(butt);
        KeyLabel.setVisible(lab);
    }

    public void setText(String one, String two, String three, String four, String five, String six, String butt, String lab, int x)
    {
        FirstTF.setPromptText(one);
        SecondTF.setPromptText(two);
        ThirdTf.setPromptText(three);
        FourthTF.setPromptText(four);
        FifthTF.setPromptText(five);
        SixthTF.setPromptText(six);
        KeyButton.setText(butt);
        KeyLabel.setText(lab);
        KeyLabel.setLayoutX(x);
    }

    public void MainVis(Boolean b, Boolean t){
        addFlMenu.setVisible(b);
        addAdmin.setVisible(b);
        deleteFl.setVisible(b);
        deleteAdmin.setVisible(b);
        blockUser.setVisible(b);
        statistics.setVisible(b);
        redFl.setVisible(b);
        watchZ.setVisible(b);
        watchR.setVisible(b);
        ex.setVisible(b);
        Back1.setVisible(t);
    }

    public void openNewScene(String window)
    {
        statistics.getScene().getWindow().hide();

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

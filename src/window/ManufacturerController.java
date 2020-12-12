package window;

import car.Cardata;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ManufacturerController extends Thread implements Initializable {

    public Label addCarsLabel;
    ObservableList<Cardata> cars= FXCollections.observableArrayList();
    TableView<Cardata> table=new TableView<>();

    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    private JSONArray data;
    private Socket socket;
    MouseEvent ev;
   TableController ctrl;

    public void connectSocket()  {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println(" manufacturer Socket is connected with server!");
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream((socket.getOutputStream()));
            this.start();
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                Object msg= in.readUnshared();
                if(msg==null)break;
                if(msg instanceof JSONArray)
                    data=(JSONArray) msg;

            }

        } catch (Exception e) {

        }
        finally{
            try {
                out.close();
                in.close();

            } catch (IOException e) {

            }

        }
    }


    public void getData(ActionEvent actionEvent) {
        System.out.println("gotData");
    }

public void getCarsData(){
    JSONObject jobject=new JSONObject();
    jobject.put("task","getCars");
    try {
        out.writeUnshared(jobject);
        Thread.sleep(2000);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {

    }
}
    public void getCars(MouseEvent mouseEvent) throws IOException {

ev=mouseEvent;



        Stage s=new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("All Cars Data ");
getCarsData();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tableView.fxml"));
        Parent root = loader.load();
        TableController controller=loader.getController();
        controller.setJarray(data);
        controller.setStream(out);
        ctrl=controller;
controller.setManu(this);


      /*
      data.forEach(d->{
            JSONObject job=(JSONObject)d;
            Cardata car=new Cardata(
                    (String)job.get("regNo"),(String)job.get("yearMade"),
                    (String)job.get("colour1"),(String)job.get("colour2"),
                    (String)job.get("colour3"),(int)job.get("quantity"),
                    (String)job.get("price"),(String)job.get("carMake"),
                    (String)job.get("carModel")

            );
            car.setCarMake((String)job.get("carMake"));
            car.setCarModel((String)job.get("carModel"));
            car.setColor1((String)job.get("colour1"));
            car.setColor2((String)job.get("colour2"));
            car.setColor3((String)job.get("colour3"));
            car.setPrice((String)job.get("price"));
            car.setQuantity((int)job.get("quantity"));
            car.setRegNo((String)job.get("regNo"));
            car.setYearMade((String)job.get("yearMade"));
            cars.add(car);
        });*/



        s.setScene(new Scene(root,900,500));

        System.out.println(data);
        s.showAndWait();
    }
public void sendJarray(){
    System.out.println(data);
        ctrl.setJarray(data);
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
connectSocket();
    }

    public void addCarAction(MouseEvent mouseEvent) throws IOException {
        Stage s=new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addingCars.fxml"));
        Parent root = loader.load();
        AddingCarsController controller=loader.getController();
        controller.setStream(out);


        s.setScene(new Scene(root,707,707));
        s.showAndWait();



    }
}

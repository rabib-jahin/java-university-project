package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController extends Thread implements Initializable {


    public TextField name;
    public TextField pass;
    public TextField confirmPass;
    private Socket socket;
    private boolean data;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;


    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println("Admin Socket is connected with server!");
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
                Boolean msg= (Boolean)in.readUnshared();
                if(msg==null)break;
data=msg;



   }

        } catch (Exception e) {

        }
        finally{
            try {
in.close();
out.close();
socket.close();
            } catch (IOException e) {

            }

        }
    }




    public void signInAction(ActionEvent actionEvent) {

if(!name.getText().equals("")&&pass.getText().equals(confirmPass.getText())){
    System.out.println("entered");
    JSONObject j=new JSONObject();
    j.put("userName", name.getText());
    j.put("password",pass.getText());
    j.put("task","createUser");
    try {
        out.writeUnshared(j);

    } catch (IOException e) {
        System.out.println(e);
    }
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println(data);


}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectSocket();
    }

    public void homeAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) name.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/home/home.fxml"));
        Parent root = loader.load();




        // Set the primary stage
        stage.setTitle("Home");
        stage.setScene(new Scene(root, 514, 423));

    }
}

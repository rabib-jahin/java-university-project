package login;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import toastMessage.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ManufacturerLoginController extends Thread  implements Initializable  {
    @FXML
    private TextField userName,password;
    private Button login;
    @FXML
    private Label label;
    @FXML
    private Label errorMsg;
    private Main main;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private Stage stage;
private boolean data;
    private Socket socket;

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println("Socket is connected with server!");

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




if(msg instanceof JSONArray){
    System.out.println("let it go");
}
else if( msg instanceof Boolean){
    data=(boolean)msg;
}

                System.out.println(data);    }

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

    public void loginAction(ActionEvent actionEvent) {


        String user=userName.getText();
        String pass=password.getText();
        if(label.getText().equals("Manufacturer Login")){
        JSONObject job=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        job.put("username",user);
        job.put("password",pass);
        job.put("task","login");


        try {
            out.writeUnshared(job);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
           // e.printStackTrace();
        }
if(data){

        try {
            showManufacturerWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        else{

            Toast.makeText(stage,"Wrong credentials",2000,500,2000);

        }

        }
    else if(label.getText().equals("Admin Login")){

   if(userName.getText().equals("admin")&& password.getText().equals("admin")) {
       try {
           showAdminWindow();
       } catch (IOException e) {

       }
   }else {

       Toast.makeText(stage,"Wrong credentials",2000,500,2000);

   }
}

       /*
       if(data!=null){ data.forEach(d->{
            JSONObject t=(JSONObject)d;
            String tempUser=(String)t.get("username");
            String tempPass=(String)t.get("password");
            if(user.equals(tempUser)&&pass.equals(tempPass)) {
                if (label.getText().equals("Manufacturer Login")) {
                    try {
                        showManufacturerWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    showViewerWindow();
                }
            }

        });}/*
        errorMsg.setText("Wrong Id or password given.Please try agin!");

/*        String[] s=data.split("\n");
        for(String t:s){

            if(user.equals(t.split(",")[0]) && pass.equals(t.split(",")[1])){
                System.out.println("success");
                if(label.getText().equals("Manufacturer Login")){
                    try {
                        showManufacturerWindow();
                    } catch (IOException e) {

                    }
                }
                else {
                    showViewerWindow();
                }
            }
 Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM USERS");
            while(rs.next()){
                System.out.println("name :"+rs.getString("username"));
                System.out.println("password "+rs.getString("password"));
            }
        }
// Stage stage = (Stage) userName.getScene().getWindow();
*/


    }

    public void checkCredentials(String data){
        String user=userName.getText();
        String pass=password.getText();
        String[] s=data.split("\n");
        for(String t:s){

            if(user.equals(t.split(",")[0]) && pass.equals(t.split(",")[1])){
                System.out.println("success");
                if(label.getText().equals("Manufacturer Login")){
                    try {
                        showManufacturerWindow();
                    } catch (IOException e) {

                    }
                }
                else if(label.getText().equals("Admin login")){
                    try {
                        showAdminWindow();
                    } catch (IOException e) {

                    }
                }
            }
        }

    }
public void setStage(Stage stage){this.stage=stage;}

    private void showManufacturerWindow() throws IOException {
        System.out.println("gg");
       // Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent root=  FXMLLoader.load(getClass().getResource("/window/Manufacturer.fxml"));

      //  ManufacturerLoginController controller = loader.getController();



        // Set the primary stage
        stage.setTitle("Manufacturer Window");
        stage.setScene(new Scene(root, 514, 423));
        stage.show();

    }

    private void showViewerWindow() {
    }

    private void showAdminWindow() throws IOException {

        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/signup.fxml"));
        Parent root = loader.load();




        // Set the primary stage
        stage.setTitle("Create User");
        stage.setScene(new Scene(root, 514, 423));

    }

    public void setMain(Main main){
        this.main=main;
    }

    public void setLabel(String text) {
        label.setText(text);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
connectSocket();
    }

    public void homeAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/home/home.fxml"));
        Parent root = loader.load();




        // Set the primary stage
        stage.setTitle("Home");
        stage.setScene(new Scene(root, 514, 423));

    }
}

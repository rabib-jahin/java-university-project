package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import login.ManufacturerLoginController;

public class HomeController {
    private Main main;
    @FXML
    private RadioButton manufacturer, viewer,admin;

    @FXML
    private Button goButton;

    public void setMain(Main main) {
        this.main = main;
    }

    public void buttonPressed(ActionEvent actionEvent) throws Exception {
        if (manufacturer.isSelected()) {
            showLoginPage("Manufacturer Login");

        } else if (viewer.isSelected()) {
            showLoginPage("Viewer Login");

        }
        else if(admin.isSelected()){
            showLoginPage("Admin Login");
        }
    }

    public void showLoginPage(String text) throws Exception {
        // XML Loading using FXMLLoader
        Stage stage = (Stage) manufacturer.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login/ManufacturerLogin.fxml"));
        Parent root = loader.load();
        ManufacturerLoginController controller = loader.getController();
        controller.setLabel(text);
        controller.setStage(stage);


        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 514, 423));
        //stage.show();
    }

    public void signUpAction(ActionEvent actionEvent) {


    }
}
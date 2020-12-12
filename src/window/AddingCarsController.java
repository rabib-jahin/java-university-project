package window;

import car.Cardata;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import login.ManufacturerLoginController;
import org.json.simple.JSONObject;
import toastMessage.Toast;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddingCarsController  {
    public AnchorPane anchor;
    public Button btn;
    public Label label;
    ObjectOutputStream out;

    @FXML
    private TextField regNo;

    @FXML
    private TextField yearMade;

    @FXML
    private TextField carMake;

    @FXML
    private TextField carModel;

    @FXML
    private TextField quantity;

    @FXML
    private TextField price;

    @FXML
    private ColorPicker colour_1;

    @FXML
    private ColorPicker colour_2;

    @FXML
    private ColorPicker colour_3;

    private File file;
    boolean edit=false;
    Cardata car;
    TableController t;
    Stage s;

    public void setStream(ObjectOutputStream out){
        this.out=out;
    }

    @FXML
    void colour1Action(ActionEvent event) {

    }

    @FXML
    void colour2Action(ActionEvent event) {

    }

    @FXML
    void colour3Action(ActionEvent event) {

    }

    @FXML
    void resetAction(ActionEvent event) {
        regNo.setText("");
        yearMade.setText("");
        price.setText("");
        quantity.setText("");
        carMake.setText("");
        carModel.setText("");


    }
    public void setCar(Cardata c){
        car=c;
    }
public void setEdit(boolean b){
        edit=b;
}

    public void submitAction(ActionEvent actionEvent) throws MalformedURLException {

        String regno=regNo.getText();
        String yearmade=yearMade.getText();
        String cmake=carMake.getText();
        String cmodel=carModel.getText();
        String pr=price.getText();
        String qn=quantity.getText();
        String c1=colour_1.getValue().toString();
        String c2=colour_2.getValue().toString();
        String c3=colour_3.getValue().toString();
        if(regno.equals("")||yearmade.equals("")||cmake.equals("")||price.equals("")||quantity.equals("")){
            Toast.makeText((Stage)price.getScene().getWindow(),"Every field must be filled",2000,500,2000);


        }
            else {

            System.out.println("action");
            JSONObject job = new JSONObject();
            job.put("regNo", regno);
            job.put("yearMade",yearmade);
            job.put("carMake",cmake);
            job.put("carModel",cmodel);
            job.put("price",pr);
            job.put("quantity",Integer.parseInt(qn));
            job.put("color_1",c1);
            job.put("color_2",c2);
            job.put("color_3",c3);
            if(!edit){
                System.out.println("addcars");
            job.put("task","addCars");}
            else {
                job.put("task", "editCar");
job.put("reg",car.getRegno());
            }
          if(file!=null) {

              job.put("image", (String)file.getAbsolutePath());
              System.out.println("not null:"+file.getAbsolutePath());
          }
          else {
              System.out.println("null");
              job.put("image", "No image");
          }

            try {
                out.writeUnshared(job);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void imageAction(ActionEvent actionEvent) {

        FileChooser fc=new FileChooser();
        fc.setTitle("Select an image");
        Stage stage= (Stage) regNo.getScene().getWindow();
         file=fc.showOpenDialog(stage);
        if(file !=null){
            System.out.println(file.getAbsolutePath());
            try {
               Image img=new Image(file.toURI().toURL().toExternalForm());


 ImageView d =new ImageView(img);
d.setFitHeight(150);
d.setFitWidth(200);
d.setLayoutX(443);
d.setLayoutY(183);
anchor.getChildren().add(d);


            } catch (MalformedURLException e) {

            }



        }


    }


    public void initialize() throws MalformedURLException {
        System.out.println(edit);

        if(edit) {
            regNo.setText(car.getRegno());
            yearMade.setText(car.getYearmade());
            price.setText(car.getPrice());
            quantity.setText(String.valueOf(car.getQuantity()));
            carMake.setText(car.getCarMake());
            carModel.setText(car.getCarModel());
            colour_1.setValue(Color.valueOf(car.getColor1()));
            colour_2.setValue(Color.valueOf(car.getColor2()));
            colour_3.setValue(Color.valueOf(car.getColor3()));
            btn.setText("Edit Now");
            label.setText("Edit car data");
            if (car.getImagePath().equals("No image")) {

            } else {
                File file2 = new File(car.getImagePath());
                file = file2;
                Image img = new Image(file.toURI().toURL().toExternalForm());


                ImageView d = new ImageView(img);
                d.setFitHeight(150);
                d.setFitWidth(200);
                d.setLayoutX(443);
                d.setLayoutY(183);

                anchor.getChildren().add(d);
                System.out.println(car.getImagePath());
            }
        }
    }


}

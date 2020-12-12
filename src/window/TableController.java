package window;

import car.Cardata;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class TableController {


    @FXML
    TableView<Cardata>table;
    @FXML
    TableColumn<Cardata,String>imagePath;
    @FXML
    TableColumn<Cardata,String> regNo;
    @FXML
    TableColumn<Cardata,String> yearMade;
    @FXML
    TableColumn<Cardata,String> colour_1;
    @FXML
    TableColumn<Cardata,String> colour_2;
    @FXML
    TableColumn<Cardata,String> colour_3;
    @FXML
    TableColumn<Cardata,Integer> quantity;
    @FXML
    TableColumn<Cardata,String> price;
    @FXML
    TableColumn<Cardata,String> carMake;
    @FXML
    TableColumn<Cardata,String> carModel;
    ObjectOutputStream out;
    JSONArray jarray=new JSONArray();
    ManufacturerController manu;

    public ObservableList<Cardata> getCars(){
        ObservableList<Cardata> ob= FXCollections.observableArrayList();
       if(jarray!=null) {
           jarray.forEach(d -> {
               JSONObject job = (JSONObject) d;
               Cardata car = new Cardata((String) job.get("regNo"), (String) job.get("yearMade"), (String) job.get("colour1"),
                       (String) job.get("colour2"), (String) job.get("colour3"), (int) job.get("quantity"), (String) job.get("price"),
                       (String) job.get("carMake"), (String) job.get("carModel"), (String) job.get("imagePath")
               );
               ob.add(car);
           });
       }
return ob;
    }
public void setManu(ManufacturerController m){
        manu=m;
}
    public void setJarray(JSONArray jarray) {
        this.jarray = jarray;
        init();

    }


    public void init() {
        regNo.setCellValueFactory(new PropertyValueFactory<Cardata,String>("regno"));
        yearMade.setCellValueFactory(new PropertyValueFactory<Cardata,String>("yearmade"));
        colour_1.setCellValueFactory(new PropertyValueFactory<Cardata,String>("color1"));
        colour_2.setCellValueFactory(new PropertyValueFactory<Cardata,String>("color2"));
        colour_3.setCellValueFactory(new PropertyValueFactory<Cardata,String>("color3"));
        quantity.setCellValueFactory(new PropertyValueFactory<Cardata,Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Cardata,String>("price"));
        carMake.setCellValueFactory(new PropertyValueFactory<Cardata,String>("carMake"));
        carModel.setCellValueFactory(new PropertyValueFactory<Cardata,String>("carModel"));
       imagePath.setCellValueFactory(new PropertyValueFactory<Cardata,String>("imagePath"));
        table.setItems(getCars());



    }

    public void getSelectedRowAction(MouseEvent mouseEvent) throws IOException {

        if(table.getSelectionModel().getSelectedItem()!=null){

            Cardata c=table.getSelectionModel().getSelectedItem();
      Stage s=new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addingCars.fxml"));
            Parent root = loader.load();
            AddingCarsController controller=loader.getController();
            controller.setStream(out);
controller.setEdit(true);
controller.setCar(c);

controller.initialize();
            s.setScene(new Scene(root,707,707));
            s.showAndWait();

        }
    }


    public void setStream(ObjectOutputStream out) {
        this.out=out;
    }

    public void refreshAction(ActionEvent actionEvent) {

       // Stage s=(Stage)  table.getScene().getWindow();

manu.getCarsData();
manu.sendJarray();




    }
}

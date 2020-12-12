package car;

import javafx.beans.property.SimpleStringProperty;

import java.io.File;

public class Cardata {
     public String regno;
    public  String yearmade;
    public String color1,color2,color3;
    public int quantity;
    public  String price;
    public  String carMake;
    public String carModel;
    public String imagePath;

    public Cardata(String regNo, String yearMade, String color1, String color2, String color3, int quantity, String price, String carMake, String carModel,String imagePath) {
        this.regno = (regNo);
        this.yearmade = (yearMade);
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.quantity = quantity;
        this.price = price;
        this.carMake = carMake;
        this.carModel = carModel;
        this.imagePath=imagePath;
    }
public String getImagePath(){
        return imagePath;
}
    public String getRegno() {
        return regno;
    }


    public String getYearmade() {
        return yearmade;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }
}

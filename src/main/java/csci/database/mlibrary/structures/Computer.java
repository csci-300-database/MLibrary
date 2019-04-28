package csci.database.mlibrary.structures;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

public class Computer extends RecursiveTreeObject<Computer> {

       StringProperty computerId;
       StringProperty type;
       StringProperty available;

       public Computer(int computerId, String type, boolean available) {
           this.computerId = new SimpleStringProperty(String.valueOf(computerId));
           this.type = new SimpleStringProperty(type);
           if (available) {
               this.available = new SimpleStringProperty("Yes");
           } else {
               this.available = new SimpleStringProperty("No");
           }
       }

}
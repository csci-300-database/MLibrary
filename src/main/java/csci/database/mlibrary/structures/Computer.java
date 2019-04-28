package csci.database.mlibrary.structures;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

public class Computer extends RecursiveTreeObject<Computer> {

       IntegerProperty computerId;
       StringProperty type;
       BooleanProperty available;

       public Computer(int computerId, String type, boolean available) {
           this.computerId = new SimpleIntegerProperty(computerId);
           this.type = new SimpleStringProperty(type);
           this.available = new SimpleBooleanProperty(available);
       }

}
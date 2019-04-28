package csci.database.mlibrary.structures;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;

public class Tutor extends RecursiveTreeObject<Tutor> {

    public StringProperty staffName;
    public StringProperty tutorNo;
    public StringProperty subject;
    public StringProperty startAvailability;
    public StringProperty endAvailability;
    public StringProperty present;

    public Tutor(String staffName, int tutorNo, String subject, Time startAvailability,
                 Time endAvailability, boolean present) {
        this.staffName = new SimpleStringProperty(staffName);
        this.tutorNo = new SimpleStringProperty(String.valueOf(tutorNo));
        this.subject = new SimpleStringProperty(subject);
        this.startAvailability = new SimpleStringProperty(startAvailability.toString());
        this.endAvailability = new SimpleStringProperty(endAvailability.toString());
        if (present) {
            this.present = new SimpleStringProperty("Present");
        } else {
            this.present = new SimpleStringProperty("Absent");
        }
    }

}
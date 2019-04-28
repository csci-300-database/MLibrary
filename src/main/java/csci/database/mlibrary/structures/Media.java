package csci.database.mlibrary.structures;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Media extends RecursiveTreeObject<Media> {
    public StringProperty mediaId;
    public StringProperty title;
    public StringProperty publisher;
    public StringProperty genre;
    public StringProperty isle;

    public Media(int mediaId,String title,String publisher,String genre,int isle)
    {
        this.mediaId=new SimpleStringProperty(String.valueOf(mediaId));
        this.title=new SimpleStringProperty(title);
        this.publisher=new SimpleStringProperty(publisher);
        this.genre=new SimpleStringProperty(genre);
        this.isle=new SimpleStringProperty(String.valueOf(isle));
    }

}

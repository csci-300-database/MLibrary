package csci.database.mlibrary.structures;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TextBook extends RecursiveTreeObject<TextBook> {
    public StringProperty textId;
    public StringProperty title;
    public StringProperty author;
    public StringProperty ISBN;
    public StringProperty subject;
    public StringProperty edition;
    public StringProperty isle;

    public TextBook(int textId, String title,String author,String ISBN,String subject,String edition,int isle)
    {
        this.textId= new SimpleStringProperty(String.valueOf(textId));
        this.title=new SimpleStringProperty(title);
        this.author=new SimpleStringProperty(author);
        this.ISBN=new SimpleStringProperty(ISBN);
        this.subject=new SimpleStringProperty(subject);
        this.edition=new SimpleStringProperty(edition);
        this.isle=new SimpleStringProperty(String.valueOf(isle));
    }

}

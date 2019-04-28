package csci.database.mlibrary.structures;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book extends RecursiveTreeObject<Book> {

    public StringProperty bookId;
    public StringProperty title;
    public StringProperty author;
    public StringProperty ISBN;
    public StringProperty genre;
    public StringProperty isle;

    public Book(int bookId, String title, String author, String ISBN, String genre, int isle) {
        this.bookId = new SimpleStringProperty(String.valueOf(bookId));
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.ISBN = new SimpleStringProperty(ISBN);
        this.genre = new SimpleStringProperty(genre);
        this.isle = new SimpleStringProperty(String.valueOf(isle));
    }

}
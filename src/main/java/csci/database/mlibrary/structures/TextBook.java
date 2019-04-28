package csci.database.mlibrary.structures;

public class TextBook {
    private int textId;
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private int isle;

    public TextBook(int textId, String title,String author,String ISBN,String genre,int isle)
    {
        this.textId=textId;
        this.title=title;
        this.author=author;
        this.ISBN=ISBN;
        this.genre=genre;
        this.isle=isle;
    }

}

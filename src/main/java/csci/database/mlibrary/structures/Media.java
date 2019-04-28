package csci.database.mlibrary.structures;

public class Media {
    private int mediaId;
    private String title;
    private String publisher;
    private String genre;
    private String edition;
    private int isle;
    
    public Media(int mediaId,String title,String publisher,String genre, String edition,int isle)
    {
        this.mediaId=mediaId;
        this.title=title;
        this.publisher=publisher;
        this.genre=genre;
        this.edition=edition;
        this.isle=isle;
    }

}

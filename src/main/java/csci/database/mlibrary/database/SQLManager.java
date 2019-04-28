package csci.database.mlibrary.database;

import csci.database.mlibrary.structures.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    private static final String DATABASE_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mariadb://azelabs.net/MLibrary";
    private static final String DATABASE_USER = "azewilous";
    private static final String DATABASE_PASSWORD = "Lucariza";

    private Connection conn;

    public SQLManager() {
        try {
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        DriverManager.setLoginTimeout(5);
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Connection successful.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<TextBook> collectTextBook() {
        List<TextBook> textList = new ArrayList<>();
        connect();
        if (conn != null) {
            String query = "SELECT * FROM TextBook";
            try {
                PreparedStatement prepStmt = conn.prepareStatement(query);
                ResultSet rs = prepStmt.executeQuery();
                TextBook textBook;
                while (rs.next()) {
                    int textId = rs.getInt("textId");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String ISBN = rs.getString("ISBN");
                    String subject = rs.getString("subject_");
                    String edition=rs.getString("edition");
                    int isle = rs.getInt("isle");
                    textBook = new TextBook(textId, title, author, ISBN, subject,edition,isle);
                    textList.add(textBook);
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return textList;
    }

    public List<Book> collectBooks() {
        List<Book> books = new ArrayList<>();
        connect();
        if (conn != null) {
            String query = "SELECT * FROM Book";
            try {
                PreparedStatement prepStmt = conn.prepareStatement(query);
                ResultSet rs = prepStmt.executeQuery();
                Book book;
                while (rs.next()) {
                    int bookId = rs.getInt("bookId");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String ISBN = rs.getString("ISBN");
                    String genre = rs.getString("genre");
                    int isle = rs.getInt("isle");
                    book = new Book(bookId, title, author, ISBN, genre, isle);
                    books.add(book);
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return books;
    }

    public List<Media> collectMedia() {
        List<Media> mediaList = new ArrayList<>();
        connect();
        if (conn != null) {
            String query = "SELECT * FROM Media";
            try {
                PreparedStatement prepStmt = conn.prepareStatement(query);
                ResultSet rs = prepStmt.executeQuery();
                Media media;
                while (rs.next()) {
                    int mediaId = rs.getInt("mediaId");
                    String title = rs.getString("title");
                    String publisher = rs.getString("publisher");
                    String genre = rs.getString("genre");
                    int isle = rs.getInt("isle");
                    media = new Media(mediaId, title, publisher,genre,isle);
                    mediaList.add(media);
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return mediaList;
    }

    public List<Computer> collectComputer() {
        List<Computer> compList = new ArrayList<>();
        connect();
        if (conn != null) {
            String query = "SELECT * FROM Computers";
            try {
                PreparedStatement prepStmt = conn.prepareStatement(query);
                ResultSet rs = prepStmt.executeQuery();
                Computer computer;
                while (rs.next()) {
                    int computerNo = rs.getInt("computerNo");
                    String type = rs.getString("type");
                    boolean availability = rs.getBoolean("availability");
                     computer= new Computer(computerNo, type, availability);
                    compList.add(computer);
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return compList;
    }

    /*
    public List<Tutor> collectTutor() {
        List<Tutor> tutorList = new ArrayList<>();
        connect();
        if (conn != null) {
            String query = "SELECT * FROM Tutor";
            try {
                PreparedStatement prepStmt = conn.prepareStatement(query);
                ResultSet rs = prepStmt.executeQuery();
                Tutor Tutor;
                while (rs.next()) {
                    int tutorNo = rs.getInt("tutorNo");
                    String staffNo = rs.getString("staffNo");
                    String subject = rs.getString("subject");
                    String sAvailability = rs.getString("sAvailability");
                    String fAvailability = rs.getString("fAvailability");
                    boolean present=rs.getBoolean("present");
                    Tutor = new Tutor(tutorNo, staffNo, subject, sAvailability, fAvailability,present);
                    tutorList.add(Tutor);
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return tutorList;
    }
    */
}

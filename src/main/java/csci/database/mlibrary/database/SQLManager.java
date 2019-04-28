package csci.database.mlibrary.database;

import csci.database.mlibrary.structures.Book;

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

}
package csci.database.mlibrary.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Connection successful.");
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
package projectgui2025;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/cms";
    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // old driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
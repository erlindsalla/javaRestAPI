package com.ritech.ritechTask.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;
public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/liber-sherbimesh";
    private static String USER = "root";
    private static String PASSWORD = "2905";
    private static Connection connection = null;

    private static void initDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * @return - Database Connection
     */
    public static Connection setUpConnection() {
        if (connection == null) {
            initDBConnection();
        }
        try {
            if (connection.isClosed()) {
                initDBConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    /**
     * Closes opened connection
     *
     * @return True if opened connection is closed, false otherwise
     */
    public static boolean closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Returns db connection
     *
     * @return Connection
     */
    public static Connection getConnection() {
        return connection;
    }

}

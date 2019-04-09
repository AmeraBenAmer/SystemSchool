package sample;

import java.sql.*;


public class DBConnect {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/school_managment_a" + "?useUnicode=true&characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;

    public static Connection setConnection() {
        try {

            Class.forName(JDBC_DRIVER);  // MySQL database connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("connect successful");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}




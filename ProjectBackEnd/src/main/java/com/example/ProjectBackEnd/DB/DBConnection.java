package com.example.ProjectBackEnd.DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String dbURL = "jdbc:mysql://10.4.53.32:3306/db63130500246";

    public static Connection getMySQLConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbURL, "63130500246", "abcd1234");
            return con;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

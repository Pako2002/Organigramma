package com.example.organigramma.UserInterface;

import java.sql.*;

public class MyJDBC {
    public static void main(String[] args) {
        try{
            final String url = "jdbc:mysql://localhost:3306/prova";
            final String user="root";
            final String password="";
            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM utenti");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("nome"));
                System.out.println(resultSet.getString("eta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

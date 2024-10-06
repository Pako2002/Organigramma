package com.example.organigramma.DAO;

import com.example.organigramma.Composite.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String oneUser="SELECT * FROM users ";
    private static final String allUser= "SELECT * FROM users";

    private static UserDAO instance;
    UserDAO() {}

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public static User getUser(long id){
        User res=null;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=oneUser+"WHERE UserID = "+id+";";
            ResultSet rs= stmt.executeQuery(where);
            res= new User(rs.getLong("UserID"), rs.getString("UserName"), rs.getString("Password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static List<User> getAllUser(){
        List<User> users= new ArrayList<>();
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
                ResultSet rs= stmt.executeQuery(allUser)
        )
        {
            while (rs.next()){
                User us= new User(rs.getLong("UserID"), rs.getString("UserName"), rs.getString("Password"));
                users.add(us);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}

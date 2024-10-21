package com.example.organigramma.DAO;

import com.example.organigramma.Composite.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String oneUser="SELECT * FROM users ";
    private static final String userID="SELECT ID FROM users ";
    private static final String allUser= "SELECT * FROM users";
    private static String addUser= "INSERT INTO users (UserName, Password)\n";
    private static String removeUser="DELETE FROM users WHERE ";
    private static String changeName= "UPDATE users\n"+"SET UserName = ";
    private static String changePassword= "UPDATE users\n"+"SET Password =";

    private static UserDAO instance;
    UserDAO() {}

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public static void changePassword(User oldUser, long newPassword){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            changePassword+="\'"+newPassword+"\'\n";
            String where;
            where="WHERE UserID = \'"+getID(oldUser.getName())+"\';";
            changePassword+=where;
            stmt.executeUpdate(changePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeName(User oldUser, String newName){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            changeName+="\'"+newName+"\'\n";
            String where;
            where="WHERE UserID = \'"+getID(oldUser.getName())+"\';";
            changeName+=where;
            stmt.executeUpdate(changeName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // dopo aver eliminato l'user devi fare in modo che si elimini tutto ciò che è collegato a lui
    public static void removeUser(User us){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where;
            where="UserID = \'"+getID(us.getName())+"\';";
            removeUser+=where;
            stmt.executeUpdate(removeUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User us) throws SQLException{

        Connection con= DriverManager.getConnection(url, user, password);
        Statement stmt= con.createStatement();

        String values=String.format("VALUES (%s, %s)",us.getName(),us.getPassword());
        addUser+=values;
        stmt.executeUpdate(addUser);
        /*
        Connection con= DriverManager.getConnection(url, user, password);
        Statement stmt= con.createStatement();

        String values="VALUES ";
        values+= "(\'"+us.getName()+"\', \'"+us.getPassword()+"\');";
        addUser+=values;
        stmt.executeUpdate(addUser);
        /*
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();

            String values="VALUES ";
            values+= "(\'"+us.getName()+"\', \'"+us.getPassword()+"\');";
            addUser+=values;
            stmt.executeUpdate(addUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
         */
    }

    public static int getID(String username){
        int res=0;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=userID+"WHERE UserName = "+username+";";
            ResultSet rs= stmt.executeQuery(where);
            res= rs.getInt("UserID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static User getUser(String username){
        User res=null;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=oneUser+"WHERE UserName = "+username+";";
            ResultSet rs= stmt.executeQuery(where);
            res= new User(rs.getString("UserName"), rs.getString("Password"));
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
                User us= new User(rs.getString("UserName"), rs.getString("Password"));
                users.add(us);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}

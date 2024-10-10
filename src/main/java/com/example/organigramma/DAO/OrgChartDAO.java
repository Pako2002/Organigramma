package com.example.organigramma.DAO;

import com.example.organigramma.Composite.OrgChart;
import com.example.organigramma.Composite.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrgChartDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String oneChart= "SELECT * FROM orgcharts ";
    private static final String allOrgChart= "SELECT * FROM orgcharts";
    /*
    private static String addUnit= "INSERT INTO units (Name, Level)\n";
    private static String removeUnit="DELETE FROM units WHERE ";
    private static String removeUnitRole= "DELETE FROM employeeroles WHERE ";
    private static String changeName= "UPDATE units\n"+"SET Name = ";
    private static String changeLevel= "UPDATE units\n"+"SET Level =";
     */

    private static OrgChartDAO instance;
    OrgChartDAO(){}

    public static OrgChartDAO getInstance(){
        if(instance == null){
            instance = new OrgChartDAO();
        }
        return instance;
    }

    public static OrgChart getOrgChart(long id){
        OrgChart res=null;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=oneChart+"WHERE UserID = "+id+";";
            ResultSet rs= stmt.executeQuery(where);
            UserDAO userDAO = new UserDAO();
            User us= userDAO.getUser(rs.getLong("UserID"));
            res= new OrgChart(rs.getLong("OrgCharID"), rs.getString("OrgCharName"), us);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<OrgChart> getAllOrgChart(){
        List<OrgChart> orgcharts= new ArrayList<>();
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
                ResultSet rs= stmt.executeQuery(allOrgChart)
        )
        {
            while (rs.next()){
                UserDAO userDAO = new UserDAO();
                User us= userDAO.getUser(rs.getLong("UserID"));
                OrgChart oc= new OrgChart(rs.getLong("OrgChartID"), rs.getString("OrgChartName"), us);
                orgcharts.add(oc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgcharts;
    }

}

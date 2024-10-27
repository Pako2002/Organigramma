package com.example.organigramma.DAO;

import com.example.organigramma.Composite.*;

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
    private static final String allOrgChart= "SELECT * FROM orgcharts ";
    private static final String addOrgChart= "INSERT INTO orgcharts (OrgChartName, UserID)\n";
    private static final String removeOrgChart="DELETE FROM orgcharts WHERE ";
    private static final String removeOUR= "DELETE FROM orgchartunitsroles WHERE ";
    private static final String changeName= "UPDATE orgcharts\n"+"SET OrgChartName = ";

    private static OrgChartDAO instance;
    OrgChartDAO(){}

    public static OrgChartDAO getInstance(){
        if(instance == null){
            instance = new OrgChartDAO();
        }
        return instance;
    }

    public static void changeName(OrgChart oldOC, String newName){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=changeName;
            where+="\'"+newName+"\'\n";
            where+="WHERE OrgChartName = \'"+oldOC.getName()+"\';";
            stmt.executeUpdate(where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeOUR(OrgChart oc){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeOUR;
            where+="OrgChartName = \'"+oc.getName()+"\';";
            stmt.executeUpdate(where);
            //qui elimino tutti i dipendenti associati all'organigramma eliminato. Non vado a toccare unità e ruoli poiché compaiono in altri org
            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            for(Employee emp:employees){
                if(emp.getOrgchart().equals(getID(oc.getName())))
                    EmployeeDAO.removeEmployee(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeOrgChart(OrgChart oc){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeOrgChart;
            where+="OrgChartName = \'"+oc.getName()+"\';";
            stmt.executeUpdate(where);
            removeOUR(oc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addOrgChart(OrgChart oc){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();

            String values=addOrgChart;
            values+="VALUES ";
            values+= "(\'"+oc.getName()+"\', \'"+UserDAO.getID(oc.getUser().getName())+"\');";
            stmt.executeUpdate(values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static OrgChart getOrgChart(long id){
        OrgChart res=null;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=oneChart+"WHERE OrgChartID = "+id+";";
            ResultSet rs= stmt.executeQuery(where);
            UserDAO userDAO = new UserDAO();
            rs.next();
            User us= userDAO.getUser(rs.getInt("UserID"));
            res= new OrgChart(rs.getString("OrgChartName"), us);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int getID(String orgName){
        int res=0;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=oneChart+"WHERE OrgChartName = \'"+orgName+"\';";
            ResultSet rs= stmt.executeQuery(where);
            rs.next();
            res=rs.getInt("OrgChartID");
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
                User us= userDAO.getUser(rs.getString("UserName"));
                OrgChart oc= new OrgChart(rs.getString("OrgChartName"), us);
                orgcharts.add(oc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgcharts;
    }

}

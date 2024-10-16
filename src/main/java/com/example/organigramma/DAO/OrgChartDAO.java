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
    private static final String allOrgChart= "SELECT * FROM orgcharts";
    private static String addOrgChart= "INSERT INTO orgcharts (OrgChartID, OrgChartName, UserID)\n";
    private static String removeOrgChart="DELETE FROM orgcharts WHERE ";
    private static String removeOUR= "DELETE FROM orgchartunitsroles WHERE ";
    private static String changeName= "UPDATE orgcharts\n"+"SET OrgChartName = ";

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
            changeName+="\'"+newName+"\'\n";
            String where;
            where="WHERE OrgChartName = \'"+oldOC.getName()+"\';";
            changeName+=where;
            stmt.executeUpdate(changeName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeOUR(OrgChart oc){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where;
            where="OrgChartID = \'"+oc.getID()+"\';";
            removeOUR+=where;
            stmt.executeUpdate(removeOUR);
            //qui elimino tutti i dipendenti associati all'organigramma eliminato. Non vado a toccare unità e ruoli poiché compaiono in altri org
            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            for(Employee emp:employees){
                if(emp.getOrgchart().equals(oc.getID()))
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
            String where;
            where="OrgChartID = \'"+oc.getID()+"\';";
            removeOrgChart+=where;
            stmt.executeUpdate(removeOrgChart);
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

            String values="VALUES ";
            values+= "(\'"+oc.getID()+"\', \'"+oc.getName()+"\', \'"+UserDAO.getID(oc.getUser().getName())+"\');";
            addOrgChart+=values;
            stmt.executeUpdate(addOrgChart);
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
            User us= userDAO.getUser(rs.getString("UserName"));
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
                User us= userDAO.getUser(rs.getString("UserName"));
                OrgChart oc= new OrgChart(rs.getLong("OrgChartID"), rs.getString("OrgChartName"), us);
                orgcharts.add(oc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgcharts;
    }

}

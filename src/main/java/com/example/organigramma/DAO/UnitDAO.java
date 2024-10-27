package com.example.organigramma.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.organigramma.Composite.*;

public class UnitDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String allUnits= "SELECT * FROM units\n";
    private static final String addUnit= "INSERT INTO units (Name, Level)\n";
    private static final String removeUnit="DELETE FROM units WHERE ";
    private static final String removeUnitRole= "DELETE FROM employeeroles WHERE ";
    private static final String changeName= "UPDATE units\n"+"SET Name = ";
    private static final String changeLevel= "UPDATE units\n"+"SET Level =";

    private static UnitDAO istance;
    UnitDAO(){}

    public static UnitDAO getInstance(){
        if(istance==null){
            istance=new UnitDAO();
        }
        return istance;
    }

    public static void addUnit(Unit unit){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();

            String values=addUnit;
            values+="VALUES ";
            values+= "(\'"+unit.getName()+"\', "+unit.getLevel()+");";
            System.out.println(values);
            stmt.executeUpdate(values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUnit(Unit unit){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeUnit;
            removeUnitRole(unit);
            where+="Name = \'"+unit.getName()+"\';";
            stmt.executeUpdate(where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUnitRole(Unit unit){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeUnitRole;
            where+="UnitName = \'"+unit.getName()+"\';";
            stmt.executeUpdate(where);
            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            for(Employee emp:employees){
                if(emp.roles.containsKey(unit))
                    emp.roles.remove(unit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeName(Unit oldUnit, String newName){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE units\n"+"SET
            String where=changeName;
            where+="\'"+newName+"\'\n";
            where+="WHERE Name = \'"+oldUnit.getName()+"\';";
            stmt.executeUpdate(where);

            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            Unit newUnit= new  CompoundUnit(newName, oldUnit.getLevel());
            Role role;
            for(Employee emp:employees){
                if(emp.roles.containsKey(oldUnit)){
                    role=emp.getRole(oldUnit);
                    emp.assignRole(newUnit, role);
                    emp.roles.remove(oldUnit);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeLevel(Unit unit, int newLevel){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE units\n"+"SET
            //Attenzione cambiando il livello l'unità dovrà essere riassegnata
            removeUnitRole(unit);
            String where=changeLevel;
            where+=newLevel+"\n";
            where+="WHERE Name = \'"+unit.getName()+"\';";
            stmt.executeUpdate(where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Unit getUnit(String unitName){
        Unit unit=null;
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=allUnits;
            where+="WHERE Name = \'"+unitName+"\';";
            ResultSet rs= stmt.executeQuery(where);
            rs.next();//
            unit = new CompoundUnit(rs.getString("Name"), rs.getInt("Level"));
            System.out.println(where);
            System.out.println(unit.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unit;
    }
    public static List<Unit> getAllUnit(){
        List<Unit> units= new ArrayList<>();
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
                ResultSet rs= stmt.executeQuery(allUnits)
        )
        {
            while (rs.next()){
                Unit unit= new CompoundUnit(rs.getString("Name"), rs.getInt("Level"));
                units.add(unit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }
}
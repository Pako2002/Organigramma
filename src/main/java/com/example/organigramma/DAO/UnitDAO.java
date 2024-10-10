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
    private static final String allUnits= "SELECT * FROM units";
    private static String addUnit= "INSERT INTO units (Name, Level)\n";
    private static String removeUnit="DELETE FROM units WHERE ";
    private static String removeUnitRole= "DELETE FROM employeeroles WHERE ";
    private static String changeName= "UPDATE units\n"+"SET Name = ";
    private static String changeLevel= "UPDATE units\n"+"SET Level =";

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

            String values="VALUES ";
            values+= "(\'"+unit.getName()+"\', "+unit.getLevel()+");";
            addUnit+=values;
            stmt.executeUpdate(addUnit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUnit(Unit unit){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where;
            removeUnitRole(unit);
            where="Name = \'"+unit.getName()+"\';";
            removeUnit+=where;
            stmt.executeUpdate(removeUnit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUnitRole(Unit unit){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where;
            where="UnitName = \'"+unit.getName()+"\';";
            removeUnitRole+=where;
            stmt.executeUpdate(removeUnitRole);
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
            changeName+="\'"+newName+"\'\n";
            String where;
            where="WHERE Name = \'"+oldUnit.getName()+"\';";
            changeName+=where;
            stmt.executeUpdate(changeName);

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
            changeLevel+=newLevel+"\n";
            String where;
            where="WHERE Name = \'"+unit.getName()+"\';";
            changeLevel+=where;
            stmt.executeUpdate(changeLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
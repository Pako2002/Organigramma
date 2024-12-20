package com.example.organigramma.DAO;

//Employee DAO (Data Access Object)
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.organigramma.Composite.OrgChart;
import com.example.organigramma.Composite.Unit;
import com.example.organigramma.Composite.Employee;
import com.example.organigramma.Composite.Role;

public class EmployeeDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String allEmployees= "SELECT * FROM employee\n";
    private static final String addEmployee= "INSERT INTO employee (ID, Name, OrgChartID)\n";
    private static final String addEmployeeRole= "INSERT INTO employeerole (EmployeeID, UnitName, RoleName)\n";
    private static final String removeEmployee="DELETE FROM employee WHERE ";
    private static final String removeEmployeeRole= "DELETE FROM employeerole WHERE ";
    private static final String changeID= "UPDATE Employee\n"+"SET ID =";
    private static final String changeName= "UPDATE Employee\n"+"SET Name =";
    private static final String changeUnit= "UPDATE EmployeeRole\n"+"SET UnitName =";
    private static final String changeRole= "UPDATE EmployeeRole\n"+"SET RoleName =";

    private static EmployeeDAO istance;
    EmployeeDAO(){}

    public static EmployeeDAO getInstance(){
        if(istance==null){
            istance=new EmployeeDAO();
        }
        return istance;
    }

    public static Employee getEmployee(String empName){
        Employee employees=null;
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=allEmployees;
            where+="WHERE Name = \'"+empName+"\';";
            ResultSet rs= stmt.executeQuery(where);
            rs.next();//
            OrgChartDAO orgChartDAO= new OrgChartDAO();
            OrgChart oc= orgChartDAO.getOrgChart(rs.getLong("OrgChartID"));
            employees = new Employee(rs.getInt("ID"), rs.getString("Name"),oc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void addEmployee(Employee employee){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String values=addEmployee;
            values+="VALUES ";
            OrgChartDAO orgChartDAO=new OrgChartDAO();
            values+= "("+employee.getId()+", \'"+employee.getName()+"\', "+orgChartDAO.getID(employee.getOrgchart().getName())+");";
            stmt.executeUpdate(values);
            for(Map.Entry<Unit,Role> entry: employee.roles.entrySet()){
                addEmployeeRole(employee, entry.getKey(), entry.getValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addEmployeeRole(Employee employee, Unit unit, Role role){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            employee.roles.put(unit, role);
            String values=addEmployeeRole;
            values+="VALUES ";
            values+="("+employee.getId()+", \'"+unit.getName()+"\', \'"+role.getName()+"\');";
            stmt.executeUpdate(values);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //REMOVING
    public static void removeEmployee(Employee employee){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeEmployee;
            removeEmployeeRole(employee);
            where+="ID = \'"+employee.getId()+"\';";
            stmt.executeUpdate(where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeEmployeeRole(Employee employee){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeEmployeeRole;
            where+="EmployeeID = "+employee.getId()+";";
            stmt.executeUpdate(where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //CHANGING
    public static void changeID(Employee employee, int newID){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE Employees\n"+"SET
            String where=changeID;
            where+=newID+"\n";
            where+="WHERE ID = "+employee.getId()+";";
            stmt.executeUpdate(where);
            employee.setId(newID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeName(Employee employee, String newName){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE Employees\n"+"SET
            String where=changeName;
            where+="\'"+newName+"\'\n";
            where+="WHERE ID = "+employee.getId()+";";
            stmt.executeUpdate(where);
            employee.setName(newName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeUnit(Employee employee, Unit oldUnit, Unit newUnit){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();

            Role role=employee.getRole(oldUnit);
            employee.assignRole(newUnit, role); //assegno il dipendente ad una nuova Unità con il vecchio ruolo
            //UPDATE Employees\n"+"SET
            String where=changeUnit;
            where+="\'"+newUnit.getName()+"\'\n";
            where+="WHERE EmployeeID = "+employee.getId()+" AND UnitName = \'"+oldUnit.getName()+"\';";
            stmt.executeUpdate(where);

            employee.removeRole(oldUnit, role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeRole(Employee employee, Role oldRole, Role newRole){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            Unit unit= employee.getUnit(oldRole);
            employee.assignRole(unit, newRole);
            //UPDATE Employees\n"+"SET
            String where=changeRole;
            where+="\'"+newRole.getName()+"\'\n";
            where+="WHERE EmployeeID = "+employee.getId()+" AND RoleName = \'"+oldRole.getName()+"\';";
            stmt.executeUpdate(where);
            employee.removeRole(unit, oldRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> getAllEmployees(OrgChart oc){
        List<Employee> employees= new ArrayList<>();
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=allEmployees;
            where+="WHERE OrgChartID = \'"+OrgChartDAO.getID(oc.getName())+"\';";
            ResultSet rs= stmt.executeQuery(where);
            while (rs.next()){
                Employee emp = new Employee(rs.getInt("ID"), rs.getString("Name"),oc);
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

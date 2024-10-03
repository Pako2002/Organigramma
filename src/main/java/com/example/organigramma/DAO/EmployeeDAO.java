package com.example.organigramma.DAO;

//Employee DAO (Data Access Object)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.organigramma.Composite.Unit;
import com.example.organigramma.EmployeeStructures.Employee;
import com.example.organigramma.FactoryMethod.Role;

public class EmployeeDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String allEmployees= "SELECT * FROM employees";
    private static String addEmployee= "INSERT INTO employees (ID, Name)\n";
    private static String addEmployeeRole= "INSERT INTO employeeroles (EmployeeID, UnitName, RoleName)\n";
    private static String removeEmployee="DELETE FROM employees WHERE ";
    private static String removeEmployeeRole= "DELETE FROM employeeroles WHERE ";
    private static String changeID= "UPDATE Employees\n"+"SET ID =";
    private static String changeName= "UPDATE Employees\n"+"SET Name =";
    private static String changeUnit= "UPDATE EmployeeRoles\n"+"SET UnitName =";
    private static String changeRole= "UPDATE EmployeeRoles\n"+"SET RoleName ="
            ;
    private static EmployeeDAO istance;
    EmployeeDAO(){}

    public static EmployeeDAO getInstance(){
        if(istance==null){
            istance=new EmployeeDAO();
        }
        return istance;
    }

    //ADDING
    public static void addEmployee(Employee employee){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String values="VALUES ";
            values+= "("+employee.getId()+", \'"+employee.getName()+"\');";
            addEmployee+=values;
            stmt.executeUpdate(addEmployee);
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
            String values="VALUES ";
            values+="("+employee.getId()+", \'"+unit.getName()+"\', \'"+role.getRoleName()+"\');";
            addEmployeeRole+=values;
            stmt.executeUpdate(addEmployeeRole);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //REMOVING
    public static void removeEmployee(Employee employee){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where;
            removeEmployeeRole(employee);
            where="ID = \'"+employee.getId()+"\';";
            removeEmployee+=where;
            stmt.executeUpdate(removeEmployee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeEmployeeRole(Employee employee){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where;
            where="EmployeeID = "+employee.getId()+";";
            removeEmployeeRole+=where;
            stmt.executeUpdate(removeEmployeeRole);
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
            changeID+=newID+"\n";
            String where;
            where="WHERE ID = "+employee.getId()+";";
            changeID+=where;
            stmt.executeUpdate(changeID);
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
            changeName+="\'"+newName+"\'\n";
            String where;
            where="WHERE ID = "+employee.getId()+";";
            changeName+=where;
            stmt.executeUpdate(changeName);
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
            changeUnit+="\'"+newUnit.getName()+"\'\n";
            String where;
            where="WHERE EmployeeID = "+employee.getId()+" AND UnitName = \'"+oldUnit.getName()+"\';";
            changeUnit+=where;
            stmt.executeUpdate(changeUnit);

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
            changeRole+="\'"+newRole.getRoleName()+"\'\n";
            String where;
            where="WHERE EmployeeID = "+employee.getId()+" AND RoleName = \'"+oldRole.getRoleName()+"\';";
            changeRole+=where;
            stmt.executeUpdate(changeRole);
            employee.removeRole(unit, oldRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> getAllEmployees(){
        List<Employee> employees= new ArrayList<>();
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
                ResultSet rs= stmt.executeQuery(allEmployees)
        )
        {
            while (rs.next()){
                Employee emp = new Employee(rs.getInt("ID"), rs.getString("Name"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

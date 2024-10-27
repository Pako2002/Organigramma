package com.example.organigramma.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.organigramma.Composite.OrgChart;
import com.example.organigramma.Composite.Unit;
import com.example.organigramma.Composite.Employee;
import com.example.organigramma.Composite.Role;

public class RoleDAO {
    private static final String url = "jdbc:mysql://localhost:3306/organigrammaaziendale";
    private static final String user="root";
    private static final String password="";
    private static final String oneRole= "SELECT * FROM roles ";
    private static final String allRoles= "SELECT * FROM roles";
    private static final String addRole= "INSERT INTO roles (Name, Level, Priority)\n";
    private static final String addOrgChartUnitsRoles= "INSERT INTO orgchartunitsroles (OrgChartID, UnitName, RoleName, Level)\n";
    private static final String removeRole="DELETE FROM roles WHERE ";
    private static final String removeRoleEmployee= "DELETE FROM employeeroles WHERE ";
    private static final String changeName= "UPDATE roles\n"+"SET Name = ";
    private static final String changeLevel= "UPDATE roles\n"+"SET Level =";
    private static final String changePriority= "UPDATE roles\n"+"SET Priority =";

    private static RoleDAO istance;
    RoleDAO(){}

    public static RoleDAO getIstance(){
        if(istance==null){
            istance=new RoleDAO();
        }
        return istance;
    }

    public static void addOrgChartUnitsRoles(OrgChart oc, Unit unit, Role role){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String values=addOrgChartUnitsRoles;
            values+="VALUES ";
            OrgChartDAO orgDAO=new OrgChartDAO();
            values+="("+orgDAO.getID(oc.getName())+", \'"+unit.getName()+"\', \'"+role.getName()+"\', "+role.getLevel()+");";
            stmt.executeUpdate(values);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRole(Role role){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();

            String values=addRole;
            values+="VALUES ";
            values+= "(\'"+role.getName()+"\', "+role.getLevel()+", "+role.getRolePriority()+");";
            stmt.executeUpdate(values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeRole(Role role){//eliminando un ruolo poi si dovrà assegnare un nuovo ruolo a tutti i dipendenti associati a quello eliminato
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeRole;
            removeRoleEmployee(role);
            where+="Name = \'"+role.getName()+"\';";
            stmt.executeUpdate(where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeRoleEmployee(Role role){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeRoleEmployee;
            where+="RoleName = \'"+role.getName()+"\';";
            stmt.executeUpdate(where);
            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            for(Employee emp:employees){
                if(emp.roles.containsValue(role)){
                    emp.roles.remove(emp.getUnit(role));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeName(Role oldRole, String newName){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE units\n"+"SET
            String where=changeName;
            where+="\'"+newName+"\'\n";
            where+="WHERE Name = \'"+oldRole.getName()+"\';";
            stmt.executeUpdate(where);

            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            Role newRole= new Role(newName, oldRole.getLevel(), oldRole.getRolePriority());
            Unit unit;
            for(Employee emp:employees){
                if(emp.roles.containsValue(oldRole)){
                    unit=emp.getUnit(oldRole);
                    emp.roles.remove(unit);
                    emp.assignRole(unit, newRole);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeLevel(Role oldRole, int level){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE units\n"+"SET
            String where=changeLevel;
            where+=level+"\n";
            where+="WHERE Name = \'"+oldRole.getName()+"\';";
            stmt.executeUpdate(where);

            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            Role newRole= new Role(oldRole.getName(), level, oldRole.getRolePriority());
            Unit unit;
            for(Employee emp:employees){
                if(emp.roles.containsValue(oldRole)){
                    unit=emp.getUnit(oldRole);
                    emp.roles.remove(unit);
                    emp.assignRole(unit, newRole);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changePriority(Role oldRole, int priority){
        try {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            //UPDATE units\n"+"SET
            String where=changePriority;
            where+=priority+"\n";
            where+="WHERE Name = \'"+oldRole.getName()+"\';";
            stmt.executeUpdate(where);

            List<Employee> employees = new ArrayList<>();
            employees.addAll(EmployeeDAO.getAllEmployees());
            Role newRole= new Role(oldRole.getName(), oldRole.getLevel(), priority);
            Unit unit;
            for(Employee emp:employees){
                if(emp.roles.containsValue(oldRole)){
                    unit=emp.getUnit(oldRole);
                    emp.roles.remove(unit);
                    emp.assignRole(unit, newRole);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Role getRole(String roleName){
        Role ris=null;
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
        )
        {
            String where;
            where=oneRole+"WHERE Name = \'"+roleName+"\';";
            ResultSet rs= stmt.executeQuery(where);
            rs.next();
            ris= new Role(rs.getString("Name"), rs.getInt("Level"), rs.getInt("Priority"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ris;
    }

    public static List<Role> getAllRole(){
        List<Role> roles= new ArrayList<>();
        try (
                Connection con= DriverManager.getConnection(url, user, password);
                Statement stmt= con.createStatement();
                ResultSet rs= stmt.executeQuery(allRoles)
        )
        {
            while (rs.next()){
                Role role= new Role(rs.getString("Name"), rs.getInt("Level"), rs.getInt("Priority"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
}

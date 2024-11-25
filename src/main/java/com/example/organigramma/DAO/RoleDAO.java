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
    private static final String oneRole= "SELECT * FROM role ";
    private static final String allRoles= "SELECT * FROM role";
    private static final String addRole= "INSERT INTO role (Name, Level, Priority)\n";
    private static final String addOrgChartUnitsRoles= "INSERT INTO orgchartunitrole (OrgChartID, UnitName, RoleName, Level)\n";
    private static final String removeOrgChartUnitsRoles= "DELETE FROM orgchartunitrole WHERE \n";
    private static final String removeRole="DELETE FROM role WHERE ";
    private static final String removeRoleEmployee= "DELETE FROM employeerole WHERE ";
    private static final String changeName= "UPDATE role\n"+"SET Name = ";
    private static final String changeLevel= "UPDATE role\n"+"SET Level =";
    private static final String changePriority= "UPDATE role\n"+"SET Priority =";

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

    public static void removeOrgChartUnitsRoles(Role role){
        try
        {
            Connection con= DriverManager.getConnection(url, user, password);
            Statement stmt= con.createStatement();
            String where=removeOrgChartUnitsRoles;
            where+="RoleName = \'"+role.getName()+"\';";
            stmt.executeUpdate(where);
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

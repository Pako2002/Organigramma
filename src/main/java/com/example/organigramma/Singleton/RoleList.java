package com.example.organigramma.Singleton;

import java.util.ArrayList;
import java.util.List;

public class RoleList {
    private static RoleList ourInstance;
    private static List<String> rolesName = new ArrayList<String>();

    private RoleList() {}

    public static RoleList getInstance() {
        if (ourInstance == null) {
            ourInstance = new RoleList();
        }
        return ourInstance;
    }

    public void add(String name) {
        rolesName.add(name);
    }
    public void remove(String name) {
        rolesName.remove(name);
    }
    public String getName(String unit) {
        for (String name : rolesName) {
            if (name.equals(unit)) {
                return name;
            }
        }
        return null;
    }
    public String getName(int index) {
        return rolesName.get(index);
    }
    public List<String> getUnitsNames() {
        return rolesName;
    }
    public String[] convert() {
        return rolesName.toArray(new String[0]);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Roles List:\n");
        for(String name : rolesName) {
            builder.append("   - " + name + "\n");
        }
        return builder.toString();
    }
}

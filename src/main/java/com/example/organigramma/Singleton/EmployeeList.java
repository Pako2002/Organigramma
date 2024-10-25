package com.example.organigramma.Singleton;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList {
    private static EmployeeList ourInstance;
    private static List<String> emplyeeName = new ArrayList<String>();

    private EmployeeList() {}

    public static EmployeeList getInstance() {
        if (ourInstance == null) {
            ourInstance = new EmployeeList();
        }
        return ourInstance;
    }

    public void add(String name) {
        emplyeeName.add(name);
    }
    public void remove(String name) {
        emplyeeName.remove(name);
    }
    public String getName(String unit) {
        for (String name : emplyeeName) {
            if (name.equals(unit)) {
                return name;
            }
        }
        return null;
    }
    public String getName(int index) {
        return emplyeeName.get(index);
    }
    public List<String> getUnitsNames() {
        return emplyeeName;
    }
    public String[] convert() {
        return emplyeeName.toArray(new String[0]);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Employees List:\n");
        for(String name : emplyeeName) {
            builder.append("   - " + name + "\n");
        }
        return builder.toString();
    }
}

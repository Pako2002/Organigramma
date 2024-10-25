package com.example.organigramma.Singleton;

import com.example.organigramma.Composite.Unit;
import com.example.organigramma.DAO.UnitDAO;

import java.util.ArrayList;
import java.util.List;

public class UnitList {
    private static UnitList ourInstance;
    private static List<String> unitsNames = new ArrayList<String>();

    private UnitList() {}

    public static UnitList getInstance() {
        if (ourInstance == null) {
            ourInstance = new UnitList();
        }
        return ourInstance;
    }

    public void add(String name) {
        unitsNames.add(name);
    }
    public void remove(String name) {
        unitsNames.remove(name);
    }
    public String getName(String unit) {
        for (String name : unitsNames) {
            if (name.equals(unit)) {
                return name;
            }
        }
        return null;
    }
    public String getName(int index) {
        return unitsNames.get(index);
    }
    public List<String> getUnitsNames() {
        return unitsNames;
    }
    public String[] convert() {
        return unitsNames.toArray(new String[0]);
    }
    @Override
    public String toString() {
     StringBuilder builder = new StringBuilder();
     builder.append("Units List:\n");
     for(String name : unitsNames) {
         builder.append("   - " + name + "\n");
     }
     return builder.toString();
    }
}

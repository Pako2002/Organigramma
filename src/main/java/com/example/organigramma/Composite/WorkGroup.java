package com.example.organigramma.Composite;

import java.util.List;

public class WorkGroup implements Unit {
    public String name;
    public int level;

    public WorkGroup(String name, int level) {
        this.name = name;
        this.level = level;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void addSubUnit(Unit subUnit) {

    }

    @Override
    public void removeSubUnit(Unit subUnit) {

    }

    @Override
    public List<Unit> getSubUnits() {
        return List.of();
    }

    @Override
    public boolean hasSubUnit() {
        return false;
    }

    @Override
    public void setSubUnits(List<Unit> subUnits) {

    }

    public String showDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + this.name + "\n");
        sb.append("Level: " + this.level + "\n");

        return sb.toString();
    }
}

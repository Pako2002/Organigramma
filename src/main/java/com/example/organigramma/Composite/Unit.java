package com.example.organigramma.Composite;

import java.util.LinkedList;
import java.util.List;

public abstract class Unit{
    protected String name;
    protected int level;
    public List<Unit> subUnits= new LinkedList<>();

    Unit(String name, int level){
        this.name=name;
        this.level=level;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public abstract void addSubUnit(Unit subUnit);

    public abstract void removeSubUnit(Unit subUnit);

    public List<Unit> getSubUnits() {
        return this.subUnits;
    }

    public void setSubUnits(List<Unit> subUnits) {
        this.subUnits = subUnits;
    }

    public abstract String showDetails();

}

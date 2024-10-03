package com.example.organigramma.Composite;

public class WorkGroup extends Unit{


    public WorkGroup(String name, int level) {
        super(name, level);
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

    @Override
    public String showDetails() {
        return "{" +
                " Unit of the work group:'"+ getName()+"'"+
                "}";
    }

    @Override
    public void addSubUnit(Unit subUnit) {
        throw new UnsupportedOperationException("Unimplemented method 'addSubUnit'");
    }

    @Override
    public void removeSubUnit(Unit subUnit) {
        throw new UnsupportedOperationException("Unimplemented method 'removeSubUnit'");
    }
}

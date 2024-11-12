package com.example.organigramma.Composite;

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

    public String showDetails() {
        return "";
    }
}

package com.example.organigramma.Composite;

public class WorkGroup implements Unit{
    public String name;
    public int level;
    public OrgChart orgchart;

    public WorkGroup(String name, int level, OrgChart orgchart) {
        this.name = name;
        this.level = level;
        this.orgchart = orgchart;
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
    public OrgChart getOrgchart() {
        return this.orgchart;
    }

    @Override
    public void setOrgchart(OrgChart orgchart) {
        this.orgchart = orgchart;
    }

    public String showDetails() {
        return "";
    }
}

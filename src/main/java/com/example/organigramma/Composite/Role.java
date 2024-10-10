package com.example.organigramma.Composite;

public class Role implements Unit{
    private String roleName;
    private int level;
    private int priority;
    private OrgChart orgCharts;

    public Role(String Role, int level, int priority){
        this.roleName=Role;
        this.level=level;
        this.priority=priority;
        this.orgCharts=orgCharts;
    }

    @Override
    public String getName() {
        return this.roleName;
    }

    @Override
    public void setName(String name) {
        this.roleName = roleName;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    //do la possibilità di gestire OrgChart per ogni singolo ruolo o unit
    @Override
    public OrgChart getOrgchart() {
        return this.orgCharts;
    }

    @Override
    public void setOrgchart(OrgChart orgchart) {

    }

    public void setRolePriority(int priority) {
        this.priority = priority;
    }

    public int getRolePriority() {
        return this.priority;
    }

}


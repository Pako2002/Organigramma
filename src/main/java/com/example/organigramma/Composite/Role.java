package com.example.organigramma.Composite;

public class Role{
    private String roleName;
    private int level;
    private int priority;

    public Role(String Role, int level, int priority){
        this.roleName=Role;
        this.level=level;
        this.priority=priority;
    }

    public String getName() {
        return this.roleName;
    }

    public void setName(String name) {
        this.roleName = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRolePriority(int priority) {
        this.priority = priority;
    }

    public int getRolePriority() {
        return this.priority;
    }

}


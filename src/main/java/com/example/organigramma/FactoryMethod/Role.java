package com.example.organigramma.FactoryMethod;

public class Role {
    private String roleName;
    private int level;
    private int priority;

    public Role(String Role, int level, int priority){
        this.roleName=Role;
        this.level=level;
        this.priority=priority;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleLevel() {
        return this.level;
    }

    public void setRoleLevel(int level) {
        this.level = level;
    }

    public int getRolePriority() {
        return this.priority;
    }

    public void setRolePriority(int priority) {
        this.priority = priority;
    }

}


package com.example.organigramma.Composite;

public class OrgChart {
    private long ID;
    private String Name;
    private final User user;

    public OrgChart(long ID, String Name, User user) {
        this.ID = ID;
        this.Name = Name;
        this.user = user;
    }
    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public User getUser() {
        return user;
    }
}

package com.example.organigramma.Composite;

import java.util.*;

public class CompoundUnit implements Unit{
    public String name;
    public int level;
    public OrgChart orgchart;
    public List<CompoundUnit> subUnits= new LinkedList<>();

    public CompoundUnit(String name, int level) {
        this.name = name;
        this.level = level;
        this.orgchart = orgchart;
    }

    public void addSubUnit(CompoundUnit subUnit){
        try {
            if(!subUnits.contains(subUnit) && subUnit.getLevel()>this.getLevel())
                subUnits.add(subUnit);
            else
                throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            System.out.println("'"+subUnit.getName()+"' è un'unità già presente nell'organigramma o di livello superiore a '"+this.getName()+"'");
        }
    }

    public void removeSubUnit(Unit subUnit){
        try {
            if(subUnits.contains(subUnit))
                subUnits.remove(subUnit);
            else
                throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            System.out.println("'"+subUnit.getName()+"'' è un'unità non presente nell'organigramma");
        }
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

    public List<CompoundUnit> getSubUnits() {
        return this.subUnits;
    }

    public void setSubUnits(List<CompoundUnit> subUnits) {
        this.subUnits = subUnits;
    }

    public String showDetails() {
        StringBuilder sb =new StringBuilder();
        sb.append("Unit: "+getName()+",\n   -Level: "+getLevel()+",\n   -SubUnits: ");
        for(int i=0; i<subUnits.size();i++){
            sb.append(subUnits.get(i).getName());
            if(i<subUnits.size()-1){
                sb.append(", ");
            }
        }
        sb.append(".");
        String res= sb.toString();
        return res;
    }

}

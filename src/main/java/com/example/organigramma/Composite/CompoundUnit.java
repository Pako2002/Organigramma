package com.example.organigramma.Composite;

import java.util.*;

public class CompoundUnit extends Unit{

    public List<Unit> subUnits= new LinkedList<>();

    public CompoundUnit(String name, int level) {
        super(name, level);
        //TODO Auto-generated constructor stub
    }

    public void addSubUnit(Unit subUnit){
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

    public List<Unit> getSubUnits() {
        return this.subUnits;
    }

    public void setSubUnits(List<Unit> subUnits) {
        this.subUnits = subUnits;
    }

    @Override
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

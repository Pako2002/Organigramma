package com.example.organigramma.Composite;

import java.util.List;

public interface Unit{
    public String getName();

    public void setName(String name);

    public int getLevel();

    public void setLevel(int level);

    public void addSubUnit(Unit subUnit);

    public void removeSubUnit(Unit subUnit);

    public List<Unit> getSubUnits();

    public boolean hasSubUnit();

    public void setSubUnits(List<Unit> subUnits);
}

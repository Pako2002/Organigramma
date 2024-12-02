package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.OrgChart;
import com.example.organigramma.Composite.Unit;
import com.example.organigramma.Composite.CompoundUnit;


public class ConcreteUnit implements UnitFactory{
    public Unit createUnit(String name, int level) {
        return new CompoundUnit(name, level);
    }
}

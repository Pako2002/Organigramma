package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.OrgChart;
import com.example.organigramma.Composite.Unit;
import com.example.organigramma.Composite.WorkGroup;

public class ConcreteWorkGroup implements UnitFactory{
    @Override
    public Unit createUnit(String name, int level, OrgChart orgchart) {
        return new WorkGroup(name, level, orgchart);
    }
}

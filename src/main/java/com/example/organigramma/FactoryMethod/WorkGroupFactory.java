package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.*;

public class WorkGroupFactory extends UnitFactory{
    @Override
    public Unit createUnit(String name, int level) {
        return new WorkGroup(name, level);
    }
}

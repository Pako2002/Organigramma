package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.*;

public class DepartmentFactory extends UnitFactory{
    @Override
    public Unit createUnit(String name, int level) {
        return new Department(name, level);
    }
}

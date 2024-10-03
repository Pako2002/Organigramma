package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.Unit;
import com.example.organigramma.Composite.CompoundUnit;


public class CompoundUnitFactory extends UnitFactory{
    @Override
    public Unit createUnit(String name, int level) {
        return new CompoundUnit(name, level);
    }
}

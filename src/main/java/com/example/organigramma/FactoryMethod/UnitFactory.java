package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.Unit;

public abstract class UnitFactory {
    public abstract Unit createUnit(String name, int level);
}

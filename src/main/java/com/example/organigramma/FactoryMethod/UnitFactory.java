package com.example.organigramma.FactoryMethod;

import com.example.organigramma.Composite.Unit;

public interface UnitFactory {
    public abstract Unit createUnit(String name, int level);
}

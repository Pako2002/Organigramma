package com.example.organigramma.EmployeeStructures;

import com.example.organigramma.FactoryMethod.Role;
import com.example.organigramma.Composite.Unit;

import java.util.Map;
import java.util.HashMap;

public class Employee {
    private long id;
    private String name;
    public Map<Unit, Role> roles= new HashMap<>(); //può avere più unità, ma solo un ruolo per unità

    public Employee(long id, String name) {
        this.id=id;
        this.name=name;
    }
    //aggiungere controlli nell'assegnazione del ruolo basati sulla gerarchia delle unitià
    public void assignRole(Unit unit, Role role){
        if(!(roles.containsKey(unit)) && unit.getLevel() == role.getRoleLevel()){ //se il dipendente non è già assegnato a quel unità e il ruolo può essere assegnato a quel livello
            roles.put(unit,role);
        }
    }

    public Unit getUnit(Role role){
        for(Map.Entry<Unit,Role> entry: roles.entrySet()){
            if(entry.getValue().equals(role)){
                return entry.getKey();
            }
        }
        return null;
    }

    public Role getRole(Unit unit){
        for(Map.Entry<Unit,Role> entry: roles.entrySet()){
            if(entry.getKey().equals(unit)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void removeRole(Unit unit, Role role){
        roles.remove(unit, role);
    }

    //ID
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    //Name
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String showRoles(){
        StringBuilder sb= new StringBuilder();
        sb.append("Employee: "+name+"\n");
        for(Map.Entry<Unit,Role> entry: roles.entrySet()){
            sb.append("     Unit: "+entry.getKey().getName()+", ");
            sb.append("Role: "+ entry.getValue().getRoleName()+";\n");
        }
        String res= sb.toString();
        return res;
    }
}

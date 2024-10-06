package com.example.organigramma.Composite;

import java.util.Map;
import java.util.HashMap;

public class Employee extends Role{
    private long id;
    private String name;
    public OrgChart orgchart;
    public Map<Unit, Role> roles= new HashMap<>(); //può avere più unità, ma solo un ruolo per unità

    public Employee(long id, String name, OrgChart orgchart) {
        super("",0,0,null);  // non so se sia la soluzione corretta
        this.id=id;
        this.name=name;
        this.orgchart =orgchart;
    }
    //aggiungere controlli nell'assegnazione del ruolo basati sulla gerarchia delle unitià
    public void assignRole(Unit unit, Role role){
        if(!(roles.containsKey(unit)) && unit.getLevel() == role.getLevel()){ //se il dipendente non è già assegnato a quel unità e il ruolo può essere assegnato a quel livello
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

    public OrgChart getOrgchart() {
        return this.orgchart;
    }

    public void setOrgchart(OrgChart orgchart) {
        this.orgchart = orgchart;
    }

    public String showRoles(){
        StringBuilder sb= new StringBuilder();
        sb.append("OrgChart Name: "+ orgchart.getName()+"\n");
        sb.append("Employee: "+name+"\n");
        for(Map.Entry<Unit,Role> entry: roles.entrySet()){
            sb.append("     Unit: "+entry.getKey().getName()+", ");
            sb.append("Role: "+ entry.getValue().getName()+";\n");
        }
        String res= sb.toString();
        return res;
    }
}

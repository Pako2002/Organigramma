package com.example.organigramma.StrutturaDati;

import com.example.organigramma.Composite.*;
import com.example.organigramma.EmployeeStructures.Employee;
import com.example.organigramma.FactoryMethod.*;
import com.example.organigramma.DAO.EmployeeDAO;
import com.example.organigramma.DAO.UnitDAO;
import com.example.organigramma.DAO.RoleDAO;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Main{

    public static void main(String[] args) {
        /*
        EmployeeDAO.getInstance();
        List<Employee> employees= new ArrayList<>();
        employees.addAll(EmployeeDAO.getAllEmployees());
        for(Employee e: employees){
            System.out.println(e.getName());
        }
        */
        CompoundUnitFactory c= new CompoundUnitFactory();
        Unit CA= c.createUnit("Ricerca e Sviluppo", 0);
        Role r= new Role("Pupupipi", 1, 0);
        Employee emp= new Employee(169, "Mario Rossi");
        emp.roles.put(CA, r);

        //EmployeeDAO.addEmployee(emp);
        //Unit AV= c.createUnit("Area Vendite", 1);
        //Role rl1= new Role("direttore", 1, 0);
        //EmployeeDAO.addEmployeeRole(emp, AV, rl1);
        //UnitDAO.removeUnit(AV);
        //UnitDAO.changeName(CA, "Goku VS Vegeta");
        //UnitDAO.changeLevel(CA, 2);

        //RoleDAO.addRole(r);
        //RoleDAO.removeRole(r);
        //RoleDAO.changeName(r,"Pupupipi");
        //RoleDAO.changeLevel(r, 1);
        RoleDAO.changePriority(r, 1);
        List<Role> roles= new ArrayList<>();
        roles.addAll(RoleDAO.getAllRole());
        for(Role rl: roles){
            System.out.println(rl.getRoleName());
        }
        /*
        Unit CA= UnitPrompt(0);
        System.out.println(CA.showDetails());

        //Make Units
        CompoundUnitFactory c= new CompoundUnitFactory();
        Unit CA= c.createUnit("Consiglio di Amministrazione", 0);
        Unit CTS= c.createUnit("Comitato Tecnico Scientifico", 1);
        Unit Acq= c.createUnit("Acquisti", 1);
        Unit Prod= c.createUnit("Produzione", 1);
        Unit AV= c.createUnit("Area Vendite", 1);
        Unit RS= c.createUnit("Ricerca e Sviluppo", 2);
        Unit MK= c.createUnit("Marketing", 2);
        Unit CC= c.createUnit("Customer Care", 2);
        //SottoUnità
        CA.addSubUnit(CTS); CA.addSubUnit(Acq); CA.addSubUnit(Prod); CA.addSubUnit(AV);
        CTS.addSubUnit(RS);
        AV.addSubUnit(MK); AV.addSubUnit(CC);


        //Make Roles
        List<Unit> UnitList= new ArrayList<>();
        UnitList.add(CA);
        int maxLevel=maxLevel(UnitList, CA.getLevel());
        //System.out.println(maxLevel);

        List<Role> roles= new ArrayList<>();
        roles.addAll(makeRoles(maxLevel));

        //Make Employees
        List<Employee> employeesList= new ArrayList<>();
        makeEmployee(UnitList, roles, employeesList, 0);
        for(Employee e: employeesList){
            System.out.println(e.getName());
        }

        Employee e1 = new Employee("159", "Mario Verdi");
        Employee e2= new Employee("753", "Pedro Pascal");
        Employee e3= new Employee("741","Luigi Armando");
        e1.assignRole(AV, r1); e1.assignRole(CA, r2);
        e2.assignRole(RS, r3); e2.assignRole(CTS, r1);
        e3.assignRole(MK, r4);

        //Struttura dati MinHeap
        List<Unit> UnitList= new ArrayList<>();
        UnitList.add(CA); UnitList.add(CTS); UnitList.add(Acq); UnitList.add(Prod);
        UnitList.add(AV); UnitList.add(RS); UnitList.add(MK); UnitList.add(CC);
        mergeSort(UnitList);

        List<Employee> EmployeeList= new ArrayList<>();
        EmployeeList.add(e1);
        EmployeeList.add(e2);
        EmployeeList.add(e3);

        List<Unit> root= new ArrayList<>();
        root.add(UnitList.get(0));
        System.out.println(printOrganigram(root, EmployeeList, 0));
        */
    }

    public static Unit UnitPrompt(int level){
        //System.out.println("ORGANIGRAMMA AZIENDALE");
        CompoundUnitFactory factory= new CompoundUnitFactory();
        @SuppressWarnings("resource")
        Scanner sc= new Scanner(System.in);
        String unitName;
        String inputString;
        //String inputString2;
        int numUnit;
        Unit unit;

        System.out.println("Livello Attuale: "+level);
        System.out.println("Crea Unità: ");
        System.out.print("Nome: ");
        unitName=sc.nextLine();
        unit= factory.createUnit(unitName, level);
        System.out.print("Questa unità ha sotto unità? (SI/NO): ");
        inputString= sc.nextLine();
        if(inputString.equals("SI")){
            System.out.print("Quante sotto unità possiede?: ");
            numUnit=sc.nextInt();
            sc.nextLine();
            while (numUnit!=0) {
                unit.addSubUnit(UnitPrompt(level+1));
                numUnit--;
            }
        }
        else if(inputString.equals("NO")){
            sc.close(); //aggiunto per test
            return unit;
        }
        else
            System.out.println("Non sai scrivere");
        sc.close(); //aggiunto per test
        return unit;
    }

    public static int maxLevel(List<Unit> list, int max){
        if(list.isEmpty()){
            return max;
        }
        int unitLevel;
        for(Unit unit: list){
            unitLevel=unit.getLevel();
            if(unitLevel>max){
                max=unitLevel;
            }
            else{
                max= maxLevel(unit.getSubUnits(), max);
            }
        }
        return max;
    }

    public static List<Role> makeRoles(int maxLevel){
        List<Role> ris= new ArrayList<>();
        Role role;
        boolean continua= true;
        boolean error=true;
        Scanner sc= new Scanner(System.in);

        String RoleName;
        int RoleLevel;
        int RolePriority;
        String next;
        while(continua){
            System.out.print("Nome Ruolo: ");
            RoleName=sc.nextLine();
            do{
                System.out.print("Livello Ruolo (max "+maxLevel+"): ");
                RoleLevel=sc.nextInt();
                sc.nextLine();
                if(RoleLevel>maxLevel){
                    System.out.println("Questo livello non esiste, inserisci un nuovo livello"); error=true;
                }
                else
                    error=false;
            }while(error==true);
            System.out.print("Priorità Ruolo: ");
            RolePriority=sc.nextInt();
            sc.nextLine();
            role= new Role(RoleName, RoleLevel, RolePriority);
            ris.add(role);
            System.out.println("Vuoi inserire altri ruoli? (SI/NO)");
            next=sc.nextLine();
            if(next.equals("NO"))
                continua=false;
        }
        sc.close();
        return ris;
    }

    public static List<Employee> makeEmployee(List<Unit> units, List<Role> roles, List<Employee> res, int i){
        if(i>=units.size())
            return res;
        boolean keepGoing=true;
        Employee e;
        Scanner sc= new Scanner(System.in);
        long id; String name; int roleIndex; String choice;
        System.out.println("Aggiungi dipendenti per l'unità "+units.get(i).getName());
        while (keepGoing) {
            System.out.print("ID: ");
            id= sc.nextInt();
            sc.nextLine();
            System.out.print("Nome: ");
            name= sc.nextLine();

            System.out.print("Elenco ruoli assegnabili: ");
            for(int j=0; j<roles.size();j++){
                System.out.println("    "+j+")"+roles.get(j).getRoleName()+",");
            }
            System.out.print("Assegna un ruolo tramite l'indice: ");
            roleIndex=sc.nextInt();
            sc.nextLine();
            e= new Employee(id, name);
            e.assignRole(units.get(i), roles.get(roleIndex));
            res.add(e);

            System.out.print("Vuoi aggiungere un altro dipendente per questa unità? (SI/NO): ");
            choice= sc.nextLine();
            if(choice.equals("NO") ){
                if(!(units.get(i).getSubUnits().isEmpty())){
                    makeEmployee(units.get(i).getSubUnits(), roles, res,0);
                }
                makeEmployee(units, roles, res, i+1);
                keepGoing=false;
            }
            else{
                keepGoing=true;
            }
        }
        sc.close();
        return res;
    }

    //print
    public static String printOrganigram(List<Unit> units, List<Employee> employees, int i){
        StringBuilder sb= new StringBuilder();
        if(i>=units.size())
            return sb.toString();
        Unit u= units.get(i);
        sb.append("Unit: "+u.getName()+", Level: "+u.getLevel()+": ");
        //sb.append(u.showDetails());
        sb.append("Employees: "+getEmployee(u, employees)+"\n");
        if(!(u.getSubUnits().isEmpty())){
            sb.append(printOrganigram(u.getSubUnits(), employees,0));
        }
        sb.append(printOrganigram(units, employees, i+1));
        return sb.toString();
    }

    private static String getEmployee(Unit units, List<Employee> employees){
        StringBuilder sb= new StringBuilder();
        for(Employee e: employees){
            for(Map.Entry<Unit,Role> entry: e.roles.entrySet()){
                if(entry.getKey()==units){
                    sb.append(e.getId()+": "+e.getName()+";");
                }
            }
        }
        return sb.toString();
    }
}
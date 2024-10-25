package com.example.organigramma.UserInterface;

import com.example.organigramma.Composite.*;
import com.example.organigramma.Singleton.*;
import com.example.organigramma.DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Scene2Controller {

    //MainScene Section
    @FXML
    Label nameLabel;
    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuItem newItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private AnchorPane scenePane;
    //MainScene Section End

    //NewOrg Section
    @FXML
    private AnchorPane OrgNamePane;
    //TextField Section
    @FXML
    private TextField OrgName;
    //units
    @FXML
    private TextField UnitName;
    @FXML
    private TextField UnitLevel;
    @FXML
    private TextField RootUnitName;
    @FXML
    private TextField SubUnitName;
    @FXML
    private TextField SubUnitLevel;
    @FXML
    private Label UnitListLabel;
    //emploees
    @FXML
    private TextField EmployeeID;
    @FXML
    private TextField EmployeeName;
    //roles
    @FXML
    private TextField RoleName;
    @FXML
    private TextField RoleLevel;
    @FXML
    private TextField RolePriority;
    //relation
    @FXML
    private TextField EmpRelationTF;
    @FXML
    private TextField UnitRelationTF;
    @FXML
    private TextField RoleRelationTF;
    @FXML
    private Label EmpRelList;
    @FXML
    private Label UnitRelList;
    @FXML
    private Label RoleRelList;
    //Button Section
    @FXML
    private Button NextUnitButton;
    @FXML
    private Button NextRoleButton;
    @FXML
    private Button EndButton;
    @FXML
    private Button AddUnitButton; private boolean unclickable=false;
    @FXML
    private Button AddSubUnitButton;
    @FXML
    private Button AddEmployeeButton;
    @FXML
    private Button AddRelationButton;
    //Organigram Area Section
    @FXML
    private Pane OrgChartPane;

    //Variables Section
    private User user;
    private OrgChart orgChart;
    private CompoundUnit unit;
    private List<Employee> employees= new LinkedList<>();
    private List<Role> roles= new LinkedList<>();

    //NewOrg Section End

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String USER_NAME;
    private String PASSWORD;

    public void displayName(String userName, String password){
        USER_NAME = userName;
        PASSWORD = password;
        nameLabel.setText("Hello: "+userName+" your password is: "+password);
    }

    //NewOrg Section
    public void newOrg(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/NewOrgScene.fxml"));
        root=loader.load();

        Scene2Controller controller = loader.getController();
        controller.setUser(USER_NAME,PASSWORD);
        //nameLabel.setText("Hello: "+USER_NAME+" your password is: "+PASSWORD);

        stage = (Stage) scenePane.getScene().getWindow(); // Ottieni lo Stage dalla scenePane
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setUser(String userName, String password){
        this.USER_NAME = userName;
        this.PASSWORD = password;
        user=new User(USER_NAME,PASSWORD);
    }

    public void NextUnit(ActionEvent event) throws IOException {    //Questo pulsante porta alla sezione per inserire le unità
        String name = OrgName.getText();
        user = new User(USER_NAME, PASSWORD);
        orgChart= new OrgChart(name, user);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddUnitScene.fxml"));
        root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setOrgChart(orgChart);
        controller.setUser(USER_NAME,PASSWORD);
        //System.out.println(user.getName());
        OrgChartDAO.addOrgChart(orgChart);  //aggiungo al database

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setOrgChart(OrgChart orgChart){
        this.orgChart=orgChart;
    }

    //NewOrg Section (UnitScene Section)
    public void AddUnit(ActionEvent actionEvent) {
        //non stai dando la possibilità all'utente di creare più root unit
        String unitName = UnitName.getText();
        int unitLevel = Integer.parseInt(UnitLevel.getText());
        unit= new CompoundUnit(unitName, unitLevel);
        UnitDAO.addUnit(unit);
        UnitList.getInstance().add(unit.getName());
        UnitListLabel.setText(UnitList.getInstance().toString());
        System.out.println(UnitList.getInstance().getUnitsNames().getFirst());
        unclickable=true;
        AddUnitButton.setDisable(unclickable);
    }

    public void AddSubUnit(ActionEvent actionEvent) {
        String rootUnit = RootUnitName.getText();
        String subUnitName = SubUnitName.getText();
        int subUnitLevel = Integer.parseInt(SubUnitLevel.getText());

        CompoundUnit subUnit= new CompoundUnit(subUnitName, subUnitLevel);

        //In base alla scelta dell'utente aggiungeremo un unità come sotto-unità di un'altra specificata da lui
        if(rootUnit.equals(unit.getName())){
            try{
                UnitDAO.addUnit(subUnit);
                unit.addSubUnit(subUnit);
                UnitList.getInstance().add(subUnit.getName());
                UnitListLabel.setText(UnitList.getInstance().toString());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else{
            try{
                setParent(unit.getSubUnits(),subUnit,rootUnit,0).addSubUnit(subUnit);
                UnitList.getInstance().add(subUnit.getName());
                UnitListLabel.setText(UnitList.getInstance().toString());
                UnitDAO.addUnit(subUnit);
            } catch (Exception e) {
                e.printStackTrace();
                UnitListLabel.setText("Errore nell'inserimento dell'unità");
                UnitListLabel.setStyle("-fx-text-fill: red;");
            }
        }
        SubUnitName.clear();
        SubUnitLevel.clear();

    }
    private CompoundUnit setParent(List<CompoundUnit> units, CompoundUnit subUnit,String rootUnit, int i){
        if(i>=units.size())
            return null;
        if(units.get(i).getName().equals(rootUnit)){
            return units.get(i);
        }
        if(units.get(i).hasSubUnit()){
            setParent(units.get(i).getSubUnits(),subUnit,rootUnit,0);
        }
        return setParent(units,subUnit,rootUnit,i+1);
    }
    public void NextRole(ActionEvent event) throws IOException, SQLException {  //Questo pulsante porta alla sezione per inserire i ruoli
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddRoleScene.fxml"));
        root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setUnit(unit);
        controller.setOrgChart(orgChart);
        controller.setUser(USER_NAME,PASSWORD);
        //System.out.println(unit.getName());

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setUnit(CompoundUnit unit){
        this.unit=unit;
    }
    //NewOrg Section (RoleScene Section)
    public void AddRole(ActionEvent actionEvent) {
        String roleName = RoleName.getText();
        int roleLevel = Integer.parseInt(RoleLevel.getText());
        int rolePriority = Integer.parseInt(RolePriority.getText());

        Role role= new Role(roleName, roleLevel, rolePriority);
        RoleList.getInstance().add(role.getName());
        RoleDAO.addRole(role);
        roles.add(new Role(roleName, roleLevel, rolePriority));

        RoleName.clear();
        RoleLevel.clear();
        RolePriority.clear();
    }

    public void NextEmployee(ActionEvent event) throws IOException {    //Questo pulsante porta alla sezione per inserire i dipendenti
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddEmployeeScene.fxml"));
        root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setRoles(roles);
        controller.setUnit(unit);
        controller.setOrgChart(orgChart);
        controller.setUser(USER_NAME,PASSWORD);
        //System.out.println(user.getName());
        //System.out.println(orgChart.getName());
        //System.out.println(unit.getName());
        //System.out.println(roles.size());

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setRoles(List<Role> roles){
        this.roles=roles;
    }
    //NewOrg Section (Emplyee Section)
    public void AddEmplyee(ActionEvent actionEvent) {
        int employeeID = Integer.parseInt(EmployeeID.getText());
        String employeeName = EmployeeName.getText();

        Employee emp= new Employee(employeeID,employeeName,orgChart);
        EmployeeList.getInstance().add(emp.getName());
        EmployeeDAO.addEmployee(emp);
        employees.add(emp);
        EmployeeID.clear();
        EmployeeName.clear();

        //System.out.println(employeeName);
    }
    public void NextRelation(ActionEvent event) throws IOException {    //Questo bottone porta alla sezione per inserire le relazioni
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddRelationScene.fxml"));
        root = loader.load();
        Scene2Controller controller = loader.getController();
        controller.setEmployees(employees);
        controller.setRoles(roles);
        controller.setUnit(unit);
        controller.setOrgChart(orgChart);
        controller.setUser(USER_NAME,PASSWORD);
        controller.displayName(USER_NAME,PASSWORD);

        EmpRelList.setText(EmployeeList.getInstance().toString());
        UnitRelList.setText(UnitList.getInstance().toString());
        RoleRelList.setText(RoleList.getInstance().toString());

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AddRelation(ActionEvent actionEvent) {

    }

    public void EndAction(ActionEvent event) throws IOException, SQLException {
        //unclickable=false;
        //AddUnitButton.setDisable(unclickable);
        //test();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        root = loader.load();
        Scene2Controller controller = loader.getController();
        controller.setEmployees(employees);
        controller.setRoles(roles);
        controller.setUnit(unit);
        controller.setOrgChart(orgChart);
        controller.setUser(USER_NAME,PASSWORD);
        controller.displayName(USER_NAME,PASSWORD);

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setEmployees(List<Employee> employees){
        this.employees=employees;
    }
    //NewOrg Section End

    public void save(){
        System.out.println("Save Org");
    }

    public void close(ActionEvent event){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close");
        alert.setHeaderText("You're about to close the program!\n");
        alert.setContentText("Do you want to save before anding?");

        if(alert.showAndWait().get()== ButtonType.OK){
            stage=(Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }

    private void test(){
        System.out.println(orgChart.getName());
        unit.showDetails();
        for (Employee emp : employees) {
            emp.showRoles();
        }

    }
}

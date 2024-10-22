package com.example.organigramma.UserInterface;

import com.example.organigramma.Composite.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
    @FXML
    private TextField UnitName;
    @FXML
    private TextField UnitLevel;
    @FXML
    private TextField SubUnitName;
    @FXML
    private TextField SubUnitLevel;
    @FXML
    private TextField EmployeeID;
    @FXML
    private TextField EmployeeName;
    @FXML
    private TextField EmployeeRole;
    @FXML
    private TextField RoleName;
    @FXML
    private TextField RoleLevel;
    @FXML
    private TextField RolePriority;
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

        stage = (Stage) scenePane.getScene().getWindow(); // Ottieni lo Stage dalla scenePane
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setUser(String userName, String password){
        this.USER_NAME = userName;
        this.PASSWORD = password;
    }

    public void NextUnit(ActionEvent event) throws IOException {
        String name = OrgName.getText();
        user = new User(USER_NAME, PASSWORD);
        orgChart= new OrgChart(name, user);
        System.out.println(user.getName());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddUnitScene.fxml"));
        root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setOrgChart(orgChart);

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
        //attento stai dando la possibilità all'utente di creare più root unit
        String unitName = UnitName.getText();
        int unitLevel = Integer.parseInt(UnitLevel.getText());
        unit= new CompoundUnit(unitName, unitLevel);

        unclickable=true;
        AddUnitButton.setDisable(unclickable);
    }

    public void AddSubUnit(ActionEvent actionEvent) {
        String subUnitName = SubUnitName.getText();
        int subUnitLevel = Integer.parseInt(SubUnitLevel.getText());

        CompoundUnit subUnit= new CompoundUnit(subUnitName, subUnitLevel);
        unit.addSubUnit(subUnit);

        SubUnitName.clear();
        SubUnitLevel.clear();
    }
    public void NextRole(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddRoleScene.fxml"));
        root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setUnit(unit);

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
        roles.add(new Role(roleName, roleLevel, rolePriority));

        RoleName.clear();
        RoleLevel.clear();
        RolePriority.clear();
    }

    public void NextEmployee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddEmployeeScene.fxml"));
        root = loader.load();

        Scene2Controller controller = loader.getController();
        controller.setRoles(roles);

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
        employees.add(emp);

        System.out.println(employeeName);
    }

    public void EndAction(ActionEvent event) throws IOException, SQLException {
        //unclickable=false;
        //AddUnitButton.setDisable(unclickable);
        test();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        root = loader.load();
        Scene2Controller controller = loader.getController();
        controller.setEmployees(employees);
        //displayName(USER_NAME,PASSWORD);

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

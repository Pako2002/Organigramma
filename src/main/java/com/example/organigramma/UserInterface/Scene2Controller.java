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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    @FXML
    private Label ErrorLabel;
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
        System.out.println(EmployeeList.getInstance().toString());
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

        controller.EmpRelList.setText(EmployeeList.getInstance().toString());
        controller.UnitRelList.setText(UnitList.getInstance().toString());
        controller.RoleRelList.setText(RoleList.getInstance().toString());

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AddRelation(ActionEvent actionEvent) {
        String empName= EmpRelationTF.getText();
        String unitName= UnitRelationTF.getText();
        String roleName= RoleRelationTF.getText();

        Employee emp= EmployeeDAO.getEmployee(empName);
        Unit unit= UnitDAO.getUnit(unitName);
        Role role= RoleDAO.getRole(roleName);
        if(unit.getLevel()==role.getLevel()){
            EmployeeDAO.addEmployeeRole(emp,unit,role);
            RoleDAO.addOrgChartUnitsRoles(orgChart,unit,role);
            ErrorLabel.setText("Everything ok");
            ErrorLabel.setStyle("-fx-text-fill: green;");
        }
        else{
            ErrorLabel.setText("The role must be at the same level as the unit");
            ErrorLabel.setStyle("-fx-text-fill: red;");
        }
        EmpRelationTF.clear();
        UnitRelationTF.clear();
        RoleRelationTF.clear();
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

        if(unit!=null){
            drawOrgChart(controller, unit, 216, 25);
        }

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setEmployees(List<Employee> employees){
        this.employees=employees;
    }
    // Disegna l'organigramma a partire dall'unità radice
    private void drawOrgChart(Scene2Controller controller, CompoundUnit unit, double x, double y) {
        Rectangle rect = new Rectangle(120, 30);
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);

        Label unitLabel = new Label(unit.getName());
        StackPane unitBox = new StackPane();
        unitBox.getChildren().addAll(rect, unitLabel);
        unitBox.setLayoutX(x - rect.getWidth() / 2);
        unitBox.setLayoutY(y);

        controller.OrgChartPane.getChildren().add(unitBox);

        // Evento di clic per mostrare i dettagli dell'unità
        unitBox.setOnMouseClicked((MouseEvent event) -> {
            showUnitDetails(unit);
        });

        double childY = y + 100; // Posizionamento verticale delle sottounità
        double childXStart = x - (unit.getSubUnits().size() * 150) / 2; // Posizionamento orizzontale iniziale

        drawChild(controller,unit.getSubUnits(),x,y,rect,childY,childXStart,0);
    }
    private void drawChild(Scene2Controller controller, List<CompoundUnit> units, double x, double y, Rectangle rect, double childY, double childXStart, int i) {
        if (i >= units.size()) {
            return;
        }

        CompoundUnit child = units.get(i);
        Line line = new Line(x, y + rect.getHeight(), childXStart + 100, childY);
        controller.OrgChartPane.getChildren().add(line);

        drawOrgChart(controller, child, childXStart + 100, childY);

        // Incrementa childXStart dopo aver disegnato il child, per posizionare i successivi
        childXStart += 150;

        // Richiama la funzione ricorsiva per disegnare il prossimo child
        drawChild(controller, units, x, y, rect, childY, childXStart, i + 1);
    }
    // Mostra i dettagli dell'unità in un popup
    private void showUnitDetails(CompoundUnit unit) {
        StringBuilder details = new StringBuilder("Dettagli Unità: " + unit.getName() + "\n");
        details.append("Livello: " + unit.getLevel() + "\n");
        details.append("Sottounità:\n");

        for (CompoundUnit child : unit.getSubUnits()) {
            details.append("- " + child.getName() + "\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dettagli Unità");
        alert.setHeaderText("Informazioni sull'unità: " + unit.getName());
        alert.setContentText(details.toString());
        alert.showAndWait();
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

    public void deleteAccount(ActionEvent event) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("You're about to delete your account!\n");
        alert.setContentText("Everything you have created will be lost and the application will close");

        if(alert.showAndWait().get()== ButtonType.OK){
            List<OrgChart> orgCharts= OrgChartDAO.getAllOrgChart(UserDAO.getID(user.getName()));
            List<Unit> units= new LinkedList<>();
            List<Role> roles= new LinkedList<>();
            List<Employee> employees= new LinkedList<>();
            for(OrgChart orgChart : orgCharts){
                units.addAll(OrgChartDAO.getUnitsFromOUR(orgChart));
                roles.addAll(OrgChartDAO.getRolesFromOUR(orgChart));
                employees.addAll(EmployeeDAO.getAllEmployees(orgChart));
                OrgChartDAO.removeOUR(orgChart);
                OrgChartDAO.removeOrgChart(orgChart);
            }
            for(Employee emp:employees){
                EmployeeDAO.removeEmployeeRole(emp);
                EmployeeDAO.removeEmployee(emp);
            }
            for(Role role : roles) {
                RoleDAO.removeRole(role);
            }
            for(Unit unit : units) {
                UnitDAO.removeUnit(unit);
            }
            UserDAO.removeUser(user);

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

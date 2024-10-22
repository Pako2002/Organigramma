package com.example.organigramma.UserInterface;


import com.example.organigramma.Composite.OrgChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NewOrgController {
    @FXML
    private AnchorPane OrgNamePane;
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
    private Button NextButton;
    private OrgChart orgChart;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void NextUnit(ActionEvent event) throws IOException, SQLException {
        String name = OrgName.getText();
        System.out.println(name);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddUnitScene.fxml"));
        root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void NextEmplRole(ActionEvent event) throws IOException, SQLException {
        String unitName = UnitName.getText();
        int unitLevel = Integer.parseInt(UnitLevel.getText());
        String subUnitName = SubUnitName.getText();
        int subUnitLevel = Integer.parseInt(SubUnitLevel.getText());
        System.out.println(unitName);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/AddEmployeeScene.fxml"));
        root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void EndAction  (ActionEvent event) throws IOException, SQLException {
        String employeeID = EmployeeID.getText();
        String employeeName = EmployeeName.getText();
        String employeeRole = EmployeeRole.getText();
        System.out.println(employeeName);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        root = loader.load();
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

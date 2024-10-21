package com.example.organigramma.UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2Controller {

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayName(String userName, String password){
        nameLabel.setText("Hello: "+userName+" your password is: "+password);
    }

    public void newOrg(ActionEvent event) throws IOException {
        root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/NewOrgScene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("newOrg");
    }

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
}

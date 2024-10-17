package com.example.organigramma.UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Scene2Controller {

    @FXML
    Label nameLabel;
    @FXML
    private MenuItem closeItem;
    @FXML
    private AnchorPane scenePane;

    Stage stage;

    public void displayName(String userName, String password){
        nameLabel.setText("Hello: "+userName+" your password is: "+password);
    }

    public void newOrg(){
        System.out.println("New Org");
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

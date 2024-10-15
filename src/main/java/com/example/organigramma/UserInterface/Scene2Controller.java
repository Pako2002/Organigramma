package com.example.organigramma.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene2Controller {

    @FXML
    Label nameLabel;

    public void displayName(String userName, String password){
        nameLabel.setText("Hello: "+userName+" your password is: "+password);
    }
}

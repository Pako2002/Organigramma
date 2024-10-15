package com.example.organigramma.UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.example.organigramma.DAO.UserDAO;
import com.example.organigramma.Composite.User;

import java.io.IOException;

public class HelloController {

    @FXML
    TextField nameTextField;
    @FXML
    TextField passwordTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException {
        String username = nameTextField.getText();
        String password = passwordTextField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        root = loader.load();

        User user= new User(1,username, password);
        UserDAO.addUser(user);

        Scene2Controller scene2controller = loader.getController();
        scene2controller.displayName(username,password);

        //root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
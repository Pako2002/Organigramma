package com.example.organigramma.UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.example.organigramma.DAO.UserDAO;
import com.example.organigramma.Composite.User;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class HelloController {

    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException, SQLException {
        String username = nameTextField.getText();
        String password = passwordTextField.getText();

        /*
        User user= new User(username, password);
        UserDAO.addUser(user);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        root = loader.load();
        Scene2Controller scene2controller = loader.getController();
        scene2controller.displayName(username,password);

        //root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

        */

        try{
            User user= new User(username, password);
            UserDAO.addUser(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
            root = loader.load();
            Scene2Controller scene2controller = loader.getController();
            scene2controller.displayName(username,password);
            //root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("This user already exist!");
            System.out.println("cacata nel puzzo");
            if(alert.showAndWait().get()== ButtonType.OK){
                //e.printStackTrace();
                nameTextField.clear();
                passwordTextField.clear();
            }
        }
    }

    /*
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
     */
}
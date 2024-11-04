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

public class Scene1Controller {

    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button SigninButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException {
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
        if(UserDAO.getUser(username)!=null && UserDAO.getPassword(username).equals(password)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
            root = loader.load();
            Scene2Controller scene2controller = loader.getController();
            scene2controller.displayName(username,password);
            scene2controller.setUser(username,password);
            //root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Wrong credentials!");
            alert.setContentText("Try to signin or using the right credentials");
            if(alert.showAndWait().get()== ButtonType.OK) {
                //e.printStackTrace();
                nameTextField.clear();
                passwordTextField.clear();
            }
        }

    }

    public void signin(ActionEvent event) throws IOException {
        String username = nameTextField.getText();
        String password = passwordTextField.getText();

        try{
            User user= new User(username, password);
            UserDAO.addUser(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
            root = loader.load();
            Scene2Controller scene2controller = loader.getController();
            scene2controller.displayName(username,password);
            scene2controller.setUser(username,password);
            //root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/Scene2.fxml"));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("This user already exist!");
            alert.setContentText("Try to login or using another username");
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
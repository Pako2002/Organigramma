package com.example.organigramma.UserInterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root=  FXMLLoader.load(getClass().getResource("/com/example/organigramma/Scene1.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            close(stage);
        });
    }
    public void close(Stage stage){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close");
        alert.setHeaderText("You're about to close the program!\n");
        alert.setContentText("Do you want to save before anding?");

        if(alert.showAndWait().get()== ButtonType.OK){
            stage.close();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
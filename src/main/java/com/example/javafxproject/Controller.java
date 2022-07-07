package com.example.javafxproject;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller extends Application {
    @FXML
    private Pane LayoutPane;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("board.fxml"));
        Parent root = fxmlLoader.load();
        LayoutPane = (Pane) root;


        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("test");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
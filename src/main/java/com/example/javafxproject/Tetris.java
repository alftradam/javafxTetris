package com.example.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Tetris extends Application {

    private static final int WIDTH = 250;
    private static final int HEIGHT = 600;
    static final int BRICK_SIZE = 25;
    static ContTest cont;
    static Pane board;
    static Stage window;


    private Image image = new Image(String.valueOf(getClass().getResource("logo arsenal.png")));
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        cont = fxmlLoader.getController();

        board = (Pane) root.getChildren().get(0);
        for (int i=0;i<HEIGHT;i+=BRICK_SIZE){
            for(int j = 0;j<WIDTH;j+=BRICK_SIZE){
                GridPane gridPane1 =new GridPane();
                gridPane1.setPrefHeight(BRICK_SIZE);
                gridPane1.setPrefWidth(BRICK_SIZE);
                gridPane1.setAlignment(Pos.CENTER);
                gridPane1.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
                gridPane1.setBorder(new Border(new BorderStroke(Color.GHOSTWHITE, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, new BorderWidths(0.5))));
                gridPane1.setLayoutX(j);
                gridPane1.setLayoutY(i);
                board.getChildren().add(gridPane1);
            }
        }

        System.out.println(root.getChildren());
        window.setTitle("Tetris");
        window.getIcons().add(image);
        window.setScene(scene);
        window.setResizable(false);
        try {
            gameOn gameOn = new gameOn(cont);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
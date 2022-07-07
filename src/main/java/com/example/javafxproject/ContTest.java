package com.example.javafxproject;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;;

public class ContTest {
    static PauseTransition pauseTransition;
    @FXML
    BorderPane nextBrickShow;
    @FXML
    static Pane pane;
    @FXML
    private GridPane gridPaneOfNextBrick;
    @FXML
    Button startButton;
    @FXML
    Button pauseButton;
    @FXML
    private GridPane gridPaneOfNextBrick2;
    @FXML
    private BorderPane nextBrickShow2;
    @FXML
    private Label levelLabel;
    @FXML
    private Label scoreLabel;
    static Double t = 500.; // ms
    static String name;
    static SequentialTransition transition = new SequentialTransition();
    private OBrick nextOBrick = new OBrick();
    private IBrick nextIbrick = new IBrick();
    private LBrick nextLBrick = new LBrick();
    private JBrick nextJBrick = new JBrick();
    private SBrick nextSBrick = new SBrick();
    private ZBrick nextZBrick = new ZBrick();
    private TBrick nextTBrick = new TBrick();
    private final static Score score=new Score();

    int num = 0;
    int next = 0;
    int next2 = (int)Math.floor(Math.random()*7+1);


    public void drawShape() {
        scoreLabel.setText(String.valueOf(score.getCurrentScore()));
        levelLabel.setText(String.valueOf(score.getLevel()));
        if(num==0 && next ==0){
            num = (int)Math.floor(Math.random()*7+1);
            next = (int)Math.floor(Math.random()*7+1);
            next2 = (int)Math.floor(Math.random()*7+1);
        }else {
            num = next;
            next = next2;
            next2 = (int)Math.floor(Math.random()*7+1);
        }
        pane = Tetris.board;
        switch (num) {
            case 1 -> {
                name = "T";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            case 2 -> {
                name = "I";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            case 3-> {
                name = "L";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            case 4 -> {
                name = "J";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            case 5-> {
                name = "O";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            case 6-> {
                name = "S";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            case 7-> {
                name ="Z";
                new Form(pane,name);
                display(next2, nextBrickShow2, gridPaneOfNextBrick2);
                display(next, nextBrickShow, gridPaneOfNextBrick);
            }
            default -> {
            }
        }
        if(pane.getChildren().get(5).isCache()){
            transition.stop();
        }
    }

    public void display(int num,BorderPane borderPane,GridPane gridPane) {
        borderPane.getChildren().remove(0);
        gridPane.getChildren().clear();
        switch (num){
            case 1->{
                displayNextBrick(gridPane,nextTBrick,"T");
            }
            case 2-> {
                displayNextBrick(gridPane,nextIbrick, "I");
            }
            case 3-> {
                displayNextBrick(gridPane,nextLBrick, "L");
            }
            case 4-> {
                displayNextBrick(gridPane,nextJBrick, "J");
            }
            case 5-> {
                displayNextBrick(gridPane,nextOBrick, "O");
            }
            case 6-> {
                displayNextBrick(gridPane,nextSBrick, "S");
            }
            case 7-> {
                displayNextBrick(gridPane,nextZBrick, "Z");
            }
        }
        borderPane.setCenter(gridPane);
    }

    //next2 Z
    private void displayNextBrick(GridPane gridPane, ZBrick nextZBrick, String name) {
        nextZBrick = new ZBrick();
        int[][] currentShape = nextZBrick.getZ_Brick().get(0);
        drawNextBrick(name, currentShape,gridPane);
    }
    //next2 S
    private void displayNextBrick(GridPane gridPane, SBrick nextSBrick, String name) {
        nextSBrick = new SBrick();
        int[][] currentShape = nextSBrick.getS_Brick().get(0);
        drawNextBrick(name, currentShape,gridPane);
    }
    //next2 L
    private void displayNextBrick(GridPane gridPane, LBrick nextLBrick, String name) {
        nextLBrick = new LBrick();
        int[][] currentShape = nextLBrick.getL_Brick().get(0);
        drawNextBrick(name, currentShape,gridPane);
    }
    //Next J
    private void displayNextBrick(GridPane gridPane, JBrick nextJBrick, String name) {
        nextJBrick = new JBrick();
        int [][]currentShape=nextJBrick.getJ_Brick().get(0);
        drawNextBrick(name, currentShape,gridPane);
    }
    //Next T
    private void displayNextBrick(GridPane gridPane, TBrick nextTBrick, String name) {
        nextTBrick = new TBrick();
        int [][]currentShape=nextTBrick.getT_Brick().get(0);
        drawNextBrick(name, currentShape,gridPane);
    }
    //Next I
    private void displayNextBrick(GridPane gridPane, IBrick nextIbrick, String name) {
        nextIbrick= new IBrick();
        int [][]currentShape=nextIbrick.getBrick().get(0);
        drawNextBrick(name, currentShape,gridPane);
    }
    //Next O
    private void displayNextBrick(GridPane gridPane, OBrick nextOBrick, String name) {
        nextOBrick=new OBrick();
        int [][]currentShape=nextOBrick.getBrick().get(0);
        drawNextBrick(name, currentShape, gridPane);
    }

    private void drawNextBrick(String name, int[][] currentShape,GridPane gridPane) {
        for (int i = 0; i< currentShape.length; i++){
            for (int j = 0; j< currentShape[i].length; j++){
                Rectangle rectangle = new Rectangle(Tetris.BRICK_SIZE-5,Tetris.BRICK_SIZE-5);
                rectangle.setFill(getFIllColor(currentShape[i][j], name));
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                gridPane.add(rectangle,j,i);
            }
        }
    }

    //paint getter
    private Paint getFIllColor(int i, String name) {
        Paint returnPaint = null;
        switch (name){
            case "O"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.PEACHPUFF;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
            case "I"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.DARKORANGE;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
            case "S"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.CHOCOLATE;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
            case "Z"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.DODGERBLUE;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
            case "T"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.MEDIUMVIOLETRED;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
            case "J"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.SPRINGGREEN;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
            case "L"->{
                switch (i) {
                    case 0 -> returnPaint = Color.TRANSPARENT;
                    case 1 -> returnPaint = Color.POWDERBLUE;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }
            }
        }
        return returnPaint;
    }

    private static Rectangle drawRect() {
        Rectangle rectangle = new Rectangle(Tetris.BRICK_SIZE-5,Tetris.BRICK_SIZE-5);
        rectangle.setStyle("");
        rectangle.setStroke(Color.DARKBLUE);
        rectangle.setStrokeWidth(0.5);
        rectangle.setFill(Color.RED);
        rectangle.setOpacity(0.5);
        return rectangle;
    }
    public static void animate(Double t){
        if(transition.getStatus().equals(Animation.Status.RUNNING)){
            transition.stop();
            transition = new SequentialTransition();
        }
        pauseTransition = new PauseTransition(Duration.millis(t));
        pauseTransition.setOnFinished(evt -> Form.brickFalling(pane));
        transition.getChildren().add(pauseTransition);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.play();

    }

    public GridPane getGridPaneOfNextBrick(){
        return this.gridPaneOfNextBrick;
    }

    private void setStartButton(Button startButton){
        startButton.setVisible(false);
    }
    @FXML
    private void keyHandling(KeyEvent event) {
        if(transition.getStatus().equals(Animation.Status.RUNNING)){
            switch (event.getCode()) {
                case LEFT, A -> Form.moveLeft(pane);
                case DOWN,S -> Form.moveDown(pane);
                case SPACE -> Form.directMoveDown(pane);
                case RIGHT,D -> Form.moveRight(pane);
                case UP,W -> Form.isRotatable2(pane, name);

                default -> {
                }
            }
        }else {
            if(event.getCode().equals(KeyCode.SPACE)){
                resume();
            }
        }
    }
    @FXML
    private void pause(){
        transition.pause();
        pauseButton.setDisable(true);
        startButton.setDisable(false);
    }
    @FXML
    private void resume(){
        transition.play();
        pauseButton.setDisable(false);
        startButton.setDisable(true);
    }
}

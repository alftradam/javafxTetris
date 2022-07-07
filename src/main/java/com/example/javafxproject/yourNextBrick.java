package com.example.javafxproject;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class yourNextBrick extends Rectangle {
    static Rectangle BRICK = new Rectangle();
    yourNextBrick(Color color){
        Rectangle brick = new Rectangle();
        BRICK.setWidth(20.0f);
        BRICK.setHeight(20.0f);
        BRICK.setFill(color);
        BRICK.setStroke(Color.LIGHTGOLDENRODYELLOW);
    }
    static Rectangle setNextBrick(Color color){
        BRICK.setWidth(20.0f);
        BRICK.setHeight(20.0f);
        BRICK.setFill(color);
        BRICK.setStroke(Color.LIGHTGOLDENRODYELLOW);
        return BRICK;
    }
    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}

package com.example.javafxproject;

import javafx.scene.text.Text;

public class Score extends Text {
    private String text;
    private static int currentScore=0;
    private static int exp=0;
    private int scoreGain;
    private static int level= 1;
    private static Boolean LEVEL_UP = false;

    public void setScoreGain(int scoreGain) {
        LEVEL_UP=false;
        this.scoreGain=scoreGain;
        exp+=scoreGain;
        currentScore+=scoreGain;
        levelUp();
    }
    public int getLevel() {
        return level;
    }
    public void levelUp(){
        if(exp>=1800){
            level++;
            LEVEL_UP=true;
            exp=0;
        }
    }
    public int getCurrentScore(){
        return currentScore;
    }
    public Boolean isLevelUp() {
        return LEVEL_UP;
    }
    public int getExp() {
        return exp;
    }
}

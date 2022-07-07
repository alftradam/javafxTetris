package com.example.javafxproject;
public class gameOn {
    public gameOn(ContTest cont){
        cont.startButton.setDisable(true);
        cont.drawShape();
        cont.animate(ContTest.t);
    }
}

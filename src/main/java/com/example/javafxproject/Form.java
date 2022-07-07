package com.example.javafxproject;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Form {
    protected static GridPane A ;
    private static Rectangle BrickA;
    private static Rectangle BrickB;
    private static Rectangle BrickC;
    private static Rectangle BrickD;

    protected static int locA = 0;
    protected static int locB = 0;
    protected static int locC = 0;
    protected static int locD = 0;

    private static final int blockX = 10;
    private static final  int blockY = 24;
    private static final int maxIndex = 240;
    protected final String name;
    private static GridPane[] arrGridPane = new GridPane[maxIndex];
    private static boolean [] lineOccupied = new boolean[blockY];
    static ContTest contTest = Tetris.cont;
    private static final Score score = new Score();

    //CONSTRUCTOR
    Form(Pane pane, String name){
        System.out.println("speed = "+ContTest.t);
        System.out.println("LEVEL = "+score.getLevel());
        arrGridPane=createArrGridPane(pane);
        this.name = name;
        if(!pane.getChildren().get(5).isCache()){
            switch (name) {
                case "T" -> T(pane);
                case "I" -> I(pane);
                case "L" -> L(pane);
                case "J"-> J(pane);
                case "O"-> O(pane);
                case "S"-> S(pane);
                case "Z"-> Z(pane);
                default -> {
                }
            }
        }else {
            ContTest.transition.stop();
            contTest.pauseButton.setDisable(true);
            contTest.startButton.setDisable(true);
        }
    }

    private static boolean[] arrBooleanOccupied(GridPane[] arrGridPanePerLine) {
        boolean[] temp = new boolean[blockY];
        int pass;
        for (int i = 0;i<lineOccupied.length;i++){
            pass=i*blockX;
            for (int j=0;j<blockX;j++) {
                temp[i] = arrGridPanePerLine[pass].isCache()&&arrGridPanePerLine[pass+1].isCache()&&arrGridPanePerLine[pass+2].isCache()
                        &&arrGridPanePerLine[pass+3].isCache()&&arrGridPanePerLine[pass+4].isCache()&&arrGridPanePerLine[pass+5].isCache()
                        &&arrGridPanePerLine[pass+6].isCache()&&arrGridPanePerLine[pass+7].isCache()&&arrGridPanePerLine[pass+8].isCache()
                        &&arrGridPanePerLine[pass+9].isCache();
            }
        }
        return temp;
    }

    public static void brickFalling(Pane pane) {
            if(locA+blockX<maxIndex&&locB+blockX<maxIndex&&locC+blockX<maxIndex&&locD+blockX<maxIndex
                    &&!pane.getChildren().get(locA+blockX).isCache()
                    &&!pane.getChildren().get(locB+blockX).isCache()
                    &&!pane.getChildren().get(locC+blockX).isCache()
                    &&!pane.getChildren().get(locD+blockX).isCache()){

                A = (GridPane) pane.getChildren().get(locB+=blockX);
                A.add(BrickB,0,0);
                A.getChildren().get(0).setCache(true);

                A = (GridPane) pane.getChildren().get(locA+=blockX);
                A.add(BrickA,0,0);
                A.getChildren().get(0).setCache(true);

                A = (GridPane) pane.getChildren().get(locC+=blockX);
                A.add(BrickC,0,0);
                A.getChildren().get(0).setCache(true);

                A = (GridPane) pane.getChildren().get(locD+=blockX);
                A.add(BrickD,0,0);
                A.getChildren().get(0).setCache(true);

            }
            else {
                pane.getChildren().get(locA).setCache(true);
                pane.getChildren().get(locB).setCache(true);
                pane.getChildren().get(locC).setCache(true);
                pane.getChildren().get(locD).setCache(true);

                locA = 0;locB=0;locC=0;locD=0;
                lineOccupied = arrBooleanOccupied(arrGridPane);
                isLineOccupied(pane);
                contTest.drawShape();

            }
    }

    private static void isLineOccupied(Pane pane) {
        int removedLine=0;
        for (int i=0;i<lineOccupied.length;i++){
            if(lineOccupied[i]){
                clearLine(pane,i*blockX);
                shiftDown(pane,i*blockX);
                removedLine++;
            }
        }
        scoring(removedLine);
        if(score.isLevelUp()){
            System.out.println(score.isLevelUp());
            double level = score.getLevel();
            ContTest.animate(ContTest.t/=(1.05+(level/100)));
        }else {
            System.out.println(score.isLevelUp());
            ContTest.animate(ContTest.t);
        }
    }

    private static void scoring(int removedLine) {
        switch (removedLine){
            case 1 -> score.setScoreGain(removedLine*200);
            case 2-> score.setScoreGain(removedLine*400);
            case 3-> score.setScoreGain(removedLine*600);
            case 4-> score.setScoreGain(removedLine*1000);
        }
    }

    private static void shiftDown(Pane pane, int i) {
        for (int row = i; row>0; row-=blockX){
            moveRowDown(pane, row);
        }
    }

    private static void moveRowDown(Pane pane, int row) {
        for (int col= row; col<row + blockX; col++){
            if(pane.getChildren().get(col).isCache()){
                Rectangle shiftedRect = (Rectangle) arrGridPane[col].getChildren().get(0);
                shiftUpperBrick(pane, col, shiftedRect);
            }
        }
    }

    private static void shiftUpperBrick(Pane pane, int loc, Rectangle brick) {
        A = (GridPane) pane.getChildren().get(loc);
        A.getChildren().remove(0);
        A = (GridPane) pane.getChildren().get(loc+blockX);
        A.add(brick,0,0);
        if(pane.getChildren().get(loc).isCache()){
            pane.getChildren().get(loc).setCache(false);
            pane.getChildren().get(loc+blockX).setCache(true);
        }
    }

    private static void clearLine(Pane pane, int i) {
        for (int col = 0; col<blockX;col++){
            arrGridPane[col+i].getChildren().remove(0);
            pane.getChildren().get((col+i)).setCache(false);
        }
    }

    private static GridPane[] createArrGridPane(Pane pane) {
        GridPane[] arrGridPane = new GridPane[maxIndex];
        for(int i =0; i<arrGridPane.length;i++){
            A=(GridPane) pane.getChildren().get(i);
            arrGridPane[i]=A;
        }
        for (int i=0; i<arrGridPane.length;i++){
            arrGridPane[i].getChildren().getClass();
        }
        return arrGridPane;
    }


    public static void moveLeft(Pane pane) {
        if(locA%blockX!=0&&locB%blockX!=0&&locC%blockX!=0&&locD%blockX!=0
            &&!pane.getChildren().get(locA-1).isCache()
            &&!pane.getChildren().get(locB-1).isCache()
            &&!pane.getChildren().get(locC-1).isCache()
            &&!pane.getChildren().get(locD-1).isCache()

//            &&!pane.getChildren().get(locA+blockX).isCache()
//            &&!pane.getChildren().get(locB+blockX).isCache()
//            &&!pane.getChildren().get(locC+blockX).isCache()
//            &&!pane.getChildren().get(locD+blockX).isCache()
            ){

            locA = handleLeft(pane, locA, BrickA);
            locB = handleLeft(pane, locB, BrickB);
            locC = handleLeft(pane, locC, BrickC);
            locD = handleLeft(pane, locD, BrickD);

//            dispLoc();
        }

    }
    public static void moveRight(Pane pane) {
        if(locA%blockX!=blockX-1&&locB%blockX!=blockX-1&&locC%blockX!=blockX-1&&locD%blockX!=blockX-1
            &&!pane.getChildren().get(locA+1).isCache()
            &&!pane.getChildren().get(locB+1).isCache()
            &&!pane.getChildren().get(locC+1).isCache()
            &&!pane.getChildren().get(locD+1).isCache()
            ){

            locA = handleRight(pane,locA,BrickA);
            locB = handleRight(pane,locB,BrickB);
            locC = handleRight(pane,locC,BrickC);
            locD = handleRight(pane,locD,BrickD);

//            dispLoc();
        }

    }
    public static void moveDown(Pane pane){
        if(locA+blockX<maxIndex&&locB+blockX<maxIndex&&locC+blockX<maxIndex&&locD+blockX<maxIndex&&
                !pane.getChildren().get(locA+blockX).isCache()
                &&!pane.getChildren().get(locB+blockX).isCache()
                &&!pane.getChildren().get(locC+blockX).isCache()
                &&!pane.getChildren().get(locD+blockX).isCache()){

            locA = handleDown(pane, locA, BrickA);
            locB = handleDown(pane, locB, BrickB);
            locC = handleDown(pane, locC, BrickC);
            locD = handleDown(pane, locD, BrickD);
        }
            score.setScoreGain(1);
    }
    public static void moveUp(Pane pane){
        if(locA+blockX<maxIndex&&locB+blockX<maxIndex&&locC+blockX<maxIndex&&locD+blockX<maxIndex&&
                !pane.getChildren().get(locA-blockX).isCache()
                &&!pane.getChildren().get(locB-blockX).isCache()
                &&!pane.getChildren().get(locC-blockX).isCache()
                &&!pane.getChildren().get(locD-blockX).isCache()){

            locA = handleUp(pane, locA, BrickA);
            locB = handleUp(pane, locB, BrickB);
            locC = handleUp(pane, locC, BrickC);
            locD = handleUp(pane, locD, BrickD);
//            dispLoc();
        }
    }

    private static void dispLoc() {
        System.out.printf("[%d, %d], [%d, %d], [%d, %d], [%d, %d]\n",
                locA/blockX, locA%blockX,
                locB/blockX, locB%blockX,
                locC/blockX, locC%blockX,
                locD/blockX, locD%blockX
                );
    }

    private static int handleRight(Pane pane, int loc, Rectangle brick) {
        A = (GridPane) pane.getChildren().get(loc);
        A.getChildren().remove(0);
        A = (GridPane) pane.getChildren().get(loc+=1);
        A.add(brick,0,0);
        return loc;
    }
    private static int handleLeft(Pane pane, int loc, Rectangle brick) {
            A = (GridPane) pane.getChildren().get(loc);
            A.getChildren().remove(0);
            A = (GridPane) pane.getChildren().get(loc-=1);
            A.add(brick,0,0);
        return loc;
    }
    private static int handleDown(Pane pane, int loc, Rectangle brick) {
        A = (GridPane) pane.getChildren().get(loc);
        A.getChildren().remove(0);
        A = (GridPane) pane.getChildren().get(loc+=blockX);
        A.add(brick,0,0);
        return loc;
    }
    private static int handleUp(Pane pane, int loc, Rectangle brick) {
        A = (GridPane) pane.getChildren().get(loc);
        A.getChildren().remove(0);
        A = (GridPane) pane.getChildren().get(loc-=blockX);
        A.add(brick,0,0);
        return loc;
    }
    public static void directMoveDown(Pane pane) {
        while (locA+blockX<maxIndex&&locB+blockX<maxIndex&&locC+blockX<maxIndex&&locD+blockX<maxIndex&&
                !pane.getChildren().get(locA+blockX).isCache()
                &&!pane.getChildren().get(locB+blockX).isCache()
                &&!pane.getChildren().get(locC+blockX).isCache()
                &&!pane.getChildren().get(locD+blockX).isCache()){
            moveDown(pane);
        }

    }

    public static void rotate(Pane pane) {
        int[] verLoc = {locA/blockX, locB/blockX, locC/blockX, locD/blockX};
        int[] horLoc = {locA%blockX, locB%blockX, locC%blockX, locD%blockX};

        int[] axis = {horLoc[0], verLoc[0]};

        int[][] transformX = new int[4][4];
        transformX[0][0] = 0; // cos(90)
        transformX[0][1] = -1; // -sin(90)
        transformX[1][0] = 1; // sin(90)
        transformX[1][1] = 0; // cos(90)

        int[] newHorLoc = new  int[4];
        int[] newVerLoc = new  int[4];

        for (int i = 0; i < 4; i++) {
            horLoc[i] = horLoc[i]-axis[0];
            verLoc[i] = verLoc[i]-axis[1];

            newHorLoc[i] = verLoc[i] * transformX[0][1]; // x*cos(-90)+y*(-sin(-90))
            newVerLoc[i] = horLoc[i] * transformX[1][0]; // x*sin(-90)+y*cos(-90)

            newHorLoc[i] = newHorLoc[i]+ axis[0];
            newVerLoc[i] = newVerLoc[i]+ axis[1];

        }

        locB = handleRotation(pane, locB, newHorLoc[1], newVerLoc[1], BrickB);
        locC = handleRotation(pane, locC, newHorLoc[2], newVerLoc[2], BrickC);
        locD = handleRotation(pane, locD, newHorLoc[3], newVerLoc[3], BrickD);

//        dispLoc();
    }

    //Checking whether those brick rotatable or not
    static void isRotatable2(Pane pane, String type){
        if(!pane.getChildren().get(locA+blockX).isCache()
                &&!pane.getChildren().get(locB+blockX).isCache()
                &&!pane.getChildren().get(locC+blockX).isCache()
                &&!pane.getChildren().get(locD+blockX).isCache()){

            if(Objects.equals(type,"L")){
                rotateL(pane);
            } else if (Objects.equals(type,"I")) {
                rotateI(pane);
            } else if (Objects.equals(type,"J")) {
                rotateJ(pane);
            } else if (Objects.equals(type,"T")) {
                rotateT(pane);
            } else if (Objects.equals(type,"S")) {
                rotateS(pane);
            } else if (Objects.equals(type,"Z")) {
                rotateZ(pane);
            } else {
                System.out.println(type);
            }
        }
    }
    //Rotate Z (logic per case)
    private static void rotateZ(Pane pane) {
        if(locC%blockX==locA%blockX){
            //Z tidur
            if(locA/blockX<1&&locB/blockX<1){
                System.out.println("Z tidur mepet atas");
                System.out.println("Z move 1");
                moveDown(pane);rotate(pane);
            }else {
                System.out.println("Z tidur ditengah");
                System.out.println("Z move 2");
                rotate(pane);
            }
        }else {
            //Z diri
            if(locB%blockX<1&&locA%blockX<1){
                System.out.println("Z diri, mepet kiri");
                if(pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locD+1).isCache()){

                }else {
                    System.out.println("Z move 3");
                    moveRight(pane);rotate(pane);
                }
            } else if (locB%blockX>8&&locA%blockX>8) {
                System.out.println("Z diri, mepet kanan");
                if(pane.getChildren().get(locB-2).isCache()||pane.getChildren().get(locC-1).isCache()||pane.getChildren().get(locD-1).isCache()){

                }else {
                    System.out.println("Z move 4");
                    moveLeft(pane);rotate(pane);
                }
            } else {
                System.out.println("Z diri, ditengah");
                if(locA+blockX==locB){
                    System.out.println("A diatas B");
                    if(pane.getChildren().get(locB-1).isCache()||pane.getChildren().get(locA-1).isCache()){
                        if(pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locD+1).isCache()){

                        }else {
                            if(locA%blockX>7&&locB%blockX>7){

                            }else {
                                System.out.println("Z move 7");
                                moveRight(pane);rotate(pane);
                            }
                        }
                    }else {
                        System.out.println("Z move 5");
                        rotate(pane);
                    }
                }else if (locA-blockX==locB){
                    System.out.println("A dibawah B");
                    if(pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locD+2).isCache()){
                        if(pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locC-1).isCache()||pane.getChildren().get(locB-2).isCache()){

                        }else {
                            if(locA%blockX==1&&locB%blockX==1){

                            }else {
                                System.out.println("Z move 6");
                                moveLeft(pane);rotate(pane);
                            }
                        }
                    }
                    else {
                        System.out.println("Z move 8");
                        rotate(pane);
                    }
                }else {
                    System.out.println("cek");
                    rotate(pane);
                }
            }
        }
    }
    //Rotate S (logic per case)
    private static void rotateS(Pane pane) {
        //S diri
        if(locA%blockX==locB%blockX){
            System.out.println("S diri");
            if(locA%blockX<1){
                if(pane.getChildren().get(locD+1).isCache()||pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locB+2).isCache()){

                }else {
                    System.out.println("S move 1 mepet kiri");
                    moveRight(pane);rotate(pane);
                }
            } else if (locA%blockX>8) {
                if(pane.getChildren().get(locC-1).isCache()||pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locB-2).isCache()){

                }else {
                    System.out.println("S move 2 mepet kanan");
                    moveLeft(pane);rotate(pane);
                }
            } else {
                System.out.println("");
                if(locA/blockX<locB/blockX){
                    System.out.println("A diatas B");
                    if(pane.getChildren().get(locA+1).isCache()){
                        if(pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locC-1).isCache()||pane.getChildren().get(locB-2).isCache()
                        ||locA%blockX==1||locB%blockX==1){

                        }else {
                            System.out.println("S move 7");
                            moveLeft(pane);rotate(pane);
                        }
                    }else {
                        if(pane.getChildren().get(locB-2).isCache()){

                        }else {
                            if (pane.getChildren().get(locB+1).isCache()&&locC%blockX<1){

                            }else {
                                System.out.println("S move 8");
                                rotate(pane);
                            }
                        }
                    }
                }else {
                    System.out.println("A dibawah B");
                    if(pane.getChildren().get(locD-2).isCache()){
                        if(pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locD+1).isCache()){

                        }else {
                            if(locC%blockX==9){

                            }else {
                                System.out.println("S move 11");
                                moveRight(pane);rotate(pane);
                            }
                        }
                    }else {
                        System.out.println("S move 12");
                        rotate(pane);
                    }
                }
            }
        }else {
            System.out.println("S tidur");
            if(locA%blockX<locB%blockX){
                //A dikiri B
                if (locA/blockX<1){
                    System.out.println("S move 3");
                    moveDown(pane);rotate(pane);
                }else {
                    if (pane.getChildren().get(locD-blockX).isCache()){
                        System.out.println("can't move 1");
                    }else {
                        System.out.println("S move 9");
                        rotate(pane);
                    }
                }
            } else if (locA%blockX>locB%blockX) {
                //A di kanan B
                if (pane.getChildren().get(locB-blockX).isCache()){
                    System.out.println("can't move 2");
                }else {
                    if(pane.getChildren().get(locD+(2*blockX)).isCache()){
                        if(pane.getChildren().get(locB-1).isCache()||locB%blockX<1){

                        }else {
                            System.out.println("S move 6");
                            moveLeft(pane);rotate(pane);moveRight(pane);
                        }
                    }else {
                        System.out.println("S move 10");
                        rotate(pane);
                    }
                }
            }else {
                System.out.println("other");
            }
        }
    }
    //Rotate T (logic per case)
    private static void rotateT(Pane pane) {
        if(locA%blockX==locD%blockX){
            System.out.println("T tidur");
            if(locA/blockX>22||locD/blockX>22){
                System.out.println("mepet bawah");
            }else {
                rotate(pane);
            }
        }else if (locA/blockX==locD/blockX) {
            System.out.println("T diri");
            if(locA%blockX<1&&locB%blockX<1&&locC%blockX<1){
                if(pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locC+2).isCache()){
                    System.out.println();
                }else {
                    System.out.println("T move 1");
                    moveRight(pane);rotate(pane);
                }
            }else if(locA%blockX>8&&locB%blockX>8&&locC%blockX>8){
                if(pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-2).isCache()||pane.getChildren().get(locC-2).isCache()){
                    System.out.println();
                }else {
                    System.out.println("T move 2");
                    moveLeft(pane); rotate(pane);
                }
            }else {
                System.out.println("T diri ditengah");
                if(locC-blockX==locA&&locA-blockX==locB&&locA+1==locD) {
                    System.out.println("A di kiri D");
                    if(pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()||pane.getChildren().get(locC-1).isCache()){
                        if(pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locC+2).isCache()){
                            System.out.println("kanan mentok");
                        }else {
                            if(locA%blockX==8){
                                System.out.println("T mepet kanan rapet");
                            }else {
                                System.out.println("kiri ada balok T move 3");
                                moveRight(pane);rotate(pane);
                            }
                        }
                    }else {
                        System.out.println("cek");
                        rotate(pane);
                    }
                }else if (locC+blockX==locA&&locA+blockX==locB&&locA-1==locD) {
                    System.out.println("A di kanan D");
                    if(pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locA+1).isCache()){
                        if(pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-2).isCache()||pane.getChildren().get(locC-2).isCache()){
                            System.out.println("kiri mentok");
                        }else {
                            if(locA%blockX==1){
                                System.out.println("mepet kiri rapet");
                            }else {
                                System.out.println("kanan ada balok T move 4");
                                moveLeft(pane);rotate(pane);
                            }
                        }
                    }else {
                        System.out.println("cek");
                        rotate(pane);
                    }
                }else {
                    System.out.println("other");
                }
            }
        }else {
            System.out.println("other");
        }
    }
    //Rotate J (logic per case)
    private static void rotateJ(Pane pane) {
        //J dipinggir kiri
        if(locA%blockX==locB%blockX){
            System.out.println("J berdiri");
            if (locC%blockX<2&&locD%blockX<2){
                System.out.println("J mepet kiri");
                if(locC<locD){
                    System.out.println("C kiri D");
                    if(pane.getChildren().get(locD+1).isCache()||pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache()
                            ||pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locB+2).isCache())
                        System.out.println("J cant rotate1");
                    else {
                        moveRight(pane);rotate(pane);
                    }
                }else {
                    System.out.println("C kanan D");
                    if(pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locC+1).isCache()
                            ||pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()){
                        System.out.println("J cant rotate 2");
                    }else {
                        rotate(pane);
                    }
                }
            }
            else if(locC%blockX>7&&locD%blockX>7){
                System.out.println("J mepet kanan");
                if(locC<locD){
                    System.out.println("C kiri D");
                    if(pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()||pane.getChildren().get(locC-1).isCache()
                            ||pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache()){
                        System.out.println("J cant rotate 3");
                    }else {
                        rotate(pane);
                    }
                }else {
                    System.out.println("C kanan D");
                    if(pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()
                            ||pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-2).isCache()){
                        System.out.println("J cant rotate 4");
                    }else {
                        moveLeft(pane);rotate(pane);
                    }
                }
            }else {
                System.out.println("J ditengah");
                if(locD+1==locC&&locB+blockX==locA){
                    System.out.println("J asli");
                    if(pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache())
                    {
                        System.out.println("J asli kanan ada balok");
                        if(pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-2).isCache()){
                            System.out.println("J cant rotate 5");
                        }else {
                            moveLeft(pane);rotate(pane);
                        }
                    } else if (pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-2).isCache()) {
                        System.out.println("J asli kiri ada balok");
                        if(pane.getChildren().get(locD-blockX).isCache()){
                            System.out.println("fix");
                            moveDown(pane);rotate(pane);
                        }else {
                            rotate(pane);
                        }
                    } else {
                        System.out.println("J asli aman");
                        rotate(pane);
                    }
                } else if (locD-1==locC&&locA+blockX==locB) {
                    System.out.println("J tongkat");
                    if(pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()||pane.getChildren().get(locC-1).isCache()){
                        System.out.println("J tongkat kiri ada balok");
                        if(pane.getChildren().get(locD+1).isCache()||pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locB+2).isCache()){
                            System.out.println("J cant rotate 6");
                        }else {
                            moveRight(pane);rotate(pane);
                        }
                    } else if (pane.getChildren().get(locD+1).isCache()||pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locB+2).isCache()) {
                        System.out.println("J tongkat kanan ada balok");
                        if(pane.getChildren().get(locC-2).isCache()||pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-2).isCache()
                        ){
                            System.out.println("J cant rotate 7");
                        }else {
                            if(locB%blockX==1){
                                if(pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locA+1).isCache()){
                                    System.out.println("J cant rotate 8");
                                }else {
                                    rotate(pane);
                                }
                            }else {
                                moveLeft(pane);rotate(pane);
                            }
                        }
                    }else {
                        System.out.println("J tongkat aman");
                        rotate(pane);
                    }
                } else {
                    System.out.println("another J form");
                }
            }
        }else {
            if(locC/blockX<1){
                System.out.println("J tidur mepet atas");
                moveDown(pane);rotate(pane);moveUp(pane);
            } else if (locC/blockX>22) {

            }else {
                System.out.println("J tidur ditengah");
                rotate(pane);
            }
        }

    }
    //Rotate I (logic per case)
    private static void rotateI(Pane pane) {
        //ngadek
        if(locA%blockX == locB%blockX){
            if(locA%blockX < 2){
                System.out.println("terlalu mepet kiri");
                if(pane.getChildren().get(locA+2).isCache()
                        ||pane.getChildren().get(locB+2).isCache()
                        ||pane.getChildren().get(locC+2).isCache()
                        ||pane.getChildren().get(locD+2).isCache()
                        ||pane.getChildren().get(locA+1).isCache()
                        ||pane.getChildren().get(locB+1).isCache()
                        ||pane.getChildren().get(locC+1).isCache()
                        ||pane.getChildren().get(locD+1).isCache()){
                    System.out.println("kamu mepet kiri, kanan ada temen");
                }
                else if(locA/blockX<3){
                    System.out.println("pojok kiri atas ngadek");
                    moveRight(pane);moveRight(pane);
                    rotate(pane);
                }else if(locA/blockX > locB/blockX){
                    System.out.println("B atas A");
                    if(pane.getChildren().get(locA+3).isCache()
                        ||pane.getChildren().get(locB+3).isCache()
                        ||pane.getChildren().get(locC+3).isCache()
                        ||pane.getChildren().get(locD+3).isCache()
                    )
                    {
                        System.out.println("cant move");
                    }
                    else {
                        moveUp(pane);
                        moveRight(pane);
                        rotate(pane);
                        moveDown(pane);
                    }
                }else {
                    System.out.println("A atas B");
                    if(pane.getChildren().get(locA+3).isCache()
                            ||pane.getChildren().get(locB+3).isCache()
                            ||pane.getChildren().get(locC+3).isCache()
                            ||pane.getChildren().get(locD+3).isCache()
                    )
                    {
                        System.out.println("cant move");
                    }
                    else {
                        moveUp(pane);
                        moveRight(pane);
                        moveRight(pane);
                        rotate(pane);
                        moveLeft(pane);
                        moveDown(pane);
                    }
                }
            }else if(locA%blockX>7) {
                System.out.println("terlalu mepet kanan");
                if(pane.getChildren().get(locA-2).isCache()
                        ||pane.getChildren().get(locB-2).isCache()
                        ||pane.getChildren().get(locC-2).isCache()
                        ||pane.getChildren().get(locD-2).isCache()
                        ||pane.getChildren().get(locA-1).isCache()
                        ||pane.getChildren().get(locB-1).isCache()
                        ||pane.getChildren().get(locC-1).isCache()
                        ||pane.getChildren().get(locD-1).isCache()){
                    System.out.println("mepet kanan dan kiri ada temen");
                }
                else if(locA/blockX<3){
                    System.out.println("pojok kanan atas ngadek");
                    moveLeft(pane);moveLeft(pane);
                    rotate(pane);
                }
                else if(locA/blockX > locB/blockX){
                    System.out.println("B atas A");
                    if(pane.getChildren().get(locA-3).isCache()||pane.getChildren().get(locA-3).isCache()
                            ||pane.getChildren().get(locB-3).isCache()||pane.getChildren().get(locB-3).isCache()
                            ||pane.getChildren().get(locC-3).isCache()||pane.getChildren().get(locC-3).isCache()
                            ||pane.getChildren().get(locD-3).isCache()||pane.getChildren().get(locD-3).isCache()
                    ){
                        System.out.println("cant move");
                    }
                    else {
                        moveUp(pane);
                        moveLeft(pane);
                        moveLeft(pane);
                        rotate(pane);
                        moveRight(pane);
                        moveDown(pane);
                    }
                }else {
                    System.out.println("A atas B");
                    if(pane.getChildren().get(locA-3).isCache()||pane.getChildren().get(locA-3).isCache()
                        ||pane.getChildren().get(locB-3).isCache()||pane.getChildren().get(locB-3).isCache()
                        ||pane.getChildren().get(locC-3).isCache()||pane.getChildren().get(locC-3).isCache()
                        ||pane.getChildren().get(locD-3).isCache()||pane.getChildren().get(locD-3).isCache()
                    )
                    {
                        System.out.println("caant move");
                    }
                    else {
                        moveUp(pane);
                        moveLeft(pane);
                        rotate(pane);
                        moveDown(pane);
                    }
                }
            }
            else {
                if(pane.getChildren().get(locA-2).isCache()
                    ||pane.getChildren().get(locB-2).isCache()
                    ||pane.getChildren().get(locC-2).isCache()
                    ||pane.getChildren().get(locD-2).isCache()
                    ||pane.getChildren().get(locA-1).isCache()
                    ||pane.getChildren().get(locB-1).isCache()
                    ||pane.getChildren().get(locC-1).isCache()
                    ||pane.getChildren().get(locD-1).isCache()){

                    if(pane.getChildren().get(locA+1).isCache()
                            ||pane.getChildren().get(locB+1).isCache()
                            ||pane.getChildren().get(locC+1).isCache()
                            ||pane.getChildren().get(locD+1).isCache()){
                        System.out.println("kanan kiri ada temen");
                    }
                    else {
                        System.out.println("kiri ada temen, tapi bisa muter");
                        if(locD/blockX>locC/blockX){
                            System.out.println("D dibawah");
                            if(pane.getChildren().get(locD-1).isCache()){
                                if (pane.getChildren().get(locD+1).isCache() ||pane.getChildren().get(locD+2).isCache()||pane.getChildren().get(locD+3).isCache()
                                ||pane.getChildren().get(locA+1).isCache() ||pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locA+3).isCache()
                                ||pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locB+3).isCache()
                                ||pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locC+2).isCache()||pane.getChildren().get(locC+3).isCache()
                                ||locD%blockX==7)

                                {
                                    System.out.println("cant rotate");
                                }
                                else {
                                    System.out.println("hahaha");
                                    moveRight(pane);
                                    rotate(pane);
                                }
                            }
                            else {
                                if (pane.getChildren().get(locD+1).isCache() ||pane.getChildren().get(locD+2).isCache())
                                {
                                    System.out.println("cant rotate 2 ");
                                }
                                else {
                                    System.out.println("hihihi");
                                    moveUp(pane); rotate(pane); moveDown(pane);
                                }
                            }
                        }else {
                            System.out.println("kuning dibawah");
                            if(pane.getChildren().get(locC-1).isCache()){
                                System.out.println("kiri -1 ada orang");
                                if((pane.getChildren().get(locC+2).isCache())){
                                    System.out.println("cant rotate");
                                }
                                else if(locC%blockX==7 ){
                                    System.out.println("cant rotate juga");
                                }
                                else if (pane.getChildren().get(locC+3).isCache()||pane.getChildren().get(locB+3).isCache()
                                    ||pane.getChildren().get(locA+3).isCache()||pane.getChildren().get(locC+3).isCache()) {
                                    System.out.println("cant move jugaa");
                                } else {
                                    System.out.println("bug check");
                                    moveRight(pane);moveRight(pane);rotate(pane);
                                }
                            }
                            else{
                                System.out.println("kanan + 2 ada");
                                if(pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locC+2).isCache()){
                                    System.out.println("cant rotate 2");
                                }
                                else {
                                    System.out.println("bug check2");
                                    moveRight(pane);rotate(pane);
                                }
                            }
                        }
                    }
                }
                else if (pane.getChildren().get(locA+2).isCache()
                        ||pane.getChildren().get(locB+2).isCache()
                        ||pane.getChildren().get(locC+2).isCache()
                        ||pane.getChildren().get(locD+2).isCache()
                        ||pane.getChildren().get(locA+1).isCache()
                        ||pane.getChildren().get(locB+1).isCache()
                        ||pane.getChildren().get(locC+1).isCache()
                        ||pane.getChildren().get(locD+1).isCache()){
                    System.out.println( "kanan ada temen");
                    if(locD/blockX>locC/blockX){
                        System.out.println("abu2 dibawah");
                        if(pane.getChildren().get(locD+2).isCache()){
                            System.out.println("kanan+2 ada");
                            if(pane.getChildren().get(locD+1).isCache()){
                                System.out.println("kanan +1 tapi ada");
                                if (pane.getChildren().get(locD - 3).isCache()) {
                                    System.out.println("cant move");
                                }else if(locC%blockX<3){
                                    System.out.println("bug fixed 2");
                                }else{
                                    moveLeft(pane);moveLeft(pane);rotate(pane);
                                }
                            }else {
                                System.out.println("kanan+1 ngga ada");
                                moveLeft(pane);rotate(pane);

                            }
                        }
                        else if(pane.getChildren().get(locD+1).isCache()){
                            System.out.println("kanan+1 ada orang");
                            if (pane.getChildren().get(locD - 3).isCache()) {
                                System.out.println("cant move");
                            }else if(locC%blockX<3){
                                System.out.println("bug fixed 2");
                            }
                            else {
                                moveLeft(pane);moveLeft(pane);rotate(pane);
                            }
                        }
                        else{
                            System.out.println("aman");
                        }
                    }
                    else {
                        System.out.println("kuneng dibawah");
                        if(pane.getChildren().get(locC+1).isCache()&&pane.getChildren().get(locC+2).isCache()){
                            System.out.println("bc");
                            if(pane.getChildren().get(locC-3).isCache()){
                                System.out.println("mampus ketemu bug naa");
                            } else if (locC%blockX<3) {
                                System.out.println("bug fixed");
                            } else {
                                moveLeft(pane);rotate(pane);
                            }
                        }
                        else if(pane.getChildren().get(locC+2).isCache()){
                            System.out.println("bc2");
                            rotate(pane);
                        }
                        else {

                        }
                    }
                } else {
                    rotate(pane);
                }
            }

        }else {
            // Turu
            if(locA/blockX < 2){
                if(!(pane.getChildren().get(locC+blockX).isCache())
                        &&!(pane.getChildren().get(locC+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locC-blockX).isCache())
                        &&!(pane.getChildren().get(locA+blockX).isCache())
                        &&!(pane.getChildren().get(locA+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locA-blockX).isCache())
                        &&!(pane.getChildren().get(locB+blockX).isCache())
                        &&!(pane.getChildren().get(locB+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locB-blockX).isCache())
                        &&!(pane.getChildren().get(locD+blockX).isCache())
                        &&!(pane.getChildren().get(locD+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locD-blockX).isCache())){
                    System.out.println("terlalu mepet atas");
                    if(locA%blockX > locB%blockX){
                        System.out.println("A kanan B");
                        moveDown(pane);moveDown(pane);
                        rotate(pane);
                    }else {
                        moveDown(pane);moveDown(pane);
                        rotate(pane);
                        System.out.println("B kanan A");
                    }
                }
            } else if (locA/blockX>21) {
                if(!(pane.getChildren().get(locC+blockX).isCache())
                        &&!(pane.getChildren().get(locC+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locC-blockX).isCache())
                        &&!(pane.getChildren().get(locA+blockX).isCache())
                        &&!(pane.getChildren().get(locA+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locA-blockX).isCache())
                        &&!(pane.getChildren().get(locB+blockX).isCache())
                        &&!(pane.getChildren().get(locB+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locB-blockX).isCache())
                        &&!(pane.getChildren().get(locD+blockX).isCache())
                        &&!(pane.getChildren().get(locD+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locD-blockX).isCache())){
                    System.out.println("terlalu mepet bawah");
                    if(locA%blockX > locB%blockX){
                        System.out.println("A kanan B");
                        moveUp(pane);
                        rotate(pane);
                    }else {
                        System.out.println("B kanan A");
                        moveUp(pane); moveUp(pane);
                        rotate(pane);
                        moveDown(pane);
                    }
                }
            }else {
                //atas bawah aman (tidur)
                if(!(pane.getChildren().get(locC+blockX).isCache())
                        &&!(pane.getChildren().get(locC+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locC-blockX).isCache())
                        &&!(pane.getChildren().get(locA+blockX).isCache())
                        &&!(pane.getChildren().get(locA+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locA-blockX).isCache())
                        &&!(pane.getChildren().get(locB+blockX).isCache())
                        &&!(pane.getChildren().get(locB+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locB-blockX).isCache())
                        &&!(pane.getChildren().get(locD+blockX).isCache())
                        &&!(pane.getChildren().get(locD+(2*blockX)).isCache())
                        &&!(pane.getChildren().get(locD-blockX).isCache())){
                    System.out.println("atas bawah aman");
                    rotate(pane);
                }
                //atas bawah ada temen
                else {
                    System.out.println("atas bawah ada temen");
                    moveUp(pane);
                    rotate(pane);
                }
            }

        }
    }
    //Rotate L (logic per case)
    private static void rotateL(Pane pane) {
        if(locA/blockX==locB/blockX){
            //L tidur
            if(locC/blockX>23){
                System.out.println("tidur, mepet bawah");
                moveUp(pane);
                rotate(pane);
            }
            else if(locC/blockX<1){
                System.out.println("tidur mepet atas");
                moveDown(pane);
                rotate(pane);
            }
            else {
                if(locB+1==locA&&locA+1==locC&&locC-blockX==locD){
                    System.out.println("D diatas");
                    if (pane.getChildren().get(locA+blockX).isCache()||pane.getChildren().get(locB+blockX).isCache()
                        ||pane.getChildren().get(locC+blockX).isCache()){
                        moveUp(pane);rotate(pane);moveDown(pane);
                    }
                    else {
                        rotate(pane);
                    }
                } else if (locD-blockX==locC&&locC+1==locA&&locA+1==locB) {
                    System.out.println("D dibawah");
                    if(pane.getChildren().get(locD+1).isCache()&&pane.getChildren().get(locD-1).isCache()){
                        System.out.println(" D dibawah, cant move");
                    } else {
                        if (pane.getChildren().get(locD+blockX).isCache()){
                            moveUp(pane);rotate(pane);moveDown(pane);
                        }
                        else {
                            rotate(pane);
                        }
                    }
                }
                else {
                    System.out.println("bentuk lain");
                    System.out.println(" tidur ditengah");
                    rotate(pane);
                }
            }
        }
        else if (locA%blockX==locC%blockX){
            //diri
            if(locC%blockX<1||locD%blockX<1){
                System.out.println("diri, mepet kiri");
                if(locC+1==locD){
                    System.out.println("C kiri D");
                    if(pane.getChildren().get(locD+1).isCache()||pane.getChildren().get(locA+2).isCache()
                        ||pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locA+1).isCache()
                        ||pane.getChildren().get(locB+1).isCache()){
                        System.out.println("cant move 1 mepet kiri");
                    }
                    else {
                        moveRight(pane);
                        rotate(pane);
                    }
                }
                else if(locC-1==locD) {
                    System.out.println("C kanan D");
                    if(pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locC+1).isCache()){
                        System.out.println("cant move 2 mepet kiri");
                    }
                    else {
                        rotate(pane);
                    }
                }else {
                    System.out.println("other case");
                }
            }else if(locC%blockX==9||locD%blockX==9){
                System.out.println( "L diri mepet kanan");
                if(locC+1==locD){
                    System.out.println("C kiri D");
                    if(pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()||pane.getChildren().get(locC-1).isCache()
                         )
                        {
                        System.out.println("cant move 1 mepet kanan");
                    }
                    else {
                        rotate(pane);
                    }
                }
                else if(locC-1==locD) {
                    System.out.println("C kanan D");
                    if(pane.getChildren().get(locD-1).isCache()||pane.getChildren().get(locB-2).isCache()||
                        pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locB-1).isCache()||
                        pane.getChildren().get(locA-1).isCache()){
                        System.out.println("cant move 2 mepet kanan");
                    }
                    else {
                        moveLeft(pane);
                        rotate(pane);
                    }
                }else {
                    System.out.println("other case");
                }
            }
            else {
                //diri di tengah
                 System.out.println("diri, ditengah");
                 if(locB==locA+blockX&&locA==locC+blockX&&locC==locD+1){
                     System.out.println("betuk 7");
                     if(pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locC+1).isCache()){
                         System.out.println("7 di kanan");
                         if(pane.getChildren().get(locB-2).isCache()||pane.getChildren().get(locA-2).isCache()||pane.getChildren().get(locC-2).isCache()){
                             System.out.println("b-2, a-2, c-2 keisi");
                         }else {
                             moveLeft(pane);rotate(pane);
                         }
                     } else if (pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache()) {
                         System.out.println("7 di kiri");
                         if(pane.getChildren().get(locC+1).isCache()||pane.getChildren().get(locC+2).isCache()
                            ||pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locA+2).isCache()
                             ||pane.getChildren().get(locB+1).isCache()||pane.getChildren().get(locB+2).isCache())
                         {
                             System.out.println("7 kiri ngga bisa gerak");
                         }else {
                             moveRight(pane);rotate(pane);
                         }
                     }else {
                         rotate(pane);
                         System.out.println("7 aman");
                     }
                 } else if (locD-1==locC&&locC-blockX==locA&&locA-blockX==locB) {
                     System.out.println("L asli");
                     if(pane.getChildren().get(locA-1).isCache()||pane.getChildren().get(locB-1).isCache() ||pane.getChildren().get(locC-1).isCache())
                     {
                         System.out.println("L asli dikiri");
                         if(pane.getChildren().get(locA+2).isCache()||pane.getChildren().get(locB+2).isCache()||pane.getChildren().get(locC+2).isCache()){
                             System.out.println(("a+2, b+2, atau c+2 ada beick"));
                         }
                         else {
                             moveRight(pane);rotate(pane);moveLeft(pane);
                         }
                     }
                     else if(pane.getChildren().get(locA+1).isCache()||pane.getChildren().get(locB+1).isCache()){
                         System.out.println("L asli dikanan");
                         if(pane.getChildren().get(locC-2).isCache()||pane.getChildren().get(locA-2).isCache()||
                                 pane.getChildren().get(locB-2).isCache()){
                             System.out.println("a-2,b-2 atau c-2 ada brick");
                         }
                         else {
                             moveLeft(pane);rotate(pane);moveRight(pane);
                         }
                     }
                     else {
                         System.out.println("L asli aman");
                         rotate(pane);
                     }

                 } else {
                     System.out.println("another form");
                     rotate(pane);
                 }
            }
        }
    }

    //transform
    private static int handleRotation(Pane pane, int loc, int newHorLoc, int newVerLoc, Rectangle brick) {
        A = (GridPane) pane.getChildren().get(loc);
        A.getChildren().remove(0);
        loc = newVerLoc*blockX + newHorLoc;
        A = (GridPane) pane.getChildren().get(loc);
        A.add(brick,0,0);
        return loc;
    }
    //T shape
    public void T(Pane pane){
        int startA = 15;
        int startB = startA-1;
        int startC = startA+1;
        int startD = startA-10;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
//        BrickA.setFill(Color.RED);
        BrickA.setFill(Color.MEDIUMVIOLETRED);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
//        BrickB.setFill(Color.BLUE);
        BrickB.setFill(Color.MEDIUMVIOLETRED);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
//        BrickC.setFill(Color.YELLOW);
        BrickC.setFill(Color.MEDIUMVIOLETRED);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
//        BrickD.setFill(Color.GREY);
        BrickD.setFill(Color.MEDIUMVIOLETRED);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
    //I shape
    public void I(Pane pane){
        int startA = 5;
        int startB = startA-1;
        int startC = startA-2;
        int startD = startA+1;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
//        BrickA.setFill(Color.RED);
        BrickA.setFill(Color.DARKORANGE);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
//        BrickB.setFill(Color.BLUE);
        BrickB.setFill(Color.DARKORANGE);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
//        BrickC.setFill(Color.YELLOW);
        BrickC.setFill(Color.DARKORANGE);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
//        BrickD.setFill(Color.GREY);
        BrickD.setFill(Color.DARKORANGE);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
    //L shape
    public void L(Pane pane){
        int startA = 15;
        int startB = startA-10;
        int startC = startA+10;
        int startD = startA+11;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
//        BrickA.setFill(Color.RED);
        BrickA.setFill(Color.POWDERBLUE);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
//        BrickB.setFill(Color.BLUE);
        BrickB.setFill(Color.POWDERBLUE);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
//        BrickC.setFill(Color.YELLOW);
        BrickC.setFill(Color.POWDERBLUE);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
//        BrickD.setFill(Color.GREY);
        BrickD.setFill(Color.POWDERBLUE);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
    // J shape
    public void J(Pane pane){
        int startA = 15;
        int startB = startA-10;
        int startC = startA+10;
        int startD = startA+9;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
//        BrickA.setFill(Color.RED);
        BrickA.setFill(Color.SPRINGGREEN);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
//        BrickB.setFill(Color.BLUE);
        BrickB.setFill(Color.SPRINGGREEN);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
//        BrickC.setFill(Color.YELLOW);
        BrickC.setFill(Color.SPRINGGREEN);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
//        BrickD.setFill(Color.GREY);
        BrickD.setFill(Color.SPRINGGREEN);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
    //O shape
    public void O(Pane pane){
        int startA = 5;
        int startB = startA-1;
        int startC = startA+10;
        int startD = startB+10;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
        BrickA.setFill(Color.PEACHPUFF);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
        BrickB.setFill(Color.PEACHPUFF);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
        BrickC.setFill(Color.PEACHPUFF);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
        BrickD.setFill(Color.PEACHPUFF);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
    //S Shape
    public void S(Pane pane){
        int startA = 5;
        int startB = startA+1;
        int startC = startA+10;
        int startD = startC-1;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
//        BrickA.setFill(Color.RED);
        BrickA.setFill(Color.CHOCOLATE);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
//        BrickB.setFill(Color.BLUE);
        BrickB.setFill(Color.CHOCOLATE);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
//        BrickC.setFill(Color.YELLOW);
        BrickC.setFill(Color.CHOCOLATE);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
//        BrickD.setFill(Color.GREY);
        BrickD.setFill(Color.CHOCOLATE);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
    //Z Shape
    public void Z(Pane pane){
        int startA = 5;
        int startB = startA-1;
        int startC = startA+10;
        int startD = startC+1;

        BrickA = new Rectangle();
        BrickA.setWidth(25.0f);
        BrickA.setHeight(25.0f);
//        BrickA.setFill(Color.RED);
        BrickA.setFill(Color.DODGERBLUE);
        BrickA.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickA.setStrokeWidth(1);

        BrickB = new Rectangle();
        BrickB.setWidth(25.0f);
        BrickB.setHeight(25.0f);
//        BrickB.setFill(Color.BLUE);
        BrickB.setFill(Color.DODGERBLUE);
        BrickB.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickB.setStrokeWidth(1);

        BrickC = new Rectangle();
        BrickC.setWidth(25.0f);
        BrickC.setHeight(25.0f);
//        BrickC.setFill(Color.YELLOW);
        BrickC.setFill(Color.DODGERBLUE);
        BrickC.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickC.setStrokeWidth(1);

        BrickD = new Rectangle();
        BrickD.setWidth(25.0f);
        BrickD.setHeight(25.0f);
//        BrickD.setFill(Color.GREY);
        BrickD.setFill(Color.DODGERBLUE);
        BrickD.setStroke(Color.LIGHTGOLDENRODYELLOW);
        BrickD.setStrokeWidth(1);

        A = (GridPane) pane.getChildren().get(startA);
        A.add(BrickA,0,0);
        A = (GridPane)pane.getChildren().get(startB);
        A.add(BrickB,0,0);
        A = (GridPane)pane.getChildren().get(startC);
        A.add(BrickC,0,0);
        A = (GridPane)pane.getChildren().get(startD);
        A.add(BrickD,0,0);

        locA = startA; locB = startB; locC=startC; locD=startD;
    }
}

package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class LBrick {
    private final List<int[][]> L_Brick= new ArrayList<>();
    LBrick(){
        L_Brick.add(new int[][]{
                {1,0},
                {1,0},
                {1,1}
        });
    }
    public List<int[][]> getL_Brick(){
        return L_Brick;
    }
}

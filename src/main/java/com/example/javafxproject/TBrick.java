package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class TBrick {
    private final List<int[][]> T_Brick = new ArrayList<>();
    TBrick(){
        T_Brick.add(new int[][]{
                {0,1,0},
                {1,1,1},
        });
    }
    public List<int[][]> getT_Brick() {
        return T_Brick;
    }
}

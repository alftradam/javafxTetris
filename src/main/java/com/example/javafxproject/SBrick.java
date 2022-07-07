package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class SBrick {
    private final List<int[][]>S_Brick=new ArrayList<>();
    SBrick(){
        S_Brick.add(new int[][]{
                {0,1,1},
                {1,1,0},
        });
    }
    public List<int[][]> getS_Brick(){
        return S_Brick;
    }
}

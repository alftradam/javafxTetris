package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class ZBrick {
    private final List<int [][]> Z_Brick = new ArrayList<>();
    ZBrick(){
        Z_Brick.add(new int[][]{
                {1,1,0},
                {0,1,1,},
        });
    }

    public List<int[][]> getZ_Brick() {
        return Z_Brick;
    }
}

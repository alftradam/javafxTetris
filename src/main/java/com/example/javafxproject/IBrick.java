package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class IBrick {
    private final List<int [][]> I_Brick = new ArrayList<>();
    IBrick(){
        I_Brick.add(new int[][]{
                {1,1,1,1},
        });
    }

    public List<int[][]> getBrick() {
        return I_Brick;
    }
}

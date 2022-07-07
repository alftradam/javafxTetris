package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class OBrick {

    static String NEXT_BRICK_NAME;
    private final List<int [][]> O_Brick = new ArrayList<>();

    OBrick(){
        O_Brick.add(new int[][]{
                {1,1},
                {1,1}
        });
    }

    public List<int[][]> getBrick() {
        return O_Brick;
    }
}
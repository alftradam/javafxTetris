package com.example.javafxproject;

import java.util.ArrayList;
import java.util.List;

public class JBrick {
    private final List<int [][]> J_Brick= new ArrayList<>();
    JBrick(){
        J_Brick.add(new int[][]{
                {0, 1},
                {0, 1},
                {1, 1}});
    }
    public List<int[][]> getJ_Brick(){
        return J_Brick;
    }
}

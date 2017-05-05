package dev.urotea.sudokubreaker.Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kakimori on 17/05/05.
 */

public class SudokuLogic {

    public static List<List<Integer>> solve(List<List<Integer>> inputList) {
        List<List<Integer>> retVal = new ArrayList<>();
        // まだモック
        for(int i=0;i<9;i+=1) {
            retVal.add(new ArrayList<Integer>());
        }
        for(int i=0;i<9;i+=1) {
            for(int j=0;j<9;j+=1) {
                retVal.get(i).add((i*9+j) % 9);
            }
        }
        return retVal;
    }

    public static List<List<Integer>> extractAreaList(List<List<Integer>> inputList) {
        List<List<Integer>> retVal = new ArrayList<>();
        for(int i=0;i<9;i+=1) {
            retVal.add(new ArrayList<Integer>());
        }

        for(int i=0;i<retVal.size();i+=1) {
            int colHead = i%3 * 3;
            int rowHead = i/3 * 3;
            for(int j=0;j<3;j+=1) {
                retVal.get(i).addAll(inputList.get(rowHead + j).subList(colHead, colHead + 3));
            }
        }
        return retVal;
    }
}

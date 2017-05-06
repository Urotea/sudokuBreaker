package dev.urotea.sudokubreaker.Logic;

import java.util.ArrayList;
import java.util.List;

import dev.urotea.sudokubreaker.Model.SudokuSolverModel;

/**
 * Created by kakimori on 17/05/05.
 */

public class SudokuLogic {

    public static List<List<Integer>> solve(List<List<Integer>> inputList) {
        List<List<SudokuSolverModel>> sudokuList = SudokuLogic.initList(inputList);
        // rowをサーチして決定している値を削除する
        for(int i=0;i<9;i+=1) {
            SudokuLogic.removeDetectedNum(sudokuList.get(i));
        }
        // colをサーチして決定している値を削除する
        for(int i=0;i<9;i+=1) {
            List<SudokuSolverModel> tmp = new ArrayList<>();
            for(List<SudokuSolverModel> list : sudokuList) {
                tmp.add(list.get(i));
            }
            SudokuLogic.removeDetectedNum(tmp);
        }

        // Areaをサーチして決定している値を削除する
        for(int i=0;i<9;i+=1) {
            List<SudokuSolverModel> tmp = new ArrayList<>();
            int colHead = i % 3 * 3;
            int rowHead = i / 3 * 3;
            for(int j=0;j<9;j+=1) {
                int col = colHead + j%3;
                int row = rowHead + j/3;
                tmp.add(sudokuList.get(row).get(col));
            }
            SudokuLogic.removeDetectedNum(tmp);
        }
        return new ArrayList<>();
    }

    /**
     * 選択肢を探索し、唯一の値がある場合、それに決定する
     * @param sudokuList
     */
    public static void detectOnlyoneNum(List<SudokuSolverModel> sudokuList) {

    }

    /**
     * 確定している値を他の選択肢から削除する
     * @param sudokuList 処理を行いたい配列
     */
    public static void removeDetectedNum(List<SudokuSolverModel> sudokuList) {
        // すでに決定している値を他のマスの選択肢から消す
        for (int j = 0; j < 9; j += 1) {
            int detectedNum = sudokuList.get(j).getDetectedNum();
            for (int k = 0; k < 9; k += 1) {
                if (j == k) continue; // 同じデータは無視する
                sudokuList.get(k).removeNum(detectedNum);
            }
        }
        return;
    }

    public static List<List<SudokuSolverModel>> initList(List<List<Integer>> inputList) {
        List<List<SudokuSolverModel>> sudokuList = new ArrayList<>();
        for (int i = 0; i < 9; i += 1) {
            sudokuList.add(new ArrayList<SudokuSolverModel>());
        }
        for (int i = 0; i < 9; i += 1) {
            for (int j = 0; j < 9; j += 1) {
                sudokuList.get(i).add(inputList.get(i).get(j) == 0 ?
                        new SudokuSolverModel() : new SudokuSolverModel(inputList.get(i).get(j)));
            }
        }
        return sudokuList;
    }

    public static List<List<Integer>> extractAreaList(List<List<Integer>> inputList) {
        List<List<Integer>> retVal = new ArrayList<>();
        for (int i = 0; i < 9; i += 1) {
            retVal.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < retVal.size(); i += 1) {
            int colHead = i % 3 * 3;
            int rowHead = i / 3 * 3;
            for (int j = 0; j < 3; j += 1) {
                retVal.get(i).addAll(inputList.get(rowHead + j).subList(colHead, colHead + 3));
            }
        }
        return retVal;
    }

}

package dev.urotea.sudokubreaker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kakimori on 17/05/06.
 */

public class SudokuSolverModel {

    private List<Integer> possibilityList;

    public SudokuSolverModel() {
        possibilityList = new ArrayList<>();
        for(int i=1;i<10;i+=1) {
            possibilityList.add(i);
        }
    }

    public SudokuSolverModel(int detected) {
        possibilityList = new ArrayList<>();
        possibilityList.add(detected);
    }

    public SudokuSolverModel(SudokuSolverModel oldModel) {
        possibilityList = new ArrayList<>();
        for(int val : oldModel.getList()) {
            possibilityList.add(val);
        }
    }

    /**
     * 可能性listの中から一つを取り出し、それをlistから削除する
     * @return
     */
    public int popList() {
        if(possibilityList.size() == 0) return 0;
        int retVal = possibilityList.get(0);
        possibilityList.remove(0);
        return retVal;
    }

    /**
     * 保持しているリストを返す
     * @return 保持しているリスト
     */
    public List<Integer> getList() {
        return possibilityList;
    }

    /**
     * リストをセットする
     * @param list セットしたいリスト
     */
    public void setList(List<Integer> list) {
        possibilityList = list;
    }

    /**
     * 数字が存在するかチェックする
     * @param expectedNum チェックしたい値
     * @return 存在するか
     */
    public boolean existNumber(int expectedNum) {
        return possibilityList.contains(expectedNum);
    }

    /**
     * 数字を削除する
     * @param num 削除したい値
     */
    public void removeNum(int num) {
        possibilityList.remove((Object)num);
    }

    /**
     * すでに決定している値を抽出する
     * @return 決定している場合は、その値。していない場合は0
     */
    public int getDetectedNum() {
        if(possibilityList.size() > 1 || possibilityList.size() < 1) return 0;
        return  possibilityList.get(0);
    }

    public void detectNum(int num) {
        possibilityList = new ArrayList<>();
        possibilityList.add(num);
    }

    /**
     * サイズを返す
     * @return サイズ
     */
    public int size() {
        return possibilityList.size();
    }

}
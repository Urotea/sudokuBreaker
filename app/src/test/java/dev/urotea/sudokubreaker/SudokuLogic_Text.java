package dev.urotea.sudokubreaker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dev.urotea.sudokubreaker.Logic.SudokuLogic;
import dev.urotea.sudokubreaker.Model.SudokuSolverModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by kakimori on 17/05/05.
 */

public class SudokuLogic_Text {

    @Test
    public void extractテスト() {
        List<List<Integer>> testData = new ArrayList<>();
        for(int i=0;i<9;i+=1) {
            testData.add(new ArrayList<Integer>());
        }

        for(int i=0;i<testData.size();i+=1) {
            for(int j=0;j<9;j+=1) {
                testData.get(i).add(i*9+j);
            }
        }

        List<List<Integer>> extracted = SudokuLogic.extractAreaList(testData);
        assertThat(extracted.get(0).get(3), is(9));
        assertThat(extracted.get(1).get(2), is(5));
    }

    @Test
    public void initListテスト() {
        List<List<Integer>> testData = new ArrayList<>();
        for(int i=0;i<9;i+=1) {
            testData.add(new ArrayList<Integer>());
        }

        for(int i=0;i<testData.size();i+=1) {
            for(int j=0;j<9;j+=1) {
                testData.get(i).add((i*9+j) % 3);
            }
        }

        List<List<SudokuSolverModel>> test = SudokuLogic.initList(testData);
        assertThat(test.get(0).get(0).getDetectedNum(), is(0));
        assertThat(test.get(0).get(1).getDetectedNum(), is(1));
    }

    @Test
    public void removeDetectedNumテスト() {
        List<SudokuSolverModel> testData = new ArrayList<>();
        for(int i=0;i<9;i+=1) {
            testData.add(new SudokuSolverModel());
        }
        for(int i=1;i<9;i+=1) {
            testData.get(i-1).detectNum(i);
        }
        assertThat(testData.get(8).getDetectedNum(), is(0));
        SudokuLogic.removeDetectedNum(testData);
        assertThat(testData.get(8).getDetectedNum(), is(9));
    }
}

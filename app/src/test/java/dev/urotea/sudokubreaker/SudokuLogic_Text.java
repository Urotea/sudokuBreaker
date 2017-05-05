package dev.urotea.sudokubreaker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dev.urotea.sudokubreaker.Logic.SudokuLogic;

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
}

package dev.urotea.sudokubreaker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dev.urotea.sudokubreaker.Model.AreaModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainActivity_UnitTest {
    @Test
    public void modelからlistへの変換() {
        // testデータ生成
        List<AreaModel> testModelList = new ArrayList<>();
        for(int i=0;i<9;i+=1) {
            AreaModel tmpModel = new AreaModel();
            for(int j=0;j<9;j+=1) {
                tmpModel.addList((i * 9) + j + "");
            }
            testModelList.add(tmpModel);
        }

        List<List<Integer>> ret = MainActivity.convertList(testModelList);
        assertThat(ret.get(0).get(0), is(0));
        assertThat(ret.get(8).get(8), is(80));
        assertThat(ret.get(3).get(0), is(27));
    }

    @Test
    public void listからmodelへの変換() {
        List<List<Integer>> testData = new ArrayList<>();
        for(int i=0;i<9;i+=1) {
            testData.add(new ArrayList<Integer>());
        }
        for(int i=0;i<9;i+=1) {
            for(int j=0;j<9;j+=1) {
                testData.get(i).add(i * 9 + j);
            }
        }
        List<AreaModel> ret = MainActivity.convertModel(testData);
        assertThat(ret.get(0).getList().get(0), is("0"));
        assertThat(ret.get(1).getList().get(0), is("3"));
        assertThat(ret.get(3).getList().get(0), is("27"));
    }
}
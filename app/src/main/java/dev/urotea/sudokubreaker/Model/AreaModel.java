package dev.urotea.sudokubreaker.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kakimori on 17/05/03.
 */

public class AreaModel implements Serializable{

    private int tag;
    private List<String> stringList;

    public AreaModel() {
        this(-1);
    }

    public AreaModel(int tag) {
        this(tag, new ArrayList<String>());
    }

    public AreaModel(int tag, List<String> stringList) {
        this.tag = tag;
        this.stringList = stringList;
    }

    /**
     * listに文字を追加します
     * @param message 追加したい文字
     */
    public void addList(String message) {
        if(stringList.size() > 8) {
            throw new ArrayIndexOutOfBoundsException("配列数が9を超えています");
        }
        this.stringList.add(message);
    }

    /**
     * tagをセットします
     * @param tag このモデルの識別子
     */
    public void setTag(int tag) {
        this.tag = tag;
    }

    /**
     * tagを取得します
     * @return tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * 格納している文字をすべて返します
     * @return stringList
     */
    public List<String> getList() {
        return stringList;
    }

    /**
     * 保持しているリストのデータを一部置換する
     * @param index 置換するindex
     * @param message 置換する内容
     */
    public void setList(int index, String message) {
        stringList.set(index, message);
    }

    /**
     * 保持しているリストの内容をすべて捨てる
     */
    public void clearList() {
        stringList.clear();
    }

    /**
     * リストをすべて置き換える
     * @param list 置換するリスト
     */
    public void setList(List<String> list) {
        stringList = list;
    }
}

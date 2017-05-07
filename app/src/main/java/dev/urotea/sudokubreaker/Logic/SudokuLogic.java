package dev.urotea.sudokubreaker.Logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import dev.urotea.sudokubreaker.Model.SudokuSolverModel;

/**
 * Created by kakimori on 17/05/05.
 */

public class SudokuLogic {

    public static List<List<Integer>> solve(List<List<Integer>> inputList) {
        List<List<SudokuSolverModel>> sudokuList = SudokuLogic.initList(inputList);
        Deque<Object> stack = new ArrayDeque<>();

        while(true) {
            SudokuLogic.coreSolver(sudokuList);
            // すべての要素が0でない値を持つ(計算終了)
            boolean isZero = false;
            for (int i = 0; i < sudokuList.size(); i += 1) {
                List<SudokuSolverModel> modelList = sudokuList.get(i);
                for (SudokuSolverModel model : modelList) {
                    if (model.getDetectedNum() == 0) {
                        isZero = true;
                    }
                }
            }
            if (!isZero) return SudokuLogic.modelToInteger(sudokuList);

            // 決定しきっていない値がある場合、最も可能性listのサイズが小さいものを選択する
            int minSize = 10, row = -1, col = -1;
            for (int i = 0; i < sudokuList.size(); i += 1) {
                for (int j = 0; j < 9; j += 1) {
                    if (sudokuList.get(i).get(j).size() > 1 && //1個の場合は値が決定している
                            minSize > sudokuList.get(i).get(j).size()) {
                        minSize = sudokuList.get(i).get(j).size();
                        row = i;
                        col = j;
                    }
                }
            }
            if (row >= 0) {  // 2個以上のデータがある(分岐する)
                int nextVal = sudokuList.get(row).get(col).popList();
                stack.addFirst(SudokuLogic.deepCopyModelList(sudokuList));
                sudokuList.get(row).get(col).detectNum(nextVal);
            } else { // 2個以上のデータがない(0個のデータがある = 計算失敗)
                if (stack.size() > 0) { // スタックしたものがあるならそれをpopする
                    sudokuList = (List<List<SudokuSolverModel>>) stack.removeFirst();
                } else { // スタックしたものがないなら計算を諦める
                    return SudokuLogic.modelToInteger(sudokuList);
                }
            }
        }
    }

    public static List<List<SudokuSolverModel>> deepCopyModelList(List<List<SudokuSolverModel>> sudokuList) {
        List<List<SudokuSolverModel>> retVal = new ArrayList<>();
        for (int i = 0; i < 9; i += 1) {
            retVal.add(new ArrayList<SudokuSolverModel>());
        }
        for (int i = 0; i < retVal.size(); i += 1) {
            List<SudokuSolverModel> tmp = sudokuList.get(i);
            for (SudokuSolverModel model : tmp) {
                retVal.get(i).add(new SudokuSolverModel(model));
            }
        }
        return retVal;
    }

    public static void coreSolver(List<List<SudokuSolverModel>> sudokuList) {
        // 現在の状態を保存する(値の更新をかけた時に変化していないかチェックするため)
        List<List<Integer>> lastDetectedList = new ArrayList<>();
        while (!SudokuLogic.isEqualList(lastDetectedList, SudokuLogic.modelToInteger(sudokuList))) {
            lastDetectedList = SudokuLogic.modelToInteger(sudokuList);
            // rowをサーチして決定している値を削除する
            for (int i = 0; i < 9; i += 1) {
                SudokuLogic.removeDetectedNum(sudokuList.get(i));
                SudokuLogic.detectOnlyoneNum(sudokuList.get(i));
            }
            // colをサーチして決定している値を削除する
            for (int i = 0; i < 9; i += 1) {
                List<SudokuSolverModel> tmp = new ArrayList<>();
                for (List<SudokuSolverModel> list : sudokuList) {
                    tmp.add(list.get(i));
                }
                SudokuLogic.removeDetectedNum(tmp);
                SudokuLogic.detectOnlyoneNum(tmp);
            }

            // Areaをサーチして決定している値を削除する
            for (int i = 0; i < 9; i += 1) {
                List<SudokuSolverModel> tmp = new ArrayList<>();
                int colHead = i % 3 * 3;
                int rowHead = i / 3 * 3;
                for (int j = 0; j < 9; j += 1) {
                    int col = colHead + j % 3;
                    int row = rowHead + j / 3;
                    tmp.add(sudokuList.get(row).get(col));
                }
                SudokuLogic.removeDetectedNum(tmp);
                SudokuLogic.detectOnlyoneNum(tmp);
            }
        }
        return;
    }

    public static boolean isEqualList(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) return false;
        for (int i = 0; i < list1.size(); i += 1) {
            List<Integer> inList1 = list1.get(i);
            List<Integer> inList2 = list2.get(i);
            if (inList1.size() != inList2.size()) return false;
            for (int j = 0; j < inList1.size(); j += 1) {
                if (inList1.get(j) != inList2.get(j)) return false;
            }
        }
        return true;
    }

    /**
     * 選択肢を探索し、唯一の値がある場合、それに決定する
     *
     * @param sudokuList
     */
    public static void detectOnlyoneNum(List<SudokuSolverModel> sudokuList) {
        for (int i = 1; i < 10; i += 1) {
            List<Integer> indexOfContains = new ArrayList<>();
            // iを可能性listに含む物のインデックスを列挙する
            for (int j = 0; j < sudokuList.size(); j += 1) {
                if (sudokuList.get(j).existNumber(i)) {
                    indexOfContains.add(j);
                }
            }
            // もしiを可能性listに含むものが1つであれば、その要素をiに確定する
            if (indexOfContains.size() == 1) {
                sudokuList.get(indexOfContains.get(0)).detectNum(i);
            }
        }
        return;
    }

    /**
     * 確定している値を他の選択肢から削除する
     *
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

    /**
     * Integerの二次元listを独自のモデルクラスに置換する
     * 0が格納されている場合は、値が未定とする
     *
     * @param inputList Integerの二次元配列
     * @return 二次元の可能性list
     */
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

    public static List<List<Integer>> modelToInteger(List<List<SudokuSolverModel>> sudokuList) {
        List<List<Integer>> retVal = new ArrayList<>();
        for (int i = 0; i < 9; i += 1) {
            retVal.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < 9; i += 1) {
            List<SudokuSolverModel> oneLineList = sudokuList.get(i);
            for (SudokuSolverModel model : oneLineList) {
                retVal.get(i).add(model.getDetectedNum());
            }
        }
        return retVal;
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

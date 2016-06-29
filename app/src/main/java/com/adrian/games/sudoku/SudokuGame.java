package com.adrian.games.sudoku;

/**
 * 数独逻辑类
 * @author RanQing
 * create at 16-6-29 下午4:45
 */
public class SudokuGame {
    private final String str = "360000000" + "004230800" + "000004200"
            + "070460003" + "820000014" + "500013020" + "001900000"
            + "007048300" + "000000045";
    private int[] sudoku = new int[9 * 9];
    private int[][][] used = new int[9][9][];   //用于存储不可用的数据

    public SudokuGame() {
        sudoku = initPuzzleString(str);
        calculateAllUsedTiles();
    }

    /**
     * 初始化数组
     *
     * @param src
     * @return
     */
    protected int[] initPuzzleString(String src) {
        int[] tmp = new int[src.length()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = src.charAt(i) - '0';
        }
        return tmp;
    }

    public String getTileString(int x, int y) {
        int v = getTile(x, y);
        if (v == 0) {
            return "";
        }
        return String.valueOf(v);
    }

    private int getTile(int x, int y) {
        return sudoku[y * 9 + x];
    }

    /**
     * 计算某一单元格中已经不可用的数据
     *
     * @param x
     * @param y
     * @return
     */
    public int[] calculateUsedTiles(int x, int y) {
        int c[] = new int[9];

        //判断同一列数据
        for (int i = 0; i < 9; i++) {
            if (i == y) {
                continue;
            }
            int t = getTile(x, i);
            if (t != 0) {
                c[t - 1] = t;
            }
        }

        //判断同一行数据
        for (int i = 0; i < 9; i++) {
            if (i == x) {
                continue;
            }
            int t = getTile(i, y);
            if (t != 0) {
                c[t - 1] = t;
            }
        }

        //判断小九宫格数据
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (x == startx && y == starty) {
                    continue;
                }
                int t = getTile(i, j);
                if (t != 0) {
                    c[t - 1] = t;
                }
            }
        }

        //压缩数据
        int nused = 0;
        for (int i :
                c) {
            if (i != 0) {
                nused++;
            }
        }
        int[] c1 = new int[nused];
        nused = 0;
        for (int i :
                c) {
            if (i != 0) {
                c1[nused++] = i;
            }
        }
        return c1;
    }

    /**
     * 用于计算所有单元格不可用数据
     */
    public void calculateAllUsedTiles() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x, y);
            }
        }
    }

    /**
     * 取出某一单元格中已经不可用数据
     *
     * @param x
     * @param y
     * @return
     */
    public int[] getUsedTilesByCoor(int x, int y) {
        return used[x][y];
    }
}

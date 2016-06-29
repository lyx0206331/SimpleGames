package com.adrian.games.sudoku;

import android.os.Bundle;

import com.adrian.games.activity.BaseActivity;

/**
 * 数独界面
 *
 * @author RanQing
 *         create at 16-6-29 下午5:59
 */
public class SudokuActivity extends BaseActivity {

    private SudokuView sudokuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        sudokuView = new SudokuView(this);
        setContentView(sudokuView);
    }

    @Override
    protected void loadData() {

    }
}

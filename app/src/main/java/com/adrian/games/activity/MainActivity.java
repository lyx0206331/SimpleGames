package com.adrian.games.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adrian.games.R;
import com.adrian.games.g2048.Game2048Activity;
import com.adrian.games.gobang.AutoGobangActivity;
import com.adrian.games.gomoku.GomokuMainActivity;
import com.adrian.games.mine.MineMainActivity;
import com.adrian.games.sudoku.SudokuActivity;

import de.cketti.library.changelog.ChangeLog;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static boolean playerFirst = true;

    private Button mMineBtn;
    private Button mGomokuBtn;
    private Button mAutoGobangBtn;
    private Button mSudokuBtn;
    private Button m2046Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        mMineBtn = (Button) findViewById(R.id.btn_mine);
        mGomokuBtn = (Button) findViewById(R.id.btn_gomoku);
        mAutoGobangBtn = (Button) findViewById(R.id.btn_auto_gobang);
        mSudokuBtn = (Button) findViewById(R.id.btn_sudoku);
        m2046Btn = (Button) findViewById(R.id.btn_2046);

        mMineBtn.setOnClickListener(this);
        mGomokuBtn.setOnClickListener(this);
        mAutoGobangBtn.setOnClickListener(this);
        mSudokuBtn.setOnClickListener(this);
        m2046Btn.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

        ChangeLog cl = new ChangeLog(this);
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mine:
                openActivity(MineMainActivity.class);
                break;
            case R.id.btn_gomoku:
                openActivity(GomokuMainActivity.class);
                break;
            case R.id.btn_auto_gobang:
                openActivity(AutoGobangActivity.class);
                break;
            case R.id.btn_sudoku:
                openActivity(SudokuActivity.class);
                break;
            case R.id.btn_2046:
                openActivity(Game2048Activity.class);
                break;
        }
    }
}

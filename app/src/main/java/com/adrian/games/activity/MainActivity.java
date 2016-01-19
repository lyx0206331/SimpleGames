package com.adrian.games.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adrian.games.R;
import com.adrian.games.gobang.AutoGobangActivity;
import com.adrian.games.gomoku.GomokuMainActivity;
import com.adrian.games.mine.MineMainActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static boolean playerFirst = true;

    private Button mMineBtn;
    private Button mGomokuBtn;
    private Button mAutoGobangBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void initView() {
        mMineBtn = (Button) findViewById(R.id.btn_mine);
        mGomokuBtn = (Button) findViewById(R.id.btn_gomoku);
        mAutoGobangBtn = (Button) findViewById(R.id.btn_auto_gobang);
    }

    private void setListener() {
        mMineBtn.setOnClickListener(this);
        mGomokuBtn.setOnClickListener(this);
        mAutoGobangBtn.setOnClickListener(this);
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
        }
    }
}

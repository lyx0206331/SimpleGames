package com.adrian.games.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adrian.games.R;
import com.adrian.games.gomoku.GomokuMainActivity;
import com.adrian.games.mine.MineMainActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mMineBtn;
    private Button mGomokuBtn;

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
    }

    private void setListener() {
        mMineBtn.setOnClickListener(this);
        mGomokuBtn.setOnClickListener(this);
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
        }
    }
}

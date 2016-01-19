package com.adrian.games.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class MineMainActivity extends AppCompatActivity {

    MineView mineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mineView = new MineView(this);
        setContentView(mineView);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        mineView.gameState = MineView.STATE_LOST;
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        mineView.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        mineView.onKeyUp(keyCode, event);
        return super.onKeyUp(keyCode, event);
    }
}

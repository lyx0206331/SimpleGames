package com.adrian.games.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.analytics.game.UMGameAgent;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UMGameAgent.setDebugMode(true);//设置输出运行时日志
        UMGameAgent.init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMGameAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMGameAgent.onResume(this);
    }

    protected void openActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(getApplication(), cls);
        startActivity(intent);
    }
}

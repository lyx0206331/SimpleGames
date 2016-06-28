package com.adrian.games.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.analytics.game.UMGameAgent;

/**
 * 基类Activity
 * @author RanQing
 * create at 16-6-29 上午1:09
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UMGameAgent.setDebugMode(true);//设置输出运行时日志
        UMGameAgent.init(this);

        initVariables();
        initViews();
        loadData();
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

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化UI
     */
    protected abstract void initViews();

    /**
     * 数据加载
     */
    protected abstract void loadData();
}

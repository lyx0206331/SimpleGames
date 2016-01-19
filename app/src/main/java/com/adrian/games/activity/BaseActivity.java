package com.adrian.games.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void openActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(getApplication(), cls);
        startActivity(intent);
    }
}

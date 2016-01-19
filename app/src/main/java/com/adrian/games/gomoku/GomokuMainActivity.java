package com.adrian.games.gomoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.adrian.games.R;

public class GomokuMainActivity extends AppCompatActivity {

    private GomokuView gbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gomoku_main);

        gbv = (GomokuView) this.findViewById(R.id.gobangview);
        gbv.setTextView((TextView) this.findViewById(R.id.text));
    }
}

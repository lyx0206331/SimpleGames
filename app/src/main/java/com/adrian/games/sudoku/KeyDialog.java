package com.adrian.games.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.adrian.games.R;

/**
 * Created by adrian on 16-6-29 17:17.
 */
public class KeyDialog extends Dialog {

    private final View[] keys = new View[9];
    private final int[] used;

    public KeyDialog(Context context, int[] used) {
        super(context);
        this.used = used;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("KeyDialog");
        setContentView(R.layout.dialog_sudoku);

        initViews();
    }

    private void initViews() {
        keys[0] = findViewById(R.id.btn_keypad_1);
        keys[1] = findViewById(R.id.btn_keypad_2);
        keys[2] = findViewById(R.id.btn_keypad_3);
        keys[3] = findViewById(R.id.btn_keypad_4);
        keys[4] = findViewById(R.id.btn_keypad_5);
        keys[5] = findViewById(R.id.btn_keypad_6);
        keys[6] = findViewById(R.id.btn_keypad_7);
        keys[7] = findViewById(R.id.btn_keypad_8);
        keys[8] = findViewById(R.id.btn_keypad_9);

        for (int i :
                used) {
            if (i != 0) {
                keys[i - 1].setVisibility(View.INVISIBLE);
            }
        }
    }
}

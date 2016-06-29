package com.adrian.games.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.adrian.games.R;

/**
 * 数独View
 *
 * @author RanQing
 *         create at 16-6-29 上午1:04
 */
public class SudokuView extends View {

    private float width;
    private float height;

    private SudokuGame game;

    public SudokuView(Context context) {
        super(context);
        game = new SudokuGame();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //计算当前单元格宽高
        width = w / 9f;
        height = h / 9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setColor(getResources().getColor(R.color.sudoku_bg));
        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);

        Paint darkPaint = new Paint();
        darkPaint.setColor(getResources().getColor(R.color.sudoku_dark));

        Paint hilitePaint = new Paint();
        hilitePaint.setColor(getResources().getColor(R.color.sudoku_hilite));

        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.sudoku_light));
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
            canvas.drawLine(i * width, 0, i * width, getHeight(), lightPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilitePaint);
        }

        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0) {
                continue;
            }
            canvas.drawLine(0, i * height, getWidth(), i * height, darkPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilitePaint);
            canvas.drawLine(i * width, 0, i * width, getHeight(), darkPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilitePaint);
        }

        Paint numPaint = new Paint();
        numPaint.setColor(Color.BLACK);
        numPaint.setStyle(Paint.Style.STROKE);
        numPaint.setTextSize(height * .75f);
        numPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics metrics = numPaint.getFontMetrics();

        float x = width / 2;
        float y = height / 2 - (metrics.descent + metrics.ascent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(game.getTileString(i, j), i * width + x, j * height + y, numPaint);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        int selectedX = (int) (event.getX() / width);
        int selectedY = (int) (event.getY() / height);
        int[] used = game.getUsedTilesByCoor(selectedX, selectedY);
        StringBuffer sb = new StringBuffer();
        for (int i :
                used) {
            Log.e("TAG", i + "");
            sb.append(i);
        }

        KeyDialog keyDialog = new KeyDialog(getContext(), used);
        keyDialog.show();
        return true;
    }
}

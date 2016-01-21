package com.adrian.games.mine;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.adrian.games.R;

import java.util.Random;

/**
 * 扫雷游戏界面
 */
public class MineView extends View {
    private static final String TAG = "MineView";

    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            MineView.this.updateView();
            MineView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    // 游戏状态 开始 胜利 失败 暂停
    public static final int STATE_PLAYING = 0;
    public static final int STATE_WIN = 1;
    public static final int STATE_LOST = 2;
    public static final int STATE_PAUSE = 3;
    public int gameState;

    private static final Random random = new Random();
    private Paint paint;
    private String message;

    private static final int tileWidth = 64;
    private static final int tileHeight = 64;
    private static final int tilesCount = 19;
    private static final int margin = 16;
    private static final int titleHeight = 30;

    private Bitmap[] tiles;

    private int[][] mapSky;
    private int[][] mapGround;

    private int tileCountX, tileCountY;
    private int offsetX, offsetY;

    int mineCount;
    int safeCount;

    private int mapX, mapY;
    private boolean altKeyDown = false;

    private static final int MILLIS_PER_TICK = 500;
    long startTime, lastTime;
    long time, remain;

    public MineView(Context context) {
        super(context);
        //设置画笔的属性
        paint = new Paint();
        paint.setARGB(255, 60, 60, 200);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        tiles = new Bitmap[tilesCount];//tilesCount = 19;
        loadTiles();
        setFocusable(true);//获得焦点
    }

    /***
     * 将资源图片的ID放入tiles数组中
     */
    private void loadTiles() {
        Resources r = this.getContext().getResources();
        for (int i = 0; i < tilesCount; i++) {//初始存下19个不同的图片
            tiles[i] = BitmapFactory.decodeResource(r, R.drawable.i00 + i);
        }
    }

    /***
     * 初始化第一层
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        // if(Main.gameState == Main.STATE_START)
        init(right - left, bottom - top);
        startTime = System.currentTimeMillis();
        updateView();

    }

    /***
     * 初始化地图，根据屏幕的长宽计算地图中格子的数量，和边缘的宽度
     *
     * @param w
     * @param h
     */
    private void init(int w, int h) {
        mapX = -1;
        mapY = -1;
        tileCountX = (int) Math.floor((w - margin * 2) / tileWidth);
        tileCountY = (int) Math.floor((h - margin * 2 - titleHeight) / tileHeight);

        offsetX = (w - (tileWidth * tileCountX)) / 2;
        offsetY = (h - (tileHeight * tileCountY) + titleHeight) / 2;

        mineCount = (int) Math.sqrt(tileCountX * tileCountY) * tileCountX
                * tileCountY / 100;//雷数?
        reset();
    }

    /***
     * 初始化两层地图，并放雷
     */
    private void reset() {
        int x, y;
        mapSky = new int[tileCountX][tileCountY];
        mapGround = new int[tileCountX][tileCountY];

        for (int i = 0; i < mineCount; i++) {
            // put down mines
            do {
                x = random.nextInt(tileCountX);
                y = random.nextInt(tileCountY);
            } while (mapGround[x][y] == 12);//避免在重复的位置布雷，设置循环
            mapGround[x][y] = 12;//12指的是有雷的那幅图片
            //周围8个相邻地方格子
            increase(x - 1, y - 1);
            increase(x - 1, y);
            increase(x - 1, y + 1);
            increase(x, y + 1);
            increase(x + 1, y + 1);
            increase(x + 1, y);
            increase(x + 1, y - 1);
            increase(x, y - 1);
        }
        // set empty tile to image i09 设置空的地域
        for (x = 0; x < tileCountX; x++) {
            for (y = 0; y < tileCountY; y++) {
                if (mapGround[x][y] == 0)
                    mapGround[x][y] = 9;
            }
        }
        safeCount = tileCountX * tileCountY - mineCount;//安全的方格数
        time = 0;
        remain = mineCount;//剩余雷数
        altKeyDown = false;
        // shuffle();
    }

    private void increase(int x, int y) {
        if (x > -1 && x < tileCountX && y > -1 && y < tileCountY) {
            if (mapGround[x][y] != 12)
                mapGround[x][y]++;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Log.v(TAG, "onDraw");
        if (altKeyDown) {//红旗的图标是否为按下样式
            canvas.drawARGB(255, 255, 0, 0);
            canvas.drawBitmap(tiles[17], offsetX + 60, 0, paint);
        } else {
            canvas.drawBitmap(tiles[16], offsetX + 60, 0, paint);
        }

        if (gameState != STATE_LOST) {//判断笑脸与哭脸
            canvas.drawBitmap(tiles[18], offsetX, 0, paint);
        } else {
            canvas.drawBitmap(tiles[15], offsetX, 0, paint);
        }

        message = "剩余:" + remain + "  耗时" + time + "秒";
        canvas.drawText(message, 0, message.length(), (offsetX << 2) + 200, offsetX, paint);

        for (int x = 0; x < tileCountX; x += 1) {
            for (int y = 0; y < tileCountY; y += 1) {
                Rect rDst = new Rect(offsetX + x * tileWidth, offsetY + y
                        * tileHeight, offsetX + (x + 1) * tileWidth, offsetY
                        + (y + 1) * tileHeight);
                canvas.drawBitmap(tiles[mapGround[x][y]], null, rDst, paint);//根据数组中的数字对应画出相应的图片
                if (gameState != STATE_LOST) {
                    if (mapSky[x][y] > -1) {
                        canvas.drawBitmap(tiles[mapSky[x][y]], null, rDst,
                                paint);
                        // canvas.drawPoint(rDst.left, rDst.top, p);
                    }
                } else {
                    if (mapGround[x][y] != 12 && mapSky[x][y] == 10) {
                        mapSky[x][y] = 14;//游戏失败后，指明插红旗的雷
                    }
                    if (mapSky[x][y] > -1 && mapGround[x][y] != 12
                            || mapSky[x][y] == 13 || mapSky[x][y] == 10) {
                        canvas.drawBitmap(tiles[mapSky[x][y]], null, rDst,
                                paint);//失败后保持状态的格子
                        // canvas.drawPoint(rDst.left, rDst.top, p);
                    }
                }
            }
        }

    }

    private void updateView() {
        Log.v(TAG, "updateView");
        if (gameState == STATE_PLAYING) {
            time = (System.currentTimeMillis() - startTime) / 1000;
            mRedrawHandler.sleep(MILLIS_PER_TICK);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        // Log.v(LOG_TAG, "action:" + action + " x:" + x + " y:" + y);

        if (action == MotionEvent.ACTION_DOWN) {
            // Log.v(TAG, "Checking start ********");
            mapX = screenX2mapX(x);
            mapY = screenY2mapY(y);

            if (gameState != STATE_LOST && mapX > -1 && mapY > -1) {
                if (gameState == STATE_PAUSE) {
                    gameState = STATE_PLAYING;
                    startTime = System.currentTimeMillis();
                    updateView();
                }

                if (altKeyDown) {//当ALt按下时 插旗
                    if (mapSky[mapX][mapY] == 0) {
                        mapSky[mapX][mapY] = 10;
                        remain--;
                    } else if (mapSky[mapX][mapY] == 10) {
                        remain++;
                        mapSky[mapX][mapY] = 11;
                    } else if (mapSky[mapX][mapY] == 11) {
                        mapSky[mapX][mapY] = 0;
                    } else if (mapSky[mapX][mapY] == -1) {//模拟扫雷左右键同时按下，sky层没有被初始化的时候为-1
                        int flags = flag(mapX - 1, mapY - 1)
                                + flag(mapX - 1, mapY)
                                + flag(mapX - 1, mapY + 1)
                                + flag(mapX, mapY + 1)
                                + flag(mapX + 1, mapY + 1)
                                + flag(mapX + 1, mapY)
                                + flag(mapX + 1, mapY - 1)
                                + flag(mapX, mapY - 1);
                        if (flags == mapGround[mapX][mapY]) {
                            Log.v(TAG, "flags:" + flags);
                            open(mapX - 1, mapY - 1);
                            open(mapX - 1, mapY);
                            open(mapX - 1, mapY + 1);
                            open(mapX, mapY + 1);
                            open(mapX + 1, mapY + 1);
                            open(mapX + 1, mapY);
                            open(mapX + 1, mapY - 1);
                            open(mapX, mapY - 1);
                        }
                    }
                } else {
                    open(mapX, mapY);
                }
                invalidate();
            } else {
                if (x < 26 && y < 26) {
                    gameState = STATE_PAUSE;
                    reset();
                    invalidate();
                }
                if (x > 30 && x < 56 && y > 0 && y < 26) {
                    altKeyDown = !altKeyDown;
                    invalidate();
                }
            }
        }
        return true;
    }

    private int flag(int x, int y) {
        if (x > -1 && x < tileCountX && y > -1 && y < tileCountY) {
            if (mapSky[x][y] == 10)
                return 1;
        }
        return 0;
    }

    public void open(int x, int y) {
        if (x > -1 && x < tileCountX && y > -1 && y < tileCountY) {
            if (mapSky[x][y] == -1)
                return;
            if (mapSky[x][y] == 0 || mapSky[x][y] == 11) {
                if (mapGround[x][y] == 12) {
                    mapSky[x][y] = 13;
                    gameState = STATE_LOST;
                } else {
                    mapSky[x][y] = -1;
                    safeCount--;
                    if (safeCount == 0) {
                        gameState = STATE_WIN;
                    }
                    if (mapGround[x][y] == 9) {
                        open(x - 1, y - 1);
                        open(x - 1, y);
                        open(x - 1, y + 1);
                        open(x, y + 1);
                        open(x + 1, y + 1);
                        open(x + 1, y);
                        open(x + 1, y - 1);
                        open(x, y - 1);
                    }
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                altKeyDown = true;
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                altKeyDown = false;
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private int screenX2mapX(int c) {//得到横轴所在的格子
        if (c - offsetX < 0)
            return -1;
        int rtn = (c - offsetX) / tileWidth;
        if (rtn >= tileCountX)
            return -1;
        return rtn;
    }

    private int screenY2mapY(int c) {//得到纵轴所在的格子
        if (c - offsetY < 0)
            return -1;
        int rtn = (c - offsetY) / tileHeight;
        if (rtn >= tileCountY)
            return -1;
        return rtn;
    }

}

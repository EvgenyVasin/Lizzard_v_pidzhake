package com.example.android.lizzard_v_pidzhake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Evasin on 31.01.2018.
 */

public class SnakeEngine extends SurfaceView implements Runnable {

    private Thread thread = null;
    private SoundPool soundPool;
    private int eat_bob = -1;
    private int snake_crash = -1;

    public enum Heading {UP, RIGHT, DOWN, LEFT}
    public boolean isSoundEnable = myMainActivity.isSoundEnable();


    private Heading heading = Heading.RIGHT;
    private int screenX;
    private int screenY;
    private int snakeLength;
    private int bobX;
    private int bobY;
    private int blockSize;
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;
    private long nextFrameTime;
    private final long FPS = 5;
    private final long MILLIS_PER_SECOND = 1000;
    private int score;
    private int[] snakeXs;
    private int[] snakeYs;
    private volatile boolean isPlaying;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public SnakeEngine(Context contex, Point size) {
        super(contex);

        screenX = size.x;
        screenY = size.y;

        blockSize = screenX / NUM_BLOCKS_WIDE;

        numBlocksHigh = screenY / blockSize;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        try {
            AssetManager assetManager = contex.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("eat_bob.ogg");
            eat_bob = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_crash.ogg");
            snake_crash = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            //Error
        }

        surfaceHolder = getHolder();
        paint = new Paint();

        snakeXs = new int[200];
        snakeYs = new int[200];

        newGame();
    }

    @Override
    public void run() {

        while (isPlaying) {

            if (updateRequired()) {

                update();
                draw();
            }

        }
    }

    public void pause() {

        isPlaying = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            //Error
        }
    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {

        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE / 2;
        snakeYs[0] = numBlocksHigh / 2;

        spawnBob();

        score = 0;

        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnBob() {
        Random random = new Random();
        bobX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        bobY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    public void eatBob() {
        snakeLength++;
        spawnBob();
        score += 1;
        if (isSoundEnable) soundPool.play(eat_bob, 1, 1, 0, 0, 1);
    }

    public void moveSnake() {
        for (int i = snakeLength; i > 0; i--) {
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];
        }

        switch (heading) {
            case UP:
                snakeYs[0]--;
                break;

            case RIGHT:
                snakeXs[0]++;
                break;

            case DOWN:
                snakeYs[0]++;
                break;

            case LEFT:
                snakeXs[0]--;
                break;
        }

    }

    private boolean detectDeath() {

        boolean death = false;

        if (snakeXs[0] == -1) death = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) death = true;
        if (snakeYs[0] == -1) death = true;
        if (snakeYs[0] == numBlocksHigh) death = true;

        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                death = true;
            }
        }
        return death;
    }

    public void update() {
        if (snakeYs[0] == bobY && snakeXs[0] == bobX) {
            eatBob();
        }

        moveSnake();

        if (detectDeath()) {
            if (isSoundEnable) soundPool.play(snake_crash, 1, 1, 0, 0, 1);
            newGame();
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182));
            paint.setColor(Color.argb(255, 255, 225, 255));

            paint.setTextSize(90);
            canvas.drawText("Score: " + score, 10, 70, paint);

            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint);
            }

            paint.setColor(Color.argb(255, 255, 0, 0));

            canvas.drawRect(bobX * blockSize,
                    (bobY * blockSize),
                    (bobX * blockSize) + blockSize,
                    (bobY * blockSize) + blockSize,
                    paint);

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    public boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch (heading) {
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch (heading) {
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;

    }

}

package com.example.android.lizzard_v_pidzhake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class myMainActivity extends Activity {

    private Button playButton;
    private Button soundButton;
    private Button exitButton;
    private Button difficulty1;
    private Button difficulty2;
    private Button difficulty3;

    private SnakeEngine snakeEngine;
    public static boolean soundEnable = true;

    public static long fps = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        playButton = (Button) findViewById(R.id.play_button);
        soundButton = (Button) findViewById(R.id.sound_button);
        exitButton = (Button) findViewById(R.id.exit_button);
        difficulty1 = (Button) findViewById(R.id.difficultly1);
        difficulty2 = (Button) findViewById(R.id.difficultly2);
        difficulty3 = (Button) findViewById(R.id.difficultly3);
    }

    public void onClickPlay(View view) {
        Intent intent = new Intent(this, SnakeActivity.class);
        startActivity(intent);
    }

    public void onClickSound(View view){
        if (soundEnable) {
            soundEnable = false;
            soundButton.setText("Sound disabled");
        } else {
            soundEnable = true;
            soundButton.setText("Sound enabled");
        }
    }

    public void onClickDifficultly1(View view){
        fps = 5;
        TextView difficultly = (TextView) findViewById(R.id.difficultly);
        difficultly.setText("LOVER DIFFICULTLY");
    }

    public void onClickDifficultly2(View view){
        fps = 10;
        TextView difficultly = (TextView) findViewById(R.id.difficultly);
        difficultly.setText("MEDIUM DIFFICULTLY");
    }

    public void onClickDifficultly3(View view){
        fps = 15;
        TextView difficultly = (TextView) findViewById(R.id.difficultly);
        difficultly.setText("HIGH DIFFICULTLY");
    }

    public void onClickExit(View view){
        this.finish();
    }

    public static boolean isSoundEnable(){
        return soundEnable;
    }

    public static long getFps(){
        return fps;
    }

}

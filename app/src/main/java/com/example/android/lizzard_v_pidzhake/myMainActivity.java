package com.example.android.lizzard_v_pidzhake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class myMainActivity extends Activity {

    private Button playButton;
    private Button scoreButton;
    private Button soundButton;
    private Button exitButton;
    private SnakeEngine snakeEngine;
    public static boolean soundEnable = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        playButton = (Button) findViewById(R.id.play_button);
        soundButton = (Button) findViewById(R.id.sound_button);
        exitButton = (Button) findViewById(R.id.exit_button);
    }

    public void onClickPlay(View view) {
        Intent intent = new Intent(this, SnakeActivity.class);
        startActivity(intent);
    }

    public void onClickSound(View view){

        if (soundEnable) {
            soundEnable = false;
            soundButton.setText("Sound disabled");
        }

        if(!soundEnable) {
            soundEnable = true;
            soundButton.setText("Sound enabled");
        }

    }

    public void onClickExit(View view){
        this.finish();
    }

    public static boolean isSoundEnable(){
        return soundEnable;
    }
}

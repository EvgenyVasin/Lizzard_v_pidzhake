package com.example.android.lizzard_v_pidzhake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button playButton;
    private Button scoreButton;
    private Button soundButton;
    private Button exitButton;
    private SnakeEngine snakeEngine;
    public static boolean soundEnable = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.play_button);
        soundButton = (Button) findViewById(R.id.sound_button);
    }

    public void onClickPlay(View view) {
                Intent intent = new Intent(this, SnakeActivity.class);
                startActivity(intent);
    }

    public void onClickSound(View view){

        if (soundEnable) {
            soundEnable = false;
            Toast.makeText(view.getContext(), "Sound disabled", Toast.LENGTH_SHORT).show();
        } else {
            soundEnable = true;
            Toast.makeText(view.getContext(), "Sound enabled", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isSoundEnable(){
        return soundEnable;
    }



}

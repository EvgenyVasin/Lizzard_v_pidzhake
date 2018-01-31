package com.example.android.lizzard_v_pidzhake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button playButton;
    private Button scoreButton;
    private Button soundButton;
    private Button exitButton;
    private SnakeEngine snakeEngine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.play_button);
    }

    public void onClick(View v) {
                Intent intent = new Intent(this, SnakeActivity.class);
                startActivity(intent);
    }



}

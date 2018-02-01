package com.example.android.lizzard_v_pidzhake;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
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
    MediaPlayer mPlayer = new MediaPlayer();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        try{
            AssetFileDescriptor afd = getAssets().openFd("main-menu.ogg");
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepare();
        } catch (Exception e){
            //Error
        }
            mPlayer.start();
            mPlayer.setLooping(true);

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
            mPlayer.stop();
        } else {
            soundEnable = true;
            soundButton.setText("Sound enabled");
            try{
                AssetFileDescriptor afd = getAssets().openFd("main-menu.ogg");
                mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mPlayer.prepare();
            } catch (Exception e){
                //Error
            }
            mPlayer.start();
            mPlayer.setLooping(true);
        }

    }

    public void onClickExit(View view){
        mPlayer.stop();
        this.finish();
    }

    public static boolean isSoundEnable(){
        return soundEnable;
    }
}

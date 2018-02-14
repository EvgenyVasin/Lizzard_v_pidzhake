package com.example.android.lizzard_v_pidzhake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {

    private View view;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent splash = new Intent(MainActivity.this, myMainActivity.class);
                    startActivity(splash);
                    finish();
                }
            }, 5000);
        }

}

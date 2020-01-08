package com.example.alejandroruizponce1.chekinkey;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new
                        Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, 2000);
    }
}
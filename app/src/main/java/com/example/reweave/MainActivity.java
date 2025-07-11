package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity extends AppCompatActivity {
    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splash = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        splash.setKeepOnScreenCondition(() -> isLoading);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            isLoading = false;
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        }, 1000);
    }
}

package com.techlabs.apdcl.view.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.databinding.ActivitySplashScreenBinding;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private PrefManager prefManager;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefManager = new PrefManager(SplashScreen.this);
        boolean isFirstTimeUser = prefManager.getIsFirstTimeUser();

        if (!isFirstTimeUser) {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            SplashScreen.this.finish();
            finish();
        } else {
            boolean hasLoggedIn = prefManager.getIsUserLogin();
            if (!hasLoggedIn) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1.0f, 1.2f,
                        1.0f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                binding.imgMainLogo.startAnimation(scaleAnimation);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }, 1500);
            } else {
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1.0f, 1.4f,
                        1.0f, 1.4f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
                scaleAnimation.setDuration(2000);
                scaleAnimation.setFillAfter(true);
                binding.imgMainLogo.startAnimation(scaleAnimation);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, DropDownActivity.class));
                        finish();
                    }
                }, 2000);
                
            }

        }
    }

}
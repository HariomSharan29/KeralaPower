package com.techlabs.apdcl.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.techlabs.apdcl.R;


public class ProgressBarLayout extends ConstraintLayout {
    private TextView processText;
    public View[] views;
    private Handler handler;
    private int delay;
    private int currentIndex;
    private int coverCount = 5;

    public ProgressBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressBarLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_progress_bar, this, true);
        processText = findViewById(R.id.processText);

        views = new View[]{
                findViewById(R.id.one), findViewById(R.id.two), findViewById(R.id.three), findViewById(R.id.four),
                findViewById(R.id.five), findViewById(R.id.six), findViewById(R.id.seven), findViewById(R.id.eight),
                findViewById(R.id.nine), findViewById(R.id.ten), findViewById(R.id.eleven), findViewById(R.id.twelve),
                findViewById(R.id.thirteen), findViewById(R.id.fourteen), findViewById(R.id.fifteen), findViewById(R.id.sixteen),
                findViewById(R.id.seventeen), findViewById(R.id.eighteen), findViewById(R.id.nineteen), findViewById(R.id.twenty),
                findViewById(R.id.tOne), findViewById(R.id.tTwo), findViewById(R.id.tThree), findViewById(R.id.tFour),
                findViewById(R.id.tFive), findViewById(R.id.tSix), findViewById(R.id.tSeven), findViewById(R.id.tEight),
                findViewById(R.id.tNine), findViewById(R.id.Thirty), findViewById(R.id.ThOne), findViewById(R.id.ThTwo),
                findViewById(R.id.ThThree), findViewById(R.id.ThFour), findViewById(R.id.ThFive), findViewById(R.id.ThSix),
                findViewById(R.id.ThSeven), findViewById(R.id.ThEight), findViewById(R.id.ThNine)
        };
        handler = new Handler(Looper.getMainLooper());
    }
    public void setProcessText(String text) {
        processText.setText(text);
    }
    public void startAnimation(int delay){
        this.delay = delay;
        this.currentIndex = 0;
        handler.post(progressAnimation);
    }
    public void stopAnimation() {
        handler.removeCallbacksAndMessages(null);
    }
    Runnable progressAnimation = new Runnable() {

        @Override
        public void run() {
            for (int i = 0; i < coverCount; i++) {
                int resetIndex = (currentIndex - i - 1 + views.length) % views.length;
                views[resetIndex].setBackgroundResource(R.drawable.view_bg);
            }

            views[(currentIndex) % views.length].setBackgroundResource(R.drawable.blue_bg3);
            views[(currentIndex + 1) % views.length].setBackgroundResource(R.drawable.blue_bg2);
            views[(currentIndex + 2) % views.length].setBackgroundResource(R.drawable.blue_bg);
            views[(currentIndex + 3) % views.length].setBackgroundResource(R.drawable.blue_bg2);
            views[(currentIndex + 4) % views.length].setBackgroundResource(R.drawable.blue_bg3);

            currentIndex = (currentIndex + 1) % views.length;
            handler.postDelayed(this, delay);
        }
    };


}


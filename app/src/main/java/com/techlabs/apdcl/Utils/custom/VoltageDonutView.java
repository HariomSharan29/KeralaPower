package com.techlabs.apdcl.Utils.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class VoltageDonutView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rectF = new RectF();

    private int feeder = 0;
    private int pss = 0;

    private int feederColor;
    private int pssColor;

    private float targetSweep = 0f;
    private float animatedSweep = 0f;

    public VoltageDonutView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setStrokeCap(Paint.Cap.ROUND);
        shadowPaint.setColor(Color.parseColor("#33000000"));

        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setStrokeCap(Paint.Cap.ROUND);
        highlightPaint.setColor(Color.WHITE);
        highlightPaint.setAlpha(80);
    }

    public void setData(int feeder, int pss, int feederColor, int pssColor) {
        this.feeder = feeder;
        this.pss = pss;
        this.feederColor = feederColor;
        this.pssColor = pssColor;
        int total = feeder + pss;
        float percent = Math.min(total, 100) / 100f;
        targetSweep = 360f * percent;
        startAnim();
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, targetSweep);
        animator.setDuration(900);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(a -> {
            animatedSweep = (float) a.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float size = Math.min(getWidth(), getHeight());
        float stroke = size * 0.18f;

        paint.setStrokeWidth(stroke);
        shadowPaint.setStrokeWidth(stroke + 6);
        highlightPaint.setStrokeWidth(stroke - 8);

        rectF.set(stroke, stroke, size - stroke, size - stroke);

        int total = feeder + pss;
        if (total == 0) return;

        float startAngle = -90;
        float clampedSweep = Math.min(animatedSweep, 360f);

        canvas.drawArc(rectF, startAngle + 4, 360f, false, shadowPaint);

        float feederSweep = feeder > 0 ? (feeder * clampedSweep) / total : 0f;
        float pssSweep = clampedSweep - feederSweep;

        if (feeder > 0) {
            paint.setColor(getResources().getColor(feederColor));
            canvas.drawArc(
                    rectF,
                    startAngle,
                    feederSweep,
                    false,
                    paint
            );

            canvas.drawArc(
                    rectF,
                    startAngle,
                    feederSweep * 0.6f,
                    false,
                    highlightPaint
            );
        }

        if (pss > 0 && clampedSweep > feederSweep) {
            paint.setColor(getResources().getColor(pssColor));
            canvas.drawArc(
                    rectF,
                    startAngle + feederSweep,
                    pssSweep,
                    false,
                    paint
            );

            canvas.drawArc(
                    rectF,
                    startAngle + feederSweep,
                    pssSweep * 0.6f,
                    false,
                    highlightPaint
            );
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
        ));

        paint.setTextSize(size * 0.18f);
        canvas.drawText(
                String.valueOf(total),
                size / 2,
                size / 2,
                paint
        );

        paint.setTextSize(size * 0.10f);
        canvas.drawText(
                "TOTAL",
                size / 2,
                size / 2 + paint.getTextSize() + 6,
                paint
        );

        paint.setStyle(Paint.Style.STROKE);
    }
}

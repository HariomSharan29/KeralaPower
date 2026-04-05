package com.techlabs.apdcl.Utils.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {

    private List<Float> values = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();

    private Paint arcPaint;
    private RectF rectF;
    private float animatedSweep = 0f;

    private float strokeWidth = 60f;
    private float gapAngle = 2f;

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        arcPaint.setStrokeCap(Paint.Cap.BUTT);
        arcPaint.setShadowLayer(10, 0, 5, 0x55000000);
        setLayerType(LAYER_TYPE_SOFTWARE, arcPaint);

        rectF = new RectF();
    }

    public void setData(List<Float> values, List<Integer> colors) {
        this.values = values;
        this.colors = colors;
        animateChart();
    }

    private void animateChart() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1200);
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

        float total = 0f;
        for (float v : values) total += v;
        if (total == 0) return;

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);

        float padding = strokeWidth * .6f;

        float left = (width - size) / 2f + padding;
        float top = (height - size) / 2f + padding;
        float right = width - ((width - size) / 2f + padding);
        float bottom = height - ((height - size) / 2f + padding);

        rectF.set(left, top, right, bottom);

        float startAngle = -90f;

        for (int i = 0; i < values.size(); i++) {

            float value = values.get(i);
            float percent = value / total;

            float sweep = percent * 360f;
            sweep -= gapAngle;
            sweep *= animatedSweep;

            arcPaint.setColor(colors.get(i));

            canvas.drawArc(rectF, startAngle, sweep, false, arcPaint);

            startAngle += (sweep + gapAngle);
        }
    }
}

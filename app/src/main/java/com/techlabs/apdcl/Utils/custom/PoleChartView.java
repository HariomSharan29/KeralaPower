package com.techlabs.apdcl.Utils.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class PoleChartView extends View {

    private final List<Float> values = new ArrayList<>();
    private final List<String> labels = new ArrayList<>();
    private final List<Integer> colors = new ArrayList<>();

    private float animatedProgress = 0f;

    private Paint frontPaint, sidePaint, topPaint, textPaint, valuePaint;
    private Path tmpPath = new Path();

    public PoleChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        frontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        frontPaint.setStyle(Paint.Style.FILL);
        frontPaint.setShadowLayer(14f, 0f, 6f,
                Color.argb(120, 0, 0, 0));

        sidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sidePaint.setStyle(Paint.Style.FILL);
        sidePaint.setShadowLayer(18f, 6f, 6f,
                Color.argb(140, 0, 0, 0));

        topPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30f);
        textPaint.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);

        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setColor(Color.BLACK);
        valuePaint.setTextSize(34f);
        valuePaint.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        valuePaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(List<Float> vals, List<Integer> cols, List<String> lbs) {
        values.clear();
        labels.clear();
        colors.clear();

        if (vals != null) values.addAll(vals);
        if (cols != null) colors.addAll(cols);
        if (lbs != null) labels.addAll(lbs);

        int n = Math.min(Math.min(values.size(), colors.size()), labels.size());
        while (values.size() > n) values.remove(values.size() - 1);
        while (colors.size() > n) colors.remove(colors.size() - 1);
        while (labels.size() > n) labels.remove(labels.size() - 1);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(a -> {
            animatedProgress = (float) a.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (values.size() == 0) return;

        float w = getWidth();
        float h = getHeight();

        float maxVal = 1f;
        for (float v : values) maxVal = Math.max(maxVal, v);

        float barArea = w * 0.75f;
        float leftMargin = w * 0.22f;
        float barWidth = barArea / (values.size() * 1.8f);
        float gap = barWidth * 0.2f;
        float startX = leftMargin;

        float depth = barWidth * 0.45f;
        float depthY = depth * 0.55f;

        float chartBottom = h * 0.85f;
        float chartTop = h * 0.25f;
        float usableHeight = chartBottom - chartTop;

        for (int i = 0; i < values.size(); i++) {

            float val = values.get(i);
            float ratio = (val / maxVal) * animatedProgress;

            float barLeft = startX + i * (barWidth + gap);
            float barRight = barLeft + barWidth;

            float barHeight = usableHeight * ratio;

            float frontTop = chartBottom - barHeight;
            float frontBottom = chartBottom;

            int baseColor = colors.get(i);
            frontPaint.setColor(baseColor);
            sidePaint.setColor(adjust(baseColor, 0.7f));
            topPaint.setColor(adjust(baseColor, 1.12f));

            tmpPath.reset();
            tmpPath.moveTo(barLeft, frontTop);
            tmpPath.lineTo(barRight, frontTop);
            tmpPath.lineTo(barRight, frontBottom);
            tmpPath.lineTo(barLeft, frontBottom);
            tmpPath.close();
            canvas.drawPath(tmpPath, frontPaint);

            tmpPath.reset();
            tmpPath.moveTo(barRight, frontTop);
            tmpPath.lineTo(barRight + depth, frontTop - depthY);
            tmpPath.lineTo(barRight + depth, frontBottom - depthY);
            tmpPath.lineTo(barRight, frontBottom);
            tmpPath.close();
            canvas.drawPath(tmpPath, sidePaint);

            tmpPath.reset();
            tmpPath.moveTo(barLeft, frontTop);
            tmpPath.lineTo(barLeft + depth, frontTop - depthY);
            tmpPath.lineTo(barRight + depth, frontTop - depthY);
            tmpPath.lineTo(barRight, frontTop);
            tmpPath.close();
            canvas.drawPath(tmpPath, topPaint);

            String valueText = String.valueOf(Math.round(val));
            float valueX = barLeft + barWidth / 1.5f;
            float valueY = frontTop - depthY - 5;
            if (valueY < valuePaint.getTextSize()) {
                valueY = valuePaint.getTextSize();
            }
            canvas.drawText(valueText, valueX, valueY, valuePaint);

            String label = labels.get(i);
            textPaint.setTextAlign(Paint.Align.LEFT);
/*            float labelX = barLeft + barWidth / 1.8f;
            float labelY = frontBottom - barHeight / 2f;*/
            float labelX = barLeft + barWidth * 0.60f;
            float labelY = frontBottom - 8f;
            canvas.save();
            canvas.rotate(-90, labelX, labelY);
            canvas.drawText(label, labelX, labelY, textPaint);
            canvas.restore();
        }
    }

    private int adjust(int color, float f) {
        int r = Math.min(255, (int) (Color.red(color) * f));
        int g = Math.min(255, (int) (Color.green(color) * f));
        int b = Math.min(255, (int) (Color.blue(color) * f));
        return Color.rgb(r, g, b);
    }

}

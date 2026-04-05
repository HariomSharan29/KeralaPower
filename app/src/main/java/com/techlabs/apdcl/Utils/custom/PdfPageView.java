package com.techlabs.apdcl.Utils.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfRenderer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class PdfPageView extends View {

    private PdfRenderer.Page page;
    private Bitmap bitmap;
    private float scaleFactor = 1f;
    private float maxScale = 4f;
    private float minScale = 1f;

    private float translateX = 0f;
    private float translateY = 0f;
    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

    public PdfPageView(Context context) {
        super(context);
        init(context);
    }

    public PdfPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
                translateX -= dx;
                translateY -= dy;
                invalidate();
                return true;
            }
        });
    }

    public void setPage(PdfRenderer.Page page) {
        this.page = page;
        renderPage();
        invalidate();
    }

    private void renderPage() {
        if (page == null) return;

        int viewWidth = getResources().getDisplayMetrics().widthPixels;
        float ratio = (float) page.getHeight() / page.getWidth();
        int viewHeight = (int) (viewWidth * ratio);

        bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (bitmap != null) {
            setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap != null) {
            canvas.save();

            canvas.translate(translateX, translateY);
            canvas.scale(scaleFactor, scaleFactor);

            canvas.drawBitmap(bitmap, 0, 0, null);

            canvas.restore();
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            scaleFactor = Math.max(minScale, Math.min(scaleFactor, maxScale));

            invalidate();
            return true;
        }
    }
}


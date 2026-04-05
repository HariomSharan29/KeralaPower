package com.techlabs.apdcl.Utils.custom;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class ZoomImageView extends AppCompatImageView {

    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mode = NONE;

    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;

    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 4f;

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
        matrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                }
                else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        float scale = newDist / oldDist;

                        Matrix tempMatrix = new Matrix(savedMatrix);
                        tempMatrix.postScale(scale, scale, mid.x, mid.y);

                        float[] values = new float[9];
                        tempMatrix.getValues(values);
                        float currentScale = values[Matrix.MSCALE_X];

                        if (currentScale > MIN_ZOOM && currentScale < MAX_ZOOM) {
                            matrix.set(tempMatrix);
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }

        setImageMatrix(matrix);
        return true;
    }

    private float spacing(MotionEvent event) {
        if (event.getPointerCount() < 2) return 0;
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        fitToScreen();
    }

    private void fitToScreen() {
        if (getDrawable() == null) return;

        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float imgWidth = getDrawable().getIntrinsicWidth();
        float imgHeight = getDrawable().getIntrinsicHeight();

        float scale = Math.min(viewWidth / imgWidth, viewHeight / imgHeight);

        matrix.reset();
        matrix.postScale(scale, scale);

        float redundantXSpace = (viewWidth - (scale * imgWidth)) / 2;
        float redundantYSpace = (viewHeight - (scale * imgHeight)) / 2;

        matrix.postTranslate(redundantXSpace, redundantYSpace);

        setImageMatrix(matrix);
    }

}

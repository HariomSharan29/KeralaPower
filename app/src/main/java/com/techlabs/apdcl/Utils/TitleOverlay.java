package com.techlabs.apdcl.Utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

public class TitleOverlay extends Overlay {

    private final String title;
    private final Paint paint;

    public TitleOverlay(String title) {
        this.title = title;
        this.paint = new Paint();
        paint.setColor(0xFF000000); // Black color
        paint.setTextSize(50); // Adjust size as needed
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (!shadow) {
            // Draw the title at the top-center of the map view
            canvas.drawText(title, (canvas.getWidth() - paint.measureText(title)) / 2, 60, paint);
        }
    }
}
package com.techlabs.apdcl.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BitmapImg {
    public static Bitmap Spotload(){
        String text = "\u3403";
        int textSize = 85;
        int textColor = Color.BLACK;
        int backgroundColor = Color.TRANSPARENT;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        int width = textBounds.width() + 16;
        int height = textBounds.height() + 16;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(backgroundColor);
        canvas.drawText(text, width / 2f, height / 2f + textBounds.height() / 2f, paint);
        return bitmap;
    }

    public static Bitmap Shuntcapacitor(){
        String text = "\u2AE9";
        int textSize = 50;
        int textColor = Color.BLACK;
        int backgroundColor = Color.TRANSPARENT;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        int width = textBounds.width() + 16; // add some padding to the width
        int height = textBounds.height() + 16; // add some padding to the height
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(backgroundColor);
        canvas.drawText(text, width / 2f, height / 2f + textBounds.height() / 2f, paint);

        int paddingLeft = 0; // Left padding in pixels
        int paddingTop = 0; // Top padding in pixels
        int paddingRight = 0; // Right padding in pixels
        int paddingBottom = 50; // Bottom padding in pixels
        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
        Canvas canva = new Canvas(paddedBitmap);
        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
        return paddedBitmap;
    }
}

package com.techlabs.apdcl.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.techlabs.apdcl.R;


public class OTFToBitmapConverter{

    public static Bitmap convertOTFToBitmap(Context context, String text, float textSize, int color) {

        Typeface typeface = ResourcesCompat.getFont(context, R.font.ots);
        typeface.getStyle();

        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.clearShadowLayer();

        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);

        int width = textBounds.width();
        int height = textBounds.height();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, -textBounds.left, -textBounds.top, paint);

        canvas.drawBitmap(bitmap, 0, 0, paint);


        return bitmap;
    }

}

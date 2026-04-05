package com.techlabs.apdcl.Utils;

import static java.lang.Math.atan2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.res.ResourcesCompat;

import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ResponseDataUtils {

    public static ArrayList<String> NetworkList = new ArrayList<>();
    public static final HashMap<String, String> SXST_MAP = new HashMap<>();


    public static boolean checkInternetConnectionAndInternetAccess(Context ctx) {
        boolean InternetAccess;
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo network = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (network != null && wifi.isConnected()) {
            InternetAccess = true;
        } else InternetAccess = network != null && network.isConnected();
        return InternetAccess;
    }

    private static boolean isExpandable = false;

    public static void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public static boolean isExpandable() {
        return isExpandable;
    }

    public static Bitmap RotateMyBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static double CalculateAngel(double fromX, double fromY, double toX, double toY) {
        double deltaX = fromX - toX;
        double deltaY = fromY - toY;
        double rad = atan2(deltaY, deltaX) * 180.0 / Math.PI;
        Log.d("Angelse", String.valueOf(rad));
        return -rad;
    }

    public static double calculateAngles(double startPointX, double startPointY, double endPointX, double endPointY) {
        double deltaX = endPointX - startPointX;
        double deltaY = endPointY - startPointY;
        double angleRadians = atan2(deltaY, deltaX);
        double angleDegrees = Math.toDegrees(angleRadians);
        if (angleDegrees < 0) {
            angleDegrees += 360;
        }
        return angleDegrees;
    }

    public static double calgles(double fx, double fy, double tx, double ty) {
        double cx = fx;
        double cy = fy;
        double ex = tx;
        double ey = ty;
        double dy = ey - cy;
        double dx = ex - cx;
        double theta = (Math.atan2(dy, dx) * 180.0) / Math.PI;
        return -theta;
    }

    public static Bitmap addPaddingToBitmap(Bitmap originalBitmap, int leftPadding, int rightPadding) {
        int newWidth = originalBitmap.getWidth() + leftPadding + rightPadding;
        int newHeight = originalBitmap.getHeight();
        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, originalBitmap.getConfig());
        Canvas canvas = new Canvas(paddedBitmap);
        canvas.drawBitmap(originalBitmap, leftPadding, 0, null);
        return paddedBitmap;
    }

    public static Bitmap DecreaseBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        int newWidth = width / 2; // Half the width
        int newHeight = height / 2; // Half the height
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false);
        return resizedBitmap;
    }

    public static double CalculateAng(double fromX, double toX, double fromY, double toY) {
        double deltaLongitude = Double.valueOf(fromX) - Double.valueOf(toX);
        double deltaLatitude = Double.valueOf(fromY) - Double.valueOf(toY);
        double angleRadians = atan2(deltaLongitude, deltaLatitude);
        double angleDegrees = Math.toDegrees(angleRadians);
        double adjustedAngle = angleDegrees;
        return adjustedAngle;
    }

    public static double calculate(double startPointX, double startPointY, double endPointX, double endPointY) {
        double deltaX = endPointX - startPointX;
        double deltaY = endPointY - startPointY;
        double angleRadians = atan2(deltaY, deltaX);
        double angleDegrees = Math.toDegrees(angleRadians);
        if (angleDegrees < 0) {
            angleDegrees += 360;
        }
        return angleDegrees;
    }

    public static Drawable createMarkerDrawable(Context context, String text) {
        final Typeface typ = ResourcesCompat.getFont(context, R.font.myfonts);
        Paint paint = new Paint();
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, bounds.left, -bounds.top, paint);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Bitmap createMarkerBitmap(Typeface typeface, String text) {
        Paint paint = new Paint();
        paint.setTextSize(18);
        paint.setTypeface(typeface);
        paint.setColor(Color.BLACK);
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(text) + 0.5f);
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, baseline, paint);
        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap changeBitmapColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);
        return resultBitmap;
    }

    public static Bitmap changeBgTransparentBitmapColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawText("", 2, 2, paint);
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);
        return resultBitmap;
    }

    public static double CalculateSplAngel(double fromX, double toX, double fromY, double toY) {
        double delta_y = fromY - toY;
        return delta_y;
    }

    public static double calculateBearing(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double deltaLon = lon2 - lon1;
        double y = Math.sin(deltaLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLon);
        double angle = Math.toDegrees(Math.atan2(y, x));
        angle = (angle + 360) % 360; // Normalize to 0-360 degrees
        return angle;
    }

    public static int getPixelColor(Bitmap bitmap, int x, int y) {
        if (x < 0 || x >= bitmap.getWidth() || y < 0 || y >= bitmap.getHeight()) {
            return 0;
        }
        return bitmap.getPixel(x, y);
    }

    public static String getHexsColor(Bitmap bitmap, int x, int y) {
        if (x < 0 || x >= bitmap.getWidth() || y < 0 || y >= bitmap.getHeight()) {
            return "";
        }

        int pixelColor = bitmap.getPixel(x, y);

        return String.format("#%06X", (0xFFFFFF & pixelColor));
    }

    public static float calculateBearings(double startLat, double startLng, double endLat, double endLng) {
        Location startLocation = new Location("start");
        startLocation.setLatitude(startLat);
        startLocation.setLongitude(startLng);
        Location endLocation = new Location("end");
        endLocation.setLatitude(endLat);
        endLocation.setLongitude(endLng);
        return startLocation.bearingTo(endLocation);
    }

    public static Bitmap changeSourceColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);
        return resultBitmap;
    }
    public static String formatDateTime(String inputDateTime) {

        // Input format
        DateTimeFormatter inputFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Output format: feb 18, 2025 - 09:30 am
        DateTimeFormatter outputFormatter =
                DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a", Locale.ENGLISH);

        LocalDateTime dateTime =
                LocalDateTime.parse(inputDateTime, inputFormatter);

        return dateTime.format(outputFormatter).toLowerCase();
    }
    public static double getSafeDouble(JsonObject obj, String key) {
        try {
            if (obj != null && obj.has(key) && !obj.get(key).isJsonNull()) {
                return obj.get(key).getAsDouble();
            }
        } catch (Exception ignored) {
        }
        return 0.0;
    }

}

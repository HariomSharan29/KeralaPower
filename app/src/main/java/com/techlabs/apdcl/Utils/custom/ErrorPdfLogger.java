package com.techlabs.apdcl.Utils.custom;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.util.concurrent.Executors;

public class ErrorPdfLogger {

    private static final String FOLDER_NAME = "Error Log";
    private static final String ROOT_FOLDER = "T-LABS-NetAnalysis";
    private static final SimpleDateFormat FILE_DATE =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat DATE_TIME =
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

    public static void logApiError(Context context, String method, String apiUrl, String error) {
        writeTxt(context, method, apiUrl, error, 0);
    }

    public static void logApiFailure(Context context, String method, String apiUrl, Throwable t) {
        writeTxt(context, method, apiUrl, Log.getStackTraceString(t), 0);
    }

    public static void logApiSuccess(Context context, String method, String apiUrl, String response) {
        writeTxt(context, method, apiUrl, response, 0);
    }

    public static void logCrash(Context context, Throwable t) {
        writeTxt(context, "CRASH", "Application Crash", Log.getStackTraceString(t), 0);
    }

    public static void logAnalysis(Context context, String method, String apiUrl, String message) {
        writeTxt(context, method, apiUrl, message, 1);
    }

    public static void logAnalysis(Context context, Exception e) {
        writeTxt(context, "CRASH", "Analysis Error", Log.getStackTraceString(e), 1);
    }
    public static void logNewConnection(Context context, String method, String apiUrl, String message) {
        writeTxt(context, method, apiUrl, message, 2);
    }

    public static void logNewConnection(Context context, Exception e) {
        writeTxt(context, "CRASH", "New Connection Error", Log.getStackTraceString(e), 2);
    }

    private static void writeTxt(Context context, String method, String apiUrl, String message, int logType) {

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                String today = FILE_DATE.format(new Date());
                String time = DATE_TIME.format(new Date());

                File docDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                if (docDir == null) {
                    return;
                }

                File appDataDir = new File(docDir, ROOT_FOLDER);
                if (!appDataDir.exists()) appDataDir.mkdirs();

                File logDir = new File(appDataDir, FOLDER_NAME);
                if (!logDir.exists()) logDir.mkdirs();

                String prefix = switch (logType) {
                    case 1 -> "AnalysisLog";
                    case 2 -> "NewConnectionLog";
                    default -> "ViewLog";
                };
                File txtFile = new File(logDir, prefix + today + ".txt");

                if (!txtFile.exists()) {
                    txtFile.createNewFile();
                }

                FileWriter fw = new FileWriter(txtFile, true);
                fw.write(time + "|" + method + "|" + apiUrl + "|" + message.replace("\n", "\\n") + "\n");
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

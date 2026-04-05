package com.techlabs.apdcl.Utils;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ListDataManager {
    private static List<JsonObject> dataList = new ArrayList<>();

    public static void sendData(JsonObject data) {
        dataList.add(data);
    }

    public static List<JsonObject> getData() {
        return new ArrayList<>(dataList);
    }

    public static void clearData() {
        dataList.clear();
    }

    private static List<JsonObject> defaultDataList = new ArrayList<>();

    public static void sendDefaultData(JsonObject data) {
        defaultDataList.add(data);
    }

    public static List<JsonObject> getDefaultData() {
        return new ArrayList<>(defaultDataList);
    }

    public static void clearDefaultData() {
        defaultDataList.clear();
    }
}

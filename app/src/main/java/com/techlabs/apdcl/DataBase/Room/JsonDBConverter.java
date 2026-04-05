package com.techlabs.apdcl.DataBase.Room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonDBConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromJsonObject(JsonObject jsonObject) {
        return jsonObject == null ? null : gson.toJson(jsonObject);
    }

    @TypeConverter
    public static JsonObject toJsonObject(String value) {
        if (value == null || value.isEmpty()) return null;
        return gson.fromJson(value, JsonObject.class);
    }
}

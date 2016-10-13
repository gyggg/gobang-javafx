package me.gobang.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;

public class JsonTool {
    static Gson gson = new GsonBuilder().create();

    public static String objToJsonString(Object o) {
        return gson.toJson(o);
    }

    public static Object jsonStringToObj(String jsonString, Class classOfT) {
        return gson.fromJson(jsonString, classOfT);
    }
}

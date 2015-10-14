package io.makerforce.undefined.util;

import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class Util {

    public static String durationToString(Duration d) {
        int sec = (int) d.toSeconds();
        return String.format((sec < 0 ? "-" : "") + "%d:%02d", (sec < 0 ? -sec : sec) / 60, ((sec < 0 ? -sec : sec) % 60));
    }

    public static URL toURLOrNull(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Deprecated
    public static String getStringFromJSONObject(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (JSONException e) {
            //e.printStackTrace();
            return "";
        }
    }

    @Deprecated
    public static String getStringFromJSONArray(JSONArray array, int key) {
        try {
            return array.getString(key);
        } catch (JSONException e) {
            //e.printStackTrace();
            return "";
        }
    }

    public static String getNotEmpty(String... strings) {
        for (String s : strings) {
            if (!s.isEmpty()) {
                return s;
            }
        }
        return "";
    }

}

package io.makerforce.undefined.model;

import io.makerforce.undefined.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class Album extends ItemList<Track> {

    public Album() {
        super();
    }

    public Album(URL picture, String title, String artist) {
        super(picture, title, artist);
    }

    public Album(JSONObject o, String albumName, String artistName, URL endPoint) {
        this(Util.toURLOrNull(endPoint + o.getString("picture")), albumName, artistName);
        JSONArray tracks = o.getJSONArray("tracks");
        for (int i = 0; i < tracks.length(); i++) {
            Track a = new Track(o.getJSONArray("tracks").getJSONObject(i), albumName, artistName, endPoint); // Track a = new Track(o.getJSONArray("tracks").getJSONObject(i), endPoint);
            super.getItems().add(a);
        }
    }

}

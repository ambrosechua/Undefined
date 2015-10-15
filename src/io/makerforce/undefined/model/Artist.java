package io.makerforce.undefined.model;

import io.makerforce.undefined.util.Util;
import org.json.JSONObject;

import java.net.URL;

public class Artist extends ItemList<Album> {

    public ItemList<Track> tracks = new ItemList<>();

    public Artist() {
        super();
    }

    public Artist(URL picture, String title) {
        super(picture, title);
    }

    public Artist(JSONObject o, String artistName, URL endPoint) {
        this(Util.toURLOrNull(endPoint + o.getString("picture")), artistName);
        o.getJSONObject("albums").keySet().forEach((key) -> {
            Album a = new Album(o.getJSONObject("albums").getJSONObject(key), key, artistName, endPoint);
            super.getItems().add(a);
            tracks.getItems().addAll(a.getItems());
        });
    }

    public ItemList<Track> getTracks() {
        return tracks;
    }

}

package io.makerforce.undefined.model;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.net.URL;

public class Album extends ItemList<Track> {

    public Album() {
        super();
    }

    public Album(Image picture, String title, String artist) {
        super(picture, title, artist);
    }

    public Album(JSONObject o, String albumName, String artistName, URL endPoint) {
        this(new Image(endPoint.toString() + o.getString("picture")), albumName, artistName);
        o.getJSONObject("tracks").keySet().forEach((key) -> {
            Track a = new Track(o.getJSONObject("tracks").getJSONObject(key), endPoint); //, key, albumName, artistName);
            super.getItems().add(a);
        });
    }


    public ObservableList<Track> getTracks() {
        return this.getItems();
    }

}

package io.makerforce.undefined.model;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.json.JSONObject;

public class Artist extends ItemList<Album> {

    public Artist() {
        super();
    }

    public Artist(Image picture, String title) {
        super(picture, title);
    }

    public Artist(JSONObject o, String artistName) {
        this(new Image(o.getString("picture")), artistName);
        o.getJSONObject("albums").keySet().forEach((key) -> {
            Album a = new Album(o.getJSONObject("albums").getJSONObject(key), key, artistName);
            super.getItems().add(a);
        });
    }

    public ObservableList<Album> getAlbums() {
        return this.getItems();
    }

}

package io.makerforce.undefined.model;

import javafx.collections.ObservableList;
import org.json.JSONObject;

import java.net.URL;

public class Library extends ItemList<Artist> {

    public Library() {
        super();
    }

    public Library(JSONObject o, URL endPoint) {
        this();
        o.getJSONObject("artists").keySet().forEach((key) -> {
            Artist a = new Artist(o.getJSONObject("artists").getJSONObject(key), key, endPoint);
            super.getItems().add(a);
        });
    }

    public ObservableList<Artist> getArtists() {
        return this.getItems();
    }

}

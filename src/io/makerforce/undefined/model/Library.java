package io.makerforce.undefined.model;

import javafx.collections.ObservableList;
import org.json.JSONObject;

public class Library extends ItemList<Artist> {

    public Library() {
        super();
    }

    public Library(JSONObject o) {
        this();
        o.getJSONObject("artists").keySet().forEach((key) -> {
            Artist a = new Artist(o.getJSONObject("artists").getJSONObject(key), key);
            super.getItems().add(a);
        });
    }

    public ObservableList<Artist> getArtists() {
        return this.getItems();
    }

}

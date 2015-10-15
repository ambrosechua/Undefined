package io.makerforce.undefined.model;

import org.json.JSONObject;

import java.net.URL;

public class Library extends ItemList<Artist> {

    public ItemList<Album> albums = new ItemList<>();
    public ItemList<Track> tracks = new ItemList<>();

    public Library() {
        super();
    }

    public Library(JSONObject o, URL endPoint) {
        this();
        o.getJSONObject("artists").keySet().forEach((key) -> {
            Artist a = new Artist(o.getJSONObject("artists").getJSONObject(key), key, endPoint);
            super.getItems().add(a);
            albums.getItems().addAll(a.getItems());
            tracks.getItems().addAll(a.getTracks().getItems());
        });
    }

    public ItemList<Album> getAlbums() {
        return albums;
    }

    public ItemList<Track> getTracks() {
        return tracks;
    }

}

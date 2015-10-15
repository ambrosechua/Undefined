package io.makerforce.undefined.model;

import io.makerforce.undefined.util.Util;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class Track implements Item {

    private ObjectProperty<URL> file = new SimpleObjectProperty<>();
    private ObjectProperty<URL> picture = new SimpleObjectProperty<>();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty album = new SimpleStringProperty();
    private StringProperty year = new SimpleStringProperty();
    private IntegerProperty trackNumber = new SimpleIntegerProperty(); // track.no OR number
    private IntegerProperty totalTracks = new SimpleIntegerProperty();
    private ObservableList<String> genre = FXCollections.observableArrayList();

    public Track() {

    }

    @Deprecated
    public Track(JSONObject track, URL endPoint) {
        this(track, "", "", endPoint);
    }

    public Track(JSONObject track, String albumName, String artistName, URL endPoint) {
        this();
        title.set(Util.getNotEmpty(
                track.getString("title"),
                (track.has("filetitle") ? track.getString("filetitle") : "")
        ));
        file.set(Util.toURLOrNull(endPoint + track.getString("file")));
        picture.set(Util.toURLOrNull(endPoint + track.getString("picture")));
        artist.set(Util.getNotEmpty(
                (track.getJSONArray("artist").length() > 0 ? track.getJSONArray("artist").getString(0) : ""),
                artistName
        ));
        album.set(Util.getNotEmpty(
                track.getString("album"),
                albumName
        ));
        year.set(track.getString("year"));
        trackNumber.set(track.getInt("number")); // track.getJSONObject("track").getInt("no");
        totalTracks.set(track.getJSONObject("track").getInt("of"));
        JSONArray genres = track.getJSONArray("genre");
        for (int i = 0; i < genres.length(); i++) {
            genre.add(genres.getString(i));
        }
    }

    public ObjectProperty<URL> pictureProperty() {
        return picture;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty subtitleProperty() {
        return artist;
    }

    public ObjectProperty<URL> fileProperty() {
        return file;
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public StringProperty albumProperty() {
        return album;
    }

    public StringProperty yearProperty() {
        return year;
    }

    public IntegerProperty trackNumberProperty() {
        return trackNumber;
    }

    public IntegerProperty totalTracksProperty() {
        return totalTracks;
    }

    public ObservableList<String> getGenre() {
        return genre;
    }

    public String toString() {
        return title.get() + " (" + artist.get() + ", " + album.get() + ")";
    }

}


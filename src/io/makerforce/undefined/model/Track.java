package io.makerforce.undefined.model;

import javafx.scene.image.Image;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class Track implements Item {

    private URL file;
    private Image picture;
    private String title;
    private String artist;
    private String album;
    private String year;
    private int trackNumber; // track.no OR number
    private int totalTracks;
    private String[] genre;

    public Track(JSONObject track) throws MalformedURLException {
        title = track.getString("title");
        file = new URL(track.getString("file"));
        picture = new Image(track.getString("picture"));
        artist = track.getString("artist");
        album = track.getString("album");
        year = track.getString("year");
        trackNumber = track.getInt("number"); // track.getJSONObject("track").getInt("no");
        totalTracks = track.getJSONObject("track").getInt("of");
        genre = (String[]) StreamSupport.stream(Spliterators.spliteratorUnknownSize(track.getJSONArray("genre").iterator(), Spliterator.ORDERED), false).toArray();
    }

    public Track() {

    }

    public Image getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return artist;
    }

    public URL getFile() {
        return file;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public String[] getGenre() {
        return genre;
    }

}


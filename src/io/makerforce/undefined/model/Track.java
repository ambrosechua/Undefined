package io.makerforce.undefined.model;

import io.makerforce.undefined.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class Track implements Item {

    private URL file;
    private URL picture;
    private String title;
    private String artist;
    private String album;
    private String year;
    private int trackNumber; // track.no OR number
    private int totalTracks;
    private String[] genre;

    public Track() {

    }

    @Deprecated
    public Track(JSONObject track, URL endPoint) {
        this(track, "", "", endPoint);
    }

    public Track(JSONObject track, String albumName, String artistName, URL endPoint) {
        this();
        title = Util.getNotEmpty(
                track.getString("title"),
                (track.has("filetitle") ? track.getString("filetitle") : "")
        );
        file = Util.toURLOrNull(endPoint + track.getString("file"));
        picture = Util.toURLOrNull(endPoint + track.getString("picture"));
        artist = Util.getNotEmpty(
                (track.getJSONArray("artist").length() > 0 ? track.getJSONArray("artist").getString(0) : ""),
                artistName
        );
        album = Util.getNotEmpty(
                track.getString("album"),
                albumName
        );
        year = track.getString("year");
        trackNumber = track.getInt("number"); // track.getJSONObject("track").getInt("no");
        totalTracks = track.getJSONObject("track").getInt("of");
        JSONArray genres = track.getJSONArray("genre");
        genre = new String[genres.length()];
        for (int i = 0; i < genres.length(); i++) {
            genre[i] = genres.getString(i);
        }
    }

    public URL getPicture() {
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

    public String toString() {
        return title + " (" + artist + ", " + album + ")";
    }

}


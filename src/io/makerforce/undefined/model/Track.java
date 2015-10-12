package io.makerforce.undefined.model;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class Track {

    private URL file;
    private URL picture;
    private String artist;
    private String album;
    private String year;
    private int trackNumber; // track.no OR number
    private int totalTracks;
    private String[] genre;

    public Track(JSONObject track) throws MalformedURLException {
        file = new URL(track.getString("file"));
        picture = new URL(track.getString("picture"));
        artist = track.getString("artist");
        album = track.getString("album");
        year = track.getString("year");
        trackNumber = track.getInt("number"); // track.getJSONObject("track").getInt("no");
        totalTracks = track.getJSONObject("track").getInt("of");
        genre = (String[]) StreamSupport.stream(Spliterators.spliteratorUnknownSize(track.getJSONArray("genre").iterator(), Spliterator.ORDERED), false).toArray();
    }

}


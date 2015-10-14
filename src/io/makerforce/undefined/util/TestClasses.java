package io.makerforce.undefined.util;

import io.makerforce.undefined.model.Album;
import io.makerforce.undefined.model.Artist;
import io.makerforce.undefined.model.Library;
import io.makerforce.undefined.model.Track;

import java.net.MalformedURLException;

public class TestClasses {

    public static void main(String[] args) throws MalformedURLException {
        Library l = new Library();

        Album a1 = new Album();
        a1.getItems().addAll(new Track(), new Track());
        Album a2 = new Album();
        a2.getItems().addAll(new Track(), new Track());
        Artist r1 = new Artist();
        r1.getItems().addAll(a1, a2);

        Album a3 = new Album();
        a1.getItems().addAll(new Track(), new Track());
        Album a4 = new Album();
        a2.getItems().addAll(new Track(), new Track());
        Artist r2 = new Artist();
        r2.getItems().addAll(a3, a4);

        l.getItems().addAll(r1, r2);
    }

}

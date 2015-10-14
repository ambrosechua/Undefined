package io.makerforce.undefined.util;

import java.net.MalformedURLException;

public class TestClasses {

    public static void main(String[] args) throws MalformedURLException {
        /*
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

        System.out.println(l.toString()); // Should print [[[null (null, null), null (null, null), null (null, null), null (null, null)], [null (null, null), null (null, null), null (null, null), null (null, null)]], [[], []]]
        */

        LibraryManager a = new LibraryManager();
        a.update();
        System.out.println(a.getLibrary());

    }

}

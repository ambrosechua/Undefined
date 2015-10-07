package io.makerforce.undefined.model;

import javafx.util.Duration;

public class Util {

    public static String durationToString(Duration d) {
        int sec = (int) d.toSeconds();
        return String.format((sec < 0 ? "-" : "") + "%d:%02d", (sec < 0 ? -sec : sec) / 60, ((sec < 0 ? -sec : sec) % 60));
    }
}

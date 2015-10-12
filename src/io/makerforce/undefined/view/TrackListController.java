package io.makerforce.undefined.view;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TrackListController {

    public TrackListController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tracklist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}

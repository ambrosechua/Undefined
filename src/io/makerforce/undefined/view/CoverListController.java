package io.makerforce.undefined.view;

import io.makerforce.undefined.model.ItemList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class CoverListController extends FlowPane {

    @FXML
    private FlowPane flowPane;

    private ItemList itemList;

    public CoverListController() {
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

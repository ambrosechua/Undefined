package io.makerforce.undefined.view;

import io.makerforce.undefined.model.ItemList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class CoverListController extends FlowPane {

    private ItemList itemList;

    public CoverListController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("coverlist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}

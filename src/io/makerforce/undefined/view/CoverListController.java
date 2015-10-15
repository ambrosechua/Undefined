package io.makerforce.undefined.view;

import io.makerforce.undefined.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class CoverListController extends FlowPane {

    private ItemList itemList;

    private InterfaceController controller;

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

    public void setItemList(ItemList list) {
        itemList = list;
        list.getItems().forEach(i -> {
            CoverItemController c = new CoverItemController((Item) i);
            this.getChildren().add(c);
            c.setOnMouseClicked((m) -> { // I gave up.
                if (i.getClass() == Artist.class) {
                    controller.showCoverList((ItemList<Item>) i); // Albums
                } else if (i.getClass() == Album.class) {
                    controller.showTrackList((ItemList<Track>) i);
                }
            });
        });/*
        this.getChildren().addAll(
                (Collection<CoverItemController>)
                        list.getItems().stream().map(i -> new CoverItemController((Item) i))
                                .collect(Collectors.toList()));
                                */
    }

    public void setController(InterfaceController c) {
        controller = c;
    }

}

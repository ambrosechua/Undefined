package io.makerforce.undefined.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class ItemList<T> implements Item {

    private Image picture;
    private String title;
    private String subtitle;

    private ObservableList<T> items = FXCollections.observableArrayList();

    public ItemList(Image picture, String title, String subtitle) {
        this.picture = picture;
        this.title = title;
        this.subtitle = subtitle;
    }

    public ItemList(Image picture, String title) {
        this(picture, title, "");
    }

    public ItemList() {
        this(null, "", "");
    }

    public Image getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public String toString() {
        return items.toString();
    }

}

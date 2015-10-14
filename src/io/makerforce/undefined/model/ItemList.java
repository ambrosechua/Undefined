package io.makerforce.undefined.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;

public class ItemList<T> implements Item {

    private URL picture;
    private String title;
    private String subtitle;

    private ObservableList<T> items = FXCollections.observableArrayList();

    public ItemList(URL picture, String title, String subtitle) {
        this.picture = picture;
        this.title = title;
        this.subtitle = subtitle;
    }

    public ItemList(URL picture, String title) {
        this(picture, title, "");
    }

    public ItemList() {
        this(null, "", "");
    }

    public URL getPicture() {
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

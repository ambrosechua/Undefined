package io.makerforce.undefined.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;

public class ItemList<T> implements Item {

    private ObjectProperty<URL> picture = new SimpleObjectProperty<>();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty subtitle = new SimpleStringProperty();

    private ObservableList<T> items = FXCollections.observableArrayList();

    public ItemList(URL picture, String title, String subtitle) {
        this.picture.set(picture);
        this.title.set(title);
        this.subtitle.set(subtitle);
    }

    public ItemList(URL picture, String title) {
        this(picture, title, "");
    }

    public ItemList() {
        this(null, "", "");
    }

    public ObjectProperty<URL> pictureProperty() {
        return picture;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty subtitleProperty() {
        return subtitle;
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public String toString() {
        return items.toString();
    }

}

package io.makerforce.undefined.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.net.URL;

public interface Item {

    ObjectProperty<URL> pictureProperty();

    StringProperty titleProperty();

    StringProperty subtitleProperty();

}

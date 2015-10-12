package io.makerforce.undefined.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CoverItemController extends AnchorPane {

    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private Parent subtitleContainer;
    @FXML
    private Label secondaryLabel;
    @FXML
    private ImageView imageView;

    private StringProperty title = new SimpleStringProperty("");
    private StringProperty subtitle = new SimpleStringProperty("");
    private StringProperty secondary = new SimpleStringProperty("");

    private StringProperty image = new SimpleStringProperty("");

    public CoverItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("coveritem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initializeBindings();
    }

    public CoverItemController(String image, String title, String subtitle, String secondary) {
        this();
        this.image.set(image);
        this.title.set(title);
        this.subtitle.set(subtitle);
        this.secondary.set(secondary);
    }

    public CoverItemController(String image, String title, String subtitle) {
        this(image, title, subtitle, "");
    }

    public CoverItemController(String image, String title) {
        this(image, title, "", "");
    }

    public void initializeBindings() {
        titleLabel.textProperty().bind(title);
        subtitleLabel.textProperty().bind(subtitle);
        secondaryLabel.textProperty().bind(secondary);
        image.addListener((observable) -> {
            String url = ((StringProperty) observable).getValue();
            if (url.equals("")) {
                imageView.setImage(null);
            } else {
                imageView.setImage(new Image(url));
            }
        });
        subtitleContainer.managedProperty().bind(subtitle.isNotEqualTo(""));
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty subtitleProperty() {
        return subtitle;
    }

    public StringProperty secondaryProperty() {
        return subtitle;
    }

    public StringProperty imageProperty() {
        return image;
    }

}

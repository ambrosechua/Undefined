package io.makerforce.undefined.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TrackListController extends VBox {

    @FXML
    private TableView tableView;
    @FXML
    private Parent coverContainer;
    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private ImageView imageView;

    private StringProperty title = new SimpleStringProperty("");
    private StringProperty subtitle = new SimpleStringProperty("");

    private StringProperty image = new SimpleStringProperty("");

    public TrackListController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tracklist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initializeBindings();
    }

    public TrackListController(String image, String title, String subtitle) {
        this();
        this.image.set(image);
        this.title.set(title);
        this.subtitle.set(subtitle);
    }

    public TrackListController(String image, String title) {
        this(image, title, "");
    }

    public void initializeBindings() {
        titleLabel.textProperty().bind(title);
        subtitleLabel.textProperty().bind(subtitle);
        image.addListener((observable) -> {
            String url = ((StringProperty) observable).getValue();
            if (url.equals("")) {
                imageView.setImage(null);
            } else {
                imageView.setImage(new Image(url));
            }
        });
        coverContainer.managedProperty().bind(title.isNotEqualTo("").and(subtitle.isNotEqualTo("")));
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty subtitleProperty() {
        return subtitle;
    }

    public StringProperty imageProperty() {
        return image;
    }

}
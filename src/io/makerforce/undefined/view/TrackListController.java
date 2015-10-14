package io.makerforce.undefined.view;

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

    public TrackListController(Image image, String title, String subtitle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tracklist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        imageView.setImage(image);
        titleLabel.setText(title);
        subtitleLabel.setText(subtitle);
        coverContainer.setManaged(!(subtitle.isEmpty() && title.isEmpty()));
    }

    public TrackListController(Image image, String title) {
        this(image, title, "");
    }

    public TrackListController() {
        this(null, "", "");
    }

}
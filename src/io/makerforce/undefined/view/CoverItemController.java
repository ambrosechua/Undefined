package io.makerforce.undefined.view;

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

    public CoverItemController(Image picture, String title, String subtitle, String secondary) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("coveritem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        imageView.setImage(picture);
        titleLabel.setText(title);
        subtitleLabel.setText(subtitle);
        secondaryLabel.setText(secondary);
        subtitleContainer.setManaged(!subtitle.isEmpty());
    }

    public CoverItemController(Image picture, String title, String subtitle) {
        this(picture, title, subtitle, "");
    }

    public CoverItemController(Image picture, String title) {
        this(picture, title, "", "");
    }

    public CoverItemController() {
        this(null, "", "", "");
    }

}

package io.makerforce.undefined.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class InterfaceController {


    @FXML
    private VBox leftPane;

    @FXML
    private VBox currentDetails;
    @FXML
    private ImageView currentImage;
    @FXML
    private Label currentTitle;
    @FXML
    private Label currentArtist;

    @FXML
    private ListView showList;
    private ObservableList<String> showListItems = FXCollections.observableArrayList("Albums", "Artists", "Songs");

    @FXML
    private ScrollPane scrollPane;

    private Image playIcon = new Image("/icons/play3.48.png");
    private Image pauseIcon = new Image("/icons/pause2.48.png");

    @FXML
    private Button pausePlayButton;
    @FXML
    private ImageView pausePlayButtonIcon;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private Slider playbackSlider;
    @FXML
    private Label playbackTimeLabel;
    @FXML
    private Label playbackLeftLabel;

    @FXML
    private ToggleButton muteToggle;
    @FXML
    private ImageView muteToggleIcon;
    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView statusIcon;


    public InterfaceController() {


    }

    public void initialize() {

        // UI Bindings

        currentImage.fitWidthProperty().bind(leftPane.widthProperty());
        currentImage.fitHeightProperty().bind(currentImage.fitWidthProperty());

        // Raw bindings

        showList.setItems(showListItems);

    }

}

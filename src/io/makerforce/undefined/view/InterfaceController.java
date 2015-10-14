package io.makerforce.undefined.view;

import io.makerforce.undefined.model.Track;
import io.makerforce.undefined.util.LibraryManager;
import io.makerforce.undefined.util.PlayManager;
import io.makerforce.undefined.util.Util;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private ListView<String> showList;
    private ObservableList<String> showListItems = FXCollections.observableArrayList("Albums", "Artists", "Songs");

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane flowPane;
    //@FXML
    private TrackListController trackList;
    //@FXML
    private CoverListController coverList;

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

    //private MediaPlayer player;
    private PlayManager player = new PlayManager();


    public InterfaceController() {

    }

    public void initialize() {

        trackList = new TrackListController();
        coverList = new CoverListController();

        // Temporary stuff
        /*
        currentImage.setImage(new Image("http://ambrose.makerforce.io:8080/art/Alan%20Walker/Fade/1"));
        currentArtist.setText("Alan Walker");
        currentTitle.setText("Fade");

        trackList = new TrackListController(new Image("http://ambrose.makerforce.io:8080/art/Alan%20Walker/Spectre/1"), "Spectre", "Alan Walker");
        coverList = new CoverListController();
        flowPane.getChildren().add(new CoverItemController(new Image("http://ambrose.makerforce.io:8080/art/Alan%20Walker/Spectre/1"), "Spectre", "Alan Walker"));
        flowPane.getChildren().add(new CoverItemController(new Image("http://ambrose.makerforce.io:8080/art/Alan%20Walker/Fade/1"), "Fade", "Alan Walker"));
        flowPane.getChildren().add(new CoverItemController(new Image("http://ambrose.makerforce.io:8080/art/Alan%20Walker/Fade/1"), "Alan Walker"));
        flowPane.getChildren().add(new CoverItemController(new Image("http://ambrose.makerforce.io:8080/art/Alan%20Walker/Fade/1"), "Alan Walker"));
        */
        try {
            player.addToQueue(new Track(new JSONObject(""), "", "", new URL(LibraryManager.DEFAULT_ENDPOINT)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // UI Bindings

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            Platform.runLater(() -> {
                currentImage.fitWidthProperty().bind(leftPane.widthProperty());
                currentImage.fitHeightProperty().bind(currentImage.fitWidthProperty());
            });
            executor.shutdown();
        }, 100, TimeUnit.MILLISECONDS);

        // Raw bindings

        showList.setItems(showListItems);
        showList.selectionModelProperty().get().clearAndSelect(0);
        showList.selectionModelProperty().get().selectedIndexProperty().addListener(observable2 -> {
            int sel = showList.selectionModelProperty().get().getSelectedIndex();
            if (sel == 0) {
                scrollPane.setContent(flowPane);
                // artists
            } else if (sel == 1) {
                scrollPane.setContent(flowPane);
                // albums
            } else if (sel == 2) {
                scrollPane.setContent(trackList);
            }
        });

        player.muteProperty().bind(muteToggle.selectedProperty());
        player.volumeProperty().bind(volumeSlider.valueProperty());

        // Player events

        // Playback status
        player.stateProperty().addListener((observable, old, state) -> {
            if (state == PlayManager.PLAYING) {
                pausePlayButtonIcon.setImage(pauseIcon);
            } else {
                pausePlayButtonIcon.setImage(playIcon);
            }
        });

        // Update slider position when music plays.
        playbackSlider.valueProperty().bind(player.currentPercent());

        // Update labels.
        playbackSlider.valueProperty().addListener((observable1, oldValue1, v) -> {
            Duration d = new Duration(v.doubleValue() * player.totalTimeProperty().get().toMillis());
            playbackTimeLabel.setText(Util.durationToString(d));
            playbackLeftLabel.setText(Util.durationToString(d.subtract(player.totalTimeProperty().get())));
        });

        // Seek when slider is released.
        playbackSlider.valueChangingProperty().addListener((observable, oldValue, c) -> {
            if (c) {
                playbackSlider.valueProperty().unbind();
            } else {
                playbackSlider.valueProperty().bind(player.currentPercent());
                //player.seek(new Duration(playbackSlider.getValue() * player.totalTimeProperty().get().toMillis()));
            }
        });

    }

    @FXML
    private void pausePlay() {

        if (player.stateProperty().get() == PlayManager.PLAYING) {
            player.pause();
        } else {
            player.play();
        }

    }

    @FXML
    private void nextTrack() {


    }

    @FXML
    private void previousTrack() {


    }

}

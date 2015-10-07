package io.makerforce.undefined.view;

import io.makerforce.undefined.model.Util;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

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

    private MediaPlayer player;

    private BooleanProperty isPlaying = new SimpleBooleanProperty(false);


    public InterfaceController() {

        player = new MediaPlayer(new Media("http://ambrose.makerforce.io/audio.mp3"));

    }

    public void initialize() {

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
        ((SelectionModel) showList.selectionModelProperty().get()).clearAndSelect(0);

        player.muteProperty().bind(muteToggle.selectedProperty());
        player.volumeProperty().bind(volumeSlider.valueProperty());

        // Player events

        // Playback status
        player.statusProperty().addListener((observable, oldValue, s) -> {
            if (
                    s == MediaPlayer.Status.PAUSED ||
                            s == MediaPlayer.Status.READY ||
                            s == MediaPlayer.Status.STOPPED ||
                            s == MediaPlayer.Status.UNKNOWN ||
                            s == MediaPlayer.Status.HALTED
                    ) {
                pausePlayButtonIcon.setImage(playIcon);
                isPlaying.setValue(false);
            } else {
                pausePlayButtonIcon.setImage(pauseIcon);
                isPlaying.setValue(true);
            }
        });

        // Update slider position when music plays.
        ChangeListener<Duration> updateSlider = (observable, oldValue, c) -> {
            playbackSlider.setValue(c.toMillis() / player.getTotalDuration().toMillis());
            playbackTimeLabel.setText(Util.durationToString(player.getCurrentTime()));
        };
        player.currentTimeProperty().addListener(updateSlider);

        // Update labels.
        playbackSlider.valueProperty().addListener((observable1, oldValue1, v) -> {
            Duration d = new Duration(v.doubleValue() * player.getTotalDuration().toMillis());
            playbackTimeLabel.setText(Util.durationToString(d));
            playbackLeftLabel.setText(Util.durationToString(d.subtract(player.getTotalDuration())));
        });

        // Seek when slider is released.
        playbackSlider.valueChangingProperty().addListener((observable, oldValue, c) -> {
            if (c) {
                player.currentTimeProperty().removeListener(updateSlider);
            } else {
                player.currentTimeProperty().addListener(updateSlider);
                player.seek(new Duration(playbackSlider.getValue() * player.getTotalDuration().toMillis()));
            }
        });

    }

    @FXML
    private void pausePlay() {

        if (isPlaying.get()) {
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

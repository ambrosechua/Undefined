package io.makerforce.undefined.view;

import io.makerforce.undefined.model.Item;
import io.makerforce.undefined.model.ItemList;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterfaceController {

    //private MediaPlayer player;
    private static PlayManager player = new PlayManager();
    private static LibraryManager libraryManager = new LibraryManager();
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
    private ObservableList<String> showListItems = FXCollections.observableArrayList("Artists", "Albums", "Songs");
    @FXML
    private ScrollPane scrollPane;
    //@FXML
    private TrackListController trackList;
    //@FXML
    private CoverListController albumList;
    //@FXML
    private CoverListController artistList;
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
    private Image cloudUpdatingIcon = new Image("/icons/cloud-download.32.png");
    private Image cloudReadyIcon = new Image("/icons/cloud-check.32.png");
    private Image cloudErrorIcon = new Image("/icons/warning.32.png");
    @FXML
    private ImageView statusIcon;

    public InterfaceController() {

    }

    public static PlayManager getPlayManager() {
        return player;
    }

    public static LibraryManager getLibraryManager() {
        return libraryManager;
    }

    public void initialize() {

        trackList = new TrackListController();
        albumList = new CoverListController();
        artistList = new CoverListController();

        albumList.setController(this);
        artistList.setController(this);

        scrollPane.setContent(artistList);

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
                scrollPane.setContent(artistList);
                // artists
            } else if (sel == 1) {
                scrollPane.setContent(albumList);
                // albums
            } else if (sel == 2) {
                scrollPane.setContent(trackList);
            }
        });

        // Library bindings

        libraryManager.stateProperty().addListener(observable -> {
            if (libraryManager.stateProperty().get() == LibraryManager.UPDATING) {
                statusIcon.setImage(cloudUpdatingIcon);
            } else if (libraryManager.stateProperty().get() == LibraryManager.READY) { // HMM tells me that not on FX thread...
                Platform.runLater(() -> {
                    trackList.setItemList(libraryManager.getLibrary().getTracks());
                    albumList.setItemList(libraryManager.getLibrary().getAlbums());
                    artistList.setItemList(libraryManager.getLibrary());
                    statusIcon.setImage(cloudReadyIcon);
                });
                clearStatusIcon();
            } else if (libraryManager.stateProperty().get() == LibraryManager.ERROR) {
                statusIcon.setImage(cloudErrorIcon);
                //clearStatusIcon();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        libraryManager.update();
                        t.cancel(); // Here's the solution to the problem of unable to shut down.
                    }
                }, 1000); // Quick hack to trigger more frequent updates.
            }
        });
        libraryManager.schedule();

        // Player bindings

        // To player
        player.muteProperty().bind(muteToggle.selectedProperty());
        player.volumeProperty().bind(volumeSlider.valueProperty());

        // From player
        currentImage.imageProperty().bind(Util.getImageFromURLProperty(player.currentPictureProperty()));
        currentTitle.textProperty().bind(player.currentTitleProperty());
        currentArtist.textProperty().bind(player.currentArtistProperty());

        // Playback status (From player)
        player.stateProperty().addListener((observable, old, state) -> {
            if (state == PlayManager.PLAYING) {
                pausePlayButtonIcon.setImage(pauseIcon);
            } else {
                pausePlayButtonIcon.setImage(playIcon);
            }
        });

        // Update slider position when music plays. (From player)
        playbackSlider.valueProperty().bind(player.currentPercent());

        // Update labels. (From slider)
        playbackSlider.valueProperty().addListener((observable, old, v) -> {
            Duration d = new Duration(v.doubleValue() * player.totalTimeProperty().get().toMillis());
            playbackTimeLabel.setText(Util.durationToString(d));
            playbackLeftLabel.setText(Util.durationToString(d.subtract(player.totalTimeProperty().get())));
        });

        // Seek when slider is released. (To player)
        playbackSlider.valueChangingProperty().addListener((observable, old, c) -> {
            if (c) {
                playbackSlider.valueProperty().unbind();
            } else {
                player.seek(new Duration(playbackSlider.getValue() * player.totalTimeProperty().get().toMillis()));
                playbackSlider.valueProperty().bind(player.currentPercent());
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

        player.next();

    }

    @FXML
    private void previousTrack() {

        player.previous();

    }

    public void showCoverList(ItemList<Item> i) {
        CoverListController c = new CoverListController();
        c.setController(this);
        c.setItemList(i);
        scrollPane.setContent(c);
    }

    public void showTrackList(ItemList<Track> i) {
        TrackListController t = new TrackListController(new Image(String.valueOf(i.pictureProperty().get())), i.titleProperty().get(), i.subtitleProperty().get());
        t.setItemList(i);
        scrollPane.setContent(t);
    }

    private void clearStatusIcon(int milliseconds) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                statusIcon.setImage(null);
                t.cancel(); // Here's the solution to the problem of unable to shut down.
            }
        }, milliseconds);
    }

    private void clearStatusIcon() {
        clearStatusIcon(4000);
    }

}

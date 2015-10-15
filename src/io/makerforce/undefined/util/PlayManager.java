package io.makerforce.undefined.util;

import io.makerforce.undefined.model.Track;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;

public class PlayManager {

    public static final PlayManagerState STOPPED = new PlayManagerState("stopped");
    public static final PlayManagerState PAUSED = new PlayManagerState("paused");
    public static final PlayManagerState PLAYING = new PlayManagerState("playing");
    public static final PlayManagerState ENDED = new PlayManagerState("ended");

    private SimpleObjectProperty<PlayManagerState> state = new SimpleObjectProperty<>(STOPPED);

    private ArrayList<Track> queue = new ArrayList<>();

    private IntegerProperty currentTrack = new SimpleIntegerProperty(-1);

    private MediaPlayer player;

    private BooleanProperty muted = new SimpleBooleanProperty();
    private DoubleProperty volume = new SimpleDoubleProperty();
    private DoubleProperty currentPercent = new SimpleDoubleProperty();
    private ObjectProperty<Duration> timeLeft = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> currentTime = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> totalTime = new SimpleObjectProperty<>(new Duration(0));

    private ObjectProperty<URL> currentPicture = new SimpleObjectProperty<>();
    private StringProperty currentTitle = new SimpleStringProperty();
    private StringProperty currentArtist = new SimpleStringProperty();

    private ChangeListener<Duration> currentTimeChange = new ChangeListener<Duration>() {
        @Override
        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration c) {
            currentPercent.set(c.toMillis() / player.getTotalDuration().toMillis());
            currentTime.set(c);
            timeLeft.set(c.subtract(currentTime.get()));
        }
    };

    public PlayManager() {

    }

    private void load(Track track) {
        player = new MediaPlayer(new Media(track.fileProperty().get().toString()));
        // To player
        player.muteProperty().bind(muted);
        player.volumeProperty().bind(volume);
        // From player
        player.currentTimeProperty().addListener(currentTimeChange);
        totalTime.bind(player.totalDurationProperty());
        currentPicture.bind(track.pictureProperty());
        currentTitle.bind(track.titleProperty());
        currentArtist.bind(track.artistProperty());
    }

    public void play() {
        if (currentTrack.get() == -1) {
            next();
        } else if (player == null) {

        } else {
            if (state.get() == PAUSED || state.get() == STOPPED) {
                player.play();
                state.set(PLAYING);
                state.get();
            } else if (state.get() == ENDED) {
                next();
            }
        }
    }

    public void pause() {
        if (player == null) {

        } else {
            if (state.get() == PLAYING) {
                player.pause();
                state.set(PAUSED);
            }
        }
    }

    public void stop() {
        if (player == null) {

        } else {
            player.stop();
            state.set(PAUSED);
        }
    }

    public void previous() {
        if (player == null) {

        } else {
            if (currentTime.get().toSeconds() < 2) {
                stop();
                if (currentTrack.get() - 1 > 0) {
                    currentTrack.set(currentTrack.get() - 1);
                    load(queue.get(currentTrack.get()));
                    play();
                }
            } else {
                player.seek(new Duration(0));
            }
        }
    }

    public void next() {
        if (player != null) {
            stop();
        }
        if (currentTrack.get() + 1 < queue.size()) {
            currentTrack.set(currentTrack.get() + 1);
            load(queue.get(currentTrack.get()));
            play();
        }
    }

    public void seek(Duration t) {
        player.seek(t);
    }

    public void setIndex(int selectedIndex) {
        currentTrack.set(selectedIndex - 1);
        next();
    }

    public void addToQueue(Track track) {
        queue.add(track);
    }

    public void addAllToQueue(ObservableList<Track> tracks) {
        queue.addAll(tracks);
    }

    public void clearQueue() {
        if (player != null) {
            stop();
        }
        queue.clear();
        currentTrack.set(-1);
    }

    // Was for creating a new PlayManager, but decided to just reset the queue
    /*
    public void destroy() {
        queue.clear();
        player.stop();
        player.dispose();
    }
    */

    public BooleanProperty muteProperty() {
        return muted;
    }

    public DoubleProperty volumeProperty() {
        return volume;
    }

    public ObjectProperty<PlayManagerState> stateProperty() {
        return state;
    }

    public DoubleProperty currentPercent() {
        return currentPercent;
    }

    public ObjectProperty<Duration> currentTimeProperty() {
        return currentTime;
    }

    public ObjectProperty<Duration> timeLeftProperty() {
        return timeLeft;
    }

    public ObjectProperty<Duration> totalTimeProperty() {
        return totalTime;
    }

    public ObjectProperty<URL> currentPictureProperty() {
        return currentPicture;
    }

    public StringProperty currentTitleProperty() {
        return currentTitle;
    }

    public StringProperty currentArtistProperty() {
        return currentArtist;
    }

    public static final class PlayManagerState {

        private final String text;

        public PlayManagerState(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }

    }

}

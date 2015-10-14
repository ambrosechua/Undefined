package io.makerforce.undefined.util;

import io.makerforce.undefined.model.Track;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlayManager {

    public static final PlayManagerState STOPPED = new PlayManagerState("stopped");
    public static final PlayManagerState PAUSED = new PlayManagerState("paused");
    public static final PlayManagerState PLAYING = new PlayManagerState("playing");
    public static final PlayManagerState ENDED = new PlayManagerState("ended");

    private SimpleObjectProperty<PlayManagerState> state = new SimpleObjectProperty<>(STOPPED);

    private ArrayList<Track> queue = new ArrayList<>();

    private IntegerProperty currentTrack = new SimpleIntegerProperty(0);

    private MediaPlayer player;

    private BooleanProperty muted = new SimpleBooleanProperty();
    private DoubleProperty volume = new SimpleDoubleProperty();
    private DoubleProperty currentPercent = new SimpleDoubleProperty();
    private ObjectProperty<Duration> timeLeft = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> currentTime = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> totalTime = new SimpleObjectProperty<>();
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
        player = new MediaPlayer(new Media(track.getFile().toString()));
        player.muteProperty().bind(muted);
        player.volumeProperty().bind(volume);
        player.currentTimeProperty().addListener(currentTimeChange);
        totalTime.set(player.totalDurationProperty().get());
    }

    public void play() {
        if (state.equals(PAUSED) || state.equals(STOPPED)) {
            player.play();
            state.set(PLAYING);
        } else if (state.equals(ENDED)) {
            next();
        }
    }

    public void pause() {
        if (state.equals(PLAYING)) {
            player.pause();
            state.set(PAUSED);
        }
    }

    public void previous() {
        if (currentTime.get().toSeconds() < 1) {
            player.stop();
            if (currentTrack.get() - 1 > 0) {
                currentTrack.subtract(1);
                load(queue.get(currentTrack.get()));
            }
        } else {
            player.seek(new Duration(0));
        }
    }

    public void next() {
        player.stop();
        if (currentTrack.get() + 1 < queue.size()) {
            currentTrack.add(1);
            load(queue.get(currentTrack.get()));
        }
    }

    public void addToQueue(Track track) {
        queue.add(track);
    }

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

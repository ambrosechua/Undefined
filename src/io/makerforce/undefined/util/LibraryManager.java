package io.makerforce.undefined.util;

import io.makerforce.undefined.model.Library;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LibraryManager {

    public static final LibraryManagerState EMPTY = new LibraryManagerState("empty");
    public static final LibraryManagerState UPDATING = new LibraryManagerState("updating");
    public static final LibraryManagerState ERROR = new LibraryManagerState("error");
    public static final LibraryManagerState READY = new LibraryManagerState("ready");

    public static final String DEFAULT_ENDPOINT = "http://undefined.ambrose.makerforce.io";
    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    private SimpleObjectProperty<LibraryManagerState> state = new SimpleObjectProperty<>();
    private URL endPoint;
    private Library l;
    private ScheduledFuture<?> future;
    //private static ArrayList<ScheduledFuture> futures = new ArrayList<>();

    public LibraryManager() {
        state.set(EMPTY);
        try {
            this.endPoint = new URL(DEFAULT_ENDPOINT);
        } catch (MalformedURLException e) {
            e.printStackTrace(); // THIS SHOULD NEVER HAPPEN
        }
    }

    public LibraryManager(URL endPoint) {
        state.set(EMPTY);
        this.endPoint = endPoint;
        if (!this.endPoint.toString().endsWith("/")) {
            try {
                this.endPoint = new URL(endPoint.toString() + "/");
            } catch (MalformedURLException e) {
                e.printStackTrace(); // ALSO SHOULD NEVER HAPPEN
            }
        }
    }

    public static void unScheduleAll() {
        //futures.forEach((future) -> {
        //    future.cancel(true);
        //});
        scheduledExecutorService.shutdownNow();
    }

    public Library getLibrary() {
        return l;
    }

    public URL getEndPoint() {
        return endPoint;
    }

    public void update() {
        try {
            state.set(UPDATING);

            HttpURLConnection con = (HttpURLConnection) endPoint.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("X-Undefined-Client", "JavaFX/0.0.2");

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            l = new Library(obj, endPoint);

            state.set(READY);
        } catch (IOException e) {
            e.printStackTrace();
            state.set(ERROR);
        }
    }

    public void schedule() {
        //this.update();
        if (future == null) {
            future = scheduledExecutorService.scheduleWithFixedDelay(() -> {
                //this.update(); // DISABLED BECAUSE: Will stop playback if it's playing due to bad implementation. And anyway the server-side stuff won't change.
                state.set(UPDATING);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                state.set(READY);
            }, 0, 60, TimeUnit.SECONDS); // Poke every minute (although nothing will change on the remote server)
            //futures.add(future);
        } else {
            throw new Error("Updates are already scheduled");
        }
    }

    public void scheduleRate() {
        future.getDelay(TimeUnit.MILLISECONDS);
    }

    public void unSchedule() {
        future.cancel(true);
        //futures.remove(future);
    }

    public ObjectProperty<LibraryManagerState> stateProperty() {
        return state;
    }

    public static final class LibraryManagerState {

        private final String text;

        public LibraryManagerState(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }

    }

}

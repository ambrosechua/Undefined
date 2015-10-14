package io.makerforce.undefined.util;

import io.makerforce.undefined.model.Library;
import javafx.beans.property.SimpleObjectProperty;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LibraryManager extends SimpleObjectProperty<LibraryManager.LibraryManagerState> {

    public static final LibraryManagerState EMPTY = new LibraryManagerState("empty");
    public static final LibraryManagerState UPDATING = new LibraryManagerState("updating");
    public static final LibraryManagerState ERROR = new LibraryManagerState("error");
    public static final LibraryManagerState READY = new LibraryManagerState("ready");

    private URL endPoint;

    private Library l;

    private ScheduledExecutorService scheduledExecutorService = null;
    private boolean hasScheduled = false;

    public LibraryManager() {
        this.set(EMPTY);
        try {
            this.endPoint = new URL("http://ambrose.makerforce.io:8080/");
        } catch (MalformedURLException e) {
            e.printStackTrace(); // THIS SHOULD NEVER HAPPEN
        }
    }

    public LibraryManager(URL endPoint) {
        this.set(EMPTY);
        this.endPoint = endPoint;
        if (!endPoint.toString().endsWith("/")) {
            try {
                endPoint = new URL(endPoint.toString() + "/");
            } catch (MalformedURLException e) {
                e.printStackTrace(); // ALSO SHOULD NEVER HAPPEN
            }
        }
    }

    public Library getLibrary() {
        return l;
    }

    public URL getEndPoint() {
        return endPoint;
    }

    public void update() {
        new Thread(() -> {
            try {
                this.set(UPDATING);

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

                this.set(READY);
            } catch (IOException e) {
                e.printStackTrace();
                this.set(ERROR);
            }
        }).run();
    }

    public void schedule() {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                this.update();
            }, 0, 60, TimeUnit.SECONDS);
        } else {
            throw new Error("Updates are already scheduled");
        }
    }

    public void unSchedule() {
        scheduledExecutorService.shutdownNow();
        scheduledExecutorService = null;
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

package io.makerforce.undefined;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent splashRoot = FXMLLoader.load(getClass().getResource("view/splash.fxml"));
        Scene splashScene = new Scene(splashRoot, 300, 200);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(splashScene);
        primaryStage.show();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("view/interface.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 640, 480));
            primaryStage.show();
            primaryStage.toFront();
            executor.shutdown();
        }, 2, TimeUnit.SECONDS);
    }

}

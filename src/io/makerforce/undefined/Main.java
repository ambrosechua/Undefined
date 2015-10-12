package io.makerforce.undefined;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage initStage) throws Exception {

        Parent splashRoot = FXMLLoader.load(getClass().getResource("view/splash.fxml"));
        Scene splashScene = new Scene(splashRoot, 300, 200);
        initStage.initStyle(StageStyle.UNDECORATED);
        initStage.setScene(splashScene);
        initStage.toFront();
        initStage.show();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> Platform.runLater(() -> {
            initStage.close();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("view/interface.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(284);
            }
            mainStage = new Stage(StageStyle.DECORATED);
            mainStage.setTitle("Undefined");
            mainStage.setScene(new Scene(root, 640, 480));
            mainStage.setMinHeight(((BorderPane) root).getMinHeight());
            mainStage.setMinWidth(((BorderPane) root).getMinWidth());
            mainStage.show();
            executor.shutdown();
        }), 1500, TimeUnit.MILLISECONDS);
    }

}

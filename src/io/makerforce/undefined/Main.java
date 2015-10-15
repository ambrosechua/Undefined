package io.makerforce.undefined;

import io.makerforce.undefined.util.LibraryManager;
import io.makerforce.undefined.view.InterfaceController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    ChangeListener<LibraryManager.LibraryManagerState> ch = null; // Because it doesn't work in there
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
        initStage.setAlwaysOnTop(true);
        initStage.show();

        ch = (state, oldValue, newValue) -> {
            InterfaceController.getLibraryManager().stateProperty().removeListener(ch);
            //ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            //executor.schedule(() -> Platform.runLater(() -> {
            Platform.runLater(() -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Main.this.getClass().getResource("view/interface.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(284);
                }
                mainStage = new Stage(StageStyle.DECORATED);
                mainStage.setTitle("Undefined");
                mainStage.setScene(new Scene(root, 640, 480));
                mainStage.setMinHeight(((BorderPane) root).getMinHeight());
                mainStage.setMinWidth(((BorderPane) root).getMinWidth());
                mainStage.setOnCloseRequest((event) -> {
                    LibraryManager.unScheduleAll();
                });
                mainStage.show();
                //executor.shutdown();
                InterfaceController.getLibraryManager().stateProperty().set(LibraryManager.UPDATING);
                InterfaceController.getLibraryManager().stateProperty().set(LibraryManager.READY);

                Thread initStageClose = new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        initStage.close();
                    });
                });
                initStageClose.start();
            });
            //}), 1500, TimeUnit.MILLISECONDS);
        };
        InterfaceController.getLibraryManager().stateProperty().addListener(ch);

        Thread init = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InterfaceController.getLibraryManager().update(); // IT DOES REAL WORK MIND YOU. SO COOL. SADLY, SCHEDULING UPDATES DONT WORK YET
        });
        init.start();

    }

}

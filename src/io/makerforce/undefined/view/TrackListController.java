package io.makerforce.undefined.view;

import io.makerforce.undefined.model.ItemList;
import io.makerforce.undefined.model.Track;
import javafx.beans.value.ObservableIntegerValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TrackListController extends VBox {

    @FXML
    private TableView tableView;
    @FXML
    private Parent coverContainer;
    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private ImageView imageView;

    @FXML
    private TableColumn<Track, Integer> trackNumberTableColumn;
    @FXML
    private TableColumn<Track, String> trackTitleTableColumn;
    @FXML
    private TableColumn<Track, String> trackArtistTableColumn;
    @FXML
    private TableColumn<Track, String> trackAlbumTableColumn;
    @FXML
    private TableColumn<Track, String> trackLengthTableColumn;

    private ItemList itemList;

    public TrackListController(Image image, String title, String subtitle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tracklist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        imageView.setImage(image);
        titleLabel.setText(title);
        subtitleLabel.setText(subtitle);
        coverContainer.setManaged(!(subtitle.isEmpty() && title.isEmpty()));
        trackNumberTableColumn.setCellValueFactory(track -> track.getValue().trackNumberProperty().asObject());
        trackTitleTableColumn.setCellValueFactory(track -> track.getValue().titleProperty());
        trackArtistTableColumn.setCellValueFactory(track -> track.getValue().artistProperty());
        trackAlbumTableColumn.setCellValueFactory(track -> track.getValue().albumProperty());
        tableView.getSelectionModel().selectedIndexProperty().addListener((selectedIndex) -> {
            InterfaceController.getPlayManager().clearQueue();
            InterfaceController.getPlayManager().addAllToQueue(itemList.getItems());
            InterfaceController.getPlayManager().setIndex(((ObservableIntegerValue) selectedIndex).get());
            //InterfaceController.getPlayManager().play();
        });
    }

    public TrackListController(Image image, String title) {
        this(image, title, "");
    }

    public TrackListController() {
        this(null, "", "");
    }

    public void setItemList(ItemList list) {
        itemList = list;
        tableView.setItems(list.getItems());
    }

}
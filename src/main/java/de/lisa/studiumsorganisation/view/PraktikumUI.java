package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Praktikum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PraktikumUI implements Initializable {


    private static Modul modul;

    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableColumn<Praktikum, Integer> numberColumn;
    @FXML
    private CheckBox praktikumBestandenBox;

    @FXML
    private TableColumn<Praktikum, String> datumColumn;
    @FXML
    private Label praktikumNameText;
    @FXML
    private Button saveButton;
    @FXML
    private TableColumn<Praktikum, Boolean> terminBestandenColumn;
    @FXML
    private TableColumn<Praktikum, String> uhrzeitColumn;
    @FXML
    private TableView<Praktikum> tableview;

    public static Modul getModul() {
        return modul;
    }

    public static void setModul(Modul modul) {
        PraktikumUI.modul = modul;
    }

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        MainUI.getMainUI().start(stage);
    }

    @FXML
    void onPraktikumBestanden(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert praktikumBestandenBox != null : "fx:id=\"praktikumBestandenBox\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert praktikumNameText != null : "fx:id=\"praktikumNameText\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert terminBestandenColumn != null : "fx:id=\"terminBestandenColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert uhrzeitColumn != null : "fx:id=\"uhrzeitColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";

    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PraktikumUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    private void updateTable() {
        tableview.getItems().clear();
        tableview.getItems().addAll();
        //update the tableview checkboxes for the praktika and the prüfung


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
  
}

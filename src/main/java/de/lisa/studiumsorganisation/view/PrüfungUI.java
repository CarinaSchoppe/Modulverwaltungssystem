package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Prüfung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrüfungUI implements Initializable {

    private static Modul modul;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<Prüfung, String> datumColumn;
    @FXML
    private TableColumn<Prüfung, Float> noteColumn;
    @FXML
    private TableColumn<Prüfung, Integer> numberColumn;
    @FXML
    private TableColumn<Prüfung, Button> pruefungBestandenColumn;
    @FXML
    private Label pruefungNameText;
    @FXML
    private Label pruefungsFormText;
    @FXML
    private Button saveButton;
    @FXML
    private TableView<Prüfung> tableview;
    @FXML
    private TableColumn<Prüfung, String> uhrzeitColumn;

    public static Modul getModul() {
        return modul;
    }

    public static void setModul(Modul modul) {
        PrüfungUI.modul = modul;
    }

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        //get current stage from event
        var stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        MainUI.getMainUI().start(stage);
    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert noteColumn != null : "fx:id=\"noteColumn\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert pruefungNameText != null : "fx:id=\"pruefungNameText\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert pruefungsFormText != null : "fx:id=\"pruefungsFormText\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PruefungUI.fxml'.";
        assert uhrzeitColumn != null : "fx:id=\"uhrzeitColumn\" was not injected: check your FXML file 'PruefungUI.fxml'.";

    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PruefungUI.fxml"));
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

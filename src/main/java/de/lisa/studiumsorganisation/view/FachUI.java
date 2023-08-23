package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Modul;
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

public class FachUI implements Initializable {

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
    private TableColumn<Fach, Integer> ectsColumn;
    @FXML
    private TableColumn<Fach, Boolean> fachBestandenColumn;
    @FXML
    private TableColumn<Fach, String> fachnameColumn;
    @FXML
    private Label modulNameText;
    @FXML
    private Button saveButton;
    @FXML
    private TableColumn<Fach, Integer> semesterColumn;
    @FXML
    private TableView<Fach> tableview;

    public static Modul getModul() {
        return modul;
    }

    public static void setModul(Modul modul) {
        FachUI.modul = modul;
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
    void onSave(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert ectsColumn != null : "fx:id=\"ectsColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert fachBestandenColumn != null : "fx:id=\"fachBestandenColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert fachnameColumn != null : "fx:id=\"fachnameColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert modulNameText != null : "fx:id=\"modulNameText\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert semesterColumn != null : "fx:id=\"semesterColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'FachUI.fxml'.";

    }

    private void updateTable() {
        tableview.getItems().clear();
        tableview.getItems().addAll();
        //update the tableview checkboxes for the praktika and the prüfung


    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/FachUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

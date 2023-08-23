package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Modul;
import javafx.application.Application;
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

public class MainUI extends Application implements Initializable {

    private static MainUI mainUI;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label ectsText;
    @FXML
    private Button fachInfosButton;

    @FXML
    private Button addButton;
    @FXML
    private TableColumn<Modul, String> modulNameColumn;

    @FXML
    private TableColumn<Modul, Boolean> modulBestandenColumn;
    @FXML
    private TableColumn<Modul, Boolean> praktikaBestandenColumn;
    @FXML
    private Button praktikumButton;
    @FXML
    private TableColumn<Modul, Boolean> pruefungBestandenColumn;
    @FXML
    private Button pruefungButton;
    @FXML
    private Button speichernButton;
    @FXML
    private Label studiengangText;
    @FXML
    private TableView<Modul> tableview;

    public static MainUI getMainUI() {
        return mainUI;
    }

    public static void setMainUI(MainUI mainUI) {
        MainUI.mainUI = mainUI;
    }

    public static void start(String[] args) {
        launch(args);
    }

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onFachInfos(ActionEvent event) throws IOException {
        var selectedModul = tableview.getSelectionModel().getSelectedItem();
        FachUI.setModul(selectedModul);
        var fachUI = new FachUI();
        //get current stage based on the event
        var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        fachUI.start(stage);
    }

    @FXML
    void onPraktikum(ActionEvent event) throws IOException {
        //get selected item 
        var selectedModul = tableview.getSelectionModel().getSelectedItem();
        PraktikumUI.setModul(selectedModul);
        var praktikumUI = new PraktikumUI();
        //get current stage based on the event
        var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        praktikumUI.start(stage);


    }

    @FXML
    void onPruefung(ActionEvent event) throws IOException {
        var selectedModul = tableview.getSelectionModel().getSelectedItem();
        PrüfungUI.setModul(selectedModul);
        var prüfungUI = new PrüfungUI();
        //get current stage based on the event
        var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        prüfungUI.start(stage);
    }

    @FXML
    void onSpeichern(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert ectsText != null : "fx:id=\"ectsText\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert fachInfosButton != null : "fx:id=\"fachInfosButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulBestandenColumn != null : "fx:id=\"modulBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulNameColumn != null : "fx:id=\"modulNameColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert praktikaBestandenColumn != null : "fx:id=\"praktikaBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert praktikumButton != null : "fx:id=\"praktikumButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert pruefungButton != null : "fx:id=\"pruefungButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert speichernButton != null : "fx:id=\"speichernButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert studiengangText != null : "fx:id=\"studiengangText\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'MainUI.fxml'.";
        mainUI = this;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/MainUI.fxml"));
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
    public void initialize(URL location, ResourceBundle resources) {

    }
}

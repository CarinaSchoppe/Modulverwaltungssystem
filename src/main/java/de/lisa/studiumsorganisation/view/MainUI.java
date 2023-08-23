package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private TableColumn<Modul, Boolean> pruefungBestandenColumn;
    @FXML
    private Button deleteButton;
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
        //create and add a new modul to the tableview
        var modul = new Modul(Modul.getModulCounter(), "Neues Modul", false, 0);
        Utility.getInstance().getModule().add(modul);
        tableview.getItems().add(modul);
    }

    @FXML
    void onFachInfos(ActionEvent event) throws IOException {
        var selectedModul = tableview.getSelectionModel().getSelectedItem();
        if (selectedModul != null) {

            FachUI.setModul(selectedModul);
            var fachUI = new FachUI();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            fachUI.start(stage);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Modul ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Modul aus, um die Fachinformationen zu sehen.");
            alert.showAndWait();
        }
    }


    @FXML
    void onSpeichern(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {
        //delete the selected modul from the tableview
        var selectedModul = tableview.getSelectionModel().getSelectedItem();
        if (selectedModul != null) {
            Utility.getInstance().getModule().remove(selectedModul);
            tableview.getItems().remove(selectedModul);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Modul ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Modul aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert ectsText != null : "fx:id=\"ectsText\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert fachInfosButton != null : "fx:id=\"fachInfosButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulBestandenColumn != null : "fx:id=\"modulBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulNameColumn != null : "fx:id=\"modulNameColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert praktikaBestandenColumn != null : "fx:id=\"praktikaBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
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

        tableview.getItems().addAll(Utility.getInstance().getModule());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modulNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modulNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modulNameColumn.setOnEditCommit(element -> (element.getTableView().getItems().get(
                element.getTablePosition().getRow())
        ).setName(element.getNewValue()));

        pruefungBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("prüfungBestanden"));
        pruefungBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(pruefungBestandenColumn));
        pruefungBestandenColumn.setOnEditCommit(result -> (result.getTableView().getItems().get(
                result.getTablePosition().getRow())
        ).setPrüfungenBestanden(result.getNewValue()));

        praktikaBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("praktikaBestanden"));
        praktikaBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(praktikaBestandenColumn));
        praktikaBestandenColumn.setOnEditCommit(result -> (result.getTableView().getItems().get(
                result.getTablePosition().getRow())
        ).setPraktikaBestanden(result.getNewValue()));

        modulBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestanden"));
        modulBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(modulBestandenColumn));
        modulBestandenColumn.setOnEditCommit(result -> (result.getTableView().getItems().get(
                result.getTablePosition().getRow())
        ).setBestanden(result.getNewValue()));
        updateTable();
    }
}

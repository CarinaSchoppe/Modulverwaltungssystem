package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.util.Utility;
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
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class FachUI implements Initializable {

    private static FachUI fachUI;
    @FXML
    private Button praktikumButton;
    @FXML
    private Button pruefungButton;
    @FXML
    private Button deleteButton;

    public static FachUI getFachUI() {
        return fachUI;
    }
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

    public static void setFachUI(FachUI fachUI) {
        FachUI.fachUI = fachUI;
    }

    public static Modul getModul() {
        return modul;
    }

    public static void setModul(Modul modul) {
        FachUI.modul = modul;
    }

    @FXML
    void onDelete(ActionEvent event) {
        var selectedFach = tableview.getSelectionModel().getSelectedItem();
        if (selectedFach != null) {


            Utility.getInstance().getFächer().remove(selectedFach);
            tableview.getItems().remove(selectedFach);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Fach ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Fach aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }

    @FXML
    void onAdd(ActionEvent event) {
        var fach = new Fach(Fach.getFachCounter(), "Neues Fach", 0, false, 0, modul.getID());
        Utility.getInstance().getFächer().add(fach);
        tableview.getItems().add(fach);
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
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert ectsColumn != null : "fx:id=\"ectsColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert fachBestandenColumn != null : "fx:id=\"fachBestandenColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert fachnameColumn != null : "fx:id=\"fachnameColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert modulNameText != null : "fx:id=\"modulNameText\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert praktikumButton != null : "fx:id=\"praktikumButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert pruefungButton != null : "fx:id=\"pruefungButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert semesterColumn != null : "fx:id=\"semesterColumn\" was not injected: check your FXML file 'FachUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'FachUI.fxml'.";
    }

    private void updateTable() {
        tableview.getItems().clear();
        tableview.getItems().addAll();
        //update the tableview checkboxes for the praktika and the prüfung
        tableview.getItems().addAll(new HashSet<>(Utility.getInstance().getFächer().stream().filter(fach -> fach.getModulID() == modul.getID()).toList()));
        modulNameText.setText(modul.getName());
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

    @FXML
    void onPraktikum(ActionEvent event) throws IOException {
        //get selected item 
        var selectedFach = tableview.getSelectionModel().getSelectedItem();
        if (selectedFach != null) {
            PraktikumUI.setFach(selectedFach);
            var praktikumUI = new PraktikumUI();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            praktikumUI.start(stage);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Fach ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Fach aus, das Sie bearbeiten möchten.");
            alert.showAndWait();
        }

    }

    @FXML
    void onPruefung(ActionEvent event) throws IOException {
        var selectedFach = tableview.getSelectionModel().getSelectedItem();
        if (selectedFach != null) {

            PrüfungsversucheUI.setFach(selectedFach);
            var prüfungUI = new PrüfungsversucheUI();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            prüfungUI.start(stage);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Fach ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Fach aus, das Sie bearbeiten möchten.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fachnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fachnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fachnameColumn.setOnEditCommit(event -> {
            Fach fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setName(event.getNewValue());
        });
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        semesterColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        semesterColumn.setOnEditCommit(event -> {
            Fach fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setSemester(event.getNewValue());
        });
        ectsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        ectsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ectsColumn.setOnEditCommit(event -> {
            Fach fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setCredits(event.getNewValue());
        });

        fachBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestanden"));
        fachBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(fachBestandenColumn));
        fachBestandenColumn.setOnEditCommit(event -> {
            Fach fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setBestanden(event.getNewValue());
        });
        updateTable();
        fachUI = this;
    }
}

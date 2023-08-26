package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.Main;
import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Prüfung;
import de.lisa.studiumsorganisation.model.Prüfungsform;
import de.lisa.studiumsorganisation.model.Prüfungsversuch;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PrüfungsUI implements Initializable {

    private static PrüfungsUI instance = null;
    @FXML
    private TableColumn<Prüfung, Integer> numberPruefungColumn;

    private static Fach fach;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Prüfung, BooleanProperty> pruefungBestandenColumn;
    @FXML
    private Button addPruefungButton;
    @FXML
    private TableColumn<Prüfungsversuch, Date> datumColumn;
    @FXML
    private Label pruefungNameText;

    @FXML
    private TableColumn<Prüfung, Prüfungsform> pruefungsFormColumn;
    @FXML
    private TableColumn<Prüfungsversuch, Float> noteColumn;
    @FXML
    private TableColumn<Prüfungsversuch, Integer> numberVersuchColumn;
    @FXML
    private TableColumn<Prüfungsversuch, BooleanProperty> pruefungVersuchBestandenColumn;
    @FXML
    private TableView<Prüfungsversuch> tableviewVersuch;
    @FXML
    private TableColumn<Prüfungsversuch, Time> uhrzeitColumn;
    @FXML
    private TableView<Prüfung> tableviewPruefung;
    @FXML
    private Button saveButton;
    @FXML
    private Button addVersuchButton;
    @FXML
    private Button deletePruefungButton;
    @FXML
    private Button deleteVersuchButton;
    @FXML
    private Label fachNameText;
    @FXML
    private Label pruefungsFormText;

    //Getter for the instance
    public static PrüfungsUI getInstance() {
        if (instance == null)
            instance = new PrüfungsUI();
        return instance;
    }

    @FXML
    void onAddVersuch(ActionEvent event) {

        //get current Prüfung from tableviewPrüfung
        var prüfung = tableviewPruefung.getSelectionModel().getSelectedItem();
        if (prüfung == null) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Keine Prüfung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Prüfung aus");
            alert.showAndWait();
            return;
        }
        var date = new Date();
        Time time = new Time(date.getTime());
        var versuch = new Prüfungsversuch(Prüfungsversuch.getPrüfungsversuchCounter(), date, time, false, 5.0F, prüfung.getID());
        Utility.getInstance().getPrüfungsversuche().add(versuch);
        updateTableVersuch(prüfung);
    }


    @FXML
    void onDeleteVersuch(ActionEvent event) {
        var versuch = tableviewVersuch.getSelectionModel().getSelectedItem();
        if (versuch != null) {
            Utility.getInstance().getPrüfungen().remove(versuch);
            //remove from tabeview
            tableviewVersuch.getItems().remove(versuch);

        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Prüfungsversuch ausgewählt");
            alert.setContentText("Bitte wählen Sie einen Prüfungsversuch aus");
            alert.showAndWait();
        }
    }


    public static Fach getFach() {
        return fach;
    }

    public static void setFach(Fach fach) {
        PrüfungsUI.fach = fach;
    }

    @FXML
    void onAddPruefung(ActionEvent event) {
        var prüfung = new Prüfung(Prüfung.getPrüfungCounter(), Prüfungsform.KLAUSUR, fach.getID(), false);
        Utility.getInstance().getPrüfungen().add(prüfung);
        updateTablePruefung();
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        MainUI.getInstance().start(stage);
    }

    @FXML
    void onDeletePruefung(ActionEvent event) {
        var item = tableviewPruefung.getSelectionModel().getSelectedItem();
        if (item != null) {
            Utility.getInstance().getPraktika().remove(item);
            tableviewPruefung.getItems().remove(item);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Keine Prüfung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Prüfung aus");
            alert.showAndWait();
        }
    }

    @FXML
    void onSave(ActionEvent event) {

        if (Main.isDummyLaunch()) {
            Utility.getInstance().getModule().forEach(modul -> System.out.println(modul.toString()));

        } else {
            Database.getInstance().saveAllData();
        }
    }

    @FXML
    void initialize() {
        assert addPruefungButton != null : "fx:id=\"addPruefungButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert addVersuchButton != null : "fx:id=\"addVersuchButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert deletePruefungButton != null : "fx:id=\"deletePruefungButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert deleteVersuchButton != null : "fx:id=\"deleteVersuchButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert fachNameText != null : "fx:id=\"fachNameText\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert noteColumn != null : "fx:id=\"noteColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert numberPruefungColumn != null : "fx:id=\"numberPruefungColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert numberVersuchColumn != null : "fx:id=\"numberVersuchColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungNameText != null : "fx:id=\"pruefungNameText\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungVersuchBestandenColumn != null : "fx:id=\"pruefungVersuchBestandenColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungsFormColumn != null : "fx:id=\"pruefungsFormColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungsFormText != null : "fx:id=\"pruefungsFormText\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert tableviewPruefung != null : "fx:id=\"tableviewPruefung\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert tableviewVersuch != null : "fx:id=\"tableviewVersuch\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert uhrzeitColumn != null : "fx:id=\"uhrzeitColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";

    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PrüfungsUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    private void updateTableVersuch(Prüfung prüfung) {
        tableviewVersuch.getItems().clear();
        //get prüfungen based on ModulID -> FachID -> Prüfungen
        tableviewVersuch.getItems().addAll(new HashSet<>(Utility.getInstance().getPrüfungsversuche().stream().filter(p -> p.getPrüfungsID() == prüfung.getID()).toList())
        );
        //update the tableview checkboxes for the praktika and the prüfung
        pruefungNameText.setText(prüfung.getFach().getName());
        pruefungsFormText.setText(prüfung.getPrüfungsform().getText());
        tableviewPruefung.refresh();
        tableviewVersuch.refresh();
    }

    private void updateTablePruefung() {
        tableviewPruefung.getItems().addAll(new HashSet<>(Utility.getInstance().getPrüfungen().stream().filter(p -> p.getFach().getID() == fach.getID()).toList()));
        fachNameText.setText(fach.getName());
        tableviewPruefung.refresh();

    }

    private void initPruefungsTable() {
        numberPruefungColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        pruefungBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        pruefungBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        pruefungBestandenColumn.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setBestanden(event.getNewValue().get())
        );
        pruefungsFormColumn.setCellValueFactory(pruefung -> new SimpleObjectProperty<>(pruefung.getValue().getPrüfungsform()));
        pruefungsFormColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Prüfungsform.values()));
        pruefungsFormColumn.setOnEditCommit(event -> {
            var pruefung = event.getRowValue();
            pruefung.setPrüfungsform(event.getNewValue());
        });
        tableviewPruefung.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateTableVersuch(newSelection));

        updateTablePruefung();
    }

    private void initVersucheTable() {
        numberVersuchColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        pruefungVersuchBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        pruefungVersuchBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        pruefungVersuchBestandenColumn.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setBestanden(event.getNewValue().get())
        );
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        noteColumn.setOnEditCommit(event -> {
            var versuch = event.getRowValue();
            versuch.setNote(event.getNewValue());
        });

        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        datumColumn.setCellFactory(column -> new TextFieldTableCell<>(Utility.DATE_FORMATTER));
        datumColumn.setOnEditCommit(event -> {
            var versuch = event.getTableView().getItems().get(event.getTablePosition().getRow());
            versuch.setDatum(event.getNewValue());
        });

        // Configure uhrzeitColumn
        uhrzeitColumn.setCellValueFactory(new PropertyValueFactory<>("uhrzeit"));
        uhrzeitColumn.setCellFactory(column -> new TextFieldTableCell<>(Utility.TIME_FORMATTER));
        uhrzeitColumn.setOnEditCommit(event -> {
            var versuch = event.getTableView().getItems().get(event.getTablePosition().getRow());
            versuch.setUhrzeit(event.getNewValue());
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initPruefungsTable();
        initVersucheTable();
    }
}

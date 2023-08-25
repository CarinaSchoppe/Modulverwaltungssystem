package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Praktikum;
import de.lisa.studiumsorganisation.model.Praktikumstermin;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
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
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PraktikumUI implements Initializable {


    private static PraktikumUI instance = null;
    @FXML
    private TableColumn<Praktikum, BooleanProperty> praktikumBestandenColumn;

    private static Fach fach;

    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label fachNameText;
    @FXML
    private Button versuchButton;

    public static Fach getFach() {
        return fach;
    }
    @FXML
    private Button addButtonPraktikum;

    @FXML
    private Button saveButton;
    @FXML
    private Button addButtonTermin;
    @FXML
    private Button deleteButtonPraktikum;
    @FXML
    private Button deleteButtonTermin;
    @FXML
    private TableColumn<Praktikum, Integer> numberColumnPraktikum;
    @FXML
    private Label praktikumNameText;
    @FXML
    private TableView<Praktikum> tableviewPraktikum;
    @FXML
    private TableColumn<Praktikumstermin, Date> datumColumn;
    @FXML
    private TableColumn<Praktikumstermin, BooleanProperty> terminBestandenColumn;
    @FXML
    private TableColumn<Praktikumstermin, Time> uhrzeitColumn;
    @FXML
    private TableView<Praktikumstermin> tableviewTermin;
    @FXML
    private TableColumn<Praktikum, Integer> numberColumnTermin;

    //Getter for the instance
    public static PraktikumUI getInstance() {
        if (instance == null)
            instance = new PraktikumUI();
        return instance;
    }

    @FXML
    void onAddTermin(ActionEvent event) {
        //get praktikum from tableviewPraktikum
        var praktikum = tableviewPraktikum.getSelectionModel().getSelectedItem();
        if (praktikum == null) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Praktikum ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Praktikum aus, zu dem Sie einen Termin hinzufügen möchten.");
            alert.showAndWait();
            return;
        }

        var date = LocalDate.now();
        //convert date to a Date.class object
        var time = Time.valueOf("00:00:00");
        var versuch = new Praktikumstermin(Praktikumstermin.getPraktikumsterminCounter(), praktikum.getID(), java.sql.Date.valueOf(date), time, false);
        Utility.getInstance().getPraktikumstermine().add(versuch);
        updateTerminTable(praktikum);
    }


    @FXML
    void onDeleteTermin(ActionEvent event) {
        var versuch = tableviewTermin.getSelectionModel().getSelectedItem();
        if (versuch != null) {
            Utility.getInstance().getPrüfungsversuche().remove(versuch);
            tableviewTermin.getItems().remove(versuch);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Prüfungsversuch ausgewählt");
            alert.setContentText("Bitte wählen Sie einen Prüfungsversuch aus");
            alert.showAndWait();
        }
    }


    public static void setFach(Fach fach) {
        PraktikumUI.fach = fach;
    }


    @FXML
    void onAddPraktikum(ActionEvent event) {
        var praktikum = new Praktikum(Praktikum.getPraktikumCounter(), false, fach.getID());
        Utility.getInstance().getPraktika().add(praktikum);
        tableviewPraktikum.getItems().add(praktikum);
        tableviewPraktikum.refresh();
        //TODO: das hier überall
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        MainUI.getInstance().start(stage);
    }

    @FXML
    void onDeletePraktikum(ActionEvent event) {
        var item = tableviewPraktikum.getSelectionModel().getSelectedItem();
        if (item != null) {
            Utility.getInstance().getPraktika().remove(item);
            tableviewPraktikum.getItems().remove(item);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Praktikum ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Praktikum aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }


    @FXML
    void onSave(ActionEvent event) {
//TODO: hier

    }


    @FXML
    void initialize() {
        assert addButtonPraktikum != null : "fx:id=\"addButtonPraktikum\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert addButtonTermin != null : "fx:id=\"addButtonTermin\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert deleteButtonPraktikum != null : "fx:id=\"deleteButtonPraktikum\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert deleteButtonTermin != null : "fx:id=\"deleteButtonTermin\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert fachNameText != null : "fx:id=\"fachNameText\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert numberColumnPraktikum != null : "fx:id=\"numberColumnPraktikum\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert numberColumnTermin != null : "fx:id=\"numberColumnTermin\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert praktikumBestandenColumn != null : "fx:id=\"praktikumBestandenColumn\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert praktikumNameText != null : "fx:id=\"praktikumNameText\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert tableviewPraktikum != null : "fx:id=\"tableviewPraktikum\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert tableviewTermin != null : "fx:id=\"tableviewTermin\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert terminBestandenColumn != null : "fx:id=\"terminBestandenColumn\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert uhrzeitColumn != null : "fx:id=\"uhrzeitColumn\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PraktikumsUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    private void updateTable() {
        tableviewPraktikum.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableviewPraktikum.getItems().addAll(new HashSet<>(Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFachID() == fach.getID()).toList()));
        fachNameText.setText(fach.getName());
        tableviewPraktikum.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPraktikumTable();
        initPraktikumsterminTable();
        instance = this;
    }

    private void updateTerminTable(Praktikum praktikum) {
        tableviewTermin.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        var items = new HashSet<>(Utility.getInstance().getPraktikumstermine().stream().filter(versuch -> versuch.getPraktikumID() == praktikum.getID()).toList());
        tableviewTermin.getItems().addAll(items);
        praktikumNameText.setText(praktikum.getFach().getName());
        tableviewPraktikum.refresh();
        tableviewTermin.refresh();

    }

    private void initPraktikumTable() {
        numberColumnPraktikum.setCellValueFactory(new PropertyValueFactory<>("ID"));
        praktikumBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        praktikumBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        praktikumBestandenColumn.setOnEditCommit(event -> {
            var praktikum = event.getTableView().getItems().get(event.getTablePosition().getRow());
            praktikum.setBestanden(event.getNewValue().get());
        });
        tableviewPraktikum.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateTerminTable(newSelection));
        updateTable();
    }

    private void initPraktikumsterminTable() {
        numberColumnTermin.setCellValueFactory(new PropertyValueFactory<>("ID"));
        terminBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        terminBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        terminBestandenColumn.setOnEditCommit(event -> {
            var praktikumsTermin = event.getTableView().getItems().get(event.getTablePosition().getRow());
            praktikumsTermin.setBestanden(event.getNewValue().get());
        });

        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        datumColumn.setCellFactory(column -> new TextFieldTableCell<>(Utility.DATE_FORMATTER));
        datumColumn.setOnEditCommit(event -> {
            var praktikumsTermin = event.getTableView().getItems().get(event.getTablePosition().getRow());
            praktikumsTermin.setDatum(event.getNewValue());
        });

        // Configure uhrzeitColumn
        uhrzeitColumn.setCellValueFactory(new PropertyValueFactory<>("uhrzeit"));
        uhrzeitColumn.setCellFactory(column -> new TextFieldTableCell<>(Utility.TIME_FORMATTER));
        uhrzeitColumn.setOnEditCommit(event -> {
            var praktikumsTermin = event.getTableView().getItems().get(event.getTablePosition().getRow());
            praktikumsTermin.setUhrzeit(event.getNewValue());
        });
    }


}

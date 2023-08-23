package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Praktikum;
import de.lisa.studiumsorganisation.model.Praktikumstermin;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PraktikumsversucheUI implements Initializable {


    private static Praktikum praktikum;

    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button deleteButton;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Praktikum, Integer> numberColumn;
    @FXML
    private CheckBox praktikumBestandenBox;

    @FXML
    private TableColumn<Praktikumstermin, Date> datumColumn;
    @FXML
    private Label praktikumNameText;
    @FXML
    private Button saveButton;
    @FXML
    private TableColumn<Praktikumstermin, Boolean> terminBestandenColumn;
    @FXML
    private TableColumn<Praktikumstermin, Time> uhrzeitColumn;
    @FXML
    private TableView<Praktikumstermin> tableview;

    public static Praktikum getPraktikum() {
        return praktikum;
    }

    public static void setPraktikum(Praktikum praktikum) {
        PraktikumsversucheUI.praktikum = praktikum;
    }

    @FXML
    void onDelete(ActionEvent event) {
        var versuch = tableview.getSelectionModel().getSelectedItem();
        if (versuch != null) {
            Utility.getInstance().getPrüfungsversuche().remove(versuch);
            tableview.getItems().remove(versuch);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Prüfungsversuch ausgewählt");
            alert.setContentText("Bitte wählen Sie einen Prüfungsversuch aus");
            alert.showAndWait();
        }
    }

    @FXML
    void onAdd(ActionEvent event) {
        var date = LocalDate.now();
        //convert date to a Date.class object
        var time = Time.valueOf("00:00:00");
        var versuch = new Praktikumstermin(Praktikumstermin.getPraktikumsterminCounter(), praktikum.getID(), java.sql.Date.valueOf(date), time, false);
        Utility.getInstance().getPraktikumstermine().add(versuch);
        tableview.getItems().add(versuch);
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        PraktikumUI.getPraktikumUI().start(stage);
    }


    @FXML
    void onSave(ActionEvent event) {
//TODO: hier

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert praktikumBestandenBox != null : "fx:id=\"praktikumBestandenBox\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert praktikumNameText != null : "fx:id=\"praktikumNameText\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert terminBestandenColumn != null : "fx:id=\"terminBestandenColumn\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
        assert uhrzeitColumn != null : "fx:id=\"uhrzeitColumn\" was not injected: check your FXML file 'PraktikumsversucheUI.fxml'.";
    
    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PraktikumsversucheUI.fxml"));
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
        tableview.getItems().addAll(new HashSet<>(Utility.getInstance().getPraktikumstermine().stream().filter(versuch -> versuch.getPraktikumID() == praktikum.getID()).toList()));
        praktikumNameText.setText(praktikum.getFach().getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        terminBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestanden"));
        terminBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(terminBestandenColumn));
        terminBestandenColumn.setOnEditCommit(event -> {
            updatePraktika();
            var praktikumsTermin = event.getTableView().getItems().get(event.getTablePosition().getRow());
            praktikumsTermin.setBestanden(event.getNewValue());
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
        updateTable();
    }

    private void updatePraktika() {
        var praktika = tableview.getItems().stream().toList();
        //if all praktika are passed set the checkbox to true
        praktikumBestandenBox.setSelected(praktika.stream().allMatch(Praktikumstermin::isBestanden));
    }

}

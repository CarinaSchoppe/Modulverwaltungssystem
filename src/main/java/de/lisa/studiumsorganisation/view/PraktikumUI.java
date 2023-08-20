package de.lisa.studiumsorganisation.view;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Praktikum;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PraktikumUI implements Initializable {


    private static Modul modul;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Praktikum, Boolean> bestandenColumn;

    @FXML
    private TableColumn<Praktikum, String> datumColumn;

    @FXML
    private Label modulName;

    @FXML
    private Button save;

    @FXML
    private TableView<Praktikum> tableview;

    @FXML
    private TableColumn<Praktikum, Integer> versuchColumn;

    @FXML
    void onAdd(ActionEvent event) {
        modul.getPraktika().add(new Praktikum(Praktikum.getPraktikumCounter(), modul.getID(), false, new Date(), 0, modul));
        updateTable();
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        var primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var root = (Parent)
                FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("/fxml/ModulUI.fxml")));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    void onSave(ActionEvent event) {
        Database.getInstance().saveAllData();
        var alert = new Alert(Alert.AlertType.INFORMATION, "Daten gespeichert");
        alert.showAndWait();
    }


    public void start(Stage primaryStage, Modul modul) throws IOException {
        PraktikumUI.modul = modul;
        var loader = new FXMLLoader(Praktikum.class.getResource("/fxml/PraktikumUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    @FXML
    void initialize() {
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert bestandenColumn != null : "fx:id=\"bestandenColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert modulName != null : "fx:id=\"modulName\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        assert versuchColumn != null : "fx:id=\"versuchColumn\" was not injected: check your FXML file 'PraktikumUI.fxml'.";
        modulName.setText(modul.getName());
    }

    private void updateTable() {
        tableview.getItems().clear();
        tableview.getItems().addAll(modul.getPraktika());
        //update the tableview checkboxes for the praktika and the prüfung
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
        bestandenColumn.setCellValueFactory(cell -> cell.getValue().getBestandenProperty());

        /*  
    private Date datum;
    private int versuch;
    */

        versuchColumn.setCellValueFactory(new PropertyValueFactory<>("versuch"));
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datumString"));
        //set the data for the tableview

        bestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(bestandenColumn));
        bestandenColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Praktikum, Boolean> newValue) ->
                        (newValue.getTableView().getItems().get(
                                newValue.getTablePosition().getRow())
                        ).setBestanden(newValue.getNewValue())
        );


        StringConverter<Integer> converter = new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return value != null ? value.toString() : "";
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        };


        versuchColumn.setCellFactory(TextFieldTableCell.<Praktikum, Integer>forTableColumn(converter));
        versuchColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Praktikum, Integer> event) -> {
                    Praktikum praktikum = event.getRowValue();
                    praktikum.setVersuch(event.getNewValue());
                }
        );
        final var sdf = new SimpleDateFormat("dd.MM.yyyy");

        final StringConverter<Date> converterDate = new StringConverter<>() {
            @Override
            public String toString(Date object) {
                return object != null ? sdf.format(object) : "";
            }

            @Override
            public Date fromString(String string) {
                try {
                    return string != null ? sdf.parse(string) : null;
                } catch (ParseException e) {
                    // Handle exception (e.g., notify user of error)
                    return null;
                }
            }
        };

        datumColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        datumColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Praktikum, String> date) -> date.getTableView().getItems().get(
                        date.getTablePosition().getRow()
                ).setDatum(converterDate.fromString(date.getNewValue()))
        );

    }
}

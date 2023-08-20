package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.application.Application;
import javafx.beans.property.Property;
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

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;


public class ModulUI extends Application implements Initializable {


    @FXML
    private Button praktikumOpen;
    @FXML
    private Button examOpen;
    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Modul, Boolean> modulBestandenColumn;

    @FXML
    private TableColumn<Modul, String> nameColumn;

    @FXML
    private TableColumn<Modul, Boolean> praktikumBestandenColumn;

    @FXML
    private TableColumn<Modul, String> pruefungsTerminColumn;

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<Modul, Integer> semesterColumn;

    @FXML
    private TableView<Modul> tableView;

    @FXML
    void onAdd(ActionEvent event) {
        Utility.getInstance().getModule().add(new Modul(Modul.getModulCounter(), "", 0, false, null));
        updateTable();
    }

    @FXML
    void onSave(ActionEvent event) {
        Database.getInstance().saveAllData();
        var alert = new Alert(Alert.AlertType.INFORMATION, "Daten gespeichert");
        alert.showAndWait();
    }

    public static void start() {
        launch();
    }

    @FXML
    void onExamOpen(ActionEvent event) throws IOException {
        var primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
    }

    @FXML
    void onPraktikumOpen(ActionEvent event) throws IOException {
        var primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        new PraktikumUI().start(primaryStage, tableView.getSelectionModel().getSelectedItem());

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert examOpen != null : "fx:id=\"examOpen\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert modulBestandenColumn != null : "fx:id=\"modulBestandenColumn\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert praktikumBestandenColumn != null : "fx:id=\"praktikumBestandenColumn\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert praktikumOpen != null : "fx:id=\"praktikumOpen\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert pruefungsTerminColumn != null : "fx:id=\"pruefungsTerminColumn\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert semesterColumn != null : "fx:id=\"semesterColumn\" was not injected: check your FXML file 'ModulUI.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ModulUI.fxml'.";

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/ModulUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    private void updateTable() {
        tableView.getItems().clear();
        tableView.getItems().addAll(Utility.getInstance().getModule());

        //update the tableview checkboxes for the praktika and the prüfung


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
        modulBestandenColumn.setCellValueFactory(cell -> cell.getValue().getBestandenProperty());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        pruefungsTerminColumn.setCellValueFactory(new PropertyValueFactory<>("PrüfungTerminString"));
        praktikumBestandenColumn.setCellValueFactory(cell -> cell.getValue().getPraktikumBestandenProperty());
        //set the data for the tableview
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Modul, String> newValue) ->
                        (newValue.getTableView().getItems().get(
                                newValue.getTablePosition().getRow())
                        ).setName(newValue.getNewValue())
        );
        modulBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(modulBestandenColumn));
        modulBestandenColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Modul, Boolean> newValue) ->
                        (newValue.getTableView().getItems().get(
                                newValue.getTablePosition().getRow())
                        ).setBestanden(newValue.getNewValue())
        );

        praktikumBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(praktikumBestandenColumn));
        praktikumBestandenColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Modul, Boolean> newValue) ->
                        (newValue.getTableView().getItems().get(
                                newValue.getTablePosition().getRow())
                        ).setPraktikumBestanden(newValue.getNewValue())
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


        semesterColumn.setCellFactory(TextFieldTableCell.<Modul, Integer>forTableColumn(converter));
        semesterColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Modul, Integer> event) -> {
                    Modul modul = event.getRowValue();
                    modul.setSemester(event.getNewValue());
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

        pruefungsTerminColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        pruefungsTerminColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Modul, String> date) -> date.getTableView().getItems().get(
                        date.getTablePosition().getRow()
                ).getPrüfung().setDatum(converterDate.fromString(date.getNewValue()))
        );


    }
}

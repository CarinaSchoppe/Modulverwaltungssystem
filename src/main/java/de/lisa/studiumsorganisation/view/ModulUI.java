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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.ResourceBundle;

public class ModulUI extends Application implements Initializable {


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

    }

    @FXML
    void onSave(ActionEvent event) {

    }

    public static void start() {
        launch();
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'Untitled'.";
        assert modulBestandenColumn != null : "fx:id=\"modulBestandenColumn\" was not injected: check your FXML file 'Untitled'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'Untitled'.";
        assert praktikumBestandenColumn != null : "fx:id=\"praktikumBestandenColumn\" was not injected: check your FXML file 'Untitled'.";
        assert pruefungsTerminColumn != null : "fx:id=\"pruefungsTerminColumn\" was not injected: check your FXML file 'Untitled'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'Untitled'.";
        assert semesterColumn != null : "fx:id=\"semesterColumn\" was not injected: check your FXML file 'Untitled'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Untitled'.";

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set the data for the tableview
        modulBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestanden"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        pruefungsTerminColumn.setCellValueFactory(new PropertyValueFactory<>("PrüfungTerminString"));
        praktikumBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("PraktikumBestanden"));

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
                        ).getPraktikum().setBestanden(newValue.getNewValue())
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
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

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
        updateTable();
    }
}

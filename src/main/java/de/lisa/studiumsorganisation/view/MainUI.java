package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.Main;
import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.application.Application;
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
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * The MainUI class is responsible for managing the main user interface of the application.
 * It extends the Application class and implements the Initializable interface.
 */
public class MainUI extends Application implements Initializable {

    private static MainUI instance = null;
    @FXML
    private Button addButtonFach;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button addButtonModul;
    @FXML
    private Button deleteButtonFach;
    @FXML
    private Button deleteButtonModul;
    @FXML
    private TableColumn<Fach, Integer> ectsColumn;
    @FXML
    private TableColumn<Fach, BooleanProperty> fachBestandenColumn;

    @FXML
    private Label ectsText;
    @FXML
    private TableColumn<Fach, String> fachnameColumn;
    @FXML
    private TableColumn<Modul, BooleanProperty> modulBestandenColumn;
    @FXML
    private TableColumn<Modul, String> modulNameColumn;
    @FXML
    private Label modulNameText;
    @FXML
    private TableColumn<Modul, BooleanProperty> praktikaBestandenColumn;
    @FXML
    private Button praktikumButton;
    @FXML
    private TableColumn<Modul, BooleanProperty> pruefungBestandenColumn;
    @FXML
    private Button pruefungButton;
    @FXML
    private Button saveButton;
    @FXML
    private TableColumn<Fach, Integer> semesterColumn;
    @FXML
    private TableView<Fach> tableviewFach;

    @FXML
    private Label studiengangText;
    @FXML
    private TableView<Modul> tableviewModul;

    //Getter for the instance
    public static MainUI getInstance() {
        return instance;
    }

    @FXML
    void onAddFach(ActionEvent event) {
        //get current fach from tableviewModul
        var modul = tableviewModul.getSelectionModel().getSelectedItem();
        if (modul == null) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Modul ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Modul aus, zu dem Sie ein Fach hinzufügen möchten.");
            alert.showAndWait();
            return;
        }

        var fach = new Fach(Fach.getFachCounter(), "Neues Fach", 0, false, 0, modul.getID());
        Utility.getInstance().getFächer().add(fach);
        modul.isBestanden();
        updateTableFach(modul);
    }


    @FXML
    void onDeleteFach(ActionEvent event) {
        var selectedFach = tableviewFach.getSelectionModel().getSelectedItem();
        if (selectedFach != null) {
            //delete all corresponding praktikumstermine and prüfungsversuche and praktika and prüfungen
            var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFachID() == selectedFach.getID()).toList();
            var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getFachID() == selectedFach.getID()).toList();
            var prüfungsversuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungstermin -> prüfungen.stream().anyMatch(prüfung -> prüfung.getID() == prüfungstermin.getPrüfung().getID())).toList();
            var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktika.stream().anyMatch(praktikum -> praktikum.getID() == praktikumstermin.getPraktikum().getID())).toList();
            Utility.getInstance().getPraktika().removeAll(praktika);
            Utility.getInstance().getPrüfungen().removeAll(prüfungen);
            Utility.getInstance().getPrüfungsversuche().removeAll(prüfungsversuche);
            Utility.getInstance().getPraktikumstermine().removeAll(praktikumstermine);
            Utility.getInstance().getFächer().remove(selectedFach);
            tableviewFach.getItems().remove(selectedFach);
            var modul = tableviewModul.getSelectionModel().getSelectedItem();
            if (modul != null) {
                modul.isBestanden();
            }
            updateTable();
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Fach ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Fach aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }


    @FXML
    void onPraktikum(ActionEvent event) throws IOException {
        var selectedFach = tableviewFach.getSelectionModel().getSelectedItem();
        if (selectedFach != null) {
            PraktikumUI.setFach(selectedFach);
            var praktikumUI = PraktikumUI.getInstance();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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
        var selectedFach = tableviewFach.getSelectionModel().getSelectedItem();
        if (selectedFach != null) {
            PrüfungsUI.setFach(selectedFach);
            var prüfungsUI = PrüfungsUI.getInstance();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            prüfungsUI.start(stage);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Fach ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Fach aus, das Sie bearbeiten möchten.");
            alert.showAndWait();
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        if (Main.isDummyLaunch()) {
            Utility.getInstance().getModule().forEach(modul -> System.out.println(modul.toString()));
        } else {
            Database.getInstance().saveAllData();
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erfolg");
            alert.setHeaderText("Daten gespeichert");
            alert.setContentText("Die Daten wurden erfolgreich gespeichert.");
            alert.showAndWait();
        }
    }


    public static void start(String[] args) {
        launch(args);
    }

    private void updateTableFach(Modul modul) {
        tableviewFach.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        if (modul == null) return;
        tableviewFach.getItems().addAll(new HashSet<>(Utility.getInstance().getFächer().stream().filter(fach -> fach.getModulID() == modul.getID()).toList()));
        modulNameText.setText(modul.getName());
        tableviewModul.refresh();
        tableviewFach.refresh();
    }


    @FXML
    void onAddModul(ActionEvent event) {
        //create and add a new modul to the tableview
        var modul = new Modul(Modul.getModulCounter(), "Neues Modul", false, 0);
        Utility.getInstance().getModule().add(modul);
        updateTable();
    }


    @FXML
    void onDeleteModul(ActionEvent event) {
        //delete the selected modul from the tableview
        var selectedModul = tableviewModul.getSelectionModel().getSelectedItem();
        if (selectedModul != null) {
            var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModulID() == selectedModul.getID()).toList();
            var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
            var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
            var prüfungsversuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungstermin -> prüfungen.stream().anyMatch(prüfung -> prüfung.getID() == prüfungstermin.getPrüfung().getID())).toList();
            var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktika.stream().anyMatch(praktikum -> praktikum.getID() == praktikumstermin.getPraktikum().getID())).toList();

            Utility.getInstance().getFächer().removeAll(fächer);
            Utility.getInstance().getPraktika().removeAll(praktika);
            Utility.getInstance().getPrüfungen().removeAll(prüfungen);
            Utility.getInstance().getPrüfungsversuche().removeAll(prüfungsversuche);
            Utility.getInstance().getPraktikumstermine().removeAll(praktikumstermine);
            Utility.getInstance().getModule().remove(selectedModul);
            tableviewModul.getItems().remove(selectedModul);
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
        assert addButtonFach != null : "fx:id=\"addButtonFach\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert addButtonModul != null : "fx:id=\"addButtonModul\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert deleteButtonFach != null : "fx:id=\"deleteButtonFach\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert deleteButtonModul != null : "fx:id=\"deleteButtonModul\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert ectsColumn != null : "fx:id=\"ectsColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert ectsText != null : "fx:id=\"ectsText\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert fachBestandenColumn != null : "fx:id=\"fachBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert fachnameColumn != null : "fx:id=\"fachnameColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulBestandenColumn != null : "fx:id=\"modulBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulNameColumn != null : "fx:id=\"modulNameColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert modulNameText != null : "fx:id=\"modulNameText\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert praktikaBestandenColumn != null : "fx:id=\"praktikaBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert praktikumButton != null : "fx:id=\"praktikumButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert pruefungButton != null : "fx:id=\"pruefungButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert semesterColumn != null : "fx:id=\"semesterColumn\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert studiengangText != null : "fx:id=\"studiengangText\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert tableviewFach != null : "fx:id=\"tableviewFach\" was not injected: check your FXML file 'MainUI.fxml'.";
        assert tableviewModul != null : "fx:id=\"tableviewModul\" was not injected: check your FXML file 'MainUI.fxml'.";
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
        tableviewModul.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableviewModul.getItems().addAll(Utility.getInstance().getModule());
        tableviewModul.refresh();
        tableviewFach.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initModulTable();
        initFachTable();
    }

    private void initModulTable() {
        modulNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modulNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modulNameColumn.setOnEditCommit(element -> (element.getTableView().getItems().get(
                element.getTablePosition().getRow())
        ).setName(element.getNewValue()));

        pruefungBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("pruefungBestandenProperty"));
        pruefungBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var modul = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(modul.getPrüfungBestanden());
                    checkBox.setOnAction(e -> modul.setPrüfungenBestanden(checkBox.isSelected()));
                }
            }
        });

        praktikaBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("praktikaBestandenProperty"));
        praktikaBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var modul = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(modul.isBestanden());
                    checkBox.setOnAction(e -> {
                        modul.setPraktikaBestanden(checkBox.isSelected());
                    });
                }
            }
        });

        modulBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        modulBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var modul = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(modul.isBestanden());
                    checkBox.setOnAction(e -> modul.setBestanden(checkBox.isSelected()));
                }
            }
        });

        tableviewModul.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateTableFach(newSelection));
        updateTable();
    }


    private void initFachTable() {
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

        fachBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        fachBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    checkBox.setOnAction(e -> {
                        var fach = getTableView().getItems().get(getIndex());
                        fach.setBestanden(checkBox.isSelected());
                    });
                }
            }
        });


    }
}

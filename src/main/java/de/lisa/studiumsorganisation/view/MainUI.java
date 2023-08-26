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

    /**
     * Represents the instance of the MainUI class.
     * <p>
     * This variable is of type MainUI and is used to hold the single instance of the MainUI class.
     * The variable is declared as private static to ensure that only a single instance of the MainUI class is created.
     * <p>
     * Example usage:
     * <p>
     * MainUI mainUI = MainUI.getInstance();
     */
    private static MainUI instance = null;
    /**
     *
     */
    @FXML
    private Button addButtonFach;

    /**
     * The ResourceBundle variable containing the resources for this controller.
     *
     * <p>ResourceBundle is a class in Java that represents a collection of resources and their localized values.
     * It is typically used for internationalization and localization purposes, where different language
     * translations or region-specific configurations are required. The resources are organized as key-value
     * pairs and can be accessed using the corresponding key.</p>
     *
     * <p>FXML is a markup language used to define the user interface components in JavaFX applications. By
     * specifying the FXML file location in the @FXML annotation, the JavaFX runtime can load and initialize
     * the user interface components defined in the FXML file.</p>
     *
     * <p>This variable, annotated with @FXML, is used to inject the ResourceBundle instance containing the
     * resources for this controller. It provides access to localized strings, images, or any other resource
     * defined in the resource bundle file. The resource bundle file can be configured with translations for
     * different languages or alternative resources for different regions. By utilizing the ResourceBundle
     * provided through this variable, the user interface components in the FXML file can be dynamically
     * updated to display the appropriate localized content.</p>
     *
     * @see ResourceBundle
     * @see FXMLLoader
     */
    @FXML
    private ResourceBundle resources;

    /**
     * The location of the associated FXML file.
     * <p>
     * This variable is annotated with {@literal @FXML} to indicate that it is part of the FXML file
     * and should be injected by the JavaFX runtime during initialization.
     * <p>
     * The {@link URL} type is used to represent the location of a resource, such as an FXML file,
     * which can be specified as a file path or a URL.
     * <p>
     * Usage example:
     * <pre>
     *     {@literal @}FXML
     *     private URL location;
     * </pre>
     */
    @FXML
    private URL location;
    /**
     * The addButtonModul variable represents a JavaFX Button object for adding a new module.
     *
     * <p>The addButtonModul variable is annotated with @FXML, indicating that it is linked to a graphical component
     * in a FXML file through the JavaFX FXMLLoader.</p>
     *
     * <p>The addButtonModul variable is declared as private, meaning it can only be accessed within the class it is declared in.</p>
     *
     * <p>Usage:</p>
     * <pre>{@code
     * addButtonModul.setOnAction(event -> {
     *     // Add module functionality here
     * });
     * }</pre>
     */
    @FXML
    private Button addButtonModul;
    /**
     *
     */
    @FXML
    private Button deleteButtonFach;
    /**
     * The deleteButtonModul variable represents a JavaFX Button instance that is used
     * for deleting a specific module. This variable is annotated with @FXML indicating
     * that it is a reference to a Button element defined in an FXML file and it is
     * injected by the JavaFX FXMLLoader.
     * <p>
     * Usage:
     * deleteButtonModul.setOnAction(event -> {
     * // Code for deleting the module goes here
     * });
     * <p>
     * Note: Make sure to assign an appropriate event handler to the deleteButtonModul
     * using the setOnAction method in order to handle the delete action.
     */
    @FXML
    private Button deleteButtonModul;
    /**
     * The ectsColumn variable represents the TableColumn for displaying ECTS information for Fach objects.
     *
     * <p>It is used in conjunction with JavaFX and the FXML markup to define and display the ECTS column
     * in a TableView.
     *
     * <p>The TableColumn is of type Integer and is assigned to Fach objects.
     */
    @FXML
    private TableColumn<Fach, Integer> ectsColumn;
    /**
     *
     */
    @FXML
    private TableColumn<Fach, BooleanProperty> fachBestandenColumn;

    /**
     * This variable represents a JavaFX label that is used to display the ECTS (European Credit Transfer and Accumulation System) text.
     * <p>
     * The ECTS text is typically used in educational settings to represent the number of credits assigned to a course or module.
     * <p>
     * The value of this label can be updated dynamically to reflect the current ECTS value for a specific course or module.
     * <p>
     * To update the text displayed by this label, you can use the setText() method of the Label class. For example:
     * <p>
     * ectsText.setText("6 ECTS"); // Sets the text to "6 ECTS"
     * <p>
     * By default, the label will be initialized with an empty value.
     */
    @FXML
    private Label ectsText;
    /**
     * The TableColumn that represents the fachname property of the Fach class.
     * This column is typically used in a TableView to display and edit the fachname values of Fach objects.
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * TableView<Fach> tableView = new TableView<>();
     * TableColumn<Fach, String> fachnameColumn = new TableColumn<>("Fachname");
     * tableView.getColumns().add(fachnameColumn);
     * }</pre>
     * </p>
     *
     * @see Fach
     * @see TableColumn
     * @see TableView
     */
    @FXML
    private TableColumn<Fach, String> fachnameColumn;
    /**
     *
     */
    @FXML
    private TableColumn<Modul, BooleanProperty> modulBestandenColumn;
    /**
     *
     */
    @FXML
    private TableColumn<Modul, String> modulNameColumn;
    /**
     * The modulNameText variable represents a JavaFX Label object used in the graphical user interface of the application.
     * <p>
     * This variable is annotated with @FXML, which means it is injected and initialized by the JavaFX framework using FXML. It is a private instance variable, accessible only within the class it is defined in.
     * <p>
     * The Label class is a part of JavaFX and serves as a control for displaying a short text, typically single line, to the user.
     */
    @FXML
    private Label modulNameText;
    /**
     *
     */
    @FXML
    private TableColumn<Modul, BooleanProperty> praktikaBestandenColumn;
    /**
     *
     */
    @FXML
    private Button praktikumButton;
    /**
     *
     */
    @FXML
    private TableColumn<Modul, BooleanProperty> pruefungBestandenColumn;
    /**
     *
     */
    @FXML
    private Button pruefungButton;
    /**
     *
     */
    @FXML
    private Button saveButton;
    /**
     *
     */
    @FXML
    private TableColumn<Fach, Integer> semesterColumn;
    /**
     * The TableView variable tableviewFach is used to display and interact with a collection of objects of type Fach.
     * It is annotated with @FXML to indicate that it is a JavaFX component that is defined in an FXML file.
     * The TableView provides a graphical representation of the data and allows the user to perform actions such as sorting
     * and filtering the data, as well as selecting and editing individual items.
     *
     * @see Fach
     * @see TableView
     */
    @FXML
    private TableView<Fach> tableviewFach;

    /**
     *
     */
    @FXML
    private Label studiengangText;
    /**
     *
     */
    @FXML
    private TableView<Modul> tableviewModul;

    /**
     * Returns the instance of the MainUI class.
     *
     * @return the instance of the MainUI class
     */ //Getter for the instance
    public static MainUI getInstance() {
        return instance;
    }

    /**
     * Starts the application.
     *
     * @param args The command line arguments.
     */
    public static void start(String[] args) {
        launch(args);
    }

    /**
     * Handles the event when a user wants to add a Fach to a selected Modul.
     *
     * @param event The action event that triggered this method.
     */
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
        var ects = modul.getFächer().stream().mapToInt(Fach::getCredits).sum();
        var bestanden = modul.getFächer().stream().filter(Fach::isBestanden).mapToInt(Fach::getCredits).sum();
        ectsText.setText(bestanden + " / " + ects);
        Utility.getInstance().getFächer().add(fach);
        modul.isBestanden();
        updateTableFach(modul);
    }

    /**
     *
     */
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
            var ects = modul.getFächer().stream().mapToInt(Fach::getCredits).sum();
            var bestanden = modul.getFächer().stream().filter(Fach::isBestanden).mapToInt(Fach::getCredits).sum();
            ectsText.setText(bestanden + " / " + ects);
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

    /**
     *
     */
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

    /**
     * Open the PrüfungsUI for the selected Fach.
     * If no Fach is selected, display an error message.
     *
     * @param event the event that triggered this method
     * @throws IOException if there is an error loading the PrüfungsUI
     */
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

    /**
     *
     */
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

    /**
     * Updates the tableview for the selected Fach with the corresponding Fach information.
     * Clears the existing items in the tableview and populates it with the Fach items filtered
     * by the given Modul.
     * Also updates the modulNameText with the name of the selected Modul.
     *
     * @param modul The Modul object to filter the Fach items.
     */
    private void updateTableFach(Modul modul) {
        tableviewFach.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        if (modul == null) return;
        tableviewFach.getItems().addAll(new HashSet<>(Utility.getInstance().getFächer().stream().filter(fach -> fach.getModulID() == modul.getID()).toList()));
        modulNameText.setText(modul.getName());

    }


    /**
     * Add a new module to the table view
     *
     * @param event The action event that triggers the method
     */
    @FXML
    void onAddModul(ActionEvent event) {
        //create and add a new modul to the tableview
        var modul = new Modul(Modul.getModulCounter(), "Neues Modul", false, 0);
        Utility.getInstance().getModule().add(modul);
        updateTable();
    }


    /**
     *
     */
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

    /**
     *
     */
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

    /**
     *
     */
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


    /**
     *
     */
    private void updateTable() {
        tableviewModul.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableviewModul.getItems().addAll(Utility.getInstance().getModule());

    }

    /**
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initModulTable();
        initFachTable();
    }

    /**
     *
     */
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
                    checkBox.setSelected(modul.getPraktikaBestanden());
                    checkBox.setOnAction(e -> modul.setPraktikaBestanden(checkBox.isSelected()));
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


    /**
     *
     */
    private void initFachTable() {
        fachnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fachnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fachnameColumn.setOnEditCommit(event -> {
            var fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setName(event.getNewValue());
        });
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        semesterColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        semesterColumn.setOnEditCommit(event -> {
            var fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setSemester(event.getNewValue());
        });
        ectsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        ectsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ectsColumn.setOnEditCommit(event -> {
            var fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setCredits(event.getNewValue());
            var modul = fach.getModul();
            //get all ectsfor that modul
            var ects = modul.getFächer().stream().mapToInt(Fach::getCredits).sum();
            var bestanden = modul.getFächer().stream().filter(Fach::isBestanden).mapToInt(Fach::getCredits).sum();
            ectsText.setText(bestanden + " / " + ects);
        });

        fachBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        fachBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var fach = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(fach.isBestanden());
                    checkBox.setOnAction(e -> fach.setBestanden(checkBox.isSelected()));
                }
            }
        });


    }
}

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
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 *
 */
public class PrüfungsUI implements Initializable {

    /**
     * This class represents an instance of the PrüfungsUI class, which is used to handle the user interface for a specific examination.
     * <p>
     * The instance variable, named "instance", is of type PrüfungsUI and is declared as private and static.
     * It is used to keep track of the single instance of the PrüfungsUI class that should exist.
     * The instance variable is initially set to null and can be accessed through static methods to either get the existing instance or create a new one if it doesn't exist.
     * <p>
     * Only one instance of the PrüfungsUI class should exist at a time, so the use of a singleton pattern is recommended.
     * This ensures that the class can be accessed globally and that a new instance is only created if necessary.
     * <p>
     * Example usage:
     * PrüfungsUI ui = PrüfungsUI.getInstance();
     *
     * @see PrüfungsUI
     */
    private static PrüfungsUI instance = null;
    /**
     * Represents a specific subject or field of study.
     * This variable is private and static, meaning it is accessible only within the class it is defined in
     * and it is shared among all instances of the class.
     * The value of the variable can be set and accessed through its getter and setter methods.
     */
    private static Fach fach;
    /**
     *
     */
    @FXML
    private TableColumn<Prüfung, Integer> numberPruefungColumn;
    /**
     * The ResourceBundle variable is used to store localized resources for the application.
     * It provides a way to access resources, such as strings, images, and other data, that can be localized for different languages or regions.
     * The resources are typically stored in property files where each key-value pair represents a resource.
     * <p>
     * The resources variable is mainly used in JavaFX applications for internationalization purposes.
     * It is automatically injected by the JavaFX FXMLLoader when loading FXML files that contain resource bindings.
     * <p>
     * Example usage:
     * ResourceBundle bundle = ResourceBundle.getBundle("myapp.resources.Bundle", Locale.getDefault());
     * <p>
     * bundle.getString("welcome.message"); // returns the localized string for the "welcome.message" key
     *
     * @since 1.0
     */
    @FXML
    private ResourceBundle resources;

    /**
     * The location variable represents the location of the FXML file associated with the current controller.
     * It is used by FXMLLoader to load the FXML file and set up the controller's UI components.
     * <p>
     * The value of this variable is automatically injected by the JavaFX runtime whenever the FXML file is loaded.
     * <p>
     * This variable is annotated with @FXML to indicate that it is a field that is injected by FXMLLoader
     * as part of the JavaFX scene graph initialization process.
     */
    @FXML
    private URL location;

    /**
     * Represents a button used for navigating back.
     * <p>
     * This button is typically used in graphical user interfaces to allow the user to navigate back to a previous screen or view.
     * It is implemented using JavaFX FXML.
     */
    @FXML
    private Button backButton;

    /**
     * The deleteButton is a JavaFX Button object that represents a button used to delete an item or perform a delete action.
     * <p>
     * This button is typically used in a graphical user interface (GUI) to allow users to delete or remove an item from a list, table, or any other data structure.
     * <p>
     * It is recommended to set an event handler or action listener on this button to define the behavior that should be executed upon clicking or activating the delete button.
     * <p>
     * Example usage:
     * <p>
     * // Create a deleteButton object
     * Button deleteButton = new Button("Delete");
     * <p>
     * // Add an event handler to deleteButton
     * deleteButton.setOnAction(event -> {
     * // Delete the selected item from the list or perform the delete action
     * });
     * <p>
     * Note: This documentation assumes that the deleteButton variable is properly initialized and connected to a Button object in the FXML file or through code.
     *
     * @see Button
     */
    @FXML
    private Button deleteButton;
    /**
     * TableColumn for displaying the "Bestanden" property of a Prüfung object.
     * The property represents whether or not the examination is passed.
     * <p>
     * The column is of type BooleanProperty to display a checkbox in the table cell,
     * indicating whether the examination is passed or not.
     * <p>
     * Usage:
     * pruefungBestandenColumn.setCellValueFactory(cellData -> cellData.getValue().bestandenProperty());
     * <p>
     * Example:
     * Prüfung pruefung = new Prüfung();
     * pruefung.setBestanden(true);
     * BooleanProperty bestandenProperty = pruefung.bestandenProperty();
     * pruefungBestandenColumn.setCellValueFactory(cellData -> bestandenProperty);
     */
    @FXML
    private TableColumn<Prüfung, BooleanProperty> pruefungBestandenColumn;
    /**
     * The addPruefungButton is a JavaFX Button object that represents a button for adding a new Pruefung.
     * <p>
     * Usage:
     * <p>
     * To use the addPruefungButton, you can attach an event handler to it using the setOnAction method.
     * This event handler will be triggered when the button is clicked.
     * <p>
     * Example:
     * <p>
     * // Create a new addPruefungButton
     * Button addPruefungButton = new Button("Add Pruefung");
     * <p>
     * // Attach an event handler to the addPruefungButton
     * addPruefungButton.setOnAction(e -> {
     * // Code to be executed when the button is clicked
     * });
     * <p>
     * Note:
     * <p>
     * This object is defined
     */
    @FXML
    private Button addPruefungButton;
    /**
     * The datumColumn variable represents a TableColumn that is used to display and manipulate dates in a JavaFX TableView.
     *
     * <p>It is specifically designed to work with the Prüfungsversuch objects, where the date column represents the date of the exam attempt.
     * This column is expected to contain Date values.</p>
     *
     * <p>By using this column, you can easily display dates in a TableView and perform various operations on the dates, such as sorting, filtering,
     * and editing.</p>
     *
     * <p>This variable should be initialized using the @FXML annotation, where it is connected to a specific TableColumn in the FXML file.</p>
     *
     * @see Prüfungsversuch
     * @see Date
     * @see TableColumn
     * @see FXML
     */
    @FXML
    private TableColumn<Prüfungsversuch, Date> datumColumn;
    /**
     * The pruefungNameText variable is an instance of the Label class. It is used to display the name of a specific exam.
     * This variable is annotated with the @FXML annotation, indicating that it is associated with an element defined in an FXML file.
     * The Label class is a part of the JavaFX library and is responsible for displaying text or an image that the user can interact with.
     * <p>
     * Usage:
     * pruefungNameText.setText("Name of the exam");
     * <p>
     * Note: This variable should only be accessed after it has been initialized through an FXML loader or a manual instantiation.
     */
    @FXML
    private Label pruefungNameText;

    /**
     * TableColumn for displaying and editing the Prüfungsform field of Prüfung object.
     * <p>
     * This TableColumn is used in a JavaFX FXML file and is associated with the pruefungsFormColumn
     * element in that file. It is used to display the Prüfungsform field in a TableView and supports
     * editing of the field.
     * <p>
     * The TableColumn is of type Prüfungsform and the TableView it is associated with contains
     * items of type Prüfung.
     * <p>
     * Usage example:
     * <pre>{@code
     * TableView<Prüfung> tableView = new TableView<>();
     * TableColumn<Prüfung, Prüfungsform> pruefungsFormColumn = new TableColumn<>("Prüfungsform");
     * pruefungsFormColumn.setCellValueFactory(new PropertyValueFactory<>("prüfungsform"));
     * tableView.getColumns().add(pruefungsFormColumn);
     * }</pre>
     */
    @FXML
    private TableColumn<Prüfung, Prüfungsform> pruefungsFormColumn;
    /**
     * The noteColumn variable represents a TableColumn used in an FXML file.
     * It represents the column in a table that displays the "note" data of type Float.
     * <p>
     * The TableColumn class is a part of JavaFX and is used to define columns in a TableView.
     * In this case, the noteColumn is used in conjunction with the Prüfungsversuch class.
     * <p>
     * The generic type <Prüfungsversuch, Float> indicates that the column will display Float values,
     * specifically from the Prüfungsversuch objects.
     * <p>
     * This variable is annotated with @FXML, which means that it is injected from an FXML file.
     * This allows the variable to be referenced and manipulated in the corresponding controller class.
     * <p>
     * Note: This documentation does not include example code or author and version tags, as requested.
     */
    @FXML
    private TableColumn<Prüfungsversuch, Float> noteColumn;
    /**
     * The numberVersuchColumn variable represents a JavaFX TableColumn that is used to display the number of an exam attempt in a graphical user interface.
     *
     * <p>This variable is annotated with @FXML to indicate that it is a reference to a JavaFX element defined in an XML file using FXML notation.</p>
     *
     * <p>The TableColumn is parametrized with <Prüfungsversuch, Integer>, where Prüfungsversuch is a custom class representing an exam attempt and Integer is the data type of the number property in Prüfungsversuch class.</p>
     *
     * <p>This column is typically used in a TableView to display the number of each exam attempt in a specific column. It can be customized to handle sorting, filtering, and rendering of the data.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * TableColumn<Prüfungsversuch, Integer> numberVersuchColumn = new TableColumn<>("Number");
     * numberVersuchColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
     * }</pre>
     */
    @FXML
    private TableColumn<Prüfungsversuch, Integer> numberVersuchColumn;
    /**
     * TableColumn representing the "Bestanden" property of the Prüfungsversuch object.
     * The value of this property indicates whether the Prüfungsversuch was passed or not.
     * This column is used in a TableView to display and edit the "Bestanden" property.
     *
     * @param <Prüfungsversuch> the type of the Prüfungsversuch object
     * @param <BooleanProperty> the type of the "Bestanden" property, which is a BooleanProperty
     */
    @FXML
    private TableColumn<Prüfungsversuch, BooleanProperty> pruefungVersuchBestandenColumn;
    /**
     * The TableView variable for displaying Prüfungsversuch objects.
     */
    @FXML
    private TableView<Prüfungsversuch> tableviewVersuch;
    /**
     * This variable represents the table column used to display the time of each examination attempt.
     * The data type of the column is Time.
     * It is annotated with @FXML to indicate that it is associated with a graphical component defined in an FXML file.
     * This variable is a private member of the class and can only be accessed within the class it is defined in.
     * <p>
     * Example usage:
     * uhrzeitColumn.setCellValueFactory(new PropertyValueFactory<>("uhrzeit"));
     * uhrzeitColumn.setCellFactory(column -> {
     * TableCell<Prüfungsversuch, Time> cell = new TableCell<Prüfungsversuch, Time>() {
     *
     * @Override protected void updateItem(Time item, boolean empty) {
     * super.updateItem(item, empty);
     * if (item == null || empty) {
     * setText(null);
     * } else {
     * setText(item.toString());
     * }
     * }
     * };
     * return cell;
     * });
     */
    @FXML
    private TableColumn<Prüfungsversuch, Time> uhrzeitColumn;
    /**
     * TableView representing a list of Prüfung objects.
     * <p>
     * The TableView tableviewPruefung is an FXML element used to display a list of Prüfung objects in a tabular format.
     * It provides various features such as sorting, filtering, and selection of rows.
     * <p>
     * The type parameter <Prüfung> specifies the type of the items in the TableView, which must be a subclass of Prüfung.
     * <p>
     * Example usage:
     * ```
     * TableView<Prüfung> tableView = new TableView<>();
     * tableView.setItems(pruefungList);
     * ```
     *
     * @see Prüfung
     */
    @FXML
    private TableView<Prüfung> tableviewPruefung;
    /**
     * Represents the "Save" button in the graphical user interface.
     * The button is used to trigger the save action and save the current state or changes made in the application.
     * In the corresponding FXML file, this button is defined with the id "saveButton" and associated with this variable.
     * <p>
     * Usage:
     * - To set an event handler for the save button, use the setOnAction() method.
     * - To retrieve the current text or label of the save button, use the getText() method.
     * - To change the text or label of the save button, use the setText() method.
     * - To disable or enable the save button, use the setDisable() method.
     * <p>
     * Example:
     * Button saveButton = new Button();
     * saveButton.setId("saveButton");
     * saveButton.setOnAction(e -> {
     * // Perform save operation
     * });
     * saveButton.setText("Save");
     * saveButton.setDisable(false);
     */
    @FXML
    private Button saveButton;
    /**
     * The addVersuchButton variable represents the button used to add a new Versuch (experiment).
     * <p>
     * It is an FXML variable, which means that it is automatically injected by the JavaFX framework
     * when the corresponding FXML file is loaded.
     * <p>
     * This button is typically used in a graphical user interface to allow the user to initiate
     * the process of adding a new Versuch.
     * <p>
     * To add an event handler to this button, use the setOnAction() method and provide the desired
     * code or method reference as the argument. The event handler will be triggered when the button
     * is clicked.
     * <p>
     * Example usage:
     * <p>
     * addVersuchButton.setOnAction(e -> {
     * // Code for adding a new Versuch goes here
     * });
     * <p>
     * Note: This documentation does not include example code.
     */
    @FXML
    private Button addVersuchButton;
    /**
     * The deletePruefungButton variable represents the button used to delete a Pruefung object.
     * This variable is annotated with @FXML to indicate that it is a reference to a UI element defined in an FXML file.
     * The UI element associated with this variable can be accessed and manipulated using this variable in the corresponding controller class.
     * <p>
     * This variable should be initialized in the FXML file by assigning it an ID and connecting it to a button UI element.
     */
    @FXML
    private Button deletePruefungButton;
    /**
     * The deleteVersuchButton variable is a JavaFX Button element that is used to trigger the deletion of a specific attempt in a system.
     * It is annotated with @FXML to indicate that it is a reference to an element defined in the associated FXML file.
     * <p>
     * This button can be used by attaching an event handler to its action property and defining the necessary logic to handle the delete attempt action.
     * <p>
     * Example usage:
     * <p>
     * // Assigning the button reference to a variable
     * Button deleteVersuchButton = new Button();
     * <p>
     * // Attaching an event handler to the button
     * deleteVersuchButton.setOnAction(event -> {
     * // Code logic for deleting the attempt
     * // ...
     * });
     * <p>
     * The deleteVersuchButton should be initialized and attached to a parent container before interacting with it.
     */
    @FXML
    private Button deleteVersuchButton;
    /**
     * Represents a JavaFX label for displaying a subject name.
     */
    @FXML
    private Label fachNameText;
    /**
     * This variable represents a JavaFX label used to display the text of an examination form.
     * <p>
     * The label is annotated with the @FXML annotation, indicating that it is defined in the associated FXML file and
     * will be automatically injected by the FXMLLoader. The label can be accessed and modified through this variable
     * within the associated controller class.
     */
    @FXML
    private Label pruefungsFormText;

    /**
     * Gets the instance of the PrüfungsUI class.
     *
     * @return the instance of the PrüfungsUI class
     */ //Getter for the instance
    public static PrüfungsUI getInstance() {
        if (instance == null)
            instance = new PrüfungsUI();
        return instance;
    }

    /**
     * Returns the current Fach object.
     *
     * @return The Fach object.
     */
    public static Fach getFach() {
        return fach;
    }

    /**
     * Sets the current Fach object that is selected in the PrüfungsUI.
     *
     * @param fach the Fach object to be set as the current selected Fach
     */
    public static void setFach(Fach fach) {
        PrüfungsUI.fach = fach;
    }

    /**
     * Handles the event when the "Add Versuch" button is clicked in the PrüfungsUI.
     * Retrieves the currently selected Prüfung from the table view and adds a new Prüfungsversuch to it.
     * Updates the table view of Prüfungsversuche for the selected Prüfung.
     * Shows an error message if no Prüfung is selected.
     *
     * @param event the ActionEvent triggered by the "Add Versuch" button
     */
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
        var versuch = new Prüfungsversuch(Prüfungsversuch.getPrüfungsversuchCounter(), date, time, false, 5.0F, prüfung.getID(), 0);
        Utility.getInstance().getPrüfungsversuche().add(versuch);
        prüfung.isBestanden();
        updateTableVersuch(prüfung);
    }

    /**
     * Deletes a Prüfungsversuch from the tableview and data model.
     * If a Prüfungsversuch is selected in the tableview, it will be removed from the data model.
     * If a corresponding Prüfung is selected, the tableview for the Prüfungsversuche of that Prüfung will be updated.
     * If it is not a dummy launch, the Prüfungsversuch will also be deleted from the database.
     * If no Prüfungsversuch is selected, an error message will be displayed.
     */
    @FXML
    void onDeleteVersuch() {
        var versuch = tableviewVersuch.getSelectionModel().getSelectedItem();
        if (versuch != null) {
            Utility.getInstance().getPrüfungsversuche().remove(versuch);
            //remove from tabeview
            var prüfung = tableviewPruefung.getSelectionModel().getSelectedItem();
            if (prüfung != null) {
                prüfung.isBestanden();
                updateTableVersuch(prüfung);
            }
            if (!Main.isDummyLaunch()) {
                Database.getInstance().deleteElement(versuch);
            }

        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Prüfungsversuch ausgewählt");
            alert.setContentText("Bitte wählen Sie einen Prüfungsversuch aus");
            alert.showAndWait();
        }
    }

    /**
     * Adds a new Prüfung to the list of Prüfungen.
     * Creates a new Prüfung object with a unique ID, Prüfungsform KLAUSUR, and the ID of the current Fach.
     * Adds the new Prüfung object to the list of Prüfungen.
     * Checks if the current Fach is passed (isBestanden) and updates the Prüfung table accordingly.
     *
     * @param event The ActionEvent that triggers the method.
     */
    @FXML
    void onAddPruefung(ActionEvent event) {
        var prüfung = new Prüfung(Prüfung.getPrüfungCounter(), Prüfungsform.KLAUSUR, fach.getID(), false);
        Utility.getInstance().getPrüfungen().add(prüfung);
        fach.isBestanden();
        updateTablePruefung();
    }

    /**
     * Handles the action event when the user clicks on the "Back" button.
     * This method closes the current window and opens the main UI window.
     *
     * @param event the action event triggered when the "Back" button is clicked
     * @throws IOException if an I/O error occurs while opening the main UI window
     */
    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        MainUI.getInstance().start(stage);
    }

    /**
     * Handles the action event when the user clicks on the "Delete Pruefung" button.
     * This method deletes the selected Pruefung from the table and corresponding Prüfungsversuche from the database.
     * If the application is not in dummy mode, it updates the database and table accordingly.
     */
    @FXML
    void onDeletePruefung() {
        var prüfung = tableviewPruefung.getSelectionModel().getSelectedItem();
        if (prüfung != null) {
            //delete all corresponding Prüfungsversuche
            var versuche = Utility.getInstance().getPrüfungsversuche().stream().filter(versuch -> versuch.getPrüfungsID() == prüfung.getID()).toList();
            versuche.forEach(Utility.getInstance().getPrüfungsversuche()::remove);
            if (!Main.isDummyLaunch()) {
                versuche.forEach(Database.getInstance()::deleteElement);
                Database.getInstance().deleteElement(prüfung);
                Utility.getInstance().getPrüfungen().remove(prüfung);
                fach.isBestanden();
                updateTablePruefung();
            }
            Utility.getInstance().getPrüfungen().remove(prüfung);
            fach.isBestanden();
            updateTablePruefung();
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Keine Prüfung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Prüfung aus");
            alert.showAndWait();
        }
    }

    /**
     * Saves the data either to the console or to the database, depending on the launch mode.
     * If the launch mode is a dummy launch, it will print all modules to the console.
     * If the launch mode is not dummy, it will save all data to the database.
     */
    @FXML
    void onSave() {
        if (Main.isDummyLaunch()) {
            Utility.getInstance().getModule().forEach(modul -> System.out.println(modul.toString()));

        } else {
            Database.getInstance().saveAllData();
        }
    }

    /**
     * This method is called to initialize the controller after its
     * root element has been completely processed.
     * It is responsible for initializing the injected FXML objects.
     */
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

    /**
     * Starts the application and sets up the primary stage.
     *
     * @param primaryStage the primary stage of the application
     * @throws IOException if there is an error loading the FXML file
     */
    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PrüfungsUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    /**
     * Update the table view for the given Prüfung.
     *
     * @param prüfung The Prüfung object to update the table view for.
     */
    private void updateTableVersuch(Prüfung prüfung) {
        tableviewVersuch.getItems().clear();
        if (prüfung == null) return;
        //get prüfungen based on ModulID -> FachID -> Prüfungen
        tableviewVersuch.getItems().addAll(new HashSet<>(Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungsversuch -> prüfungsversuch.getPrüfungsID() == prüfung.getID()).toList())
        );
        //update the tableview checkboxes for the praktika and the prüfung
        pruefungNameText.setText(prüfung.getFach().getName());
        pruefungsFormText.setText(prüfung.getPrüfungsform().getText());

        tableviewPruefung.refresh();
        tableviewVersuch.refresh();
    }

    /**
     * Updates the tableview with the list of Prüfungen.
     * <p>
     * The method clears the items in the tableview and then adds the Prüfungen
     * that belong to the specified Fach. It also sets the Fach name in the
     * corresponding text field. Finally, it refreshes the tableview and the
     * versuch tableview.
     */
    private void updateTablePruefung() {
        tableviewPruefung.getItems().clear();
        tableviewPruefung.getItems().addAll(new HashSet<>(Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getFachID() == fach.getID()).toList()));
        fachNameText.setText(fach.getName());
        tableviewPruefung.refresh();
        tableviewVersuch.refresh();

    }

    /**
     * Initializes the table for displaying exams related to a specific subject.
     * Sets up the cell value factories and cell factories for each column in the table.
     * Binds the selected item property of the table to update the table for displaying attempts related to the selected exam.
     */
    private void initPruefungsTable() {
        numberPruefungColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        pruefungBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        pruefungBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var prüfung = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(prüfung.isBestanden());
                    checkBox.setOnAction(e -> prüfung.setBestanden(checkBox.isSelected()));
                }
            }
        });
        pruefungsFormColumn.setCellValueFactory(pruefung -> new SimpleObjectProperty<>(pruefung.getValue().getPrüfungsform()));
        pruefungsFormColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Prüfungsform.values()));
        pruefungsFormColumn.setOnEditCommit(event -> {
            var pruefung = event.getRowValue();
            pruefung.setPrüfungsform(event.getNewValue());
        });


        tableviewPruefung.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateTableVersuch(newSelection));

        updateTablePruefung();
    }

    /**
     * Initializes the table for displaying attempts related to a specific exam.
     * Sets up the cell value factories and cell factories for each column in the table.
     * Binds the selected item property of the table to update the table for displaying attempts related to the selected exam.
     * Also configures edit operations for note, datum, and uhrzeit columns.
     */
    private void initVersucheTable() {
        numberVersuchColumn.setCellValueFactory(new PropertyValueFactory<>("versuchsnummer"));
        numberVersuchColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberVersuchColumn.setOnEditCommit(event -> {
            var versuch = event.getRowValue();
            versuch.setVersuchsnummer(event.getNewValue());
        });
        pruefungVersuchBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        pruefungVersuchBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var prüfung = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(prüfung.isBestanden());
                    checkBox.setOnAction(e -> prüfung.setBestanden(checkBox.isSelected()));
                }
            }
        });

        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        noteColumn.setOnEditCommit(event -> {
            var versuch = event.getRowValue();
            versuch.setNote(event.getNewValue());
            //get current prüfungsversuch
            var prüfung = tableviewPruefung.getSelectionModel().getSelectedItem();
            versuch.isBestanden();
            if (prüfung != null) {
                prüfung.isBestanden();
                updateTableVersuch(prüfung);
            }
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

    /**
     * Initializes the controller.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initPruefungsTable();
        initVersucheTable();
    }
}

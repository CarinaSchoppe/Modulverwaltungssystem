package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.Main;
import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Studiengang;
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
     * The addButtonFach variable represents a JavaFX Button object that is used to add a new Fach (subject) to the application.
     *
     * When this button is clicked, it triggers an action that allows the user to add a new Fach to the application.
     *
     * To use this variable, make sure to initialize it in the JavaFX controller class and connect it to the corresponding button in the UI.
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
     * The deleteButtonFach variable represents the delete button for a specific Fach (subject).
     * This button can be used to delete the selected Fach from a list or perform any other related actions.
     *
     * Note: This is an FXML variable, which means it is associated with a UI element defined in an FXML file.
     *       It should be injected using the @FXML annotation before accessing it.
     *       For example: @FXML private Button deleteButtonFach;
     *
     * Usage:
     *  - Assign an event handler to this button to define what should happen when it is clicked.
     *  - Update the button's label or icon to provide meaningful user interaction.
     *  - Use this button's properties (e.g., disabled, visible) to control its behavior in the UI.
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
     * TableColumn representing the "Bestanden" field of a "Fach" object.
     *
     * The "Bestanden" field is represented as a BooleanProperty, indicating whether the "Fach" has been passed or not.
     * This TableColumn is used in JavaFX for displaying and editing the "Bestanden" field of "Fach" objects in a TableView.
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
     * The modulBestandenColumn is a private TableColumn object used in an FXML file. 
     * It is used to display a BooleanProperty of a Modul object in a table view.
     *
     * The modulBestandenColumn is not intended to be accessed directly from outside the class 
     * where it is defined. It is only used within the FXML file for displaying the data in the 
     * table view.
     *
     * The column displays the BooleanProperty value as a checkbox in the table view. The checkbox 
     * represents whether the Modul object is marked as "bestanden" (passed) or not.
     *
     * To define the modulBestandenColumn in FXML, use the following syntax:
     *
     *   <TableColumn fx:id="modulBestandenColumn" prefWidth="100">
     *       <cellValueFactory>
     *           <PropertyValueFactory property="bestanden" />
     *       </cellValueFactory>
     *       <cellFactory>
     *           <CheckBoxTableCell fx:converter="com.example.BooleanPropertyStringConverter" />
     *       </cellFactory>
     *   </TableColumn>
     *
     * Note that the fx:id attribute must be set to "modulBestandenColumn" to link it with the 
     * corresponding variable in the controller class.
     */
    @FXML
    private TableColumn<Modul, BooleanProperty> modulBestandenColumn;
    /**
     * The modulNameColumn variable is a TableColumn that is used to display the name of a module.
     * It is defined with a generic type of Modul, which represents the data type of the cells in the column.
     * The cells in this column will display string values.
     *
     * This variable is annotated with @FXML, indicating that it is associated with a JavaFX FXML file.
     * It should be set to private to encapsulate the internal state of the class.
     *
     * Example Usage:
     *
     * // Create a new TableColumn
     * TableColumn<Modul, String> modulNameColumn = new TableColumn<>("Module Name");
     *
     * // Set the cell value factory to display the name of the module
     * modulNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
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
     * TableColumn representing whether a praktika has been completed or not for a Modul.
     * The TableColumn is associated with a BooleanProperty that indicates if the praktika has been completed or not.
     */
    @FXML
    private TableColumn<Modul, BooleanProperty> praktikaBestandenColumn;
    /**
     * Represents a button used for starting or ending the "Praktikum" activity.
     */
    @FXML
    private Button praktikumButton;
    /**
     * The TableColumn representing the "Pruefung Bestanden" column in a TableView of Modul objects.
     *
     * This column displays whether the Pruefung (exam) for a Modul has been passed or not. The column uses a
     * BooleanProperty to represent the state of this property for each Modul.
     *
     * This TableColumn is part of a JavaFX FXML file and is annotated with @FXML to be accessible in the
     * associated controller class.
     *
     * @see Modul
     * @see TableColumn
     * @see BooleanProperty
     */
    @FXML
    private TableColumn<Modul, BooleanProperty> pruefungBestandenColumn;
    /**
     * The pruefungButton variable represents a button used for initiating an examination or test.
     * It is part of a graphical user interface (GUI).
     *
     * This variable is annotated with @FXML, indicating that it is associated with a specific
     * element in a JavaFX FXML file.
     *
     * To use this variable, first, it needs to be initialized properly by loading the FXML file
     * where it is defined and injecting it into the corresponding controller. Then, it can be
     * accessed using the appropriate getter or setter methods provided by the GUI framework
     * being used.
     *
     * Example usage:
     *
     * // Load the FXML file and initialize the controller
     * FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
     * Parent root = loader.load();
     * GuiController controller = loader.getController();
     *
     * // Access the pruefungButton and set its text
     * Button pruefungButton = controller.getPruefungButton();
     * pruefungButton.setText("Start Examination");
     *
     * // Attach an event handler to the pruefungButton
     * pruefungButton.setOnAction(event -> {
     *     // Code to handle button click event
     *     // ...
     * });
     *
     * Note: The above example assumes that the FXML file defines a button with the id "pruefungButton".
     * The actual FXML file contents may vary depending on the specific UI design and layout.
     */
    @FXML
    private Button pruefungButton;
    /**
     * The saveButton is a JavaFX Button object used for saving data or triggering a save operation.
     * This variable is annotated with @FXML to indicate that it is defined in the FXML file and
     * injected by the JavaFX framework.
     *
     * Use the saveButton to set an event handler or perform any necessary actions when the button
     * is clicked. This can include saving form data, updating database records, or triggering any
     * other save-related functionality in your application.
     *
     * Example usage:
     *     saveButton.setOnAction(event -> {
     *         // Perform save operation
     *         saveData();
     *     });
     *
     *     private void saveData() {
     *         // Implement save logic here
     *     }
     */
    @FXML
    private Button saveButton;
    /**
     * The semesterColumn variable represents a table column for displaying the semester of a Fach object. 
     * It is an instance of the TableColumn class with a generic type of Integer.
     *
     * This variable is annotated with @FXML, indicating that it is used in conjunction with JavaFX's FXML markup language
     * for defining user interfaces. It is typically associated with a column element in an FXML file.
     *
     * This TableColumn is used to display the semester value of Fach objects in a TableView. The column will
     * render the Integer value as text in the table cells.
     *
     * Example usage:
     *
     * // Create the TableColumn
     * TableColumn<Fach, Integer> semesterColumn = new TableColumn<>("Semester");
     *
     * // Set the cell value factory to retrieve the semester property from the Fach object
     * semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
     *
     * // Add the column to a TableView
     * tableView.getColumns().add(semesterColumn);
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
     * The studiengangText represents a JavaFX Label element used to display the name of a study program.
     * It is annotated with @FXML to indicate that it is an element of the user interface defined in an FXML file.
     *
     * This variable is private to ensure encapsulation and should only be accessed and modified by the associated controller class.
     *
     * Usage Example:
     *
     * In the associated controller class, you can access and modify the text of the studiengangText label as follows:
     *
     * // Accessing the label
     * Label label = studiengangText;
     *
     * // Setting the text of the label
     * label.setText("Computer Science");
     *
     * // Retrieving the text of the label
     * String labelText = label.getText();
     *
     * Note: The actual behavior of the studiengangText label may depend on the specific implementation in the associated FXML file and controller class.
     *
     * @see Label
     * @see FXML
     */
    @FXML
    private Label studiengangText;
    /**
     * The tableviewModul variable represents a JavaFX TableView control that is used to display and interact with a collection of Modul objects.
     * It is annotated with @FXML to indicate that it is injected from the FXML file.
     *
     * The TableView is a UI control used to display tabular data in a flexible and customizable way.
     * In this case, the tableviewModul variable is specifically designed to display Modul objects, which may contain different properties or attributes that can be shown in columns of the table.
     *
     * Modul is the generic type parameter provided to the TableView, indicating that this tableview will display and work with Modul objects.
     * The TableView will automatically extract the necessary data from the Modul objects using JavaFX property accessors like get methods or JavaFX properties.
     *
     * This variable allows accessing and manipulating the TableView and its associated data programmatically. It can be used to add or remove Modul objects from the table, modify their data, control the table's appearance and behavior, and register event handlers to respond to user actions or selection events.
     *
     * Note that this documentation does not include example code or use any @author and @version tags as specified in the requirements.
     *
     * @see TableView
     * @see Modul
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
     * Handles the event when a user wants to delete a Fach from a selected Modul.
     *
     * @param event The action event that triggered this method.
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
            praktika.forEach(Utility.getInstance().getPraktika()::remove);
            prüfungen.forEach(Utility.getInstance().getPrüfungen()::remove);
            prüfungsversuche.forEach(Utility.getInstance().getPrüfungsversuche()::remove);
            praktikumstermine.forEach(Utility.getInstance().getPraktikumstermine()::remove);
            Utility.getInstance().getFächer().remove(selectedFach);
            var modul = tableviewModul.getSelectionModel().getSelectedItem();
            if (modul == null) return;
            var ects = modul.getFächer().stream().mapToInt(Fach::getCredits).sum();
            var bestanden = modul.getFächer().stream().filter(Fach::isBestanden).mapToInt(Fach::getCredits).sum();
            ectsText.setText(bestanden + " / " + ects);
            modul.isBestanden();
            if (!Main.isDummyLaunch()) {
                Database.getInstance().deleteElement(selectedFach);
                praktika.forEach(Database.getInstance()::deleteElement);
                prüfungen.forEach(Database.getInstance()::deleteElement);
                prüfungsversuche.forEach(Database.getInstance()::deleteElement);
                praktikumstermine.forEach(Database.getInstance()::deleteElement);
            }
            updateTableFach(modul);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Fach ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Fach aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }

    /**
     * Opens the Praktikum UI for the selected Fach.
     *
     * @param event the ActionEvent that triggered the method
     * @throws IOException if an I/O error occurs while starting the Praktikum UI
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
     * Save all data to the database.
     * If the application is in dummy launch mode, print the modules to the console.
     * Otherwise, save all data to the database and display a success message.
     *
     * @param event the event that triggered this method
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
        tableviewModul.refresh();
        tableviewFach.refresh();
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
     * Delete the selected module from the table view
     *
     * @param event The action event that triggers the method
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

            if (!Main.isDummyLaunch()) {
                Database.getInstance().deleteElement(selectedModul);
                fächer.forEach(Database.getInstance()::deleteElement);
                praktika.forEach(Database.getInstance()::deleteElement);
                prüfungen.forEach(Database.getInstance()::deleteElement);
                prüfungsversuche.forEach(Database.getInstance()::deleteElement);
                praktikumstermine.forEach(Database.getInstance()::deleteElement);
            }
            Utility.getInstance().getFächer().removeAll(fächer);
            Utility.getInstance().getPraktika().removeAll(praktika);
            Utility.getInstance().getPrüfungen().removeAll(prüfungen);
            Utility.getInstance().getPrüfungsversuche().removeAll(prüfungsversuche);
            Utility.getInstance().getPraktikumstermine().removeAll(praktikumstermine);
            Utility.getInstance().getModule().remove(selectedModul);
            updateTable();
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Modul ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Modul aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller.
     *
     * This method is automatically called during the loading of the FXML file MainUI.fxml.
     * It verifies that all @FXML annotated fields are properly injected and throws an AssertionError if any of them is null.
     *
     * @throws AssertionError if any of the @FXML annotated fields is null
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
     * Starts the application by loading the main UI from the FXML file,
     * setting up the stage, and displaying the UI.
     * @param primaryStage the primary stage for the application
     * @throws IOException if an error occurs while loading the FXML file
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

        //starte eine popup mit texteingabe und einem okay feld 
        //wenn okay gedrückt wird, wird ein neues modul erstellt und der name wird auf den text gesetzt
        //wenn abbrechen gedrückt wird, wird nichts gemacht

        var alert = new TextInputDialog();
        alert.setTitle("Neuer Studiengang");
        alert.setHeaderText("Bitte geben Sie den Namen des Studiengangs ein.");
        alert.setContentText("Name:");
        var result = alert.showAndWait();

        if (result.isPresent()) {
            //erstelle einen neuen studiengang und speichere diesen in der datenbank
            var studiengang = new Studiengang(Studiengang.getStudiengangCounter(), result.get());
            Database.getInstance().saveAllData();

        }
    }


    /**
     * Clears the items in the table view and updates it with the latest module data.
     * Also refreshes the table views for praktika and prüfung checkboxes.
     */
    private void updateTable() {
        tableviewModul.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableviewModul.getItems().addAll(Utility.getInstance().getModule());
        tableviewModul.refresh();
        tableviewFach.refresh();
    }

    /**
     * The initialize method is called during the view's initialization process.
     * It initializes the view by setting up the module and subject tables.
     *
     * @param location   The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initModulTable();
        initFachTable();
    }

    /**
     * Initializes the modul table by setting the cell factories and event handlers.
     * The modulNameColumn is set to a TextFieldTableCell, and its OnEditCommit event
     * updates the name property of the modul.
     * The pruefungBestandenColumn, praktikaBestandenColumn, and modulBestandenColumn
     * are set to CheckBoxTableCells, and their updateItem methods set the selected
     * state of the checkbox based on the corresponding modul property. The OnAction
     * event of the checkboxes updates the corresponding modul property when changed.
     * The tableviewModul's selectedItemProperty is set to a listener that invokes
     * the updateTableFach method with the new selected modul as a parameter.
     * Finally, the method calls the updateTable method.
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
     * Initializes the table for displaying subjects.
     * Sets cell factories for each column, handles cell editing and updates the table when a selection is made.
     * Also handles the "bestanden" checkbox for each subject.
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
            if (event.getNewValue() < 0) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Ungültige ECTS");
                alert.setContentText("Bitte geben Sie eine positive Zahl ein.");
                alert.showAndWait();
                return;
            }
            var fach = event.getTableView().getItems().get(event.getTablePosition().getRow());
            fach.setCredits(event.getNewValue());
            var modul = fach.getModul();
            //get all ectsfor that modul
            var module = Utility.getInstance().getModule();
            //get ects of all module and all fächer
            var ects = module.stream().mapToInt(modul1 -> modul1.getFächer().stream().mapToInt(Fach::getCredits).sum()).sum();
            //get ects of all module and all fächer that are bestanden
            var bestanden = module.stream().mapToInt(modul1 -> modul1.getFächer().stream().filter(Fach::isBestanden).mapToInt(Fach::getCredits).sum()).sum();

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

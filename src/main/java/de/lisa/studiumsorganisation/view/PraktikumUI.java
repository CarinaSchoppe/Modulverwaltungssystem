package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.Main;
import de.lisa.studiumsorganisation.controller.Database;
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
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 *
 */
public class PraktikumUI implements Initializable {


    /**
     * PraktikumUI is a singleton class representing a single instance of the PraktikumUI user interface.
     * This class provides a static variable instance which can be accessed globally to access the single instance.
     * Access to the instance is restricted to only the methods within the class.
     *
     * Example usage:
     * PraktikumUI ui = PraktikumUI.getInstance();
     * // Use the ui instance to interact with the user interface
     *
     * Note: This is a private class variable and should not be accessed directly from outside the class.
     * To access the instance, use the getInstance() method instead.
     */
    private static PraktikumUI instance = null;
    /**
     * The Fach class represents a specific subject or discipline.
     * It is a private static variable that holds an instance of the Fach class.
     */
    private static Fach fach;
    /**
     * The praktikumBestandenColumn variable represents a TableColumn used to display the "Bestanden" property of Praktikum objects.
     * The column displays a checkbox for each row, indicating whether the Praktikum is passed or not.
     *
     * The TableColumn is parameterized with the Praktikum class and a BooleanProperty, which allows dynamic updating of the checkbox state.
     *
     * This variable is annotated with @FXML to indicate that it is a JavaFX element that can be injected by the FXML loader.
     *
     * Example usage:
     *
     * // Create a new TableColumn instance
     * TableColumn<Praktikum, BooleanProperty> praktikumBestandenColumn = new TableColumn<>("Bestanden");
     *
     * // Set the cell factory to display a checkbox
     * praktikumBestandenColumn.setCellFactory(CheckBoxTableCell.<Praktikum>forTableColumn(praktikumBestandenColumn));
     *
     * // Set the column value factory to bind to the "bestanden" property of Praktikum objects
     * praktikumBestandenColumn.setCellValueFactory(param -> param.getValue().bestandenProperty());
     */
    @FXML
    private TableColumn<Praktikum, BooleanProperty> praktikumBestandenColumn;
    /**
     * Button variable for adding functionality.
     */
    @FXML
    private Button addButton;
    /**
     * The backButton variable represents a button that allows the user to navigate back to the previous screen or page.
     *
     * Usage:
     * backButton.setOnAction(event -> {
     *     // Do something when the backButton is clicked
     * });
     *
     * Properties:
     * - text: The text to be displayed on the button
     * - graphic: The graphic (an image or icon) to be displayed on the button
     * - disabled: Specifies whether the button is disabled or not
     * - visible: Specifies whether the button is visible or not
     *
     * Example:
     * FXMLLoader loader = new FXMLLoader(getClass().getResource("MyScene.fxml"));
     * Parent root = loader.load();
     *
     * Button backButton = (Button) loader.getNamespace().get("backButton");
     * backButton.setText("Back");
     * backButton.setGraphic(new ImageView("back.png"));
     * backButton.setDisable(true);
     * backButton.setVisible(false);
     *
     * Scene scene = new Scene(root);
     * stage.setScene(scene);
     * stage.show();
     *
     * Note: This Javadoc assumes you are using JavaFX and FXML for your user interface development.*/
    @FXML
    private Button backButton;
    /**
     * The deleteButton variable represents a button element in the user interface.
     * It is used to trigger the deletion of an item or perform a delete action.
     *
     * This variable is annotated with @FXML to indicate that it is a reference to an
     * element in the FXML file that has been injected using JavaFX's FXMLLoader.
     *
     * Example usage:
     *
     *     // Assign an event handler to the deleteButton
     *     deleteButton.setOnAction(event -> {
     *         // Perform delete action
     *     });
     *
     *     // Disable the deleteButton
     *     deleteButton.setDisable(true);
     *
     *     // Get the text of the deleteButton
     *     String buttonText = deleteButton.getText();
     *
     * Note:
     * This documentation does not include the example code according to the instructions given.
     */
    @FXML
    private Button deleteButton;


    /**
     * The ResourceBundle containing the resources for this controller.
     * This variable is used to retrieve localized strings, images, and other resources.
     */
    @FXML
    private ResourceBundle resources;

    /**
     * The location of the resource used for loading the FXML file.
     */
    @FXML
    private URL location;
    /**
     * The fachNameText variable is a JavaFX Label used to display the name of a subject.
     */
    @FXML
    private Label fachNameText;
    /**
     * The versuchButton is a JavaFX Button used for performing a specific action.
     * It is defined as a private instance variable using the @FXML annotation, which allows it to be
     * accessed by the FXML file that defines the user interface.
     *
     * The versuchButton can be used to trigger an action when it is clicked by the user. This can be
     * done by registering an event handler to handle the click event of the button.
     *
     * For example:
     *
     * versuchButton.setOnAction(event -> {
     *     // perform some action when the button is clicked
     * });
     *
     * Note: This documentation does not contain example code as per the request.
     */
    @FXML
    private Button versuchButton;
    /**
     * The addButtonPraktikum variable is a reference to the button used for adding a new praktikum.
     * It is annotated with @FXML to indicate that it is used as a control in the user interface defined by the FXML file.
     * This variable can be accessed and manipulated within the corresponding controller class.
     *
     * Note: The example code is not included in this documentation.
     */
    @FXML
    private Button addButtonPraktikum;
    /**
     * The save button in the user interface.
     */
    @FXML
    private Button saveButton;
    /**
     * Represents the "Add" button used for adding a new termin.
     */
    @FXML
    private Button addButtonTermin;
    /**
     * The deleteButtonPraktikum variable is an instance of the Button class and is used to delete a specific item or perform a delete operation in a specific scenario.
     *
     * This variable is annotated with the @FXML tag to indicate that it is used in conjunction with JavaFX's FXML framework for defining graphical user interfaces.
     *
     * This variable is private, which means it can only be accessed within the scope of the class in which it is declared.
     *
     * Usage example:
     * deleteButtonPraktikum.setOnAction(event -> {
     *     // Perform delete operation here
     * });
     */
    @FXML
    private Button deleteButtonPraktikum;
    /**
     * The deleteButtonTermin variable is an instance of the Button class and is used in the JavaFX 
     * scene when deleting a termin.
     *
     * <p>It is annotated with the @FXML annotation to mark it as a field that is injected by
     * the JavaFX FXMLLoader. This means that the variable is automatically initialized with
     * a reference to the Button object defined in the corresponding FXML file.
     *
     * <p>This variable holds the reference to the delete button which is used to delete a termin from
     * the application. The button is typically bound to an event handler or an action to perform the
     * deletion logic.
     *
     * <p>Example Usage:
     *
     * <pre>{@code
     * deleteButtonTermin.setOnAction(e -> {
     *     // Perform deletion logic here
     * });
     * }</pre>
     *
     * @see Button
     */
    @FXML
    private Button deleteButtonTermin;
    /**
     * The TableColumn representing the number of a Praktikum.
     * It is used to display and interact with the number information of a Praktikum object.
     *
     * The cell values are of type Integer, which represents the unique number of a Praktikum.
     *
     * This TableColumn is part of a JavaFX FXML file and is annotated with @FXML for automatic injection.
     *
     * To use this TableColumn, you can:
     * - Use it in a TableView to display the number of each Praktikum.
     * - Customize its appearance and behavior using the provided setCellValueFactory and setCellFactory methods.
     * - Access the current cell value using the getValue method.
     *
     * Example Usage:
     * ```java
     * TableView<Praktikum> tableView = new TableView<>();
     * TableColumn<Praktikum, Integer> numberColumn = new TableColumn<>("Number");
     * numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
     *
     * tableView.getColumns().add(numberColumn);
     * ```
     */
    @FXML
    private TableColumn<Praktikum, Integer> numberColumnPraktikum;
    /**
     * The praktikumNameText variable is a JavaFX Label object used to display the name of a praktikum.
     */
    @FXML
    private Label praktikumNameText;
    /**
     * The TableView variable for displaying Praktikum objects.
     *
     * This variable is used to display and interact with a table of Praktikum objects.
     * It is specifically designed for use with the JavaFX framework and can be accessed
     * using the JavaFXFXMLLoader class.
     */
    @FXML
    private TableView<Praktikum> tableviewPraktikum;
    /**
     * The datumColumn variable is a private TableColumn object that is used in conjunction with the JavaFX FXML framework.
     * It represents a column in a TableView component that displays Date values.
     *
     * This TableColumn is specifically designed to work with objects of the Praktikumstermin class.
     *
     * Example usage:
     *
     * // Initialize the datumColumn variable
     * datumColumn = new TableColumn<>();
     *
     * // Set the text/label for the column header
     * datumColumn.setText("Datum");
     *
     * // Set the property value factory to extract the Date value from the Praktikumstermin object
     * datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
     *
     * // Add the column to the TableView
     * tableView.getColumns().add(datumColumn);
     */
    @FXML
    private TableColumn<Praktikumstermin, LocalDate> datumColumn;
    /**
     * The TableColumn for the "terminBestanden" property of the Praktikumstermin class.
     * It represents whether the practical session is completed or not.
     * The TableColumn is bound to a BooleanProperty.
     */
    @FXML
    private TableColumn<Praktikumstermin, BooleanProperty> terminBestandenColumn;
    /**
     * The uhrzeitColumn variable is a private TableColumn object used in JavaFX for displaying time values.
     * It is associated with the Praktikumstermin object and represents the column for displaying the time of the Praktikumstermin in a table.
     * The type of values stored in this column is Time.
     *
     * Example usage:
     *
     * // Initialize the uhrzeitColumn
     * uhrzeitColumn = new TableColumn<>();
     *
     * // Set the column name
     * uhrzeitColumn.setText("Uhrzeit");
     *
     * // Set the cell value factory to display the time value from the Praktikumstermin object
     * uhrzeitColumn.setCellValueFactory(new PropertyValueFactory<>("uhrzeit"));
     *
     * // Add the uhrzeitColumn to the TableView
     * tableView.getColumns().add(uhrzeitColumn);
     *
     * This variable should not be accessed or modified directly outside of its associated JavaFX controller class.
     */

    @FXML
    private TableColumn<Praktikumstermin, LocalTime> uhrzeitColumn;
    /**
     * The TableView control represents a control used to display and manipulate tabular data.
     *
     * <p>
     * The tableviewTermin variable is an instance of TableView class with a generic type of Praktikumstermin.
     * It is used to display and interact with a collection of Praktikumstermin objects.
     * </p>
     *
     * <p>
     * The TableView control organizes data into rows and columns, where each row represents an object
     * and each column represents a property or attribute of that object. The tableviewTermin variable
     * is populated with Praktikumstermin objects, which are defined by the Praktikumstermin class.
     * </p>
     *
     * <p>
     * To add data to the tableviewTermin, you can set its items property by calling the setItems() method
     * and passing in an ObservableList<Praktikumstermin> as the argument. This will populate the table view
     * with the given collection of Praktikumstermin objects.
     * </p>
     *
     * <p>
     * The TableView control also provides various methods to manipulate the data, such as adding, removing,
     * updating, or sorting rows. You can use these methods to perform operations on the data displayed in
     * the tableviewTermin.
     * </p>
     *
     * <p>
     * To customize the appearance and behavior of the TableView control, you can use CSS stylesheets
     * or JavaFX properties and events. You can also define custom cell factories and cell value factories
     * to control how the data is displayed and edited in the table view.
     * </p>
     *
     * <p>
     * It is important to note that the tableviewTermin variable is annotated with the @FXML annotation,
     * which indicates that it is a JavaFX-annotated field and is injected by the FXMLLoader when the
     * corresponding FXML file is loaded.
     * </p>
     */
    @FXML
    private TableView<Praktikumstermin> tableviewTermin;
    /**
     * The numberColumnTermin variable is a TableColumn object used in a JavaFX application.
     * It represents a column in a table of practicums, with each cell displaying the corresponding number of the practicum.
     * The type parameter of the TableColumn is set to Integer to specify that the cells will contain integer values.
     *
     * This variable is annotated with @FXML to indicate that it is a JavaFX element that is injected from the FXML file.
     * It is used in the controller class to define and manipulate the corresponding column in the UI.
     */
    @FXML
    private TableColumn<Praktikumstermin, Integer> numberColumnTermin;

    /**
     * Retrieves the Fach object that is currently set.
     *
     * @return the Fach object that is currently set.
     */
    public static Fach getFach() {
        return fach;
    }

    /**
     * Sets the Fach object for the PraktikumUI class.
     *
     * @param fach the Fach object to be set
     */
    public static void setFach(Fach fach) {
        PraktikumUI.fach = fach;
    }

    /**
     *
     */ //Getter for the instance
    public static PraktikumUI getInstance() {
        if (instance == null)
            instance = new PraktikumUI();
        return instance;
    }

    /**
     * Handles the event when a user clicks the "Add Termin" button.
     * Retrieves the selected Praktikum from the tableviewPraktikum and adds a new Praktikumstermin to it.
     * If no Praktikum is selected, an error alert is displayed.
     * The new Praktikumstermin is added to the list of Praktikumstermine using Utility.getInstance().getPraktikumstermine().
     * The Praktikum's "isBestanden" state is updated.
     * Finally, the termin table is updated for the selected Praktikum.
     *
     * @param event The ActionEvent object representing the button click event.
     */
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

        var date = new Date();
        Time time = new Time(date.getTime());
        var versuch = new Praktikumstermin(Praktikumstermin.getPraktikumsterminCounter(), praktikum.getID(), date, time, false, 0);
        Utility.getInstance().getPraktikumstermine().add(versuch);
        praktikum.isBestanden();
        updateTerminTable(praktikum);
    }

    /**
     * Handles the event when a user clicks the "Delete Termin" button.
     * Retrieves the selected Praktikumstermin object from the tableviewTermin and removes it from the list of Praktikumstermine.
     * If the application is not in dummy mode, the Praktikumstermin is also removed from the database using Database.getInstance().deleteElement().
     * Retrieves the selected Praktikum object from the tableviewPraktikum and updates its "isBestanden" state.
     * Finally, the termin table is updated for the selected Praktikum.
     *
     * @param*/
    @FXML
    void onDeleteTermin(ActionEvent event) {
        var versuch = tableviewTermin.getSelectionModel().getSelectedItem();
        if (versuch != null) {
            Utility.getInstance().getPraktikumstermine().remove(versuch);
            if (!Main.isDummyLaunch()) {
                Database.getInstance().deleteElement(versuch);
            }
            var praktikum = tableviewPraktikum.getSelectionModel().getSelectedItem();
            if (praktikum != null) {
                praktikum.isBestanden();
                updateTerminTable(praktikum);
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
     * Adds a new Praktikum to the list of Praktika.
     *
     * @param event The event that triggered the method.
     */
    @FXML
    void onAddPraktikum(ActionEvent event) {
        var praktikum = new Praktikum(Praktikum.getPraktikumCounter(), false, fach.getID());
        Utility.getInstance().getPraktika().add(praktikum);
        fach.isBestanden();
        updateTable();
    }

    /**
     * This method is called when the 'Back' button is clicked.
     * It navigates back to the main UI window.
     *
     * @param event The action event triggered by clicking the 'Back' button.
     * @throws IOException If an IO error occurs while navigating back to the main UI window.
     */
    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        MainUI.getInstance().start(stage);
    }

    /**
     * Deletes the selected Praktikum from the tableviewPraktikum and its corresponding Praktikumstermine.
     * If the application is not in dummy mode, the Praktikum and its Praktikumstermine will also be deleted from the database.
     * After deletion, the table will be updated.
     */
    @FXML
    void onDeletePraktikum() {
        var praktikum = tableviewPraktikum.getSelectionModel().getSelectedItem();
        if (praktikum != null) {
            var termine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktikumstermin.getPraktikumID() == praktikum.getID()).toList();
            termine.forEach(Utility.getInstance().getPraktikumstermine()::remove);
            if (!Main.isDummyLaunch()) {
                Database.getInstance().deleteElement(praktikum);
                termine.forEach(Database.getInstance()::deleteElement);
            }
            Utility.getInstance().getPraktika().remove(praktikum);
            fach.isBestanden();
            updateTable();

            //delete all corresponding praktikumstermine
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Praktikum ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Praktikum aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }


    /**
     * Saves the data either to the database or prints it to the console if in dummy mode.
     *
     * @param event the action event that triggered the save method
     */
    @FXML
    void onSave(ActionEvent event) {

        if (Main.isDummyLaunch()) {
            Utility.getInstance().getModule().forEach(modul -> System.out.println(modul.toString()));

        } else {
            Database.getInstance().saveAllData();
        }
    }


    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It is responsible for injecting the FXML elements defined in the corresponding FXML file.
     * It asserts that all injected elements are not null.
     *
     * This method is used to initialize the UI components and perform any necessary setup tasks.
     *
     * Note: This method does not return any value.
     */
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

    /**
     * Starts the application by loading the FXML file and setting up the primary stage.
     *
     * @param primaryStage the primary stage for the application
     * @throws IOException if there is an error loading the FXML file
     */
    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PraktikumsUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    /**
     * Clears the tableviewPraktikum items and updates it with new data.
     */
    private void updateTable() {
        tableviewPraktikum.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableviewPraktikum.getItems().addAll(new HashSet<>(Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFachID() == fach.getID()).toList()));
        fachNameText.setText(fach.getName());
        tableviewTermin.refresh();
        tableviewPraktikum.refresh();
    }

    /**
     * Initializes the controller.
     *
     * @param location The URL of the FXML file for the controller.
     * @param resources The ResourceBundle containing localized resources for the controller.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initPraktikumTable();
        initPraktikumsterminTable();
    }

    /**
     * Updates the termin table with the given praktikum.
     * Clears the existing items in the table and adds new items based on the praktikum.
     * Also updates the praktikum name text field.
     * Refreshes the termin and praktikum table views.
     *
     * @param praktikum The praktikum object to update the termin table with.
     */
    private void updateTerminTable(Praktikum praktikum) {
        tableviewTermin.getItems().clear();
        if (praktikum == null) return;
        //update the tableview checkboxes for the praktika and the prüfung
        var items = new HashSet<>(Utility.getInstance().getPraktikumstermine().stream().filter(versuch -> versuch.getPraktikumID() == praktikum.getID()).toList());
        tableviewTermin.getItems().addAll(items);
        praktikumNameText.setText(praktikum.getFach().getName());
        tableviewTermin.refresh();
        tableviewPraktikum.refresh();

    }

    /**
     * Initializes the praktikum table.
     * Sets up the cell value factories for the columns and the cell factory for the praktikumBestandenColumn.
     * Also adds a listener to the tableviewPraktikum's selectedItemProperty to update the termin table accordingly.
     */
    private void initPraktikumTable() {
        numberColumnPraktikum.setCellValueFactory(new PropertyValueFactory<>("ID"));
        praktikumBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        praktikumBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var praktikum = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(praktikum.isBestanden());
                    checkBox.setOnAction(e -> praktikum.setBestanden(checkBox.isSelected()));
                }
            }
        });
        tableviewPraktikum.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateTerminTable(newSelection));
        updateTable();
    }

    /**
     * Initializes the praktikumsterminTable with the appropriate cell factories and event listeners.
     * The "numberColumnTermin" column is associated with the "ID" property of the praktikumstermin.
     * The "terminBestandenColumn" column is associated with the "bestandenProperty" property of the praktikumstermin.
     * The "terminBestandenColumn" column uses a CheckBoxTableCell to display and manipulate the "bestandenProperty" property.
     * The "datumColumn" column is associated with the "datum" property of the praktikumstermin.
     * The "datumColumn" column uses a TextFieldTableCell with a date formatter to display and edit the "datum" property.
     * The "uhrzeitColumn" column is associated with the "uhrzeit" property of the praktikumstermin.
     * The "uhrzeitColumn" column uses a TextFieldTableCell with a time formatter to display and edit the "uhrzeit" property.
     * The tableviewPraktikum is configured to update the praktikumsterminTable with the selected item.
     * The updateTable() method is called to populate the praktikumsterminTable with data.
     */
    private void initPraktikumsterminTable() {
        numberColumnTermin.setCellValueFactory(new PropertyValueFactory<>("terminnummer"));
        numberColumnTermin.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numberColumnTermin.setOnEditCommit(event -> {
            var termin = event.getRowValue();
            termin.setTerminnummer(event.getNewValue());
        });
        terminBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestandenProperty"));
        terminBestandenColumn.setCellFactory(tc -> new CheckBoxTableCell<>() {
            @Override
            public void updateItem(BooleanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    CheckBox checkBox = (CheckBox) this.getGraphic();
                    var praktikumstermin = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(praktikumstermin.isBestanden());
                    checkBox.setOnAction(e -> praktikumstermin.setBestanden(checkBox.isSelected()));
                }
            }
        });

        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        datumColumn.setCellFactory(
                column -> new TableCell<>() {
                    private final DatePicker datePicker = new DatePicker();

                    private boolean inUpdate = false;

                    {
                        datePicker.converterProperty().set(Utility.DATE_FORMATTER);

                        datePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                            if (!inUpdate && newValue != null) {
                                System.out.println("Selected Date: " + newValue);

                                if (isEditing()) {
                                    commitEdit(newValue);
                                } else {
                                    startEdit();
                                    commitEdit(newValue);
                                    cancelEdit();
                                }
                            }
                        });
                        datumColumn.setOnEditCommit(event -> {
                            try {
                                var tableView = this.getTableView(); // Tabelle holen
                                var versuch = tableView.getSelectionModel().getSelectedItem();
                                versuch.setDatum(event.getNewValue());
                            } catch (NullPointerException e) {
                                var alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Fehler");
                                alert.setHeaderText("Kein Praktikumstermin ausgewählt");
                                alert.setContentText("Bitte wählen Sie einen Praktikumstermin aus, dessen Datum Sie ändern möchten.");
                                alert.showAndWait();
                            }
                        });
                        setGraphic(datePicker);
                    }

                    @Override
                    protected void updateItem(LocalDate date, boolean empty) {
                        inUpdate = true;
                        super.updateItem(date, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            datePicker.setValue(date);
                            setGraphic(datePicker);
                        }
                        inUpdate = false;
                    }
                }
        );

        // Configure uhrzeitColumn
        uhrzeitColumn.setCellValueFactory(new PropertyValueFactory<>("uhrzeit"));
        uhrzeitColumn.setCellFactory(TextFieldTableCell.forTableColumn(Utility.TIME_FORMATTER));
        uhrzeitColumn.setOnEditCommit(event -> {
            var versuch = event.getRowValue();
            versuch.setUhrzeit(event.getNewValue());
        });
    }


}

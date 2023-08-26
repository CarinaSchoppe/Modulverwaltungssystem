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

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * Represents the user interface for managing praktikums.
 * Implements the Initializable interface for initializing the UI.
 */
public class PraktikumUI implements Initializable {


    /**
     *
     */
    private static PraktikumUI instance = null;
    /**
     * Represents a Fach variable.
     * <p>
     * This variable holds an instance of the Fach class, which represents a subject or course.
     * <p>
     * The variable is declared as private static, meaning it is accessible within the scope of its class
     * and can be accessed even without creating an instance of the class. However, it is only accessible
     * within the same class and not accessible from any other classes or instances.
     */
    private static Fach fach;
    /**
     * The TableColumn for the "Bestanden" property of a Praktikum object.
     * It represents whether the Praktikum has been passed or not.
     *
     * @FXML
     * @private
     */
    @FXML
    private TableColumn<Praktikum, BooleanProperty> praktikumBestandenColumn;
    /**
     * Button used to add an item.
     */
    @FXML
    private Button addButton;
    /**
     * Represents the "Back" button in the user interface.
     *
     * The backButton variable is an instance of the Button class which is used to navigate 
     * back to the previous screen or state in the application. It is annotated with 
     * @FXML to indicate that it is associated with a graphical element defined in an FXML 
     * file.
     *
     * Usage:
     * The backButton can be accessed and manipulated through the corresponding getter and 
     * setter methods. It can be used to register event handlers or to change its appearance, 
     * behavior, or text dynamically.
     *
     * Example:
     * // Accessing the backButton variable
     * Button backButton = getBackButton();
     *
     * // Assigning an event handler to the backButton
     * backButton.setOnAction(event -> {
     *     // Perform an action when the backButton is clicked
     * });
     *
     * // Changing the text of the backButton dynamically
     * backButton.setText("Go Back");
     *
     * // Disabling the backButton
     * backButton.setDisable(true);
     *
     * Note:
     * The backButton is typically used in conjunction with other UI elements and event 
     * handlers to provide navigation functionality within the application.
     */
    @FXML
    private Button backButton;
    /**
     * The deleteButton variable represents a button used for deleting items or performing delete actions in a graphical user interface (GUI).
     * This variable is annotated with @FXML, indicating that it is a reference to a graphical element defined in an FXML file.
     * Therefore, it is typically used in conjunction with JavaFX and FXML to handle user interactions and trigger delete actions.
     *
     * Usage example:
     *
     * // In your JavaFX controller class:
     *
     * // Import necessary JavaFX classes
     * import javafx.fxml.FXML;
     * import javafx.scene.control.Button;
     *
     * // Define deleteButton variable with @FXML annotation
     * @FXML
     * private Button deleteButton;
     *
     * // Handle deleteButton click event in an event handler method
     * @FXML
     * private void handleDeleteButtonClicked() {
     *     // Perform delete action here
     *     // ...
     * }
     *
     * // In your FXML file:
     *
     * <!-- Import necessary JavaFX classes and define controller class -->
     * <?xml version="1.0" encoding="UTF-8"?>
     * <?import javafx.scene.control.Button?>
     * <?import javafx.scene.layout.VBox?>
     * <VBox xmlns="http://javafx.com/javafx/15" xmlns:fx="http://javafx.com/fxml/1" fx:controller="yourControllerClass">
     *     <!-- Other GUI elements -->
     *     <Button fx:id="deleteButton" text="Delete" onAction="#handleDeleteButtonClicked" />
     *     <!-- Other GUI elements -->
     * </VBox>
     *
     * Note: This is just an example of how the deleteButton variable can be used in a JavaFX application.
     * Your actual implementation may vary depending on the requirements of your application.
     */
    @FXML
    private Button deleteButton;


    /**
     * A variable that represents the resource bundle for this application's resources.
     * The ResourceBundle is used for localization and contains the translated strings and other resources
     * for different languages and locales.
     *
     * <p>
     * It is annotated with @FXML to indicate that this variable is associated with a JavaFX component
     * and is injected using JavaFX's FXML loader.
     * </p>
     *
     * <p>
     * This variable is typically used in JavaFX controllers to access localized strings and other resources.
     * By using the ResourceBundle, the application can easily support multiple languages and locales
     * without modifying the code.
     * </p>
     */
    @FXML
    private ResourceBundle resources;

    /**
     *
     */
    @FXML
    private URL location;
    /**
     * The fachNameText variable represents a JavaFX Label object used to display the name of a subject or course.
     * It is annotated with the @FXML annotation to indicate that it is a JavaFX component and it is injected
     * from the FXML file.
     *
     * This variable is private to maintain encapsulation and follows the naming convention in Java, using camel-case
     * with the first letter lowercase.
     *
     * The fachNameText Label can be used to set or retrieve the text content displayed in the label using
     * appropriate methods like setText() and getText().
     *
     * Example usage:
     *
     * // Using the variable to set the text
     * fachNameText.setText("Mathematics");
     *
     * // Using the variable to get the text
     * String subjectName = fachNameText.getText();
     *
     * Note: This documentation assumes that the variable is accessible within the required scope.
     */
    @FXML
    private Label fachNameText;
    /**
     * A JavaFX Button object representing a "Versuch" button.
     */
    @FXML
    private Button versuchButton;
    /**
     *
     */
    @FXML
    private Button addButtonPraktikum;
    /**
     * The saveButton variable represents a button in the user interface 
     * that is used to save data or perform save-related actions.
     *
     * This variable is annotated with @FXML to mark it as an element 
     * defined in the FXML file and inject it into the controller class.
     *
     * It is of type Button, which is a class from the JavaFX library, 
     * and is used to create a clickable button in a graphical user interface.
     *
     * This button is typically associated with saving data or performing 
     * save-related actions when clicked by the user.
     */
    @FXML
    private Button saveButton;
    /**
     * The addButtonTermin variable represents the button element in a user interface
     * that is used to add a new term or event.
     *
     * This variable is annotated with @FXML as it is defined in the FXML file and
     * injected using the JavaFX FXML loader.
     *
     * Example usage:
     *
     * // Create a new instance of the addButtonTermin button
     * Button addButtonTermin = new Button();
     *
     * // Set the text on the button
     * addButtonTermin.setText("Add Term");
     *
     * // Set an event handler for the button
     * addButtonTermin.setOnAction(e -> {
     *     // Code to handle the button click event
     * });
     */
    @FXML
    private Button addButtonTermin;
    /**
     * A JavaFX Button used for deleting a "Praktikum".
     *
     * This button allows the user to delete a "Praktikum" from the system when clicked.
     *
     * This button is annotated with @FXML to be injected by the JavaFX FXMLLoader.
     */
    @FXML
    private Button deleteButtonPraktikum;
    /**
     * Represents a reference to the deleteButtonTermin button in an JavaFX application.
     * This button is usually used to delete a specific termin object or perform a delete operation.
     */
    @FXML
    private Button deleteButtonTermin;
    /**
     * The number column for the Praktikum table.
     * This column displays the numbers of the Praktikum objects.
     *
     * @FXML
     * private TableColumn<Praktikum, Integer> numberColumnPraktikum;
     */
    @FXML
    private TableColumn<Praktikum, Integer> numberColumnPraktikum;
    /**
     *
     */
    @FXML
    private Label praktikumNameText;
    /**
     * The TableView object used to display Praktikum objects.
     *
     * The tableviewPraktikum variable is a JavaFX TableView object. It is used to display the data for the Praktikum class in a tabular form.
     * Each row in the TableView represents an instance of the Praktikum class, and each column represents a property of the object.
     *
     * The TableView is a data-aware control that allows you to bind it to a data source and automatically update the UI when the data changes.
     * You can customize the appearance and behavior of the TableView by using its properties and methods.
     *
     * This variable is typically injected using the @FXML annotation, which marks it for automatic initialization by JavaFX during the creation of the UI. 
     * It is then accessible in the controller class that corresponds to the UI.
     *
     * To use the TableView, you need to set its columns and provide a data source. You can do this programmatically in your Java code, 
     * or you can define them declaratively in an FXML file and load it using an FXMLLoader.
     *
     * Example usage:
     *
     * // Create a TableView instance
     * TableView<Praktikum> tableviewPraktikum = new TableView<>();
     *
     * // Create TableColumn instances for each property of the Praktikum class
     * TableColumn<Praktikum, String> column1 = new TableColumn<>("Property 1");
     * TableColumn<Praktikum, Integer> column2 = new TableColumn<>("Property 2");
     *
     * // Set the value factory for each column to define the data source for that column
     * column1.setCellValueFactory(new PropertyValueFactory<>("property1"));
     * column2.setCellValueFactory(new PropertyValueFactory<>("property2"));
     *
     * // Add the columns to the TableView
     * tableviewPraktikum.getColumns().add(column1);
     * tableviewPraktikum.getColumns().add(column2);
     *
     * // Set the data source for the TableView
     * tableviewPraktikum.setItems(dataList);
     *
     * // Add the TableView to a layout container
     * layoutContainer.getChildren().add(tableviewPraktikum);
     */
    @FXML
    private TableView<Praktikum> tableviewPraktikum;
    /**
     * The datumColumn variable is a private TableColumn object that represents the column for displaying dates in a table.
     * It is annotated with @FXML, indicating that it is controlled by the JavaFX FXML framework.
     * The generic type parameters of TableColumn specify that this column displays values of type Date for objects of type Praktikumstermin.
     *
     * <p>Usage:</p>
     * <pre>{@code
     *     TableColumn<Praktikumstermin, Date> datumColumn = new TableColumn<>();
     * }</pre>
     *
     * @see TableColumn
     * @see Praktikumstermin
     */
    @FXML
    private TableColumn<Praktikumstermin, Date> datumColumn;
    /**
     * The terminBestandenColumn variable represents a TableColumn which displays whether a Praktikumstermin has been completed or not.
     * It is used in the JavaFX scene and is associated with a BooleanProperty.
     * The column shows a checkbox to indicate if the Praktikumstermin has been completed.
     *
     * Note: This variable is annotated with @FXML, indicating it is injected by the FXMLLoader and should not be manually instantiated.
     *
     * @see TableColumn
     * @see BooleanProperty
     * @see Praktikumstermin
     */
    @FXML
    private TableColumn<Praktikumstermin, BooleanProperty> terminBestandenColumn;
    /**
     *
     */
    @FXML
    private TableColumn<Praktikumstermin, Time> uhrzeitColumn;
    /**
     *
     */
    @FXML
    private TableView<Praktikumstermin> tableviewTermin;
    /**
     *
     */
    @FXML
    private TableColumn<Praktikum, Integer> numberColumnTermin;

    /**
     * Retrieves the current instance of the Fach object.
     *
     * @return The current instance of the Fach object.
     */
    public static Fach getFach() {
        return fach;
    }

    /**
     * Sets the Fach for the PraktikumUI class.
     *
     * @param fach the Fach to set
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
     *
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

        var date = LocalDate.now();
        //convert date to a Date.class object
        var time = Time.valueOf("00:00:00");
        var versuch = new Praktikumstermin(Praktikumstermin.getPraktikumsterminCounter(), praktikum.getID(), java.sql.Date.valueOf(date), time, false);
        Utility.getInstance().getPraktikumstermine().add(versuch);
        praktikum.isBestanden();

        updateTerminTable(praktikum);
    }

    /**
     * Deletes a Prüfungsversuch from the TableView and the Utility list.
     * If a Praktikum is selected in the TableViewPraktikum, it checks if the Praktikum is passed.
     * If no Prüfungsversuch is selected, it shows an error message.
     *
     * @param event the ActionEvent triggered by the user
     */
    @FXML
    void onDeleteTermin(ActionEvent event) {
        var versuch = tableviewTermin.getSelectionModel().getSelectedItem();
        if (versuch != null) {
            Utility.getInstance().getPrüfungsversuche().remove(versuch);
            tableviewTermin.getItems().remove(versuch);
            var praktikum = tableviewPraktikum.getSelectionModel().getSelectedItem();
            if (praktikum != null) {
                praktikum.isBestanden();
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
     *
     */
    @FXML
    void onAddPraktikum(ActionEvent event) {
        var praktikum = new Praktikum(Praktikum.getPraktikumCounter(), false, fach.getID());
        Utility.getInstance().getPraktika().add(praktikum);
        tableviewPraktikum.getItems().add(praktikum);
        fach.isBestanden();

    }

    /**
     *
     */
    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        MainUI.getInstance().start(stage);
    }

    /**
     *
     */
    @FXML
    void onDeletePraktikum(ActionEvent event) {
        var item = tableviewPraktikum.getSelectionModel().getSelectedItem();
        if (item != null) {
            Utility.getInstance().getPraktikumstermine().removeIf(termin -> termin.getPraktikumID() == item.getID());
            Utility.getInstance().getPraktika().remove(item);
            fach.isBestanden();
            tableviewPraktikum.getItems().remove(item);

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
     *
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
     *
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
     *
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
     *
     */
    private void updateTable() {
        tableviewPraktikum.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableviewPraktikum.getItems().addAll(new HashSet<>(Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFachID() == fach.getID()).toList()));
        fachNameText.setText(fach.getName());

    }

    /**
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initPraktikumTable();
        initPraktikumsterminTable();
    }

    /**
     *
     */
    private void updateTerminTable(Praktikum praktikum) {
        tableviewTermin.getItems().clear();
        if (praktikum == null) return;
        //update the tableview checkboxes for the praktika and the prüfung
        var items = new HashSet<>(Utility.getInstance().getPraktikumstermine().stream().filter(versuch -> versuch.getPraktikumID() == praktikum.getID()).toList());
        tableviewTermin.getItems().addAll(items);
        praktikumNameText.setText(praktikum.getFach().getName());


    }

    /**
     *
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
     *
     */
    private void initPraktikumsterminTable() {
        numberColumnTermin.setCellValueFactory(new PropertyValueFactory<>("ID"));
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

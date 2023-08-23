package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Praktikum;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PraktikumUI implements Initializable {

    private static Fach fach;
    private static PraktikumUI praktikumUI;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Praktikum, Integer> numberColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableView<Praktikum> tableview;
    @FXML
    private TableColumn<Praktikum, Boolean> terminBestandenColumn;
    @FXML
    private Button versuchButton;

    public static Fach getFach() {
        return fach;
    }

    @FXML
    private Label praktikumNameText;

    @FXML
    private Button saveButton;

    public static void setFach(Fach fach) {
        PraktikumUI.fach = fach;
    }

    public static PraktikumUI getPraktikumUI() {
        return praktikumUI;
    }

    public static void setPraktikumUI(PraktikumUI praktikumUI) {
        PraktikumUI.praktikumUI = praktikumUI;
    }

    @FXML
    void onAdd(ActionEvent event) {
        var praktikum = new Praktikum(Praktikum.getPraktikumCounter(), false, fach.getID());
        tableview.getItems().add(praktikum);
        Utility.getInstance().getPraktika().add(praktikum);
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        var stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        FachUI.getFachUI().start(stage);
    }

    @FXML
    void onDelete(ActionEvent event) {
        var item = tableview.getSelectionModel().getSelectedItem();
        if (item != null) {
            Utility.getInstance().getPraktika().remove(item);
            tableview.getItems().remove(item);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Praktikum ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Praktikum aus, das Sie löschen möchten.");
            alert.showAndWait();
        }
    }


    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onVersuch(ActionEvent event) throws IOException {
        var selectedPraktikum = tableview.getSelectionModel().getSelectedItem();
        if (selectedPraktikum != null) {
            PraktikumsversucheUI.setPraktikum(selectedPraktikum);
            var praktikumUI = new PraktikumsversucheUI();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            praktikumUI.start(stage);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Kein Praktikum ausgewählt");
            alert.setContentText("Bitte wählen Sie ein Praktikum aus, das Sie bearbeiten möchten.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert praktikumNameText != null : "fx:id=\"praktikumNameText\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert terminBestandenColumn != null : "fx:id=\"terminBestandenColumn\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
        assert versuchButton != null : "fx:id=\"versuchButton\" was not injected: check your FXML file 'PraktikumsUI.fxml'.";
    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PraktikumsUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    private void updateTable() {
        tableview.getItems().clear();
        //update the tableview checkboxes for the praktika and the prüfung
        tableview.getItems().addAll(new HashSet<>(Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFachID() == fach.getID()).toList()));
        praktikumNameText.setText(fach.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        praktikumUI = this;
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        terminBestandenColumn.setCellValueFactory(new PropertyValueFactory<>("bestanden"));
        terminBestandenColumn.setCellFactory(CheckBoxTableCell.forTableColumn(terminBestandenColumn));
        terminBestandenColumn.setOnEditCommit(event -> {
            Praktikum praktikum = event.getTableView().getItems().get(event.getTablePosition().getRow());
            praktikum.setBestanden(event.getNewValue());
        });
        updateTable();
    }
}

package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Prüfung;
import de.lisa.studiumsorganisation.model.Prüfungsversuch;
import de.lisa.studiumsorganisation.util.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class PrüfungsversucheUI implements Initializable {

    private static Prüfung prüfung;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<Prüfungsversuch, String> datumColumn;
    @FXML
    private TableColumn<Prüfungsversuch, Float> noteColumn;
    @FXML
    private TableColumn<Prüfungsversuch, Integer> numberColumn;
    @FXML
    private TableColumn<Prüfungsversuch, Button> pruefungBestandenColumn;
    @FXML
    private Label pruefungNameText;
    @FXML
    private Label pruefungsFormText;
    @FXML
    private Button saveButton;
    @FXML
    private TableView<Prüfungsversuch> tableview;
    @FXML
    private TableColumn<Prüfungsversuch, String> uhrzeitColumn;
    @FXML
    private Button deleteButton;


    public static Prüfung getPrüfung() {
        return prüfung;
    }

    public static void setPrüfung(Prüfung prüfung) {
        PrüfungsversucheUI.prüfung = prüfung;
    }

    @FXML
    void onAdd(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {
        var prüfung = tableview.getSelectionModel().getSelectedItem();
        if (prüfung != null) {
            Utility.getInstance().getPrüfungen().remove(prüfung);
            //remove from tabeview
            tableview.getItems().remove(prüfung);

        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Keine Prüfung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Prüfung aus");
            alert.showAndWait();
        }
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        //get current stage from event
        var stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        PrüfungsUI.getPrüfungsUI().start(stage);
    }

    @FXML
    void onSave(ActionEvent event) {
//TODO: hier
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert datumColumn != null : "fx:id=\"datumColumn\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert noteColumn != null : "fx:id=\"noteColumn\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert pruefungNameText != null : "fx:id=\"pruefungNameText\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert pruefungsFormText != null : "fx:id=\"pruefungsFormText\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        assert uhrzeitColumn != null : "fx:id=\"uhrzeitColumn\" was not injected: check your FXML file 'PruefungsversucheUI.fxml'.";
        pruefungsFormText.setText(getPrüfung().getPrüfungsform().getText());
    }

    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/PruefungsversucheUI.fxml"));
        var root = (Parent) loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Studiumsorganisation");
        primaryStage.setScene(new Scene(root));
        initialize();
        primaryStage.show();
    }

    private void updateTable() {
        tableview.getItems().clear();
        //get prüfungen based on ModulID -> FachID -> Prüfungen
        tableview.getItems().addAll(new HashSet<>(Utility.getInstance().getPrüfungsversuche().stream().filter(p -> p.getPrüfungsID() == prüfung.getID()).toList())
        );
        //update the tableview checkboxes for the praktika and the prüfung
        pruefungNameText.setText(getPrüfung().getFach().getName());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateTable();

    }
}

package de.lisa.studiumsorganisation.view;

import de.lisa.studiumsorganisation.model.Fach;
import de.lisa.studiumsorganisation.model.Prüfung;
import de.lisa.studiumsorganisation.model.Prüfungsform;
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
import java.util.ResourceBundle;

public class PrüfungsUI implements Initializable {

    private static PrüfungsUI prüfungsUI;

    private static Fach fach;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Prüfung, Integer> numberColumn;

    @FXML
    private TableColumn<Prüfung, Boolean> pruefungBestandenColumn;

    @FXML
    private Label pruefungNameText;

    @FXML
    private TableColumn<Prüfung, Prüfungsform> pruefungsFormColumn;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Prüfung> tableview;

    @FXML
    private Button versuchButton;

    public static PrüfungsUI getPrüfungsUI() {
        return prüfungsUI;
    }

    public static void setPrüfungsUI(PrüfungsUI prüfungsUI) {
        PrüfungsUI.prüfungsUI = prüfungsUI;
    }

    public static Fach getFach() {
        return fach;
    }

    public static void setFach(Fach fach) {
        PrüfungsUI.fach = fach;
    }

    @FXML
    void onAdd(ActionEvent event) {
        var prüfung = new Prüfung(Prüfung.getPrüfungCounter(), Prüfungsform.KLAUSUR, fach.getID(), false);
        Utility.getInstance().getPrüfungen().add(prüfung);
        tableview.getItems().add(prüfung);
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
            alert.setHeaderText("Keine Prüfung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Prüfung aus");
            alert.showAndWait();
        }
    }

    @FXML
    void onSave(ActionEvent event) {
//TODO: hier

    }

    @FXML
    void onVersuch(ActionEvent event) throws IOException {
        var selectedPrüfung = tableview.getSelectionModel().getSelectedItem();
        if (selectedPrüfung != null) {
            PrüfungsversucheUI.setPrüfung(selectedPrüfung);
            var prüfungsUI = new PrüfungsversucheUI();
            //get current stage based on the event
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
            prüfungsUI.start(stage);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Keine Prüfung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Prüfung aus, um die Prüfungsversuche zu sehen.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungBestandenColumn != null : "fx:id=\"pruefungBestandenColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungNameText != null : "fx:id=\"pruefungNameText\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert pruefungsFormColumn != null : "fx:id=\"pruefungsFormColumn\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";
        assert versuchButton != null : "fx:id=\"versuchButton\" was not injected: check your FXML file 'PrüfungsUI.fxml'.";

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prüfungsUI = this;
    }
}

package de.lisa.studiumsorganisation.controller;

import de.lisa.studiumsorganisation.model.*;
import de.lisa.studiumsorganisation.util.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database instance;


    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "stdinfos";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private final Connection connection;


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    private Database() {
        instance = this;
        connection = connect(URL);
    }

    private static Connection connect(String URL) {
        try {
            var connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Verbindung erfolgreich hergestellt");
            return connection;
        } catch (SQLException e) {
            System.out.println("Das funktioniert so nicht");
            System.exit(1);
        }
        return null;
    }


    public void loadAllData() {
        Utility.getInstance().getPraktika().clear();
        Utility.getInstance().getModule().clear();
        Utility.getInstance().getPrüfungen().clear();
        Utility.getInstance().getPraktikumstermine().clear();
        Utility.getInstance().getPrüfungsversuche().clear();
        Utility.getInstance().getFächer().clear();
        Utility.getInstance().getStudiengänge().clear();
        try {
            loadAllPraktika();
            loadAllModule();
            loadAllPrüfungen();
            loadAllPraktikumstermine();
            loadAllPrüfungsversuche();
            loadAllFächer();
            loadStudiengänge();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadStudiengänge() throws SQLException {
        var query = "SELECT * FROM studiengang";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var studiengang = new Studiengang(result.getInt("StudID"), result.getString("Studienverlaufsplan"));
            Utility.getInstance().getStudiengänge().add(studiengang);
        }
        System.out.println("Studiengänge geladen!");
    }

    private void loadAllFächer() throws SQLException {
        var query = "SELECT * FROM fach";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var fach = new Fach(result.getInt("FachID"), result.getString("FachName"), result.getInt("FachSemester"), result.getBoolean("FachBestanden"), result.getInt("ECTS"), result.getInt("ModulID"));
            Utility.getInstance().getFächer().add(fach);
        }
        System.out.println("Fächer geladen!");
    }

    private void loadAllPrüfungsversuche() throws SQLException {
        var query = "SELECT * FROM pruefungsversuch";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var prüfungsversuch = new Prüfungsversuch(result.getInt("PruefVersuchID"), result.getDate("PruefDatum"), result.getTime("PruefUhrzeit"), result.getBoolean("PruefBestanden"), result.getFloat("PruefNote"), result.getInt("PruefID"));
            Utility.getInstance().getPrüfungsversuche().add(prüfungsversuch);
        }
        System.out.println("Prüfungsversuche geladen!");
    }

    private void loadAllPraktikumstermine() throws SQLException {
        var query = "SELECT * FROM praktikumstermin";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var praktikumstermin = new Praktikumstermin(result.getInt("PraktTerminID"), result.getInt("PraktID"), result.getDate("PraktDatum"), result.getTime("PraktUhrzeit"), result.getBoolean("TerminBestanden"));
            Utility.getInstance().getPraktikumstermine().add(praktikumstermin);
        }
        System.out.println("Praktikumstermine geladen!");
    }

    private void loadAllPrüfungen() throws SQLException {
        var query = "SELECT * FROM pruefung";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var formString = result.getString("PruefForm");
            var form = Prüfungsform.valueOf(formString.toUpperCase());
            var prüfung = new Prüfung(result.getInt("PruefID"), form, result.getInt("FachID"), result.getBoolean("PruefBestanden"));
            Utility.getInstance().getPrüfungen().add(prüfung);
        }
        System.out.println("Prüfungen geladen!");
    }

    private void loadAllModule() throws SQLException {
        //load all data from the table Modul and create instances of Modul class for each and add it to Util.getInstance().getModule()
        var query = "SELECT * FROM modul";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var modul = new Modul(result.getInt("ModulID"), result.getString("ModulName"), result.getBoolean("ModulBestanden"), result.getInt("StudID"));
            Utility.getInstance().getModule().add(modul);
        }
        System.out.println("Module geladen!");

    }

    private void loadAllPraktika() throws SQLException {
        //load all data from the table Praktikum and create instances of Praktikum class for each and add it to Util.getInstance().getPraktika()
        var query = "SELECT * FROM praktikum";
        var statement = connection.createStatement();
        var result = statement.executeQuery(query);
        while (result.next()) {
            var praktikum = new Praktikum(result.getInt("PraktID"), result.getBoolean("PraktBestanden"), result.getInt("FachID"));
            Utility.getInstance().getPraktika().add(praktikum);
        }
        System.out.println("Praktika geladen!");

    }


}

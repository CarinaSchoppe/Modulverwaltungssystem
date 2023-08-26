package de.lisa.studiumsorganisation.controller;

import de.lisa.studiumsorganisation.model.*;
import de.lisa.studiumsorganisation.util.Utility;

import java.sql.Connection;
import java.sql.Date;
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

    public void saveAllData() {
        saveAllPraktika();
        saveAllModule();
        saveAllPrüfungen();
        saveAllPraktikumstermine();
        saveAllPrüfungsversuche();
        saveAllFächer();
        saveStudiengänge();
    }

    private void saveStudiengänge() {
        //create an SQL query that updates all prüfung or creates new ones based on the ID from Utility.getInstance()
        //execute the query
        //            var studiengang = new Studiengang(result.getInt("StudID"), result.getString("Studienverlaufsplan"));
        var query = "INSERT INTO studiengang(StudID, Studienverlaufsplan)        VALUES (?,  ?)        ON DUPLICATE KEY UPDATE                   Studienverlaufsplan = VALUES(Studienverlaufsplan);";
        Utility.getInstance().getStudiengänge().forEach(studiengang -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, studiengang.getID());
                statement.setString(2, studiengang.getStudienverlaufsplan());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveAllFächer() {
        //create an SQL query that updates all prüfung or creates new ones based on the ID from Utility.getInstance()
        //execute the query
        //            var fach = new Fach(result.getInt("FachID"), result.getString("FachName"), result.getInt("FachSemester"), result.getBoolean("FachBestanden"), result.getInt("ECTS"), result.getInt("ModulID"));
        var query = "INSERT INTO fach(FachID, FachName, FachSemester, ECTS, ModulID)        VALUES (?,  ?, ?, ?, ?, ?)        ON DUPLICATE KEY UPDATE                   FachName = VALUES(FachName),                   FachSemester = VALUES(FachSemester),                 ECTS = VALUES(ECTS),                   ModulID = VALUES(ModulID);";
        Utility.getInstance().getFächer().forEach(fach -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, fach.getID());
                statement.setString(2, fach.getName());
                statement.setInt(3, fach.getSemester());
                statement.setInt(4, fach.getCredits());
                statement.setInt(5, fach.getModulID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveAllPrüfungsversuche() {
        //create an SQL query that updates all prüfung or creates new ones based on the ID from Utility.getInstance()
        //execute the query
        //            var prüfungsversuch = new Prüfungsversuch(result.getInt("PruefVersuchID"), result.getDate("PruefDatum"), result.getTime("PruefUhrzeit"), result.getBoolean("PruefBestanden"), result.getFloat("PruefNote"), result.getInt("PruefID"));
        var query = "INSERT INTO pruefungsversuch(PruefVersuchID, PruefDatum, PruefUhrzeit, PruefBestanden, PruefNote, PruefID)        VALUES (?,  ?, ?, ?, ?, ?)        ON DUPLICATE KEY UPDATE                   PruefDatum = VALUES(PruefDatum),                   PruefUhrzeit = VALUES(PruefUhrzeit),                   PruefBestanden = VALUES(PruefBestanden),                   PruefNote = VALUES(PruefNote),                   PruefID = VALUES(PruefID);";
        Utility.getInstance().getPrüfungsversuche().forEach(prüfungsversuch -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, prüfungsversuch.getID());
                //convert java.util.date new Date(prüfungsversuch.getDatum()) to java.sql.date
                statement.setDate(2, new Date(prüfungsversuch.getDatum().getTime()));
                statement.setTime(3, prüfungsversuch.getUhrzeit());
                statement.setBoolean(4, prüfungsversuch.isBestanden());
                statement.setFloat(5, prüfungsversuch.getNote());
                statement.setInt(6, prüfungsversuch.getPrüfungsID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveAllPraktikumstermine() {
        //create an SQL query that updates all prüfung or creates new ones based on the ID from Utility.getInstance()
        //execute the query
        //            var praktikumstermin = new Praktikumstermin(result.getInt("PraktTerminID"), result.getInt("PraktID"), result.getDate("PraktDatum"), result.getTime("PraktUhrzeit"), result.getBoolean("TerminBestanden"));
        var query = "INSERT INTO praktikumstermin(PraktTerminID, PraktID, PraktDatum, PraktUhrzeit, TerminBestanden)        VALUES (?,  ?, ?, ?, ?)        ON DUPLICATE KEY UPDATE                   PraktID = VALUES(PraktID),                   PraktDatum = VALUES(PraktDatum),                   PraktUhrzeit = VALUES(PraktUhrzeit),                   TerminBestanden = VALUES(TerminBestanden);";
        Utility.getInstance().getPraktikumstermine().forEach(praktikumstermin -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, praktikumstermin.getID());
                statement.setInt(2, praktikumstermin.getPraktikumID());
                //convert java.util.date new Date(praktikumstermin.getDatum()) to java.sql.date
                statement.setDate(3, new Date(praktikumstermin.getDatum().getTime()));
                statement.setTime(4, praktikumstermin.getUhrzeit());
                statement.setBoolean(5, praktikumstermin.isBestanden());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveAllPrüfungen() {
        //create an SQL query that updates all prüfung or creates new ones based on the ID from Utility.getInstance()
        //execute the query
        //            var prüfung = new Prüfung(result.getInt("PruefID"), form, result.getInt("FachID"), false);
        var query = "INSERT INTO pruefung(PruefID, PruefForm, FachID)        VALUES (?,  ?, ?)        ON DUPLICATE KEY UPDATE                   PruefForm = VALUES(PruefForm),                   FachID = VALUES(FachID);";
        Utility.getInstance().getPrüfungen().forEach(prüfung -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, prüfung.getID());
                statement.setString(2, prüfung.getPrüfungsform().getText());
                statement.setInt(3, prüfung.getFachID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveAllModule() {
        //create an SQL query that updates all module or creates new ones based on the ID from Utility.getInstance().getModule()
        //execute the query
        //            var modul = new Modul(result.getInt("ModulID"), result.getString("ModulName"), false, result.getInt("StudID"));
        var query = "INSERT INTO modul(ModulID, ModulName, StudID)        VALUES (?,  ?, ?)        ON DUPLICATE KEY UPDATE                   ModulName = VALUES(ModulName),                   StudID = VALUES(StudID);";
        Utility.getInstance().getModule().forEach(modul -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, modul.getID());
                statement.setString(2, modul.getName());
                statement.setInt(3, modul.getStudiengangID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void saveAllPraktika() {
        //create an SQL query that updates all praktika or creates new ones based on the ID from Utility.getInstance().getPraktika()
        //execute the query
        var query = "INSERT INTO praktikum_tabelle(id, FachID)        VALUES (?,  ?)        ON DUPLICATE KEY UPDATE                   fachID = VALUES(fachID);";
        Utility.getInstance().getPraktika().forEach(praktikum -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, praktikum.getID());
                statement.setInt(3, praktikum.getFachID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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
            updateBestandenAttributes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateBestandenAttributes() {
        //update the bestanden attributes of all fächer, praktika, module and prüfungen based on the unterlaying element 
        Utility.getInstance().getFächer().forEach(fach -> fach.setBestanden(fach.isBestanden()));
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
            var fach = new Fach(result.getInt("FachID"), result.getString("FachName"), result.getInt("FachSemester"), false, result.getInt("ECTS"), result.getInt("ModulID"));
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
            var prüfung = new Prüfung(result.getInt("PruefID"), form, result.getInt("FachID"), false);
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
            var modul = new Modul(result.getInt("ModulID"), result.getString("ModulName"), false, result.getInt("StudID"));
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
            var praktikum = new Praktikum(result.getInt("PraktID"), false, result.getInt("FachID"));
            Utility.getInstance().getPraktika().add(praktikum);
        }
        System.out.println("Praktika geladen!");

    }


}

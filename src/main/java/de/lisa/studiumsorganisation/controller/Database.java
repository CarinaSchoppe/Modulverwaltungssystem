package de.lisa.studiumsorganisation.controller;

import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Praktikum;
import de.lisa.studiumsorganisation.model.Prüfung;
import de.lisa.studiumsorganisation.model.Prüfungsform;
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

    private Connection connection;


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
        loadAllModules();
        loadAllExams();
        loadAllExercises();

    }

    private void loadAllExercises() {
        //create an sql query to load all exercises from the table "praktikum" and adds the exercises to the list "praktika" under the Utility class

        var query = "SELECT * FROM praktikum";

        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var id = result.getInt("ID");
                var modulId = result.getInt("modulID");
                var bestanden = result.getBoolean("bestanden");
                var datum = result.getDate("datum");
                var versuch = result.getInt("versuch");

                Utility.getInstance().getPraktika().add(new Praktikum(id, modulId, bestanden, datum, versuch));
                System.out.println("Praktikum geladen: " + id + " " + modulId + " " + bestanden + " " + datum + " " + versuch);
            }
        } catch (SQLException e) {
            System.out.println("Das funktioniert so nicht");
            System.exit(1);
        }
    }

    private void loadAllExams() {
        //create an sql query to load all prüfungen from the table "pruefung" and adds the exercises to the list "prüfungen" under the Utility class

        var query = "SELECT * FROM pruefung";

        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var id = result.getInt("ID");
                var modulId = result.getInt("modulID");
                var prüfungsformString = result.getString("pruefungsform");
                var prüfungsform = Prüfungsform.valueOf(prüfungsformString);
                var versuch = result.getInt("versuch");
                var datum = result.getDate("datum");
                var note = result.getFloat("note");
                var bestanden = result.getBoolean("bestanden");

                Utility.getInstance().getPrüfungen().add(new Prüfung(id, modulId, prüfungsform, versuch, datum, note, bestanden));
                System.out.println("Prüfung geladen: " + id + " " + modulId + " " + prüfungsform + " " + versuch + " " + datum + " " + note + " " + bestanden);
            }
        } catch (SQLException e) {
            System.out.println("Das funktioniert so nicht");
            System.exit(1);
        }
    }

    private void loadAllModules() {
        //create an sql query to load all Module from the table "modul" and adds the exercises to the list "module" under the Utility class
        var query = "SELECT * FROM modul";

        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(query);

            while (result.next()) {
                var id = result.getInt("ID");
                var name = result.getString("name");
                var semester = result.getInt("semester");
                var bestanden = result.getBoolean("bestanden");

                Utility.getInstance().getModule().add(new Modul(id, name, semester, bestanden));
                System.out.println("Modul geladen: " + id + " " + name + " " + semester + " " + bestanden);
            }
        } catch (SQLException e) {
            System.out.println("Das funktioniert so nicht");
            System.exit(1);
        }

    }
}

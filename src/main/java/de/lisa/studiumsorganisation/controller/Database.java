package de.lisa.studiumsorganisation.controller;

import de.lisa.studiumsorganisation.model.*;
import de.lisa.studiumsorganisation.util.Utility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Database class represents a database connection and provides methods to save and load data from the database.
 * It uses a singleton pattern to ensure that only one instance of the class is created.
 */
public class Database {

    /**
     * The constant variable representing the host address.
     *
     * <p>
     * The {@code HOST} variable provides the hostname or IP address of the server.
     * It is a private constant and cannot be modified outside the class.
     * </p>
     * <p>
     * This variable is set to the default value of "localhost", representing the
     * local machine where the code is executed.
     * </p>
     *
     * @since 1.0
     */
    private static final String HOST = "localhost";
    /**
     * The PORT constant represents the default port number used for a specific service.
     * It is a private, static and final variable that cannot be modified once assigned.
     * The value of this constant is 3306.
     *
     * <p>
     * This constant is commonly used in network programming to specify the port number
     * for establishing a connection, such as in TCP/IP or UDP communication.
     * </p>
     *
     * <p>
     * Note: It is important to ensure that the relevant service is running on the specified
     * port for successful communication.
     * </p>
     *
     * @since JDK 1.0
     */
    private static final int PORT = 3306;
    /**
     * Represents the name of the database used in the application.
     * The value of this variable is set to "stdinfos".
     * <p>
     * This variable is declared as private and static, indicating that it is a constant
     * and accessible only within the current class. It cannot be modified once assigned.
     * </p>
     * <p>
     * The name "stdinfos" is used to identify the database which stores information
     * related to students.
     * </p>
     */
    private static final String DATABASE = "stdinfos";
    /**
     * This variable holds the default username for the system.
     * It is set to "root".
     *
     * @since 1.0
     */
    private static final String USERNAME = "root";
    /**
     * The password used for authentication.
     */
    private static final String PASSWORD = "";
    /**
     * The URL variable represents the MySQL database connection URL with specific parameters.
     * It is constructed using the HOST, PORT, and DATABASE variables.
     * The URL includes parameters to enable Unicode support and properly handle timezones.
     */
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    /**
     *
     */
    private static Database instance;
    /**
     *
     */
    private final Connection connection;


    /**
     * Private constructor for the Database class.
     * Sets the instance variable to the current instance of the class.
     * Connects to the database using the provided URL.
     */
    private Database() {
        instance = this;
        connection = connect();
    }

    /**
     *
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     *
     */
    private static Connection connect() {
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

    /**
     *
     */
    public void saveAllData() {
        saveStudiengänge();
        saveAllPraktikumstermine();
        saveAllPrüfungsversuche();
        saveAllPrüfungen();
        saveAllPraktika();
        saveAllFächer();
        saveAllModule();
    }

    /**
     * Saves all studiengänge data to the database.
     * This method retrieves the studiengänge objects from the Utility instance,
     * creates an SQL query to update or create new studiengänge based on their IDs,
     * and executes the query to save the data in the database.
     */
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
                System.out.println("Studiengang mit ID:" + studiengang.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Saves all subjects to the database by executing an SQL query. If a subject already exists with the same ID,
     * it will be updated; otherwise, a new subject will be created.
     * <p>
     * The query looks like this:
     * INSERT INTO fach(FachID, FachName, FachSemester, ECTS, ModulID)
     * VALUES (?, ?, ?, ?, ?)
     * ON DUPLICATE KEY UPDATE
     * FachName = VALUES(FachName),
     * FachSemester = VALUES(FachSemester),
     * ECTS = VALUES(ECTS),
     * ModulID = VALUES(ModulID);
     *
     * @throws SQLException if an error occurs while executing the SQL query
     */
    private void saveAllFächer() {
        //create an SQL query that updates all prüfung or creates new ones based on the ID from Utility.getInstance()
        //execute the query
        //        `FachID`        int(11)     NOT NULL,
        //    `FachName`      varchar(50) NOT NULL,
        //    `Semester`      int(11)   DEFAULT 0,
        //    `ECTS`          int(11)   DEFAULT NULL,
        //    `ModulID`       int(11)     NOT NULL,
        var query = "INSERT INTO fach(FachID, FachName, Semester, ECTS, ModulID)        VALUES (?,  ?, ?, ?, ?, ?)        ON DUPLICATE KEY UPDATE                   FachName = VALUES(FachName),                   Semester = VALUES(FachSemester),                 ECTS = VALUES(ECTS),                   ModulID = VALUES(ModulID);";
        Utility.getInstance().getFächer().forEach(fach -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, fach.getID());
                statement.setString(2, fach.getName());
                statement.setInt(3, fach.getSemester());
                statement.setInt(4, fach.getCredits());
                statement.setInt(5, fach.getModulID());
                statement.executeUpdate();
                System.out.println("Fach mit ID:" + fach.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Saves all Prüfungsversuche to the database.
     * Creates a new Prüfungsversuch if it doesn't exist, or updates an existing one based on the ID from Utility.getInstance().
     */
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
                System.out.println("Prüfungsversuch mit ID:" + prüfungsversuch.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Saves all praktikumstermine to the database. It creates a new praktikumstermin if it doesn't exist in the database, or updates the existing one based on its ID.
     *
     * @return None
     */
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
                System.out.println("Praktikumstermin mit ID:" + praktikumstermin.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     */
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
                System.out.println("Prüfung mit ID:" + prüfung.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     */
    private void saveAllModule() {
        //create an SQL query that updates all module or creates new ones based on the ID from Utility.getInstance().getModule()
        //execute the query
        var query = "INSERT INTO modul(ModulID, ModulName, StudID)        VALUES (?,  ?, ?)        ON DUPLICATE KEY UPDATE                   ModulName = VALUES(ModulName),                   StudID = VALUES(StudID);";
        Utility.getInstance().getModule().forEach(modul -> {
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, modul.getID());
                statement.setString(2, modul.getName());
                statement.setInt(3, modul.getStudiengangID());
                statement.executeUpdate();
                System.out.println("Modul mit ID:" + modul.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     */
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
                System.out.println("Praktikum mit ID:" + praktikum.getID() + " wurde gespeichert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     *
     */
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

    /**
     * Updates the "bestanden" attribute of all "Fächer" (subjects), "Praktika" (internships),
     * "Module" (modules), and "Prüfungen" (exams) based on the underlying elements.
     * <p>
     * This method iterates over all "Fächer" objects in the Utility class and sets
     * their "bestanden" attribute to the result of the "isBestanden" method. This
     * ensures that the "bestanden" attribute reflects the current status of each element.
     * <p>
     * Note: The "bestanden" attribute represents whether the corresponding element has been passed.
     * If an element has been passed, its "bestanden" attribute is true. Otherwise, it is false.
     */
    private void updateBestandenAttributes() {
        //update the bestanden attributes of all fächer, praktika, module and prüfungen based on the unterlaying element 
        Utility.getInstance().getFächer().forEach(fach -> fach.setBestanden(fach.isBestanden()));
    }

    public void deleteElement(Basemodel element) {
        if (element instanceof Fach fach) {
            var query = "DELETE FROM fach WHERE FachID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, fach.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (element instanceof Modul modul) {
            var query = "DELETE FROM modul WHERE ModulID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, modul.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (element instanceof Praktikum praktikum) {
            var query = "DELETE FROM praktikum WHERE PraktID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, praktikum.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (element instanceof Praktikumstermin praktikumstermin) {
            var query = "DELETE FROM praktikumstermin WHERE PraktTerminID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, praktikumstermin.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (element instanceof Prüfung prüfung) {
            var query = "DELETE FROM pruefung WHERE PruefID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, prüfung.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (element instanceof Prüfungsversuch prüfungsversuch) {
            var query = "DELETE FROM pruefungsversuch WHERE PruefVersuchID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, prüfungsversuch.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (element instanceof Studiengang studiengang) {
            var query = "DELETE FROM studiengang WHERE StudID = ?";
            try {
                var statement = connection.prepareStatement(query);
                statement.setInt(1, studiengang.getID());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
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

    /**
     * Loads all the subjects from the database.
     *
     * @throws SQLException if an SQL exception occurs
     */
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

    /**
     *
     */
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

    /**
     * Loads all praktikumstermine from the database and adds them to the list of praktikumstermine in the Utility class.
     *
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Load all Prüfungen from the database.
     * <p>
     * This method retrieves all Prüfungen stored in the database table "pruefung" and populates the Prüfungen collection
     * in the Utility class.
     *
     * @throws SQLException if there is an error executing the database query
     */
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

    /**
     * Loads all data from the table Modul and creates instances of Modul class for each entry.
     * Adds each created Modul instance to the list of modules in the Utility class.
     *
     * @throws SQLException if there is an error accessing the database
     */
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

    /**
     * Loads all data from the table Praktikum and creates instances of Praktikum class for each,
     * and adds them to Util.getInstance().getPraktika() list.
     *
     * @throws SQLException if there is an error executing the SQL query.
     */
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

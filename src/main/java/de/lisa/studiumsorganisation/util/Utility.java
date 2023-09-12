package de.lisa.studiumsorganisation.util;

import de.lisa.studiumsorganisation.model.*;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;

/**
 * Utility class for various utility functions and constants.
 */
@Getter
public class Utility {


    /**
     * A formatter for converting between Date objects and string representations in the format dd.MM.yyyy.
     *
     * <p>{@code DATE_FORMATTER} is a static final variable of type {@code StringConverter<Date>}. It provides two methods for converting between Date objects and strings in the specified format.</p>
     *
     * <p>The {@code toString} method takes a Date object as input and returns a string representation of the date in the format dd.MM.yyyy. It extracts the first 10 characters from the default string representation of the Date object.</p>
     *
     * <p>The {@code fromString} method takes a string in the format dd.MM.yyyy as input and returns a Date object constructed from the input string.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     *     String dateStr = DATE_FORMATTER.toString(new Date());
     *     Date date = DATE_FORMATTER.fromString(dateStr);
     * }</pre>
     */
    public static final StringConverter<LocalDate> DATE_FORMATTER = new StringConverter<>() {
        @Override
        public String toString(LocalDate object) {
            //erhält ein Date Objekt und gibt es als String zurück im format dd.MM.yyyy
            return object.toString().substring(0, 10);
        }

        @Override
        public LocalDate fromString(String string) {
            //bekommt einen string im format dd.MM.yyyy und gibt ein Date Objekt zurück
            try {
                var d = Integer.parseInt(string.substring(0, 2));
                var m = Integer.parseInt(string.substring(3, 5));
                var y = Integer.parseInt(string.substring(6, 10));
                return LocalDate.of(y, m, d);
            } catch (NumberFormatException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Ungültige Eingabe");
                alert.setContentText("Bitte geben Sie ein gültiges Datum ein. dd.MM.yyyy");
                alert.showAndWait();
                return null;
            }
        }
    };
    /**
     * A static final variable that represents a StringConverter for converting Time objects to strings and vice versa.
     *
     * <p>This StringConverter formats Time objects in the following format: "hh:mm:ss".</p>
     * <p>It also parses strings in the format "hh:mm:ss" and returns a corresponding Time object.</p>
     *
     * @since (insert version number)
     */
    public static final StringConverter<LocalTime> TIME_FORMATTER = new StringConverter<>() {
        @Override
        public String toString(LocalTime object) {
            //erhält ein Time Objekt und gibt es als String zurück im format hh:mm:sss
            return object.toString().substring(0, 8);
        }

        @Override
        public LocalTime fromString(String string) {
            //bekommt einen string im format hh:mm:sss und gibt ein Time Objekt zurück
            try {
                var h = Integer.parseInt(string.substring(0, 2));
                var m = Integer.parseInt(string.substring(3, 5));
                return LocalTime.of(h, m, 00);
            } catch (NumberFormatException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Ungültige Eingabe");
                alert.setContentText("Bitte geben Sie eine gültige Uhrzeit ein. hh:mm");
                alert.showAndWait();
                return null;
            }

        }
    };
    /**
     * Utility class for performing various operations.
     */
    private static Utility instance;
    /**
     * Represents a module container using a HashSet.
     *
     * <p>
     * This class provides an efficient way to store and manage modules using a HashSet.
     * The container can hold objects of the {@link Modul} class and allows for fast insertion,
     * deletion, and retrieval of modules.
     * </p>
     *
     * <p>
     * The module container is designed to be thread-safe. It ensures that the state of the container
     * is consistent, even when accessed by multiple threads concurrently. The module container also
     * guarantees the uniqueness of modules by using a HashSet, which enforces that duplicate modules
     * are not added to the container.
     * </p>
     *
     * <p>
     * This class should be used when you need to store a collection of modules and perform
     * operations such as adding, removing, or searching for specific modules efficiently.
     * </p>
     *
     * <p>
     * Please note that this class should only be used internally within the module system and
     * should not be exposed to the public. If you need to provide access to the module container
     * from external code, consider encapsulating it within a public class or providing public
     * methods to interact with the container instead.
     * </p>
     */
    private final HashSet<Modul> module;
    /**
     * The prüfungen variable stores a set of Prüfung objects.
     * It is declared as private and final to enforce encapsulation and immutability.
     * The set is implemented using the HashSet class, which provides efficient
     * insertion, deletion, and retrieval operations.
     * <p>
     * Prüfungen represents a collection of exams or tests that are being managed or
     * processed in some way.
     * <p>
     * Prüfung is a custom class that represents an individual exam or test.
     * It contains relevant information such as the exam name, date, time, and any
     * associated metadata or properties.
     * <p>
     * The purpose of this member variable is to store and manage the collection of
     * Prüfung objects, providing a convenient way to perform operations on the set
     * of exams as a whole.
     * <p>
     * The access modifier private restricts direct access to this variable from
     * outside the class. It can only be accessed through public methods defined in
     * the class.
     * <p>
     * The final keyword ensures that the variable cannot be reassigned once it has
     * been initialized, providing immutability and preventing unintended changes.
     * <p>
     * Example usage:
     * <p>
     * // Create a new set of prüfungen
     * Set<Prüfung> prüfungen = new HashSet<>();
     * <p>
     * // Add a new Prüfung object to the set
     * Prüfung prüfung1 = new Prüfung("Math Exam", "2021-05-20", "10:00 AM");
     * prüfungen.add(prüfung1);
     * <p>
     * // Add another Prüfung object to the set
     * Prüfung prüfung2 = new Prüfung("Science Exam", "2021-05-22", "2:00 PM");
     * prüfungen.add(prüfung2);
     * <p>
     * // Remove a Prüfung object from the set
     * prüfungen.remove(prüfung1);
     * <p>
     * // Retrieve the size of the set
     * int size = prüfungen.size();
     * <p>
     * Note: This is just an example usage. The actual code may vary depending on
     * the context and requirements of the application.
     */
    private final HashSet<Prüfung> prüfungen;
    /**
     * A private final HashSet that stores Praktikum objects.
     * <p>
     * Praktika represents a collection of Praktikum objects.
     */
    private final HashSet<Praktikum> praktika;
    /**
     * Represents a set of academic subjects.
     * <p>
     * This class encapsulates a set of subjects, represented as a HashSet of type Fach.
     * It provides operations to manipulate and access the set of subjects.
     */
    private final HashSet<Fach> fächer;
    /**
     * A private final HashSet that stores Praktikumstermin objects.
     * Praktikumstermine are the available practical training sessions for a given task or course.
     * This collection ensures uniqueness of Praktikumstermin objects.
     */
    private final HashSet<Praktikumstermin> praktikumstermine;
    /**
     * Holds a set of Prüfungsversuch objects.
     */
    private final HashSet<Prüfungsversuch> prüfungsversuche;
    /**
     * Holds a collection of Studiengang objects.
     */
    private final HashSet<Studiengang> studiengänge;

    /**
     * Private constructor for the Utility class.
     * <p>
     * This constructor initializes various sets used by the utility class,
     * including module, prüfungen, praktika, fächer, praktikumstermine,
     * prüfungsversuche, and studiengänge. It also sets the instance variable to
     * reference the current instance of the Utility class.
     */
    private Utility() {
        module = new HashSet<>();
        prüfungen = new HashSet<>();
        praktika = new HashSet<>();
        fächer = new HashSet<>();
        praktikumstermine = new HashSet<>();
        prüfungsversuche = new HashSet<>();
        studiengänge = new HashSet<>();
        instance = this;
    }

    /**
     * Returns the instance of the Utility class.
     * <p>
     * If the instance is not already created, a new instance is created and returned.
     * Otherwise, the existing instance is returned.
     *
     * @return the instance of the Utility class
     */
    public static Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }


    public LocalDate convertDateToLocalDate(Date datum) {
        return datum.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    public LocalTime convertTimeToLocalTime(Time uhrzeit) {
        return LocalTime.of(uhrzeit.getHours(), uhrzeit.getMinutes(), uhrzeit.getSeconds());
    }
}

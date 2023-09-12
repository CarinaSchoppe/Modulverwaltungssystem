package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

/**
 * The Praktikumstermin class represents a practical session appointment.
 * It stores information about the ID, practical session ID, date, time, and
 * completion status of the appointment.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Praktikumstermin extends Basemodel {

    /**
     * Keeps track of the number of praktikumstermin instances created.
     */
    private static int praktikumsterminCounter = 0;

    /**
     * Represents a BooleanProperty for keeping track of the "bestanden" property.
     */
    private final BooleanProperty bestandenProperty;
    /**
     * Represents the ID of a praktikum.
     */
    private int praktikumID;
    /**
     * The datum variable represents a specific date.
     * This variable is intended to store and manipulate dates.
     * It is recommended to use this variable with care and ensure that its value is always up-to-date.
     */
    private LocalDate datum;
    /**
     * Represents the time.
     */
    private Time uhrzeit;

    private int terminnummer;
    /**
     * Creates a new instance of Praktikumstermin with the given parameters.
     *
     * @param ID          the ID of the Praktikumstermin
     * @param praktikumID the ID of the related Praktikum
     * @param datum       the date of the Praktikumstermin
     * @param uhrzeit     the time of the Praktikumstermin
     * @param bestanden   true if the Praktikumstermin has been passed, false otherwise
     */
    public Praktikumstermin(int ID, int praktikumID, Date datum, Time uhrzeit, boolean bestanden, int terminnummer) {
        super(ID);
        this.praktikumID = praktikumID;
        this.datum = Utility.getInstance().convertDateToLocalDate(datum);
        this.uhrzeit = uhrzeit;
        this.terminnummer = terminnummer;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID >= praktikumsterminCounter) praktikumsterminCounter = ID + 1;
    }

    /**
     * Retrieves the value of the practical term counter.
     *
     * @return The value of the practical term counter.
     */
    public static int getPraktikumsterminCounter() {
        return praktikumsterminCounter;
    }

    /**
     * Returns true if the Praktikumstermin is marked as "bestanden", false otherwise.
     *
     * @return true if the Praktikumstermin is marked as "bestanden", false otherwise.
     */
    public boolean isBestanden() {
        return bestandenProperty.get();
    }

    /**
     * Setter method to set the value of the boolean property 'bestanden'.
     *
     * @param bestanden The new value to be set for the 'bestanden' property.
     */
    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        getPraktikum().isBestanden();
    }

    /**
     * Retrieves the Praktikum object with the given ID.
     *
     * @return the Praktikum object with the specified ID, or null if not found.
     */
    public Praktikum getPraktikum() {
        return Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getID() == praktikumID).findFirst().orElse(null);
    }

}

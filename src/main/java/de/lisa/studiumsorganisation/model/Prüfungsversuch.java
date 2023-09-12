package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * The Prüfungsversuch class represents an attempt for an exam.
 * It stores information about the ID, date, time, result, and associated Prüfung.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Prüfungsversuch extends Basemodel {

    /**
     * Keeps track of the number of attempts made during an exam.
     * <p>
     * The `prüfungsversuchCounter` variable is used to store and update the count of attempts made during an exam.
     * This variable is private and static, which means it is accessible only within its class and there is only one
     * common instance shared among all objects of the class.
     */
    private static int prüfungsversuchCounter = 0;
    /**
     * This variable represents a Boolean property that indicates whether a file is marked as "bestanden" or not.
     * The value of this property can be observed and changed.
     *
     * @see BooleanProperty
     * @see javafx.beans.property.Property
     */
    private final BooleanProperty bestandenProperty;
    /**
     * The ID variable represents the unique identifier for an object.
     * It is a private integer variable that can only be accessed within
     * the class it is defined in.
     */
    private int ID;
    /**
     * The datum variable represents a specific date in time.
     * It is a private instance variable of the Date data type.
     * <p>
     * Note: This documentation does not include example code snippets.
     */
    private LocalDate datum;
    /**
     * Represents the time of day.
     */
    private LocalTime uhrzeit;
    /**
     * The note variable represents a floating point value that stores the note value.
     *
     * <p>
     * Note: The value stored in the note variable should be between 0.0 and 1.0.
     * </p>
     *
     * @see #getNote()
     * @see #setNote(float)
     */
    private float note;
    /**
     * The unique identifier for an examination.
     * <p>
     * This variable represents the private integer value used to store the identification number
     * of an examination. Each examination is assigned a unique identifier to differentiate it from
     * other examinations.
     * <p>
     * The value of this variable should only be accessed and modified through the appropriate getter
     * and setter methods, in order to ensure data encapsulation and maintain the integrity of the
     * variable's value.
     */
    private int prüfungsID;

    /**
     * Represents the experiment number.
     * <p>
     * The versuchsnummer variable stores the experiment number as an integer value. It is used to uniquely identify a particular experiment.
     * The variable is private and can only be accessed within the class that declares it.
     * <p>
     * Usage:
     * To set the experiment number, use the setter method setVersuchsnummer(int versuchsnummer).
     * To get the experiment number, use the getter method getVersuchsnummer().
     * <p>
     * Example:
     * <p>
     * Experiment experiment = new Experiment();
     * experiment.setVers
     */
    private int versuchsnummer;

    /**
     * Initializes a new instance of the Prüfungsversuch class.
     *
     * @param ID         the ID of the Prüfungsversuch
     * @param datum      the date of the Prüfungsversuch
     * @param uhrzeit    the time of the Prüfungsversuch
     * @param bestanden  true if the Prüfungsversuch is passed, false otherwise
     * @param note       the grade of the Prüfungsversuch
     * @param prüfungsID the ID of the associated Prüfung
     */
    public Prüfungsversuch(int ID, Date datum, Time uhrzeit, boolean bestanden, float note, int prüfungsID, int versuchsnummer) {
        super(ID);
        this.datum = Utility.getInstance().convertDateToLocalDate(datum);
        this.uhrzeit = Utility.getInstance().convertTimeToLocalTime(uhrzeit);
        this.note = note;
        this.prüfungsID = prüfungsID;
        this.versuchsnummer = versuchsnummer;
        if (ID >= prüfungsversuchCounter) prüfungsversuchCounter = ID + 1;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
    }

    /**
     * Returns the value of the PrüfungsversuchCounter.
     *
     * @return the value of the PrüfungsversuchCounter
     */
    public static int getPrüfungsversuchCounter() {
        return prüfungsversuchCounter;
    }

    /**
     * Returns the value of the "bestanden" property.
     *
     * @return the value of the "bestanden" property
     */
    public boolean isBestanden() {
        return bestandenProperty.get();
    }

    /**
     * Setter method for the bestanden property.
     * Updates the bestandenProperty and checks if the Prüfung is bestanden.
     *
     * @param bestanden The new value for the bestanden property
     */
    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        getPrüfung().isBestanden();
    }

    /**
     * Retrieves the Prüfung object based on the prüfungsID.
     *
     * @return The Prüfung object corresponding to the prüfungsID.
     */
    public Prüfung getPrüfung() {
        return Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getID() == prüfungsID).findFirst().orElse(null);
    }

    public void setNote(float note) {
        if (note >= 5.0) {
            this.note = 5.0f;
        } else if (note <= 1.0) {
            this.note = 1.0f;
        } else
            this.note = note;
        setBestanden(!(this.note >= 4.0));

    }
}

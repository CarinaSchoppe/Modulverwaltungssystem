package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a Prüfung (examination).
 * <p>
 * A Prüfung has the following properties:
 * - ID: The unique identifier for the Prüfung.
 * - prüfungsform: The form of the Prüfung (e.g., written, oral).
 * - fachID: The ID of the associated Fach (subject) for the Prüfung.
 * - bestandenProperty: The property indicating whether the Prüfung is passed or not.
 * <p>
 * Prüfung instances can be created using the constructor with the following parameters:
 * - ID: The unique identifier for the Prüfung.
 * - prüfungsform: The form of the Prüfung (e.g., written, oral).
 * - fachID: The ID of the associated Fach (subject) for the Prüfung.
 * - bestanden: The initial value indicating whether the Prüfung is passed or not.
 * <p>
 * The getter methods allow access to the properties of the Prüfung:
 * - isBestanden: Returns true if all Prüfungsversuche associated with this Prüfung are passed, otherwise false.
 * - getPrüfungCounter: Returns the current value of the Prüfung counter.
 * - getFach: Returns the Fach associated with this Prüfung.
 * <p>
 * The setter method allows updating the bestandenProperty of the Prüfung:
 * - setBestanden: Sets the bestandenProperty of the Prüfung and updates the bestandenProperty of all associated Prüfungsversuche.
 */
@Getter
@Setter
@EqualsAndHashCode
public class Prüfung extends Basemodel {

    /**
     * Counter variable to keep track of the number of checks performed.
     *
     * This variable is only accessible within the scope of the class it is declared in.
     * It is used to count the number of checks performed and can be incremented or
     * accessed using the methods provided.
     */
    private static int prüfungCounter = 0;

    /**
     * The bestandenProperty represents a boolean property that is used to indicate whether a certain bestanden state is true or false.
     *
     * <p>
     * This property is read-only and can be accessed using the getter method {@link #getBestandenProperty()}.
     * </p>
     *
     * <p>
     * The property is implemented using the BooleanProperty class from the JavaFX library as a private final member variable.
     * </p>
     *
     * @see BooleanProperty
     */
    private final BooleanProperty bestandenProperty;
    /**
     * Represents the form of an exam.
     *
     * <p>
     * The Prüfungsform class is used to store information about the form of an exam,
     * such as written, oral, practical, etc.
     *
     * <p>
     * The value of this variable is private and can only be accessed through getter and setter methods.
     * The variable stores an instance of the Prüfungsform class.
     *
     * @since 1.0
     */
    private Prüfungsform prüfungsform;
    /**
     * The fachID variable represents the unique identifier for a subject.
     *
     * This variable is of type int and is private, meaning it can only be accessed within the class it is declared in.
     * The fachID is used to identify and distinguish different subjects in the system.
     *
     * It is important to note that the exact usage and meaning of the fachID may vary depending on the context in which it is used.
     *
     * @see #getFachID()
     * @see #setFachID(int)
     */
    private int fachID;

    /**
     * Creates a new Prüfung object.
     *
     * @param ID           the ID of the Prüfung
     * @param prüfungsform the Prüfungsform of the Prüfung
     * @param fachID       the fachID of the Prüfung
     * @param bestanden    the bestanden status of the Prüfung
     */
    public Prüfung(int ID, Prüfungsform prüfungsform, int fachID, boolean bestanden) {
        super(ID);
        this.prüfungsform = prüfungsform;
        this.fachID = fachID;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID >= prüfungCounter) prüfungCounter = ID + 1;
    }

    /**
     * Returns the value of the static variable 'prüfungCounter'.
     * The 'prüfungCounter' keeps track of the number of times
     * the 'Prüfung' object is created.
     *
     * @return the value of the 'prüfungCounter' variable.
     */
    public static int getPrüfungCounter() {
        return prüfungCounter;
    }

    /**
     * Checks if the Prüfung is bestanden.
     * It retrieves all Prüfungsversuche associated with this Prüfung and checks if all
     * of them are bestanden. If there are no Prüfungsversuche for this Prüfung, the method
     * checks the isBestanden status of the associated Fach.
     *
     * @return true if the Prüfung is bestanden, false otherwise.
     */
    public boolean isBestanden() {
        //get all prüfungsversuche to this prüfung and check if all are bestanden
        var versuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungsversuch -> prüfungsversuch.getPrüfung().getID() == this.getID()).toList();
        if (versuche.isEmpty()) {
            getFach().isBestanden();
            return bestandenProperty.get();
        }
        bestandenProperty.set(versuche.stream().allMatch(it -> it.getBestandenProperty().get()));
        getFach().isBestanden();
        return bestandenProperty.get();
    }

    /**
     * Sets the "bestanden" property of this Prüfung.
     * Updates all associated Prüfungsversuche to set their "bestanden" property to the same value.
     * Also checks if the associated Fach is considered "bestanden".
     *
     * @param bestanden {@code true} if the Prüfung is considered passed, {@code false} otherwise.
     */
    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        //go through all Prüfungsversuche and set them as bestanden
        var versuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungsversuch -> prüfungsversuch.getPrüfung().getID() == this.getID()).toList();
        versuche.forEach(prüfungsversuch -> prüfungsversuch.setBestanden(bestanden));
        getFach().isBestanden();
    }

    /**
     * Returns the Fach associated with this Prüfung.
     *
     * @return the Fach associated with this Prüfung, or null if no Fach is found.
     */
    public Fach getFach() {
        return Utility.getInstance().getFächer().stream().filter(fach -> fach.getID() == fachID).findFirst().orElse(null);
    }
}

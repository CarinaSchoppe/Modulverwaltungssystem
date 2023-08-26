package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

/**
 * Represents a Praktikum.
 */
@Data
public class Praktikum {

    /**
     * Variable to keep track of the number of praktikums.
     * This variable is private and static, ensuring that it can only be accessed within the current class.
     * The initial value is set to 0.
     *
     * @see #incrementPraktikumCounter()
     * @see #getPraktikumCounter()
     * @see #resetPraktikumCounter()
     */
    private static int praktikumCounter = 0;
    /**
     * Represents a unique identifier.
     * <p>
     * This variable is used to store a unique identifier for an object. Once the identifier is assigned, it cannot be modified.
     * <p>
     * The variable is declared as private to encapsulate the implementation details and ensure that only the class it belongs
     * to can modify its value. It is also marked as final to prevent any modifications after the initial assignment.
     */
    private final int ID;
    /**
     * A boolean property representing the status of whether the file has been processed successfully.
     *
     * This property is read-only and cannot be modified directly. It can only be accessed through its
     * getter method.
     *
     * @return The status of whether the file has been processed successfully.
     */
    private final BooleanProperty bestandenProperty;
    /**
     * Stores the ID of the specific subject.
     * This variable is used to uniquely identify the subject.
     * The subject ID is private to ensure encapsulation.
     *
     * @since 1.0
     */
    private int fachID;

    /**
     * Create a new instance of the Praktikum class.
     *
     * @param ID         the ID of the Praktikum
     * @param bestanden  indicate whether the Praktikum is successfully completed
     * @param fachID     the ID of the Fach associated with the Praktikum
     */
    public Praktikum(int ID, boolean bestanden, int fachID) {
        this.ID = ID;
        this.fachID = fachID;
        this.bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID >= praktikumCounter) praktikumCounter = ID + 1;
    }

    /**
     * Returns the value of the praktikumCounter.
     * The praktikumCounter maintains a count of all Praktikum instances created so far.
     *
     * @return the value of the praktikumCounter
     */
    public static int getPraktikumCounter() {
        return praktikumCounter;
    }

    /**
     * Sets the value of the praktikumCounter variable.
     * This method is used to update the value of the praktikumCounter variable.
     *
     * @param praktikumCounter The new value for the praktikumCounter variable.
     */
    public static void setPraktikumCounter(int praktikumCounter) {
        Praktikum.praktikumCounter = praktikumCounter;
    }

    /**
     * Checks if the specified Praktikum is passed or not.
     *
     * @return true if the Praktikum is passed, false otherwise.
     */
    public boolean isBestanden() {
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktikumstermin.getPraktikum().getID() == this.ID).toList();
        if (praktikumstermine.isEmpty()) {
            getFach().isBestanden();
            return bestandenProperty.get();
        }
        bestandenProperty.set(praktikumstermine.stream().allMatch(it -> it.getBestandenProperty().get()));
        getFach().isBestanden();
        return bestandenProperty.get();
    }


    /**
     * Sets the "bestanden" property of the Praktikum object.
     *
     * @param bestanden The value indicating whether the Praktikum is passed or not.
     */
    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        //go through all praktikumstermine and set them as bestanden
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktikumstermin.getPraktikum().getID() == this.ID).toList();
        praktikumstermine.forEach(praktikumstermin -> praktikumstermin.setBestanden(bestanden));
        getFach().isBestanden();
    }

    /**
     * Retrieves the Fach associated with this object.
     *
     * @return The Fach object associated with this object, or null if the Fach is not found.
     */
    public Fach getFach() {
        return Utility.getInstance().getFächer().stream().filter(fach -> fach.getID() == fachID).findFirst().orElse(null);
    }
}

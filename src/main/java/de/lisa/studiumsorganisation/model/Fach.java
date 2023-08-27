package de.lisa.studiumsorganisation.model;


import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a course, or "Fach" in German.
 */
@Getter
@Setter
public class Fach extends Basemodel {

    /**
     * The fachCounter variable is an integer that represents the counter of fach objects.
     * It can be used to keep track of the number of fach objects that have been created.
     * <p>
     * This variable is marked with the @Getter annotation, which automatically generates a getter method for accessing its value.
     * The getter method allows other classes to retrieve the current value of the fachCounter.
     * <p>
     * Usage example:
     * int counter = fachCounter;
     * <p>
     * Note: This variable is marked as private and static, meaning that it can only be accessed within the class and can be shared across multiple instances of the class.
     */
    @Getter
    private static int fachCounter = 0;
    /**
     * Represents a unique identifier.
     */
    /**
     * Represents a BooleanProperty indicating whether the file has been bestanden.
     *
     * <p>
     * The bestandenProperty variable is used to track the state of whether the file has been bestanden or not.
     *
     * <p>
     * It is a private final variable of type BooleanProperty, which is a specialization of Property 
     * that has an underlying primitive boolean value. The final modifier ensures that the reference cannot be changed
     * once it has been assigned a value.
     *
     * <p>
     * The value of the bestandenProperty can be modified using its setValue(boolean) method, and can be retrieved using
     * its getValue() method. Other features such as listeners and bindings can also be utilized to track changes
     * to the value of the property.
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * // Create a new BooleanProperty with an initial value of false
     * BooleanProperty bestandenProperty = new SimpleBooleanProperty(false);
     *
     * // Add a listener to track changes to the value of the property
     * bestandenProperty.addListener((observable, oldValue, newValue) -> {
     *     System.out.println("Value changed from " + oldValue + " to " + newValue);
     * });
     *
     * // Set the value of the property to true
     * bestandenProperty.setValue(true);
     * }</pre>
     */
    private final BooleanProperty bestandenProperty;
    /**
     * The name of an object.
     */
    private String name;
    /**
     * Represents the semester of a course.
     *
     * This variable is used to store the semester number of a course. It is a private
     * integer variable that should only be accessed and modified through appropriate
     * getter and setter methods.
     *
     * The semester number typically represents the order in which the course is offered
     * within an academic year. For example, the first semester may represent the fall
     * semester, while the second semester may represent the spring semester.
     */
    private int semester;
    /**
     * Represents the status of a file being passed as an argument.
     * This variable is used to indicate whether the file has been successfully processed or not.
     *
     * <p>
     * <b>NOTE:</b> This variable should only be used within the scope of its containing class.
     * </p>
     *
     * @see MyClass
     */
    private boolean bestanden;
    /**
     * The number of credits associated with a specific object.
     * This variable stores an integer value representing the amount of credits.
     *
     * <p>
     * This variable is private and can only be accessed within the class where it is declared.
     * </p>
     *
     * @see ClassWhereVariableIsDeclared
     */
    private int credits;
    /**
     * The module ID.
     *
     * This private variable represents the unique identifier for a module.
     * It is an integer value that is used internally within the system.
     */
    private int modulID;

    /**
     * Creates a new Fach object with the specified ID, name, semester, completion status, credits, and module ID.
     *
     * @param ID the ID of the Fach
     * @param name the name of the Fach
     * @param semester the semester of the Fach
     * @param bestanden the completion status of the Fach
     * @param credits the credits of the Fach
     * @param modulID the module ID of the Fach
     */
    public Fach(int ID, String name, int semester, boolean bestanden, int credits, int modulID) {
        super(ID);
        this.name = name;
        this.semester = semester;
        this.bestanden = bestanden;
        this.credits = credits;
        this.modulID = modulID;
        if (ID >= fachCounter) fachCounter = ID + 1;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
    }

    /**
     * Checks whether the Fach (subject) is passed or not.
     * This method evaluates the passing status of the Fach based on its related Prüfungen (exams) and Praktika (practicals).
     *
     * @return true if the Fach is passed, false otherwise.
     */
    public boolean isBestanden() {
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getFach().getID() == this.getID()).toList();
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFach().getID() == this.getID()).toList();

        if (prüfungen.isEmpty() && praktika.isEmpty()) {
            getModul().isBestanden();
            return bestandenProperty.get();
        } else if (praktika.isEmpty()) {
            bestandenProperty.set(prüfungen.stream().allMatch(it -> it.getBestandenProperty().get()));
            getModul().isBestanden();
            return bestandenProperty.get();
        } else if (prüfungen.isEmpty()) {
            bestandenProperty.set(praktika.stream().allMatch(it -> it.getBestandenProperty().get()));
            getModul().isBestanden();
            return bestandenProperty.get();
        }
        bestandenProperty.set(prüfungen.stream().allMatch(it -> it.getBestandenProperty().get()) && praktika.stream().allMatch(it -> it.getBestandenProperty().get()));
        getModul().isBestanden();
        return bestandenProperty.get();
    }

    /**
     * Sets the "bestanden" flag for this Fach and all associated Prüfungen and Praktika.
     *
     * @param bestanden The new value for the "bestanden" flag
     */
    public void setBestanden(boolean bestanden) {
        //get all prüfungen to this fach and set them as bestanden
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getFach().getID() == this.getID()).toList();
        prüfungen.forEach(prüfung -> prüfung.setBestanden(bestanden));
        //get all praktika to this fach and set them as bestanden
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFach().getID() == this.getID()).toList();
        praktika.forEach(praktikum -> praktikum.setBestanden(bestanden));
        this.bestanden = bestanden;
        bestandenProperty.set(bestanden);
        getModul().isBestanden();
    }

    /**
     * Retrieves the module associated with this subject.
     *
     * @return The module associated with this subject, or null if no module is found.
     */
    public Modul getModul() {
        return Utility.getInstance().getModule().stream().filter(modul -> modul.getID() == modulID).findFirst().orElse(null);
    }


}

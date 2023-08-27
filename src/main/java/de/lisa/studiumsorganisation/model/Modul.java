package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a module.
 * <p>
 * A module encapsulates information about a specific subject or topic covered in a study program.
 * It keeps track of related courses, exams, and practicals.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Modul extends Basemodel {

    /**
     * The modulCounter class keeps track of the count of a particular module.
     * This variable is private and static, meaning it can only be accessed within the class it is defined in,
     * and it is shared among all instances of the class.
     */
    private static int modulCounter = 0;

    /**
     * The bestandenProperty is a private final BooleanProperty.
     *
     * <p>
     * This variable represents a boolean property that determines whether
     * a file has been successfully loaded or saved.
     * </p>
     *
     * <p>
     * The variable is declared as private final to ensure immutability.
     * </p>
     *
     * <p>
     * As a BooleanProperty, it provides bindings and observability features
     * for other properties or UI elements that depend on its value.
     * </p>
     *
     * <p>
     * This variable should be used to track the loaded or saved status of a file
     * in your code. It can be accessed using getter methods or used in bindings
     * as necessary.
     * </p>
     */
    private final BooleanProperty bestandenProperty;
    /**
     * A private final BooleanProperty used for indicating whether the praktika
     * has been completed or not.
     */
    private final BooleanProperty praktikaBestandenProperty;
    /**
     * The private final BooleanProperty pruefungBestandenProperty represents a boolean property indicating whether the pruefung (exam) has been passed or not.
     *
     * <p>
     * The value of pruefungBestandenProperty can be set or retrieved using appropriate getter and setter methods.
     * </p>
     *
     * @since the current version of the software
     *
     * @see BooleanProperty
     */
    private final BooleanProperty pruefungBestandenProperty;
    /**
     * The name of the variable.
     */
    private String name;
    /**
     * Represents the identifier of the academic program of a student.
     * The studiengangID is a private variable that holds the value of the academic program identifier.
     * The integer value of the studiengangID can be any non-negative number.
     *
     * Usage:
     * The studiengangID is accessed and modified by getter and setter methods defined in the enclosing class.
     */
    private int studiengangID;

    /**
     * Constructs a new Modul with the specified properties.
     *
     * @param ID The ID of the Modul.
     * @param name The name of the Modul.
     * @param bestanden Indicates if the Modul is passed.
     * @param studiengangID The ID of the Studiengang associated with the Modul.
     */
    public Modul(int ID, String name, boolean bestanden, int studiengangID) {
        super(ID);
        this.name = name;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        praktikaBestandenProperty = new SimpleBooleanProperty(getPraktikaBestanden());
        pruefungBestandenProperty = new SimpleBooleanProperty(getPrüfungBestanden());
        this.studiengangID = studiengangID;
        if (ID >= modulCounter) modulCounter = ID + 1;
    }

    /**
     * Returns the value of the modulCounter variable.
     *
     * @return the value of the modulCounter
     */
    public static int getModulCounter() {
        return modulCounter;
    }

    /**
     * Retrieves the Studiengang associated with this Modul.
     *
     * @return The Studiengang object associated with this Modul, or null if no association exists.
     */
    public Studiengang getStudiengang() {
        return Utility.getInstance().getStudiengänge().stream().filter(studiengang -> studiengang.getID() == studiengangID).findFirst().orElse(null);
    }

    /**
     * Determines whether the Prüfung (exam) is passed or not.
     *
     * It finds all Prüfungen related to this module and checks if all of them are passed.
     * If no related Prüfungen or all of them are not passed, it returns false.
     *
     * @return true if all related Prüfungen are passed, otherwise false.
     */
    public boolean getPrüfungBestanden() {
        //find all prüfungen related to this module and than check if all of them are true if they exist if not than its false
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
        if (fächer.isEmpty()) return false;

        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
        if (prüfungen.isEmpty()) return false;
        //check all prüfungen related to the fächer if they are all bestanden than return true
        return prüfungen.stream().allMatch(Prüfung::isBestanden);
    }

    /**
     * Sets the "bestanden" status for all related exams.
     *
     * @param bestanden the status to set for the exams
     */
    public void setPrüfungenBestanden(Boolean bestanden) {
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
        if (fächer.isEmpty()) return;
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
        prüfungen.forEach(prüfung -> prüfung.setBestanden(bestanden));
    }

    /**
     * Retrieves the status of whether all praktika related to this module have been passed.
     *
     * @return true if all praktika related to this module have been passed, false otherwise
     */
    public boolean getPraktikaBestanden() {
        //find all prüfungen related to this module and than check if all of them are true if they exist if not than its false
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
        if (fächer.isEmpty()) return false;

        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
        if (praktika.isEmpty()) return false;
        //check all prüfungen related to the fächer if they are all bestanden than return true
        return praktika.stream().allMatch(Praktikum::isBestanden);
    }

    /**
     * Sets the "bestanden" status of all Praktikum objects related to this module.
     *
     * @param bestanden the new "bestanden" status to be set
     */
    public void setPraktikaBestanden(Boolean bestanden) {
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
        if (fächer.isEmpty()) return;
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
        praktika.forEach(praktikum -> praktikum.setBestanden(bestanden));
    }

    /**
     * Checks if all associated subjects in the module are passed.
     *
     * @return true if all associated subjects are passed, false otherwise.
     */
    public boolean isBestanden() {
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
        if (fächer.isEmpty()) {
            return bestandenProperty.get();
        }
        bestandenProperty.set(fächer.stream().allMatch(it -> it.getBestandenProperty().get()));
        return bestandenProperty.get();
    }

    /**
     * Sets whether the module is passed or not.
     *
     * @param bestanden true if the module is passed, false otherwise
     */
    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();

        if (fächer.isEmpty()) return;
        fächer.forEach(fach -> fach.setBestanden(bestanden));
    }

    /**
     * Returns a string representation of the Modul object, including all associated Fächer, Prüfungen, Praktika, Prüfungsversuche, and Praktikumstermine.
     *
     * @return a string representation of the Modul object and its associated elements.
     */
    @Override
    public String toString() {
        //gib alle alemente der klasse modul aus sowie alle mit dem modul zusammenhängenden fächer und deren prüfungen und praktika aus sowie deren termine und versuche
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
        var prüfungsversuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungstermin -> prüfungen.stream().anyMatch(prüfung -> prüfung.getID() == prüfungstermin.getPrüfung().getID())).toList();
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktika.stream().anyMatch(praktikum -> praktikum.getID() == praktikumstermin.getPraktikum().getID())).toList();

        //printe nun alle elemente aus
        var stringBuilder = new StringBuilder();
        stringBuilder.append("Modul: ").append(name).append("\n");
        stringBuilder.append("ID: ").append(getID()).append("\n");
        stringBuilder.append("Bestanden: ").append(bestandenProperty.get()).append("\n");
        stringBuilder.append("Fächer: ").append("\n");
        fächer.forEach(fach -> {
            stringBuilder.append("\t").append(fach.getName()).append("\n");
            stringBuilder.append("\t").append(fach.getID()).append("\n");
            stringBuilder.append("\t").append("Bestanden: ").append(fach.isBestanden()).append("\n");
            stringBuilder.append("\t").append("Prüfungen: ").append("\n");
            prüfungen.forEach(prüfung -> {
                stringBuilder.append("\t\t").append(prüfung.getID()).append("\n");
                stringBuilder.append("\t\t").append(prüfung.getPrüfungsform().getText()).append("\n");
                stringBuilder.append("\t\t").append("Bestanden: ").append(prüfung.isBestanden()).append("\n");
                stringBuilder.append("\t\t").append("Prüfungsversuche: ").append("\n");
                prüfungsversuche.forEach(prüfungsversuch -> {
                    stringBuilder.append("\t\t\t").append(prüfungsversuch.getID()).append("\n");
                    stringBuilder.append("\t\t\t").append(prüfungsversuch.getDatum()).append("\n");
                    stringBuilder.append("\t\t\t").append("Bestanden: ").append(prüfungsversuch.isBestanden()).append("\n");
                });
            });
            stringBuilder.append("\t").append("Praktika: ").append("\n");
            praktika.forEach(praktikum -> {
                stringBuilder.append("\t\t").append(praktikum.getID()).append("\n");
                stringBuilder.append("\t\t").append("Bestanden: ").append(praktikum.isBestanden()).append("\n");
                stringBuilder.append("\t\t").append("Praktikumstermine: ").append("\n");
                praktikumstermine.forEach(praktikumstermin -> {
                    stringBuilder.append("\t\t\t").append(praktikumstermin.getID()).append("\n");
                    stringBuilder.append("\t\t\t").append(praktikumstermin.getDatum()).append("\n");
                    stringBuilder.append("\t\t\t").append("Bestanden: ").append(praktikumstermin.isBestanden()).append("\n");
                });
            });
        });
        return stringBuilder.toString();
    }

    public List<Fach> getFächer() {
        return Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.getID()).toList();
    }
}

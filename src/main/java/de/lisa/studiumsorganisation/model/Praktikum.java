package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

@Data
public class Praktikum {

    private final int ID;
    private boolean bestanden;
    private final BooleanProperty bestandenProperty;
    private static int praktikumCounter = 0;
    private int fachID;

    public Praktikum(int ID, boolean bestanden, int fachID) {
        this.ID = ID;
        this.bestanden = bestanden;
        this.fachID = fachID;
        this.bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID > praktikumCounter) praktikumCounter = ID + 1;
    }

    public static int getPraktikumCounter() {
        return praktikumCounter;
    }

    public static void setPraktikumCounter(int praktikumCounter) {
        Praktikum.praktikumCounter = praktikumCounter;
    }

    public boolean isBestanden() {
        return bestandenProperty.get();
    }


    public void setBestanden(boolean bestanden) {
        this.bestanden = bestanden;
        bestandenProperty.set(bestanden);
        //go through all praktikumstermine and set them as bestanden
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktikumstermin.getPraktikum().getID() == this.ID).toList();
        praktikumstermine.forEach(praktikumstermin -> praktikumstermin.setBestanden(bestanden));
    }

    public Fach getFach() {
        return Utility.getInstance().getFächer().stream().filter(fach -> fach.getID() == fachID).findFirst().orElse(null);
    }
}

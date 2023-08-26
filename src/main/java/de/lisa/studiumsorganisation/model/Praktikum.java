package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

@Data
public class Praktikum {

    private final int ID;
    private final BooleanProperty bestandenProperty;
    private static int praktikumCounter = 0;
    private int fachID;

    public Praktikum(int ID, boolean bestanden, int fachID) {
        this.ID = ID;
        this.fachID = fachID;
        this.bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID >= praktikumCounter) praktikumCounter = ID + 1;
    }

    public static int getPraktikumCounter() {
        return praktikumCounter;
    }

    public static void setPraktikumCounter(int praktikumCounter) {
        Praktikum.praktikumCounter = praktikumCounter;
    }

    public boolean isBestanden() {
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktikumstermin.getPraktikum().getID() == this.ID).toList();
        if (praktikumstermine.isEmpty()) return false;
        bestandenProperty.set(praktikumstermine.stream().allMatch(it -> it.getBestandenProperty().get()));
        getFach().isBestanden();
        return bestandenProperty.get();
    }


    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        //go through all praktikumstermine and set them as bestanden
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktikumstermin.getPraktikum().getID() == this.ID).toList();
        praktikumstermine.forEach(praktikumstermin -> praktikumstermin.setBestanden(bestanden));
        getFach().isBestanden();
    }

    public Fach getFach() {
        return Utility.getInstance().getFächer().stream().filter(fach -> fach.getID() == fachID).findFirst().orElse(null);
    }
}

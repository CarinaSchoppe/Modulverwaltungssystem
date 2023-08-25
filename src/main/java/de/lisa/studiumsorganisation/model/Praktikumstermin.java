package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Praktikumstermin {

    private static int praktikumsterminCounter = 0;
    private final int ID;
    private final BooleanProperty bestandenProperty;
    private int praktikumID;
    private Date datum;
    private Time uhrzeit;
    private boolean bestanden;

    public Praktikumstermin(int ID, int praktikumID, Date datum, Time uhrzeit, boolean bestanden) {
        this.ID = ID;
        this.praktikumID = praktikumID;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.bestanden = bestanden;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID >= praktikumsterminCounter) praktikumsterminCounter = ID + 1;
    }

    public boolean isBestanden() {
        return bestandenProperty.get();
    }

    public void setBestanden(boolean bestanden) {
        this.bestanden = bestanden;
        bestandenProperty.set(bestanden);
    }

    public static int getPraktikumsterminCounter() {
        return praktikumsterminCounter;
    }

    public Praktikum getPraktikum() {
        return Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getID() == praktikumID).findFirst().orElse(null);
    }

}

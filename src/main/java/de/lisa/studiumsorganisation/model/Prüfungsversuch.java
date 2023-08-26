package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Prüfungsversuch {

    private static int prüfungsversuchCounter = 0;
    private final BooleanProperty bestandenProperty;
    private int ID;
    private Date datum;
    private Time uhrzeit;
    private float note;
    private int prüfungsID;

    public Prüfungsversuch(int ID, Date datum, Time uhrzeit, boolean bestanden, float note, int prüfungsID) {
        this.ID = ID;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.note = note;
        this.prüfungsID = prüfungsID;
        if (ID >= prüfungsversuchCounter) prüfungsversuchCounter = ID + 1;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
    }

    public boolean isBestanden() {
        return bestandenProperty.get();
    }


    public static int getPrüfungsversuchCounter() {
        return prüfungsversuchCounter;
    }

    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
    }

    public Prüfung getPrüfung() {
        return Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getID() == prüfungsID).findFirst().orElse(null);
    }
}

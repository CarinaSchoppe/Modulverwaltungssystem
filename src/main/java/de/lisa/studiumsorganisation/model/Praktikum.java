package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Praktikum {
    
    private int ID;
    private int modulID;
    
    private boolean bestanden;
    private Date datum;
    private int versuch;
    private Modul modul;
    private static int praktikumCounter = 0;

    public Praktikum(int ID, int modulID, boolean bestanden, Date datum, int versuch, Modul modul) {
        this.ID = ID;
        this.modulID = modulID;
        this.bestanden = bestanden;
        this.datum = datum;
        this.versuch = versuch;
        this.modul = modul;
        if (praktikumCounter < ID) {
            praktikumCounter = ID + 1;
        }
        bestandenProperty = new SimpleBooleanProperty(bestanden);
    }

    public static int getPraktikumCounter() {
        return praktikumCounter;
    }

    private final BooleanProperty bestandenProperty;

    public boolean isBestanden() {
        return bestandenProperty.get();
    }

    public void setBestanden(boolean bestanden) {
        this.bestanden = bestanden;
        bestandenProperty.set(bestanden);
    }
    public String getDatumString() {
        final var sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(getDatum());
    }
}

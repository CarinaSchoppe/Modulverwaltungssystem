package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Prüfung {
    
    private int ID;
    private int modulID;
    private Prüfungsform prüfungsform;
    private static int prüfungCounter = 0;
    private int versuch;
    private Date datum;
    private float note;
    private boolean bestanden;
    private Modul modul;

    private final BooleanProperty bestandenProperty;

    public Prüfung(int ID, int modulID, Prüfungsform prüfungsform, int versuch, Date datum, float note, boolean bestanden, Modul modul) {
        this.ID = ID;
        this.modulID = modulID;
        this.prüfungsform = prüfungsform;
        this.versuch = versuch;
        this.datum = datum;
        this.note = note;
        this.bestanden = bestanden;
        this.modul = modul;
        if (prüfungCounter < ID) {
            prüfungCounter = ID + 1;
        }
        bestandenProperty = new SimpleBooleanProperty(bestanden);
    }

    public static int getPrüfungCounter() {
        return prüfungCounter;
    }

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

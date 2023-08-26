package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

@Data
public class Prüfung {

    private final int ID;
    private Prüfungsform prüfungsform;
    private static int prüfungCounter = 0;
    private final BooleanProperty bestandenProperty;
    private int fachID;

    public Prüfung(int ID, Prüfungsform prüfungsform, int fachID, boolean bestanden) {
        this.ID = ID;
        this.prüfungsform = prüfungsform;
        this.fachID = fachID;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (ID >= prüfungCounter) prüfungCounter = ID + 1;
    }

    public boolean isBestanden() {
        //get all prüfungsversuche to this prüfung and check if all are bestanden
        var versuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungsversuch -> prüfungsversuch.getPrüfung().getID() == this.ID).toList();
        if (versuche.isEmpty()) {
            getFach().isBestanden();
            return bestandenProperty.get();
        }
        bestandenProperty.set(versuche.stream().allMatch(it -> it.getBestandenProperty().get()));
        getFach().isBestanden();
        return bestandenProperty.get();
    }


    public static int getPrüfungCounter() {
        return prüfungCounter;
    }


    public void setBestanden(boolean bestanden) {
        bestandenProperty.set(bestanden);
        //go through all Prüfungsversuche and set them as bestanden
        var versuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungsversuch -> prüfungsversuch.getPrüfung().getID() == this.ID).toList();
        versuche.forEach(prüfungsversuch -> prüfungsversuch.setBestanden(bestanden));
        getFach().isBestanden();
    }

    public Fach getFach() {
        return Utility.getInstance().getFächer().stream().filter(fach -> fach.getID() == fachID).findFirst().orElse(null);
    }
}

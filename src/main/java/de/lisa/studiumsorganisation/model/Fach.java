package de.lisa.studiumsorganisation.model;


import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class Fach {

    @Getter
    private static int fachCounter = 0;
    private final int ID;
    private final BooleanProperty bestandenProperty;
    private String name;
    private int semester;
    private boolean bestanden;
    private int credits;
    private int modulID;

    public Fach(int ID, String name, int semester, boolean bestanden, int credits, int modulID) {
        this.ID = ID;
        this.name = name;
        this.semester = semester;
        this.bestanden = bestanden;
        this.credits = credits;
        this.modulID = modulID;
        if (ID >= fachCounter) fachCounter = ID + 1;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
    }

    public boolean isBestanden() {
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getFach().getID() == this.ID).toList();
        if (prüfungen.isEmpty()) return false;
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFach().getID() == this.ID).toList();
        if (praktika.isEmpty()) return false;
        bestandenProperty.set(prüfungen.stream().allMatch(Prüfung::isBestanden) && praktika.stream().allMatch(Praktikum::isBestanden));
        return bestandenProperty.get();
    }

    public void setBestanden(boolean bestanden) {
        //get all prüfungen to this fach and set them as bestanden
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getFach().getID() == this.ID).toList();
        prüfungen.forEach(prüfung -> prüfung.setBestanden(bestanden));
        //get all praktika to this fach and set them as bestanden
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getFach().getID() == this.ID).toList();
        praktika.forEach(praktikum -> praktikum.setBestanden(bestanden));
        this.bestanden = bestanden;
        bestandenProperty.set(bestanden);
    }

    public Modul getModul() {
        return Utility.getInstance().getModule().stream().filter(modul -> modul.getID() == modulID).findFirst().orElse(null);
    }


}

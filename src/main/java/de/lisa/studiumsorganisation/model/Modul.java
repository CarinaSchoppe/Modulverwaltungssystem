package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import lombok.Data;

@Data
public class Modul {

    private final int ID;
    private static int modulCounter = 0;
    private String name;
    private boolean bestanden;
    private int studiengangID;

    public Modul(int ID, String name, boolean bestanden, int studiengangID) {
        this.ID = ID;
        this.name = name;
        this.bestanden = bestanden;
        this.studiengangID = studiengangID;
        if (ID > modulCounter) modulCounter = ID + 1;
    }

    public Studiengang getStudiengang() {
        return Utility.getInstance().getStudiengänge().stream().filter(studiengang -> studiengang.getID() == studiengangID).findFirst().orElse(null);
    }

    public static int getModulCounter() {
        return modulCounter;
    }

    public boolean getPrüfungBestanden() {
        //find all prüfungen related to this module and than check if all of them are true if they exist if not than its false
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.ID).toList();
        if (fächer.isEmpty()) return false;

        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
        if (prüfungen.isEmpty()) return false;
        //check all prüfungen related to the fächer if they are all bestanden than return true
        return prüfungen.stream().allMatch(Prüfung::isBestanden);
    }

    public void setPrüfungenBestanden(Boolean bestanden) {
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.ID).toList();
        if (fächer.isEmpty()) return;
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
        prüfungen.forEach(prüfung -> prüfung.setBestanden(bestanden));
    }

    public boolean getPraktikaBestanden() {
        //find all prüfungen related to this module and than check if all of them are true if they exist if not than its false
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.ID).toList();
        if (fächer.isEmpty()) return false;

        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
        if (praktika.isEmpty()) return false;
        //check all prüfungen related to the fächer if they are all bestanden than return true
        return praktika.stream().allMatch(Praktikum::isBestanden);
    }

    public void setPraktikaBestanden(Boolean bestanden) {
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.ID).toList();
        if (fächer.isEmpty()) return;
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
        praktika.forEach(praktikum -> praktikum.setBestanden(bestanden));
    }

    public void setBestanden(boolean bestanden) {
        this.bestanden = bestanden;
        if (bestanden) {
            this.bestanden = true;
            setPraktikaBestanden(true);
            setPrüfungenBestanden(true);
        }
    }
}

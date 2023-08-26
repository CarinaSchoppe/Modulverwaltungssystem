package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

@Data
public class Modul {

    private final int ID;
    private static int modulCounter = 0;
    private String name;
    private int studiengangID;
    private final BooleanProperty bestandenProperty;
    private final BooleanProperty praktikaBestandenProperty;
    private final BooleanProperty pruefungBestandenProperty;

    public Modul(int ID, String name, boolean bestanden, int studiengangID) {
        this.ID = ID;
        this.name = name;
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        praktikaBestandenProperty = new SimpleBooleanProperty(getPraktikaBestanden());
        pruefungBestandenProperty = new SimpleBooleanProperty(getPrüfungBestanden());
        this.studiengangID = studiengangID;
        if (ID >= modulCounter) modulCounter = ID + 1;
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
        bestandenProperty.set(bestanden);
        if (bestanden) {
            setPraktikaBestanden(true);
            setPrüfungenBestanden(true);
        }
    }

    public boolean isBestanden() {
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.ID).toList();
        if (fächer.isEmpty()) return false;
        bestandenProperty.set(fächer.stream().allMatch(Fach::isBestanden));
        return bestandenProperty.get();
    }

    @Override
    public String toString() {
        //gib alle alemente der klasse modul aus sowie alle mit dem modul zusammenhängenden fächer und deren prüfungen und praktika aus sowie deren termine und versuche
        var fächer = Utility.getInstance().getFächer().stream().filter(fach -> fach.getModul().getID() == this.ID).toList();
        var prüfungen = Utility.getInstance().getPrüfungen().stream().filter(prüfung -> fächer.stream().anyMatch(fach -> fach.getID() == prüfung.getFachID())).toList();
        var praktika = Utility.getInstance().getPraktika().stream().filter(praktikum -> fächer.stream().anyMatch(fach -> fach.getID() == praktikum.getFachID())).toList();
        var prüfungsversuche = Utility.getInstance().getPrüfungsversuche().stream().filter(prüfungstermin -> prüfungen.stream().anyMatch(prüfung -> prüfung.getID() == prüfungstermin.getPrüfung().getID())).toList();
        var praktikumstermine = Utility.getInstance().getPraktikumstermine().stream().filter(praktikumstermin -> praktika.stream().anyMatch(praktikum -> praktikum.getID() == praktikumstermin.getPraktikum().getID())).toList();

        //printe nun alle elemente aus
        var stringBuilder = new StringBuilder();
        stringBuilder.append("Modul: ").append(name).append("\n");
        stringBuilder.append("ID: ").append(ID).append("\n");
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
}

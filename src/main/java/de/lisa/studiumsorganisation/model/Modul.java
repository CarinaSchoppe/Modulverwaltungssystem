package de.lisa.studiumsorganisation.model;


import de.lisa.studiumsorganisation.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Modul {

    private int ID;
    private String name;
    private int semester;
    private boolean bestanden;

    public Praktikum getPraktikum() {
        return Utility.getInstance().getPraktika().stream().filter(praktikum -> praktikum.getModulID() == ID).findFirst().orElse(null);
    }

    public Prüfung getPrüfung() {
        return Utility.getInstance().getPrüfungen().stream().filter(prüfung -> prüfung.getModulID() == ID).findFirst().orElse(null);
    }

    public String getPrüfungTerminString() {
        if (getPrüfung() == null) {
            return "Keine Prüfung";
        }
        return getPrüfung().getDatum().toString();
    }

    public boolean getPraktikumBestanden() {
        if (getPraktikum() == null) {
            return false;
        }
        return getPraktikum().isBestanden();
    }


}

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
}

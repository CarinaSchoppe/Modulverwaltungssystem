package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Prüfung {
    
    private int ID;
    private int modulID;
    private Prüfungsform prüfungsform;
    
    private int versuch;
    private Date datum;
    private float note;
    private boolean bestanden;

    public Modul getModul(){
        return Utility.getInstance().getModule().stream().filter(modul -> modul.getID() == modulID).findFirst().orElse(null);
    }
    
}

package de.lisa.studiumsorganisation.model;

import de.lisa.studiumsorganisation.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Praktikum {
    
    private int ID;
    private int modulID;
    
    private boolean bestanden;
    private Date datum;
    private int versuch;
    
    public Modul getModul(){
        return Utility.getInstance().getModule().stream().filter(modul -> modul.getID() == modulID).findFirst().orElse(null);
    }
    
}

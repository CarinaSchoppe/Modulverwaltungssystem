package de.lisa.studiumsorganisation;

import de.lisa.studiumsorganisation.view.MainUI;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        //lade Datenbank
        dummyData();
        //Database.getInstance().loadAllData();
        //starte UI
        MainUI.start(args);

        //TODO: boolean sind nicht gespeichert bei der UI
    }

    private static void dummyData() {

        //Create a new date with current date as its value
        var date = new Date();
    
    }
}

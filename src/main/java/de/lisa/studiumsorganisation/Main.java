package de.lisa.studiumsorganisation;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.view.MainUI;

/**
 *
 */
public class Main {

    private static final boolean dummyLaunch = true;

    public static boolean isDummyLaunch() {
        return dummyLaunch;
    }

   
    public static void main(String[] args) {
        //lade Datenbank
        if (dummyLaunch) {
            System.out.println("Dummy data loaded");
        }
        else
            Database.getInstance().loadAllData();
        //starte UI
        MainUI.start(args);

        //TODO: boolean sind nicht gespeichert bei der UI
    }


}

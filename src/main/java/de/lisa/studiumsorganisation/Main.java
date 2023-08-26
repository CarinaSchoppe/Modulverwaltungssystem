package de.lisa.studiumsorganisation;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.view.MainUI;

import java.util.Date;

public class Main {

    private static boolean dummyLaunch = true;

    public static boolean isDummyLaunch() {
        return dummyLaunch;
    }

    public static void setDummyLaunch(boolean dummyLaunch) {
        Main.dummyLaunch = dummyLaunch;
    }

    public static void main(String[] args) {
        //lade Datenbank
        if (dummyLaunch)
            dummyData();
        else
            Database.getInstance().loadAllData();
        //starte UI
        MainUI.start(args);

        //TODO: boolean sind nicht gespeichert bei der UI
    }

    private static void dummyData() {

        //Create a new date with current date as its value
        var date = new Date();

    }
}

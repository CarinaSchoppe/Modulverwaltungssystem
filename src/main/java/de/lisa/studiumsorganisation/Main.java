package de.lisa.studiumsorganisation;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Praktikum;
import de.lisa.studiumsorganisation.model.Prüfung;
import de.lisa.studiumsorganisation.model.Prüfungsform;
import de.lisa.studiumsorganisation.util.Utility;
import de.lisa.studiumsorganisation.view.ModulUI;

import javax.xml.crypto.Data;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        //lade Datenbank
        dummyData();
        //Database.getInstance().loadAllData();
        //starte UI
        ModulUI.start();
    }

    private static void dummyData() {
        Utility.getInstance().getModule().add(new Modul(1, "Mathe", 1, true, null));
        Utility.getInstance().getModule().add(new Modul(2, "Physik", 1, false, null));
        Utility.getInstance().getModule().add(new Modul(3, "Informatik", 1, false, null));

        //Create a new date with current date as its value
        Date date = new Date();
        Utility.getInstance().getPrüfungen().add(new Prüfung(1, 1, Prüfungsform.KLAUSUR, 0, new Date(), 1.0f, true, null));
        Utility.getInstance().getPrüfungen().add(new Prüfung(2, 2, Prüfungsform.KLAUSUR, 0, new Date(), 1.0f, true, null));
        Utility.getInstance().getPrüfungen().add(new Prüfung(3, 3, Prüfungsform.KLAUSUR, 0, new Date(), 1.0f, true, null));

        Utility.getInstance().getPraktika().add(new Praktikum(1, 1, true, date, 1, null));
        Utility.getInstance().getPraktika().add(new Praktikum(2, 2, true, date, 2, null));
        Utility.getInstance().getPraktika().add(new Praktikum(3, 3, true, date, 3, null));

        Database.addAllElements();
    }
}

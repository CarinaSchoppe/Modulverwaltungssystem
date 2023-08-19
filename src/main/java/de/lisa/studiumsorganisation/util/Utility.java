package de.lisa.studiumsorganisation.util;

import de.lisa.studiumsorganisation.controller.Database;
import de.lisa.studiumsorganisation.model.Modul;
import de.lisa.studiumsorganisation.model.Praktikum;
import de.lisa.studiumsorganisation.model.Prüfung;
import lombok.Getter;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Handler;

@Getter
public class Utility {


    private HashSet<Modul> module;
    private HashSet<Prüfung> prüfungen;
    private HashSet<Praktikum> praktika;

    private Utility() {
        module = new HashSet<>();
        prüfungen = new HashSet<>();
        praktika = new HashSet<>();
        instance = this;
    }

    private static Utility instance;

    public static Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

   
}

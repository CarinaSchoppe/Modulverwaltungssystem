package de.lisa.studiumsorganisation.util;

import de.lisa.studiumsorganisation.model.*;
import lombok.Getter;

import java.util.HashSet;

@Getter
public class Utility {


    private final HashSet<Modul> module;
    private final HashSet<Prüfung> prüfungen;
    private final HashSet<Praktikum> praktika;
    private final HashSet<Fach> fächer;
    private final HashSet<Praktikumstermin> praktikumstermine;
    private final HashSet<Prüfungsversuch> prüfungsversuche;
    private final HashSet<Studiengang> studiengänge;

    private Utility() {
        module = new HashSet<>();
        prüfungen = new HashSet<>();
        praktika = new HashSet<>();
        fächer = new HashSet<>();
        praktikumstermine = new HashSet<>();
        prüfungsversuche = new HashSet<>();
        studiengänge = new HashSet<>();
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

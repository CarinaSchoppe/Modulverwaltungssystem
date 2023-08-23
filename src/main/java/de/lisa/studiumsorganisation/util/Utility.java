package de.lisa.studiumsorganisation.util;

import de.lisa.studiumsorganisation.model.*;
import javafx.util.StringConverter;
import lombok.Getter;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;

@Getter
public class Utility {


    public static final StringConverter<Date> DATE_FORMATTER = new StringConverter<>() {
        @Override
        public String toString(Date object) {
            //erhält ein Date Objekt und gibt es als String zurück im format dd.MM.yyyy
            return object.toString().substring(0, 10);
        }

        @Override
        public Date fromString(String string) {
            //bekommt einen string im format dd.MM.yyyy und gibt ein Date Objekt zurück
            return new Date(string);
        }
    };
    public static final StringConverter<Time> TIME_FORMATTER = new StringConverter<>() {
        @Override
        public String toString(Time object) {
            //erhält ein Time Objekt und gibt es als String zurück im format hh:mm:sss
            return object.toString().substring(0, 8);
        }

        @Override
        public Time fromString(String string) {
            //bekommt einen string im format hh:mm:sss und gibt ein Time Objekt zurück
            var h = Integer.parseInt(string.substring(0, 2));
            var m = Integer.parseInt(string.substring(3, 5));
            var s = Integer.parseInt(string.substring(6, 8));
            return new Time(h, m, s);

        }
    };
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

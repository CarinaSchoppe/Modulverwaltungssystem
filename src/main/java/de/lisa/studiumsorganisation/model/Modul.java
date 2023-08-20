package de.lisa.studiumsorganisation.model;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Data
public class Modul {

    private static int modulCounter = 0;
    private int ID;
    private String name;
    private int semester;
    private boolean bestanden;
    private Prüfung prüfung;
    private final Set<Praktikum> praktika = new HashSet<>();

    public Modul(int ID, String name, int semester, boolean bestanden, Prüfung prüfung) {
        this.ID = ID;
        this.name = name;
        this.semester = semester;
        this.bestanden = bestanden;
        this.prüfung = prüfung;
        praktikumBestandenProperty = new SimpleBooleanProperty(getPraktikumBestanden());
        bestandenProperty = new SimpleBooleanProperty(bestanden);
        if (modulCounter < ID) {
            modulCounter = ID + 1;
        }
    }

    public static int getModulCounter() {
        return modulCounter;
    }

    private final BooleanProperty bestandenProperty;
    private final BooleanProperty praktikumBestandenProperty;

    public boolean isBestanden() {
        return bestandenProperty.get();
    }

    public void setBestanden(boolean bestanden) {
        this.bestanden = bestanden;
        bestandenProperty.set(bestanden);
    }

    public String getPrüfungTerminString() {
        if (getPrüfung() == null) {
            return "Keine Prüfung";
        }
        final var sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(getPrüfung().getDatum());
    }

    private boolean getPraktikumBestanden() {
        if (praktika.isEmpty())
            return false;
        //check if all praktika are bestanden == true
        return praktika.stream().allMatch(Praktikum::isBestanden);
    }

    public void setPraktikumBestanden(boolean bestanden) {
        praktikumBestandenProperty.set(bestanden);
        praktika.forEach(it -> it.setBestanden(bestanden));
    }

}

package de.lisa.studiumsorganisation.model;

public enum Prüfungsform {
    
    KLAUSUR("Schriftliche Klausur"),
    HAUSARBEIT("Hausarbeit"),
    PRAESENTATION("Praesentation"),
    PROJEKTARBEIT("Projektarbeit"),
    SONSTIGES("Sonstiges");

    
    private final String name;

    Prüfungsform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package de.lisa.studiumsorganisation.model;

public enum Prüfungsform {
    
    KLAUSUR("Schriftliche Klausur"),
    HAUSARBEIT("Hausarbeit"),
    PRAESENTATION("Praesentation"),
    PROJEKTARBEIT("Projektarbeit"),
    SONSTIGES("Sonstiges");

    
    private final String text;

    Prüfungsform(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

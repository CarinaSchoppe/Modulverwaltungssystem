package de.lisa.studiumsorganisation.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Studiengang {

    private static int studiengangCounter = 0;
    private int ID;
    private String studienverlaufsplan;


}

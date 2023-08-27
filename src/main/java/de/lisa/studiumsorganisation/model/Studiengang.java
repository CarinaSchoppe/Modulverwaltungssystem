package de.lisa.studiumsorganisation.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a study program.
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Studiengang extends Basemodel {

    /**
     * Represents the counter for the number of studiengangs.
     * <p>
     * The studiengangCounter is a private static variable used to keep track of
     * the number of studiengangs created.
     * </p>
     * <p>
     * This counter is incremented each time a new studiengang is instantiated.
     * </p>
     * <p>
     * Note that this is an internal implementation detail and should not be accessed
     * or modified directly by external code.
     * </p>
     *
     * @since 1.0
     */
    private static int studiengangCounter = 0;

    /**
     * The studienverlaufsplan represents the string variable that holds the study plan information.
     * It is a private variable that is accessible only within the class it is declared in.
     *
     * <p>
     * The study plan is a document that outlines the courses that a student needs to complete in order
     * to successfully graduate from a particular study program. It provides a detailed overview of the
     * required courses and their prerequisites, as well as the recommended order in which they should be taken.
     * </p>
     *
     * <p>
     * The studienverlaufsplan variable stores the study plan as a string. Typically, this string would contain
     * structured data, such as JSON or XML, that can be parsed and processed by the application. The specific format
     * and content of the string depends on the study program and the organization that provides the study plan.
     * </p>
     *
     * <p>
     * Access to the studienverlaufsplan variable is restricted to the class that declares it. Other parts of the
     * application can access it indirectly, through getter and setter methods provided by the class.
     * </p>
     */
    private String studienverlaufsplan;

    public Studiengang(int ID, String studienverlaufsplan) {
        super(ID);
        this.studienverlaufsplan = studienverlaufsplan;
        if (ID >= studiengangCounter) studiengangCounter = ID + 1;
    }
}

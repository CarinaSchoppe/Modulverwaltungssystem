package de.lisa.studiumsorganisation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is an abstract base model for other models in the system.
 */
@Data
@AllArgsConstructor
public abstract class Basemodel {

    /**
     * Represents the identifier for a variable.
     * This identifier is a unique integer value assigned to the variable.
     * <p>
     * This class is used for maintaining consistency and uniqueness of the identifiers within the system.
     * Once assigned, the ID cannot be modified.
     */
    private final int ID;


}

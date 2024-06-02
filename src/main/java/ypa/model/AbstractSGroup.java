package ypa.model;

import java.util.List;

/**
 * Represents an abstract group of cells in the Sujiko puzzle.
 * Provides common functionality for different types of groups.
 * 
 * @author Dainius Gelzinis 1995006
 */
public abstract class AbstractSGroup {
    protected List<SCell> cells;

    /**
     * Constructs an abstract group with the specified cells.
     *
     * @param cells the list of cells in the group.
     * @pre cells != null
     * @post this.cells == cells
     */
    public AbstractSGroup(List<SCell> cells) {
        this.cells = cells;
    }

    /**
     * Validates the group according to specific rules.
     * Each subclass must implement this method based on its validation logic.
     *
     * @return true if the group is valid, false otherwise.
     * @pre true
     * @post result == true if the group is valid, false otherwise
     */
    public abstract boolean isValid();

    /**
     * Calculates the sum of the values in the group's cells.
     *
     * @return the sum of the values of the cells.
     * @pre true
     * @post result == the sum of the values of the cells
     */
    protected int sumValues() {
        return cells.stream().filter(SCell::isSet).mapToInt(SCell::getValue).sum();
    }

    /**
     * Clears all cells in the group, resetting them to their initial state.
     * 
     * @pre true
     * @post all cells in the group are cleared
     */
    public void clear() {
        cells.forEach(SCell::clear);
    }
}

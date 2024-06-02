package ypa.model;

/**
 * Represents a cell in the Sujiko puzzle.
 * Each cell can hold an integer value between 1 and 9 or be unset.
 * 
 * @author Dainius Gelzinis 1995006
 */
public class SCell {
    private Integer value;
    private boolean isSet;
    private Location location;

    /**
     * Constructs an unset cell with no value.
     * 
     * @pre true
     * @post value == null && isSet == false
     */
    public SCell() {
        this.value = null;  // Cells start as unset
        this.isSet = false;
    }

    /**
     * Constructs a cell with a specified location.
     * 
     * @param location the location of the cell.
     * @pre location != null
     * @post this.location == location
     */
    public SCell(Location location) {
        this();
        this.location = location;
    }

    /**
     * Gets the value of the cell.
     *
     * @return the value of the cell, or null if the cell is unset.
     * @pre true
     * @post result == value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Sets the value of the cell.
     *
     * @param value the value to set, must be between 1 and 9.
     * @throws IllegalArgumentException if the value is not between 1 and 9.
     * @pre value >= 1 && value <= 9
     * @post this.value == value && isSet == true
     */
    public void setValue(Integer value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("Value must be between 1 and 9.");
        }
        this.value = value;
        this.isSet = true;
    }

    /**
     * Checks if the cell has a value set.
     *
     * @return true if the cell has a value, false otherwise.
     * @pre true
     * @post result == isSet
     */
    public boolean isSet() {
        return isSet;
    }

    /**
     * Clears the value of the cell, making it unset.
     * 
     * @pre true
     * @post value == null && isSet == false
     */
    public void clear() {
        this.value = null;
        this.isSet = false;
    }

    /**
     * Gets the location of the cell.
     *
     * @return the location of the cell.
     * @pre true
     * @post result == location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the cell.
     *
     * @param location the new location of the cell.
     * @pre location != null
     * @post this.location == location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}

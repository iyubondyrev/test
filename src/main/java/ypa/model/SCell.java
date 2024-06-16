package ypa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a cell in the Sujiko puzzle.
 * Each cell can hold an integer value between 1 and 9 or be unset.
 * 
 * @author Dainius Gelzinis 1995006
 * @author Akvile Lukauskaite 1953648
 */
public class SCell {
    /** The blocked state. */
    public static final int BLOCKED = -1;

    /** The empty state. */
    public static final int EMPTY = 0;

    /** String for blocked state. */
    public static final String BLOCKED_STR = "\\";

    /** String for empty state. */
    public static final String EMPTY_STR = ".";

    /** The cell's state. */
    private int state;

    /**
     * The groups to which this cell belongs.
     * The first group will be the whole grid.
     */
    private final List<SAbstractGroup> groups;

    /** Location in the grid, if any. */
    private SLocation location;

    /** The grid to which this cell belongs, if any. */
    private SGrid grid;

    /**
     * Constructs a cell with a given state.
     *
     * @param state  state
     * @pre {@code state} is a valid state
     */
    public SCell(final int state) {
        if (state < BLOCKED) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "(" + state + ").pre failed");
        }
        this.state = state;
        groups = new ArrayList<>();
        grid = null;
    }
    
    /**
     * Constructs a cell with a state given by a string.
     *
     * @param state  state
     */
    public SCell(final String state) {
        this(fromString(state));
    }

    /**
     * Constructs a cell from a given scanner.
     *
     * @param scanner  the given scanner
     */
    public SCell(final Scanner scanner) {
        this(scanner.next());
    }

    public int getState() {
        return state;
    }

    /**
     * Sets a new cell state.
     *
     * @param state  the new state
     * @pre {@code state} is valid
     */
    public void setState(int state) {
        if (state < BLOCKED) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + ".setState().pre failed: state == " + state + " < " + BLOCKED);
        }
        for (SAbstractGroup group : groups) {
            group.update(this, state);
        }
        this.state = state;
    }

    /**
     * Returns whether cell is blocked.
     *
     * @return whether {@code this} is blocked
     */
    public boolean isBlocked() {
        return state == BLOCKED;
    }

    /**
     * Returns whether cell is empty.
     *
     * @return whether {@code this} is empty
     */
    public boolean isEmpty() {
        return state == EMPTY;
    }

    /**
     * Returns whether cell is filled.
     *
     * @return whether {@code this} is filled
     */
    public boolean isFilled() {
        return state > EMPTY;
    }

    public SLocation getLocation() {
        return location;
    }

    public void setLocation(SLocation location) {
        this.location = location;
    }

    public SGrid getGrid() {
        return grid;
    }

    public void setGrid(SGrid grid) {
        this.grid = grid;
    }

    /**
     * Returns whether this cell is involved in a rule violation.
     *
     * @return whether this cell conforms to the rules
     */
    public boolean isOK() {
        if (!this.isFilled()) {
            return true;
        }
        for (SAbstractGroup group : groups) {
            if (group == grid) {
                if (grid.hasDuplicates()) {
                    return false;
                }
                continue;
            }
            if (!group.isValid()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether this cell is associated with a given group.
     *
     * @param group  the group to check
     * @return whether {@code this} is element of {@code group}
     */
    public boolean isContainedIn(final SAbstractGroup group) {
        return groups.contains(group);
    }

    /**
     * Adds a group containing this cell.
     *
     * @param group  the group to add
     * @pre {@code group != null && ! isElementOf(group)}
     * @modifies {@code this}
     * @post {@code isElementOf(group)}
     */
    void add(final SAbstractGroup group) {
        groups.add(group);
    }

    /**
     * Converts string to cell state.
     *
     * @param s  string to convert
     * @return cell state corresponding to s
     * @throws IllegalArgumentException  if invalid string
     */
    public static int fromString(final String s) {
        switch (s) {
            case BLOCKED_STR -> {
                return BLOCKED;
            }
            case EMPTY_STR -> {
                return EMPTY;
            }
            default -> {
                try {
                    int result = Integer.parseInt(s);
                    if (result <= EMPTY) {
                        throw new IllegalArgumentException(SCell.class.getSimpleName()
                                + ".fromString(" + s + ").pre failed");
                    }
                    return result;
                } catch (Exception e) {
                    throw new IllegalArgumentException(SCell.class.getSimpleName()
                            + ".fromString(" + s + ").pre failed");
                }
            }
        }
    }

    @Override
    public String toString() {
        return switch (state) {
            case BLOCKED -> BLOCKED_STR;
            case EMPTY -> EMPTY_STR;
            default -> String.valueOf(state);
        };
    }

    public Iterable<SAbstractGroup> groups() {
        return groups;
    }
    
}

package ypa.model;

import java.util.Scanner;

import ypa.model.SPuzzle.Mode;

/**
 * Represents the Sujiko puzzle, managing the grid and its state.
 * 
 * @author Dainius Gelzinis 1995006
 */
public class SPuzzle {
    private String name;
    private SGrid grid;
    private int minNumber = 1;
    private int maxNumber = 9;
    
    private Mode mode;

    /**
     * Enum of working modes for puzzle.
     */
    public enum Mode {
        /** When puzzle can be edited. */
        EDIT,
        /** When puzzle can be solved, but not edited. */
        SOLVE,
        /** When puzzle can only be viewed, but not changed. */
        VIEW
    }

    /**
     * Constructs a new puzzle with the specified name and grid.
     *
     * @param name the name of the puzzle.
     * @param grid the grid of the puzzle.
     * @pre name != null && grid != null
     * @post this.name == name && this.grid == grid
     */
    public SPuzzle(String name, SGrid grid) {
        this.name = name;
        this.grid = grid;
    }

    public String getName() {
        return name;
    }
    
    /**
     * Gets the entries in this puzzle, so as to iterate over them.
     *
     * @return the entries of this puzzle as iterable
     */
    public Iterable<SEntry> getEntries() {
        return grid.getEntries();
    }
    
    /**
     * Constructs a new puzzle with initial state read from given scanner,
     * and with a given name.
     * The actual dimensions are determined from the input.
     *
     * @param scanner  the given scanner
     * @param name  the given name
     */
    public SPuzzle(final Scanner scanner, final String name) {
        this.name = name;
        this.mode = SPuzzle.Mode.VIEW;
        this.grid = new SGrid(scanner);
    }

    /**
     * Sets the name of the puzzle.
     * 
     * @param name the new name of the puzzle.
     * @pre name != null
     * @post this.name == name
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getMinNumber() {
        return minNumber;
    }

    /**
     * Sets the minimum number allowed in the puzzle.
     * 
     * @param minNumber the new minimum number.
     * @throws IllegalArgumentException if minNumber is not between 1 and maxNumber.
     * @pre minNumber >= 1 && minNumber <= maxNumber
     * @post this.minNumber == minNumber
     */
    public void setMinNumber(int minNumber) {
        if (minNumber < 1 || minNumber > maxNumber) {
            throw new IllegalArgumentException("Invalid min number.");
        }
        this.minNumber = minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * Sets the maximum number allowed in the puzzle.
     * 
     * @param maxNumber the new maximum number.
     * @throws IllegalArgumentException if maxNumber is not between minNumber and 9.
     * @pre maxNumber >= minNumber && maxNumber <= 9
     * @post this.maxNumber == maxNumber
     */
    public void setMaxNumber(int maxNumber) {
        if (maxNumber < minNumber || maxNumber > 9) {
            throw new IllegalArgumentException("Invalid max number.");
        }
        this.maxNumber = maxNumber;
    }

    /**
     * Checks if a given number is valid according to the puzzle's rules.
     * 
     * @param n the number to check.
     * @return true if the number is valid, false otherwise.
     * @pre true
     * @post result == (minNumber <= n && n <= maxNumber)
     */
    public boolean isValidNumber(int n) {
        return minNumber <= n && n <= maxNumber;
    }

    /**
     * Checks if the puzzle is solved.
     * 
     * @return true if the puzzle is solved, false otherwise.
     * @pre true
     * @post result == grid.isSolved()
     */
    public boolean isSolved() {
        return this.isValid() && grid.isFull();
    }

    /**
     * Gets the cell at the specified row and column in the grid.
     * 
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the cell at the specified row and column.
     * @pre 0 <= row < 5 && 0 <= col < 5
     * @post result == grid.getCell(row, col)
     */
    public SCell getCell(int row, int col) {
        return grid.getCell(row, col);
    }

    /**
     * Gets the cells in this puzzle, so as to iterate over them.
     *
     * @return the cells of this puzzle as iterable
     */
    public Iterable<SCell> getCells() {
        return grid;
    }

    /**
     * Checks whether the current state of the puzzle is valid.
     *
     * @return whether the current state of this puzzle is valid
     */
    public boolean isValid() {
        return grid.isValid() && (this.mode == Mode.VIEW || this.mode == Mode.SOLVE);
    }

    /**
     * Clears all the cells in the puzzle, resetting them to their initial state.
     * 
     * @pre true
     * @post all cells in the grid are cleared
     */
    public void clear() {
        grid.entries.forEach(sEntry -> sEntry.clear());
    }
    
    /**
     * Gets the number of columns in this puzzle.
     *
     * @return number of columns
     */
    public int getColumnCount() {
        return grid.getColumnCount();
    }

    /**
     * Gets the number of rows in this puzzle.
     *
     * @return number of rows
     */
    public int getRowCount() {
        return grid.getRowCount();
    }
    
    /**
     * Check whether puzzle have a cell with indicate row and column.
     * 
     * @param rowIndex index of row.
     * @param columnIndex index of column.
     * @return If puzzle has indicated cell, true. Otherwise, false.
     */
    public boolean has(final int rowIndex, final int columnIndex) {
        return 0 <= rowIndex && rowIndex < grid.getRowCount()
                && 0 <= columnIndex && columnIndex < grid.getColumnCount();
    }
    
    public Mode getMode() {
        return mode;
    }
    
    /**
     * Gets number of cells with a given state.
     *
     * @param state  the given state
     * @return number of cells with state {@code state}
     * @post {@code \result == (\num_of Cell cell : getCells();
     *   cell.getState() == state)}
     */
    public int getStateCount(final int state) {
        return grid.getStateCount(state);
    }

    /**
     * Sets the mode of this puzzle.
     *
     * @param mode the new mode
     */
    public void setMode(Mode mode) {
        

        if (this.mode != Mode.EDIT && mode == Mode.EDIT) {

            this.clear();
            this.grid.blockAllAndEmptyEntries();

        } else if (this.mode == Mode.EDIT && mode != Mode.EDIT) {
            this.grid.returnToTheDefaultState();
        }

        this.mode = mode;
 
    }
    
    public String gridAsString() {
        return grid.gridAsString();
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}

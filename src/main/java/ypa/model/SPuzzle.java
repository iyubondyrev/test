package ypa.model;

import java.util.List;

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
        return grid.isSolved();
    }

    /**
     * Gets the cell at the specified row and column in the grid.
     * 
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the cell at the specified row and column.
     * @pre 0 <= row < 3 && 0 <= col < 3
     * @post result == grid.getCell(row, col)
     */
    public SCell getCell(int row, int col) {
        return grid.getCell(row, col);
    }

    /**
     * Clears all the cells in the puzzle, resetting them to their initial state.
     * 
     * @pre true
     * @post all cells in the grid are cleared
     */
    public void clear() {
        grid.groups.forEach(SGroup::clear);
    }
}

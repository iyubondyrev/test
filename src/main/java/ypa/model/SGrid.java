package ypa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grid in the Sujiko puzzle.
 * This class manages the grid and the groups within it.
 * 
 * @author Dainius Gelzinis 1995006
 */
public class SGrid {
    private List<List<SCell>> grid;
    protected List<SGroup> groups;

    /**
     * Constructs a grid with the specified groups.
     *
     * @param groups the list of groups in the grid.
     * @pre groups != null
     * @post this.groups == groups
     */
    public SGrid(List<SGroup> groups) {
        this.groups = groups;
        grid = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<SCell> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(new SCell(new Location(i, j)));
            }
            grid.add(row);
        }
    }

    /**
     * Gets the cell at the specified row and column.
     *
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the cell at the specified row and column.
     * @pre 0 <= row < 3 && 0 <= col < 3
     * @post result == grid.get(row).get(col)
     */
    public SCell getCell(int row, int col) {
        return grid.get(row).get(col);
    }

    /**
     * Checks if the grid is solved.
     *
     * @return true if all groups in the grid are valid, false otherwise.
     * @pre true
     * @post result == true if all groups are valid, false otherwise
     */
    public boolean isSolved() {
        return groups.stream().allMatch(SGroup::isValid);
    }
}

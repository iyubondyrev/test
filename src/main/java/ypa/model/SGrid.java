package ypa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a grid in the Sujiko puzzle.
 * This class manages the grid and the groups within it.
 * Our grid dimensions are 5x5 where (2, 2), (2, 4), (4, 2) and (4, 4) 
 * are the sums, other cells that have an even number in their 
 * locations are blocked.
 * 
 * @author Dainius Gelzinis 1995006
 * @author Akvile Lukauskaite 1953648
 */
public class SGrid extends AbstractGroup implements Iterable<SCell> {
    private List<List<SCell>> grid;
    private List<List<SCell>> matrix;
    protected List<SGroup> groups;
    
    /** Number of rows. */
    private final int nRows = 5;

    /** Number of columns. */
    private final int nColumns = 5;
    
    /** The entry specifications. TODO: Should be final but gives error*/
    private List<SEntry> entries;

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
                row.add(new SCell(SCell.BLOCKED));
            }
            grid.add(row);
        }
    }
    
    /**
     * Constructs a grid from given scanner.
     * @pre {@code scanner != null} and it delivers a valid puzzle grid
     * @author Akvile Lukauskaite 1953648
     * @param scanner  scanner
     * @throws NullPointerException  if {@code scanner == null}
     */
    public SGrid(final Scanner scanner) {
        if (scanner == null) {
            throw new NullPointerException("Scanner is null");
        }
        
        //1. our required dimensions are 5x5
        matrix = new ArrayList<>();
        entries = SEntry.scanEntries(scanner);
        
        // 2. Initialize the matrix to all blocked cells.
        for (int rowIndex = 0; rowIndex != nRows; ++rowIndex) {
            final List<SCell> row = new ArrayList<>();
            matrix.add(row);
            for (int columnIndex = 0; columnIndex != nColumns; ++columnIndex) {
                final SCell cell = new SCell(SCell.BLOCKED);
                cell.setGrid(this);
                cell.setLocation(new SLocation(rowIndex, columnIndex));
                row.add(cell);
                associate(cell, this);
            }
        }
        
        // 3. define the cell states and associate them
        for (SEntry entry: entries) {
            SLocation loc = entry.getLocation();
            for (int i = 0; i < entries.size(); ++i) {
                squareGroups(loc, entry);
                //so if our location was (2, 2) then now it is (1, 1)
                loc = new SLocation(loc.getRow() - 1, loc.getColumn() - 1);
                squareGroups(loc, entry);
                loc = new SLocation(loc.getRow() - 1, loc.getColumn() + 1);
                squareGroups(loc, entry);
                loc = new SLocation(loc.getRow() + 1, loc.getColumn() - 1);
                squareGroups(loc, entry);
                loc = new SLocation(loc.getRow() + 1, loc.getColumn() + 1);
                squareGroups(loc, entry);
            }
            
        }
        
    }
    
    /**
     * Helper method that is meant to associate the four surrounding cells to 
     * the target sum cell.
     * @author Akvile Lukauskaite 1953648
     */
    private void squareGroups(SLocation loc, SEntry entry) {
        final SCell cell = getCell(loc);
        cell.setState(SCell.EMPTY); // must be done before associate
        associate(cell, entry);
    }
    
    /**
     * Gets the cell at given coordinates.
     *
     * @param rowIndex  the row coordinate to get from
     * @param columnIndex  the column coordinate to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre {@code 0 <= rowIndex < getRows() &&
     *   0 <= columnIndex < getColumns()}
     * @post {@code \result = cells[rowIndex, columnIndex]}
     */
    public SCell getCell(final int rowIndex, final int columnIndex) {
        return matrix.get(rowIndex).get(columnIndex);
    }
    
    /**
     * Gets the cell at given location.
     *
     * @param location  the location to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre location appears in grid
     * @post {@code \result == } cell at location
     */
    public SCell getCell(final SLocation location) {
        return getCell(location.getRow(), location.getColumn());
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
    
    /**
     * Puts a cell in a group.
     *
     * @param cell  the cell to put
     * @param group  the group to put into
     * @throws IllegalArgumentException  if precondition violated
     * @pre {@code cell != null && group != null &&
     *   ! cell.isElementOf(group) && ! group.contains(cell)}
     * @post {@code group.contains(cell)}
     */
    public static void associate(
            final SCell cell, final AbstractGroup group)
            throws IllegalArgumentException {
        if (cell == null) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: cell == null");
        }
        if (group == null) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: group == null");
        }
        if (cell.isContainedIn(group)) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: cell is already associated with group");
        }
        if (group.contains(cell)) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: group already contains cell");
        }
        group.add(cell);
        cell.add(group);
    }
    
    /**
     * Gets the number of columns in this grid.
     *
     * @return number of columns
     */
    public int getColumnCount() {
        return nColumns;
    }

    /**
     * Gets the number of rows in this grid.
     *
     * @return number of rows
     */
    public int getRowCount() {
        return nRows;
    }
    
    public List<SEntry> getEntries() {
        return entries;
    }
    
    /**
     * Checks whether this grid is full (no more empty cells).
     *
     * @return whether this grid is full
     */
    public boolean isFull() {
        return this.getStateCount(SCell.EMPTY) == 0;
    }
    
    @Override
    public boolean isValid() {
        for (SEntry entry : entries) {
            if (!entry.isValid()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Clears the non-blocked cells.
     *
     * @post {@code \forall Cell cell : cells;
     *   (cell.isEmpty()) == ! cell.isBlocked)}
     */
    public void clear() {
        for (final SCell cell : this) {
            if (!cell.isBlocked()) {
                cell.setState(KCell.EMPTY);
            }
        }
    }

    /**
     * Returns entries as a string.
     *
     * @return string representation of the entries
     */
    public String entriesAsString() {
        StringBuilder result = new StringBuilder();
        for (SEntry entry : entries) {
            result.append(entry).append("\n");
        }
        return result.toString();
    }

    /**
     * Converts the grid of cell states to a string in 2D layout.
     *
     * @return string representation of {@code this.matrix}
     */
    public String gridAsString() {
        final StringBuilder result = new StringBuilder();
        for (List<SCell> row : matrix) {
            for (SCell cell : row) {
                result.append(" ");
                result.append(cell.toString());
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Converts this grid to a string.
     *
     * @return string representation of this
     */
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.entriesAsString());
        final StringBuilder separator = new StringBuilder("=\n");
        for (List<SCell> row : matrix) {
            for (SCell cell : row) {
                if (!cell.isBlocked() && !cell.isEmpty()) {
                    result.append(separator);
                    separator.setLength(0);
                    result.append(cell.getLocation());
                    result.append(" = ");
                    result.append(cell);
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }
}

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
public class SGrid extends SAbstractGroup implements Iterable<SCell> {
    private List<List<SCell>> grid;
    private List<List<SCell>> matrix;
    //protected List<SGroup> groups;
    
    /** Number of rows. */
    private final int nRows = 5;

    /** Number of columns. */
    private final int nColumns = 5;
    
    /** The entry specifications. TODO: Should be final but gives error*/
    protected List<SEntry> entries;

    
    // /**
    //  * Constructs a grid with the specified groups.
    //  *
    //  * @param groups the list of groups in the grid.
    //  * @pre groups != null
    //  * @post this.groups == groups
    //  */
    // public SGrid(List<SGroup> groups) {
    //     this.groups = groups;
    //     grid = new ArrayList<>();
    //     for (int i = 0; i < 3; i++) {
    //         List<SCell> row = new ArrayList<>();
    //         for (int j = 0; j < 3; j++) {
    //             row.add(new SCell(SCell.BLOCKED));
    //         }
    //         grid.add(row);
    //     }
    // }
    
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
                //squareGroups(loc, entry);
                //so if our location was (2, 2) then now it is (1, 1)
            SLocation loc1 = new SLocation(loc.getRow() - 1, loc.getColumn() - 1);
            squareGroups(loc1, entry);
            SLocation loc2 = new SLocation(loc.getRow() - 1, loc.getColumn() + 1);
            squareGroups(loc2, entry);
            SLocation loc3 = new SLocation(loc.getRow() + 1, loc.getColumn() - 1);
            squareGroups(loc3, entry);
            SLocation loc4 = new SLocation(loc.getRow() + 1, loc.getColumn() + 1);
            squareGroups(loc4, entry); 
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

        boolean rowCorrect = loc.getRow() == entry.getLocation().getRow();
        boolean columnCorrect = loc.getColumn() == entry.getLocation().getColumn();

        if (rowCorrect && columnCorrect) {
            cell.setState(SCell.BLOCKED);
        }
    }
    
    /**
     * Blocks all cells and empties entry cells.
     */
    
    public void blockAllAndEmptyEntries() {
        for (SCell cell: this) {
            cell.setState(SCell.BLOCKED);
        }

        for (SEntry entry: this.getEntries()) {
            SCell cell = this.getCell(entry.getLocation().getRow(), 
                entry.getLocation().getColumn());
            cell.setState(SCell.EMPTY);
        }
    }

    /**
     * Returns the grid to its default state.
     * 
     * @throws IllegalArgumentException if sums cells are not between 10 and 30
     */
    
    public void returnToTheDefaultState() throws IllegalArgumentException {
        for (SEntry entry: this.getEntries()) {
            SLocation loc = entry.getLocation();
            SCell entryCell = this.getCell(loc.getRow(), loc.getColumn());
            if (!(10 <= entry.getSpecification().getSum()
                   && entry.getSpecification().getSum() <= 30)) {
                throw new IllegalArgumentException("Sums cells must not be between 10 and 30");
            }

            entryCell.setState(SCell.BLOCKED);

            for (int i = - 1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    if (i * j == 0) {
                        continue;
                    }
                    SLocation cellLoc = new SLocation(loc.getRow() + i, loc.getColumn() + j);
                    SCell cell = this.getCell(cellLoc);
                    cell.setState(SCell.EMPTY);
                }
                
            }
        }
        
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
            final SCell cell, final SAbstractGroup group)
            throws IllegalArgumentException {
        if (cell == null) {
            throw new IllegalArgumentException(SGrid.class.getSimpleName()
                    + ".associate.pre failed: cell == null");
        }
        if (group == null) {
            throw new IllegalArgumentException(SGrid.class.getSimpleName()
                    + ".associate.pre failed: group == null");
        }
        
        // TODO I have commented it out because we in our case several cells
        // can belong to one group
        
        /*if (cell.isContainedIn(group)) {
            throw new IllegalArgumentException(SGrid.class.getSimpleName()
                    + ".associate.pre failed: cell is already associated with group");
        }
        if (group.contains(cell)) {
            throw new IllegalArgumentException(SGrid.class.getSimpleName()
                    + ".associate.pre failed: group already contains cell");
        }*/
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

    /**
     * Check whether grid has duplicated cells.
     */
    public boolean hasDuplicates() {
        for (int i = 1; i != 10; ++i) {
            if (this.getStateCount(i) > 1) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isValid() {
        for (SEntry entry : entries) {
            SCell entryCell = this.getCell(entry.getLocation().getRow(),
                    entry.getLocation().getColumn());
            if (!entryCell.isBlocked()) {
                return false;
            }
            if (!entry.isValid()) {
                return false;
            }
        }
        
        return !hasDuplicates();
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
                cell.setState(SCell.EMPTY);
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

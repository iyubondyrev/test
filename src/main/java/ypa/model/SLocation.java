package ypa.model;

/**
 * Represents a location in the Sujiko puzzle grid (immutable).
 * 
 * @author Dainius Gelzinis 1995006
 */
public class SLocation {
    private final int row;
    private final int column;

    /**
     * Constructs a new location from given row and column.
     *
     * @param row the given row coordinate
     * @param column the given column coordinate
     * @pre 0 <= row < 3 && 0 <= column < 3
     * @post this.row == row && this.column == column
     */
    public SLocation(int row, int column) {
        if (row < 0 || row >= 3 || column < 0 || column >= 3) {
            throw new IllegalArgumentException("Row and column must be between 0 and 2.");
        }
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, column);
    }
}

package ypa.model;

import java.util.Scanner;
import java.util.regex.Pattern;

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
    public SLocation(final int row, final int column) {
        if (row < 0 || row >= 3 || column < 0 || column >= 3) {
            throw new IllegalArgumentException("Row and column must be between 0 and 2.");
        }
        this.row = row;
        this.column = column;
    }
    
    /** Constructs a new location from a given scanner.
     * @author Akvile Lukauskaite 1953648
     * @param scanner  the given scanner
     */
    public SLocation(final Scanner scanner) {
        Pattern original = scanner.delimiter();
        scanner.skip("\\p{javaWhitespace}*");
        scanner.useDelimiter("");
        row = scanner.next("[a-zA-Z]").toLowerCase().charAt(0) - 'a' + 1;
        scanner.useDelimiter(original);
        column = scanner.nextInt();
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

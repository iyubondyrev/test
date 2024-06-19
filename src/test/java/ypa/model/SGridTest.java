package ypa.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SGrid class.
 */
public class SGridTest {

    private SGrid grid;
    private Scanner validScanner;

    /**
     * SetUp environment for tests.
     */
    @BeforeEach
    public void setUp() {
        String validInput = "2 2 15\n2 4 20\n4 2 25\n4 4 30\n";
        validScanner = new Scanner(validInput);
        grid = new SGrid(validScanner);
    }

    @Test
    public void testConstructorWithValidScanner() {
        assertDoesNotThrow(() -> new SGrid(validScanner));
    }

    @Test
    public void testConstructorWithNullScanner() {
        assertThrows(NullPointerException.class, () -> new SGrid(null));
    }

    @Test
    public void testGetCell() {
        SCell cell = grid.getCell(2, 2);
        assertNotNull(cell);
        assertEquals(SCell.BLOCKED, cell.getState());
    }

    @Test
    public void testGetCellWithLocation() {
        SLocation location = new SLocation(2, 2);
        SCell cell = grid.getCell(location);
        assertNotNull(cell);
        assertEquals(SCell.BLOCKED, cell.getState());
    }

    @Test
    public void testBlockAllAndEmptyEntries() {
        grid.blockAllAndEmptyEntries();
        for (SCell cell : grid) {
            assertEquals(SCell.BLOCKED, cell.getState());
        }
        for (SEntry entry : grid.getEntries()) {
            SCell cell = grid.getCell(entry.getLocation());
            assertEquals(SCell.EMPTY, cell.getState());
        }
    }

    @Test
    public void testReturnToTheDefaultState() {
        assertDoesNotThrow(() -> grid.returnToTheDefaultState());
        for (SEntry entry : grid.getEntries()) {
            SCell entryCell = grid.getCell(entry.getLocation());
            assertEquals(SCell.BLOCKED, entryCell.getState());
        }
    }

    @Test
    public void testGetColumnCount() {
        assertEquals(5, grid.getColumnCount());
    }

    @Test
    public void testGetRowCount() {
        assertEquals(5, grid.getRowCount());
    }

    @Test
    public void testHasDuplicates() {
        assertFalse(grid.hasDuplicates());
    }

    @Test
    public void testIsValid() {
        assertTrue(grid.isValid());
    }

    @Test
    public void testClear() {
        grid.clear();
        for (SCell cell : grid) {
            if (!cell.isBlocked()) {
                assertEquals(SCell.EMPTY, cell.getState());
            }
        }
    }

    @Test
    public void testGridAsString() {
        String gridString = grid.gridAsString();
        assertTrue(gridString.contains(" "));
    }
}

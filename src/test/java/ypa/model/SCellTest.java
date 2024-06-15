package ypa.model;

import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for SCell.
 * @author Akvile Lukauskaite 1953648
 */
public class SCellTest {
    private SCell blockedCell;
    private SCell emptyCell;
    private SCell filledCell;
    
    @BeforeEach
    public void setUp() {
        blockedCell = new SCell(SCell.BLOCKED);
        emptyCell = new SCell(SCell.EMPTY);
        filledCell = new SCell(5);
    }

    @Test
    public void testConstructorWithState() {
        assertEquals(SCell.BLOCKED, blockedCell.getState());
        assertEquals(SCell.EMPTY, emptyCell.getState());
        assertEquals(5, filledCell.getState());
    }

    @Test
    public void testConstructorWithInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> new SCell(-2));
    }

    @Test
    public void testConstructorWithString() {
        SCell cell = new SCell("\\");
        assertEquals(SCell.BLOCKED, cell.getState());

        cell = new SCell(".");
        assertEquals(SCell.EMPTY, cell.getState());

        cell = new SCell("4");
        assertEquals(4, cell.getState());
    }

    @Test
    public void testConstructorWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> new SCell("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new SCell("0"));
    }

    @Test
    public void testConstructorWithScanner() {
        Scanner scanner = new Scanner("\\");
        SCell cell = new SCell(scanner);
        assertEquals(SCell.BLOCKED, cell.getState());

        scanner = new Scanner(".");
        cell = new SCell(scanner);
        assertEquals(SCell.EMPTY, cell.getState());

        scanner = new Scanner("7");
        cell = new SCell(scanner);
        assertEquals(7, cell.getState());
    }

    @Test
    public void testSetState() {
        emptyCell.setState(3);
        assertEquals(3, emptyCell.getState());

        filledCell.setState(9);
        assertEquals(9, filledCell.getState());
    }

    @Test
    public void testSetStateInvalid() {
        assertThrows(IllegalArgumentException.class, () -> emptyCell.setState(-2));
    }

    @Test
    public void testIsBlocked() {
        assertTrue(blockedCell.isBlocked());
        assertFalse(emptyCell.isBlocked());
        assertFalse(filledCell.isBlocked());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(blockedCell.isEmpty());
        assertTrue(emptyCell.isEmpty());
        assertFalse(filledCell.isEmpty());
    }

    @Test
    public void testIsFilled() {
        assertFalse(blockedCell.isFilled());
        assertFalse(emptyCell.isFilled());
        assertTrue(filledCell.isFilled());
    }

    @Test
    public void testToString() {
        assertEquals("\\", blockedCell.toString());
        assertEquals(".", emptyCell.toString());
        assertEquals("5", filledCell.toString());
    }

    @Test
    public void testFromString() {
        assertEquals(SCell.BLOCKED, SCell.fromString("\\"));
        assertEquals(SCell.EMPTY, SCell.fromString("."));
        assertEquals(3, SCell.fromString("3"));
    }

    @Test
    public void testFromStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> SCell.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> SCell.fromString("0"));
    }
}

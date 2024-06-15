package ypa.reasoning;

import ypa.reasoning.SEntryWithOneEmptyCell;
import ypa.command.SCompoundCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link EntryWithOneEmptyCell}.
 *
 * @author wstomv
 */
public class SEntryWithOneEmptyCellTest {

    private SPuzzle puzzle;

    /**
     * Prepares each test case.
     */
    @BeforeEach
    public void setUp() {
        puzzle = new SPuzzle(new Scanner(SReasonerTest.PUZZLE), "Test");
        System.out.println(puzzle);
        System.out.println(puzzle.gridAsString());
    }

    /**
     * Test of apply method, of class EntryWithOneEmptyCell.
     */
    @Test
    public void testApply1() {
        System.out.println("applyToCell");
        SCell cell00 = puzzle.getCell(0, 0);
        SCell cell02 = puzzle.getCell(0, 2);
        SCell cell20 = puzzle.getCell(2, 0);
        SCell cell22 = puzzle.getCell(2, 2);
        cell00.setState(5);
        cell02.setState(3);
        cell20.setState(7);
        SEntryWithOneEmptyCell instance = new SEntryWithOneEmptyCell(puzzle);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(1, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed"),
                () -> assertEquals(2, cell22.getState(), "cell 2, 2 state")
        );
    }
    
    /**
     * Test of apply method, of class EntryWithOneEmptyCell.
     */
    @Test
    public void testApply2() {
        System.out.println("applyToCell");
        SCell cell00 = puzzle.getCell(0, 2);
        SCell cell02 = puzzle.getCell(0, 4);
        SCell cell20 = puzzle.getCell(2, 2);
        SCell cell22 = puzzle.getCell(2, 4);
        cell00.setState(6);
        cell02.setState(4);
        cell20.setState(7);
        SEntryWithOneEmptyCell instance = new SEntryWithOneEmptyCell(puzzle);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(1, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed"),
                () -> assertEquals(1, cell22.getState(), "cell 2, 4 state")
        );
    }
}

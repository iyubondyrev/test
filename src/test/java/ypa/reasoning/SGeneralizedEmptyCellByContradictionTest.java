package ypa.reasoning;

import ypa.reasoning.SReasoner;
import ypa.reasoning.SEmptyCellReasoner;
import ypa.reasoning.SGeneralizedEmptyCellByContradiction;
import ypa.reasoning.SEntryWithOneEmptyCell;
import ypa.command.SCompoundCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link SGeneralizedEmptyCellByContradiction}.
 *
 * @author wstomv
 */
public class SGeneralizedEmptyCellByContradictionTest {

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
     * Test of applyToCell method, of class SEntryWithOneEmptyCell.
     */
    @Test
    public void testApplyToCell() {
        System.out.println("applyToCell");
        SCell cell11 = puzzle.getCell(0, 0);
        cell11.setState(1);
        SCell cell12 = puzzle.getCell(0, 2);
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SEmptyCellReasoner instance = new SGeneralizedEmptyCellByContradiction(puzzle, reasoner);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.applyToCell(cell12);
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertFalse(result.isExecuted(), "result.executed"),
                () -> assertEquals(1, cell11.getState(), "cell 0, 0 state"),
                () -> assertEquals(SCell.EMPTY, cell12.getState(), "cell 0, 2 state")
        );
    }

    /**
     * Test of apply method, of class SEntryWithOneEmptyCell.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SEmptyCellReasoner instance = new SGeneralizedEmptyCellByContradiction(puzzle, reasoner);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed")
        );
    }

}

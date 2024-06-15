package ypa.reasoning;

import ypa.reasoning.SEmptyCellReasoner;
import ypa.command.SCompoundCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link SEmptyCellReasoner}.
 *
 * @author wstomv
 */
public class SEmptyCellReasonerTest {

    private SPuzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new SPuzzle(new Scanner(SReasonerTest.PUZZLE), "Test");
    }

    /**
     * Test of applyToCell method, of class EmptyCellReasoner.
     */
    @Test
    public void testApplyToCell() {
        System.out.println("applyToCell");
        SEmptyCellReasoner instance = new SEmptyCellReasonerImpl(puzzle);
        SCell cell = puzzle.getCell(0, 0);
        SCompoundCommand result = instance.applyToCell(cell);
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertFalse(result.isExecuted(), "result.executed")
        );
    }

    /**
     * Test of apply method, of class EmptyCellReasoner.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        SEmptyCellReasoner instance = new SEmptyCellReasonerImpl(puzzle);
        SCompoundCommand result = instance.apply();
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed")
        );
    }

    private static class SEmptyCellReasonerImpl extends SEmptyCellReasoner {
        public SEmptyCellReasonerImpl(SPuzzle puzzle) {
            super(puzzle);
        }
    }

}

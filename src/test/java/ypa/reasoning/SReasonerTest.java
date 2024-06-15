package ypa.reasoning;

import ypa.reasoning.SReasoner;
import ypa.command.SCompoundCommand;
import ypa.model.SPuzzle;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link Reasoner}.
 *
 * @author wstomv
 */
public class SReasonerTest {

    // public static final String PUZZLE = "a 1 - 3 2\nb 1 - 7 2\na 1 | 4 2\na 2 | 6 2\n";
    public static final String PUZZLE = "b 2 17\nb 4 18\nd 2 15\nd 4 15\n";

    /** Puzzle for testing. */
    private final SPuzzle puzzle;

    public SReasonerTest() {
        puzzle = new SPuzzle(new Scanner(PUZZLE), "Test");
    }

    /**
     * Test of apply method, of class Reasoner.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        SReasoner instance = new SReasoner(puzzle);
        SCompoundCommand result = instance.apply();
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed")
        );
    }

}

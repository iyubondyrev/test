package ypa.solvers;

import ypa.solvers.SBacktrackSolver;
import ypa.model.SPuzzle;
import ypa.reasoning.SEntryWithOneEmptyCell;
import ypa.reasoning.SFixpointReasoner;
import ypa.reasoning.SReasoner;
import ypa.reasoning.SReasonerTest;
import ypa.command.SCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link BacktrackSolver}.
 *
 * @author Aleksandr Vardanian 1942263
 * @author Aleksandr Nikolaev 2001675
 */
public class SBacktrackSolverTest {

    private SPuzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new SPuzzle(new Scanner(SReasonerTest.PUZZLE), "Test");
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithoutReasoner() {
        System.out.println("solve w/o reasoner");
        SBacktrackSolver instance = new SBacktrackSolver(puzzle, null);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(9, instance.getCommands().size(), "commands size")
        );
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithSimpleReasoner() {
        System.out.println("solve with simple reasoner");
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SBacktrackSolver instance = new SBacktrackSolver(puzzle, reasoner);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        System.out.println(puzzle.gridAsString());

        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(15, instance.getCommands().size(), "commands size")
        );
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithFixpointReasoner() {
        System.out.println("solve with fixpoint");
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        reasoner = new SFixpointReasoner(puzzle, reasoner);
        SBacktrackSolver instance = new SBacktrackSolver(puzzle, reasoner);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(11, instance.getCommands().size(), "commands size")
        );
    }
}

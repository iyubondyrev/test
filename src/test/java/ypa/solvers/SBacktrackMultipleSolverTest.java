package ypa.solvers;

import ypa.solvers.SBacktrackMultipleSolver;
import ypa.solvers.SolutionRecord;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import ypa.reasoning.SEntryWithOneEmptyCell;
import ypa.reasoning.SFixpointReasoner;
import ypa.reasoning.SReasoner;
import ypa.reasoning.SReasonerTest;
import ypa.command.SCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link SBacktrackMultipleSolver}.
 *
 * @author Aleksandr Vardanian 1942263
 * @author Aleksandr Nikolaev 2001675
 */
public class SBacktrackMultipleSolverTest {

    private SPuzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new SPuzzle(new Scanner(SReasonerTest.PUZZLE), "Test");
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolver1() {
        System.out.println("solve w/o reasoner");
        SBacktrackMultipleSolver instance = new SBacktrackMultipleSolver(puzzle, null);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        ArrayList<SolutionRecord> solutions = instance.getSolutions();

        System.out.println("Solutions:");
        for (SolutionRecord solution: solutions) {
            for (int el: solution.solution) {
                System.out.print(el + " ");
            }
            System.out.println();
        }

        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertEquals(4, solutions.size(), "number of solution"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(9, instance.getCommands().size(), "commands size")
        );
    }

    @Test
    public void testSolver2() {
        System.out.println("solve w/o reasoner");
        SCell cell22 = puzzle.getCell(2, 2);
        cell22.setState(1);

        SBacktrackMultipleSolver instance = new SBacktrackMultipleSolver(puzzle, null);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        ArrayList<SolutionRecord> solutions = instance.getSolutions();

        System.out.println("Solutions:");
        for (SolutionRecord solution: solutions) {
            for (int el: solution.solution) {
                System.out.print(el + " ");
            }
            System.out.println();
        }

        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertEquals(3, solutions.size(), "number of solution"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(8, instance.getCommands().size(), "commands size")
        );
    }
}

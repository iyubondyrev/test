package ypa.reasoning;

import ypa.reasoning.SReasoner;
import ypa.reasoning.SFixpointReasoner;
import ypa.reasoning.SEntryWithOneEmptyCell;
import ypa.command.SCompoundCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link SFixpointReasoner}.
 *
 * @author Aleksandr Vardanian 1942263
 * @author Aleksandr Nikolaev 2001675
 */
public class SFixpointReasonerTest {

    private SPuzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new SPuzzle(new Scanner(SReasonerTest.PUZZLE), "Test");
    }

    /**
     * Test of apply method, of class FixpointReasoner.
     */
    @Test
    public void testApplyEmpty() {
        System.out.println("apply empty");
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SFixpointReasoner instance = new SFixpointReasoner(puzzle, reasoner);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed"),
                () -> assertFalse(puzzle.isSolved(), "puzzle solved")
        );
    }

    /**
     * Test of apply method, of class FixpointReasoner.
     */
    @Test
    public void testApplySolved() {
        System.out.println("apply solved");
        SCell cell00 = puzzle.getCell(0, 0);
        SCell cell02 = puzzle.getCell(0, 2);
        SCell cell04 = puzzle.getCell(0, 4);
        SCell cell20 = puzzle.getCell(2, 0);
        SCell cell22 = puzzle.getCell(2, 2);
        SCell cell24 = puzzle.getCell(2, 4);
        SCell cell40 = puzzle.getCell(2, 0);
        SCell cell42 = puzzle.getCell(4, 2);
        SCell cell44 = puzzle.getCell(2, 4);
        cell00.setState(4);
        cell02.setState(7);
        cell04.setState(8);
        cell20.setState(5);
        cell22.setState(1);
        cell24.setState(2);
        cell42.setState(3);
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SFixpointReasoner instance = new SFixpointReasoner(puzzle, reasoner);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(2, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved")
        );
    }

    /**
     * Test of apply method, of class FixpointReasoner.
     */
    @Test
    public void testApplyUnsolvable1() {
        System.out.println("apply immediately unsolvable");
        SCell cell00 = puzzle.getCell(0, 0);
        SCell cell02 = puzzle.getCell(0, 2);
        SCell cell04 = puzzle.getCell(0, 4);
        SCell cell20 = puzzle.getCell(2, 0);
        SCell cell22 = puzzle.getCell(2, 2);
        SCell cell24 = puzzle.getCell(2, 4);
        SCell cell40 = puzzle.getCell(2, 0);
        SCell cell42 = puzzle.getCell(4, 2);
        SCell cell44 = puzzle.getCell(2, 4);
        cell00.setState(4);
        cell02.setState(7);
        cell04.setState(8);
        cell20.setState(5);
        cell22.setState(1);
        cell24.setState(2);
        cell42.setState(6);
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SFixpointReasoner instance = new SFixpointReasoner(puzzle, reasoner);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertNull(result, "result null"),
                () -> assertFalse(puzzle.isSolved(), "puzzle not solved"),
                () -> assertEquals(2, puzzle.getStateCount(SCell.EMPTY), "puzzle unchanged")
        );
    }

    /**
     * Test of apply method, of class FixpointReasoner.
     */
    @Test
    public void testApplyUnsolvable2() {
        System.out.println("apply indirectly unsolvable");
        SCell cell00 = puzzle.getCell(0, 0);
        SCell cell02 = puzzle.getCell(0, 2);
        SCell cell04 = puzzle.getCell(0, 4);
        SCell cell20 = puzzle.getCell(2, 0);
        SCell cell22 = puzzle.getCell(2, 2);
        SCell cell24 = puzzle.getCell(2, 4);
        SCell cell40 = puzzle.getCell(2, 0);
        SCell cell42 = puzzle.getCell(4, 2);
        SCell cell44 = puzzle.getCell(2, 4);
        cell00.setState(4);
        cell02.setState(7);
        cell20.setState(9);
        cell22.setState(1);
        cell24.setState(2);
        SReasoner reasoner = new SEntryWithOneEmptyCell(puzzle);
        SFixpointReasoner instance = new SFixpointReasoner(puzzle, reasoner);
        System.out.println(puzzle.gridAsString());
        SCompoundCommand result = instance.apply();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertNull(result, "result null"),
                () -> assertFalse(puzzle.isSolved(), "puzzle not solved"),
                () -> assertEquals(4, puzzle.getStateCount(SCell.EMPTY), "puzzle unchanged")
        );
    }
}
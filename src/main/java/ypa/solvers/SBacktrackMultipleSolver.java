package ypa.solvers;

import ypa.command.SCommand;
import ypa.command.SSetCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import ypa.reasoning.SReasoner;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Backtracking solver for a puzzle that can find multiple solutions.
 * The solutions are stored in a list of {@link SolutionRecord} objects.
 * 
 * @author Aleksandr Vardanian 1942263
 * @author Aleksandr Nikolaev 2001675
 */
public class SBacktrackMultipleSolver extends SBacktrackSolver {
    protected ArrayList<SolutionRecord> solutions;

    /**
     * Constructs a backtracking solver for a given puzzle.
     *
     * @param puzzle  the puzzle
     * @throws IllegalArgumentException  if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public SBacktrackMultipleSolver(SPuzzle puzzle, final SReasoner reasoner) {
        super(puzzle, reasoner);
        this.solutions = new ArrayList<SolutionRecord>();
    }

    /**
     * Solves the puzzle and stores the solutions in a list of {@link SolutionRecord} objects.
     * @return true if the puzzle is solvable, false otherwise
     */
    @Override
    public boolean solve() {
        boolean reply = false;

        if (this.getManySolution()) {
            ArrayList<SCommand> executedCommands = this.solutions.get(0).commands;

            for (int ind = 0; ind != executedCommands.size(); ++ind) {
                SCommand command = executedCommands.get(ind);
                command.execute();
                this.commands.push(command);
            }

            reply = true;
        }

        return reply;
    }

    /**
     * Returns the list of solutions.
     * @return the list of solutions
     */
    public ArrayList<SolutionRecord> getSolutions() {
        return this.solutions;
    }

    /**
     * Adds every unique solution to the list of solutions and reverses the commands.
     * @return true if there are solutions, false otherwise
     */

    private boolean getManySolution() {
        while (this.getOneUniqueSolution()) {
            ArrayList<SCommand> reversedCommand = this.reverseCommands();
            SolutionRecord solution = new SolutionRecord(this.getPuzzleState(), reversedCommand);
            this.solutions.add(solution);

            for (int ind = reversedCommand.size() - 1; ind != -1; --ind) {
                SCommand command = reversedCommand.get(ind);
                command.revert();
            }
        }

        return this.solutions.size() > 0;
    }

    /**
     * Reverses the commands in the stack.
     * @return the reversed commands
     */
    private ArrayList<SCommand> reverseCommands() {
        ArrayList<SCommand> reversedCommands = new ArrayList<SCommand>();
        
        while (!this.commands.empty()) {
            reversedCommands.add(this.commands.pop());
        }

        Collections.reverse(reversedCommands);
        return reversedCommands;
    }

    /**
     * Checks if the found solution is unique.
     * @param solution the solution to check
     * @return true if the solution is unique, false otherwise
     */
    private boolean isUniqueSolution(final int[] solution) {
        boolean uniqueSolution = true;

        for (SolutionRecord otherSolution: this.solutions) {
            if (areEqualArrays(solution, otherSolution.solution)) {
                uniqueSolution = false;
            }
        }

        return uniqueSolution;
    }

    /**
     * Checks if two arrays are equal.
     * @param array1 the first array
     * @param array2 the second array
     * @return true if the arrays are equal, false otherwise
     */
    private static boolean areEqualArrays(final int[] array1, final int[] array2) {
        boolean reply = true;

        if (array1.length == array2.length) {
            for (int ind = 0; ind != array1.length; ++ind) {
                if (array1[ind] != array2[ind]) {
                    reply = false;
                }
            }
        } else {
            reply = false;
        }

        return reply;
    }

    /**
     * Finds one unique solution for the puzzle.
     * @return true if a solution is found, false otherwise
     */
    private boolean getOneUniqueSolution() {
        // Backtracking solver, using the reasoner if not null
        if (reasoner != null) {
            // first, fill in cells by reasoning
            final SCommand compound = reasoner.apply();
            if (compound == null) {
                // not solvable
                return false;
            }
            commands.push(compound);
        }
        final SCell cell = getEmptyCell();
        if (cell == null) {
            // no more empty cells
            return puzzle.isValid();
        } else {
            // cell is empty; set it in all possible ways
            for (int state = puzzle.getMinNumber(); state <= puzzle.getMaxNumber(); ++state) {
                final SCommand command = new SSetCommand(cell, state);
                command.execute();
                if (puzzle.isValid()) {
                    commands.push(command);
                    // number of open cells is one less
                    if (getOneUniqueSolution() && this.isUniqueSolution(this.getPuzzleState())) {
                        return true;
                    }
                    // no solution found
                    commands.pop();
                }
                // restore cell state
                command.revert();
            }
        }
        if (reasoner != null) {
            // to revert the reasoned cells
            commands.pop().revert();
        }

        return false;
    }

    /**
     * Returns the state of the puzzle.
     * @return the state of the puzzle
     */
    public int[] getPuzzleState() {

        int[] puzzleState = new int[9];

        SCell cell00 = puzzle.getCell(0, 0);
        SCell cell02 = puzzle.getCell(0, 2);
        SCell cell04 = puzzle.getCell(0, 4);
        SCell cell20 = puzzle.getCell(2, 0);
        SCell cell22 = puzzle.getCell(2, 2);
        SCell cell24 = puzzle.getCell(2, 4);
        SCell cell40 = puzzle.getCell(4, 0);
        SCell cell42 = puzzle.getCell(4, 2);
        SCell cell44 = puzzle.getCell(4, 4);

        SCell[] cells = {cell00, cell02, cell04, cell20, cell22, cell24, cell40, cell42, cell44};
        
        for (int i = 0; i < 9; ++i) {
            puzzleState[i] = cells[i].getState();
        }

        return puzzleState;
    }
}

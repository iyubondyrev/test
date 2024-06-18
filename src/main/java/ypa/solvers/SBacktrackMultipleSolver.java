package ypa.solvers;

import ypa.command.SCommand;
import ypa.command.SSetCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;
import ypa.model.SGrid;
import ypa.reasoning.SReasoner;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public ArrayList<SolutionRecord> getSolutions() {
        return this.solutions;
    }

    private boolean getManySolution() {
        while(this.getOneUniqueSolution()) {
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


    private ArrayList<SCommand> reverseCommands() {
        ArrayList<SCommand> reversedCommands = new ArrayList<SCommand>();
        
        while (!this.commands.empty()) {
            reversedCommands.add(this.commands.pop());
        }

        Collections.reverse(reversedCommands);
        return reversedCommands;
    }


    /**
     * 
     * @param uniqueSolution
     * @return
     */
    private boolean isUniqueSolution(final int[] solution) {
        boolean uniqueSolution = true;

        for (SolutionRecord otherSolution: this.solutions) {
            if (AreEqualArrays(solution, otherSolution.solution)) {
                uniqueSolution = false;
            }
        }

        return uniqueSolution;
    }

    /**
     * 
     * @param array1
     * @param array2
     * @return
     */
    private static boolean AreEqualArrays(final int[] array1, final int[] array2) {
        boolean reply = true;

        if (array1.length == array2.length) {
            for (int ind = 0; ind != array1.length; ++ind) {
                if (array1[ind] != array2[ind]) {
                    reply = false;
                }
            }
        }
        else {
            reply = false;
        }

        return reply;
    }

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
//

        return false;
    }

    /**
     * 
     * @return
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

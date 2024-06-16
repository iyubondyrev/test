package ypa.solvers;

import ypa.command.SCommand;
import ypa.model.SPuzzle;

import java.util.Collection;
import java.util.Stack;

/**
 * Abstract base class for solvers of Kakuro puzzles.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public abstract class SAbstractSolver {

    /** The puzzle being solved. */
    protected SPuzzle puzzle;

    /** Commands executed. */
    protected Stack<SCommand> commands;

    /**
     * Constructs a reasoner for a given puzzle.
     *
     * @param puzzle  the puzzle
     * @throws IllegalArgumentException  if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public SAbstractSolver(final SPuzzle puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "().pre failed: puzzle == null");
        }
        this.puzzle = puzzle;
        commands = new Stack<>();
    }

    /**
     * Gets the commands whose execution led to current puzzle state.
     *
     * @return commands executed to get to current puzzle state
     */
    public Collection<SCommand> getCommands() {
        return commands;
    }

    /**
     * Either finds one solution of the puzzle from its current state,
     * if solvable, or leaves the puzzle unchanged.
     *
     * @return whether puzzle was solved
     * @pre {@code puzzle != null}
     * @modifies {@code puzzle}
     * @post {@code
     *      (\result && puzzle.isSolved()) || (! \result && puzzle unchanged)}
     */
    public abstract boolean solve();

}

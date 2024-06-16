package ypa.reasoning;

import ypa.command.SCommand;
import ypa.command.SCompoundCommand;
import ypa.command.SSetCommand;
import ypa.model.SCell;
import ypa.model.SPuzzle;

/**
 * When only one way of filling an empty cell does not lead to an invalid state,
 * then that one way of filling is forced.
 * This is slightly more general (and possibly more costly) than
 * the {@link SEntryWithOneEmptyCell} reasoner.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SBasicEmptyCellByContradiction extends SEmptyCellReasoner {

    public SBasicEmptyCellByContradiction(SPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    SCompoundCommand applyToCell(final SCell cell) throws NullPointerException {
        SCompoundCommand result = super.applyToCell(cell);
        SCommand candidateForcedCommand = null; // command that worked, if any

        for (int state = puzzle.getMinNumber(); state <= puzzle.getMaxNumber(); ++state) {
            SCommand command = new SSetCommand(cell, state);
            command.execute();
            boolean valid = puzzle.isValid();
            command.revert();
            if (valid) {
                // no contraction; command is a candidate
                if (candidateForcedCommand == null) {
                    // first command that is valid; memorize it
                    candidateForcedCommand = command;
                } else {
                    // multiple valid ways of filling cell; no forced command
                    return result;
                }
            }
        }
        // at most one command worked

        if (candidateForcedCommand == null) {
            // all commands failed: puzzle not solvable
            return null;
        } else {
            // exactly one command worked
            result.add(candidateForcedCommand);
            return result;
        }
    }

}

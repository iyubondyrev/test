package ypa.reasoning;

import ypa.command.SCommand;
import ypa.command.SCompoundCommand;
import ypa.command.SSetCommand;
import ypa.model.SAbstractGroup;
import ypa.model.SCell;
import ypa.model.SEntry;
import ypa.model.SPuzzle;

/**
 * When all cells but one of an entry have been filled,
 * then the last empty cell remaining can be calculated.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SEntryWithOneEmptyCell extends SEmptyCellReasoner {

    public SEntryWithOneEmptyCell(SPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    SCompoundCommand applyToCell(SCell cell) throws NullPointerException {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "applyToCell.pre failed: cell is not empty");
        }
        SCompoundCommand result = super.applyToCell(cell);

        for (SAbstractGroup g : cell.groups()) {
            if (g instanceof SEntry && g.getStateCount(SCell.EMPTY) == 1) {
                // g is a horizontal or vertical entry with one empty cell
                int sum = ((SEntry) g).getSpecification().getSum();
                int newState = sum - g.getTotal();
                if (!puzzle.isValidNumber(newState)) {
                    return null;
                }
                final SCommand command = new SSetCommand(cell, newState);
                command.execute();
                final boolean valid = puzzle.isValid();
                command.revert();
                if (valid) {
                    result.add(command);
                    return result;
                } else {
                    return null;
                }
            }
        }

        return result;
    }

}

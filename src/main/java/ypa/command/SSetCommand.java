package ypa.command;

import ypa.model.SCell;

import java.util.Collection;

/**
 * The command to set the state of a cell.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SSetCommand extends SGenericCommand<SCell> {

// Representation of command state
    /** The command's parameter. */
    private final int newState;

    /** Previous state of the receiver, for revert(). */
    private int oldState;
//

    /**
     * Constructs a set command for a given receiver and new state.
     *
     * @param receiver  the given receiver
     * @param newState  the new state
     */
    public SSetCommand(final SCell receiver, final int newState) {
        super(receiver);
// Initialize command state
        this.newState = newState;
//
    }

// Operations
    @Override
    public void execute() {
        super.execute();
        oldState = receiver.getState(); // should not be done in constructor!
        receiver.setState(newState);
    }

    @Override
    public void revert() {
        super.revert();
        receiver.setState(oldState);
    }

    @Override
    public Collection<SCell> getCells() {
        Collection<SCell> result = super.getCells();
        result.add(receiver);
        return result;
    }
//

}

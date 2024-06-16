package ypa.command; // <<<<< TODO: Comment this line out when submitting to Momotor!

import java.util.Stack;

/**
 * Facilities for an undo-redo mechanism, on the basis of commands.
 *
 * <!--//# BEGIN TODO: Names, student IDs, group name, and date-->
 * <p><b>Dainius Gelžinis 1995006, Akvilė Lukauskaitė 1953648</b></p>
 * <p><b>Graded Assignment 60, 15-06-24</b></p>
 * <!--//# END TODO-->
 */

public class UndoRedo {

    //# BEGIN TODO: Representation in terms of instance variables, incl. rep. inv.
    private final Stack<SCommand> doneCommands;
    
    private final Stack<SCommand> undoneCommands;
    
    /**
     * Constructs a new undo-redo manager.
     */
    public UndoRedo() {
        doneCommands = new Stack<>();
        undoneCommands = new Stack<>();
    }

    /**
     * Returns whether an {@code undo} is possible.
     *
     * @return whether {@code undo} is possible
     */
    public boolean canUndo() {
        return !doneCommands.isEmpty();    
    }

    /**
     * Returns whether a {@code redo} is possible.
     *
     * @return whether {@code redo} is possible
     */
    public boolean canRedo() {
        return !undoneCommands.isEmpty();
    }

    /**
     * Returns command most recently done.
     *
     * @return command at top of undo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canUndo()}
     */
    public SCommand lastDone() throws IllegalStateException {
        if (!canUndo()) {
            throw new IllegalStateException("No commands to undo.");
        }
        return doneCommands.peek();
    }

    /**
     * Returns command most recently undone.
     *
     * @return command at top of redo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canRedo()}
     */
    public SCommand lastUndone() throws IllegalStateException {
        if (!canRedo()) {
            throw new IllegalStateException("No commands to redo.");
        }
        return undoneCommands.peek();
    }

    /**
     * Clears all undo-redo history.
     *
     * @modifies {@code this}
     */
    public void clear() {
        doneCommands.clear();
        undoneCommands.clear();
    }

    /**
     * Adds given command to the do-history.
     * If the command was not yet executed, then it is first executed.
     *
     * @param command the command to incorporate
     * @modifies {@code this}
     */
    public void did(final SCommand command) {
        if (!command.isExecuted()) {
            command.execute();
        }
        doneCommands.push(command);
        undoneCommands.clear(); // Clear the redo stack when a new command is executed
    }

    /**
     * Undo the most recently done command, optionally allowing it to be redone.
     *
     * @param redoable whether to allow redo
     * @throws IllegalStateException if precondition violated
     * @pre {@code canUndo()}
     * @modifies {@code this}
     */
    public void undo(final boolean redoable) throws IllegalStateException {
        if (!canUndo()) {
            throw new IllegalStateException("No commands to undo.");
        }
        SCommand command = doneCommands.pop();
        command.revert();
        if (redoable) {
            undoneCommands.push(command);
        }
    }

    /**
     * Redo the most recently undone command.
     *
     * @throws IllegalStateException if precondition violated
     * @pre {@code canRedo()}
     * @modifies {@code this}
     */
    public void redo() throws IllegalStateException {
        if (!canRedo()) {
            throw new IllegalStateException("No commands to redo.");
        }
        SCommand command = undoneCommands.pop();
        command.execute();
        doneCommands.push(command);
    }

    /**
     * Undo all done commands.
     *
     * @param redoable whether to allow redo
     * @modifies {@code this}
     */
    public void undoAll(final boolean redoable) {
        while (canUndo()) {
            undo(redoable);
        }
    }

    /**
     * Redo all undone commands.
     *
     * @modifies {@code this}
     */
    public void redoAll() {
        while (canRedo()) {
            redo();
        }
    }
}


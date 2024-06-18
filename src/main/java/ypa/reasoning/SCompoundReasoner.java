package ypa.reasoning;

import ypa.command.SCompoundCommand;
import ypa.model.SPuzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies multiple reasoners in sequence.
 * The sequence reflects the order in which reasoners were added.
 * Based on the Composite design pattern.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SCompoundReasoner extends SReasoner {

    /** The sequence of reasoners. */
    private final List<SReasoner> reasoners = new ArrayList<>();

    public SCompoundReasoner(SPuzzle puzzle) {
        super(puzzle);
    }

    /**
     * Adds a reasoner.
     *
     * @param reasoner  reasoner to add
     * @pre {@code reasoner != null} and already set for {@code puzzle}
     * @throws IllegalArgumentException  if {@code strategy ! null ||
     *     strategy} is not for same puzzle
     */
    public void add(final SReasoner reasoner) {
        if (reasoner == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + ".add().pre failed: reasoning == null");
        }
        reasoners.add(reasoner);
    }

    @Override
    public SCompoundCommand apply() {
        //  Apply sequence of reasoners until first change
        for (SReasoner reasoner : reasoners) {
            final SCompoundCommand command = reasoner.apply();
            if (command == null) {
                return command;
            } else if (command.size() > 0) {
                return command;
            }
        }

        return super.apply();
    }

}

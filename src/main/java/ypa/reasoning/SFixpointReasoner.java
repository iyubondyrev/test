package ypa.reasoning;

import ypa.command.SCompoundCommand;
import ypa.model.SPuzzle;

/**
 * A reasoner that repeatedly applies a given reasoner
 * until the least fixed point is reached (a closure operation).
 * That is, the reasoner is repeated until either there is no further change,
 * or until an invalid state is reached,
 * in which case all previous changes are reverted.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SFixpointReasoner extends SReasonerDecorator {

    public SFixpointReasoner(SPuzzle puzzle, SReasoner reasoner) {
        super(puzzle, reasoner);
    }

    @Override
    public SCompoundCommand apply() {
        final SCompoundCommand result = super.apply();

        // repeatedly apply reasoner until no change occurs
        SCompoundCommand compound;
        do {
            compound = reasoner.apply();
            if (compound == null) {
                result.revert(); // HARD-TO-FIND DEFECT IF OMITTED!
                return compound;
            }
            result.addAll(compound);
        } while (compound.size() > 0);

        return result;
    }

}

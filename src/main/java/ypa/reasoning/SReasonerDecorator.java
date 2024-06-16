package ypa.reasoning;

import ypa.model.SPuzzle;

/**
 * Abstract base class for reasoner decorators, holding common code.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public abstract class SReasonerDecorator extends SReasoner {

    /** The reasoner being decorated. */
    protected SReasoner reasoner;

    /* Rep. invariant
     *  reasoner.puzzle == this.puzzle
     */

    /**
     * Constructs a reasoner for the given puzzle and reasoner.
     *
     * @param puzzle  the puzzle to reason about
     * @param reasoner  the reasoner to use before validity checking
     * @throws IllegalArgumentException  if precondition failed
     * @pre {@code puzzle != null  && reasoner != null && reasoner.puzzle == puzzle}
     */
    public SReasonerDecorator(SPuzzle puzzle, final SReasoner reasoner) {
        super(puzzle);
        if (reasoner == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "setReasoner.pre failed: reasoning == null");
        }
        if (reasoner.puzzle != this.puzzle) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "setReasoner.pre failed: reasoning.puzzle != this.puzzle");
        }
        this.reasoner = reasoner;
    }

}

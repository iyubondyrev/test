package ypa.model;

import java.util.List;

/**
 * Represents a group of cells in the Sujiko puzzle.
 * Each group has a target sum that the sum of its cells values must match.
 * 
 * @author Dainius Gelzinis 1995006
 */
public class SGroup extends SAbstractGroup {

    private int targetSum;

    /**
     * Validates the group by checking if the sum of its cells values equals the target sum.
     *
     * @return true if the group is valid, false otherwise.
     * @pre true
     * @post result == (sumValues() == targetSum && cells.stream().allMatch(SCell::isSet))
     */
    @Override
    public boolean isValid() {
        return true;
    }

    private int sumValues() {
        return targetSum;
    }

    void clear() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}

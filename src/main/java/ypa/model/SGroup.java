package ypa.model;

import java.util.List;

/**
 * Represents a group of cells in the Sujiko puzzle.
 * Each group has a target sum that the sum of its cells values must match.
 * 
 * @author Dainius Gelzinis 1995006
 */
public class SGroup extends AbstractSGroup {
    private int targetSum;

    /**
     * Constructs a group with the specified cells and target sum.
     *
     * @param cells the list of cells in the group.
     * @param targetSum the target sum for the group.
     * @throws IllegalArgumentException if the number of cells is not 4.
     * @pre cells != null && cells.size() == 4
     * @post this.cells == cells && this.targetSum == targetSum
     */
    public SGroup(List<SCell> cells, int targetSum) {
        super(cells);
        if (cells.size() != 4) {
            throw new IllegalArgumentException("Each group must consist of 4 cells.");
        }
        this.targetSum = targetSum;
    }

    /**
     * Validates the group by checking if the sum of its cells values equals the target sum.
     *
     * @return true if the group is valid, false otherwise.
     * @pre true
     * @post result == (sumValues() == targetSum && cells.stream().allMatch(SCell::isSet))
     */
    @Override
    public boolean isValid() {
        int currentSum = sumValues();
        return currentSum == targetSum && cells.stream().allMatch(SCell::isSet);
    }
}

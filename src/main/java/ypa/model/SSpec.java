package ypa.model;

import java.util.Scanner;
import java.util.Set;

/**
 * Specification for a Sujiko combination, consisting of a sum (immutable).
 *
 * @inv NonNegative: {@code 0 <= getSum()}
 *
 * @author (modified) Akvile Lukauskaite 1953648
 */
public class SSpec {

    /** The sum. */
    private final int sum;

    /**
     * Constructs a new {@code SSpec} with given sum and length.
     *
     * @param sum  the given sum
     */
    public SSpec(final int sum) {
        assert sum >= 10 && sum <= 30 : "Spec.pre failed: sum " + sum + " not in [10, 30]";
        this.sum = sum;
    }

    /**
     * Constructs a new {@code SSpec} from given set of integers.
     *
     * @param combination  the given combination
     */
    public SSpec(final Set<Integer> combination) {
        assert combination != null : "SSpec.pre failed: combination == null";
        int sum = 0;
        for (int d : combination) {
            sum += d;
        }
        this.sum = sum;
    }

    /**
     * Constructs a new {@code SSpec} from a given scanner.
     *
     * @param scanner  the given scanner
     */
    public SSpec(final Scanner scanner) {
        this.sum = scanner.nextInt();
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return String.format("%2d", sum);
    }

    public String toStringLong() {
        return "{ sum: " + sum + "}";
    }

}

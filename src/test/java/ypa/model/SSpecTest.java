package ypa.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SSpecTest {

    private Set<Integer> validCombination;
    private Set<Integer> nullCombination;
    private Set<Integer> negativeCombination;
    private Scanner validScanner;
    private Scanner invalidScanner;
    
    @BeforeEach
    public void setUp() {
        validCombination = new HashSet<>();
        validCombination.add(5);
        validCombination.add(15);
        
        System.out.println(validCombination + "\n\n\n\n\n");
        
        nullCombination = null;

        negativeCombination = new HashSet<>();
        negativeCombination.add(-1);
        negativeCombination.add(1);
        negativeCombination.add(10);

        validScanner = new Scanner("15");
        invalidScanner = new Scanner("35");
    }
    
    @Test
    public void testConstructorWithValidSum() {
        SSpec spec = new SSpec(15);
        assertEquals(15, spec.getSum());
    }

    @Test
    public void testConstructorWithValidCombination() {
        SSpec spec = new SSpec(validCombination);
        assertEquals(20, spec.getSum());
    }

    @Test
    public void testConstructorWithNullCombination() {
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            new SSpec(nullCombination);
        });
        assertTrue(exception.getMessage().contains("combination == null"));
    }

    @Test
    public void testConstructorWithNegativeCombination() {
        SSpec spec = new SSpec(negativeCombination);
        assertEquals(10, spec.getSum());
    }

    @Test
    public void testConstructorWithValidScanner() {
        SSpec spec = new SSpec(validScanner);
        assertEquals(15, spec.getSum());
    }

    @Test
    public void testConstructorWithInvalidScanner() {
        SSpec spec = new SSpec(invalidScanner);
        assertEquals(35, spec.getSum()); // Assumes no validation on scanner input in constructor
    }

    @Test
    public void testGetSum() {
        SSpec spec = new SSpec(25);
        assertEquals(25, spec.getSum());
    }

    @Test
    public void testToString() {
        SSpec spec = new SSpec(20);
        assertEquals("20", spec.toString());
    }

    @Test
    public void testToStringLong() {
        SSpec spec = new SSpec(20);
        assertEquals("{ sum: 20}", spec.toStringLong());
    }
}

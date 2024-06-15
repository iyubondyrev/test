package ypa.model;

import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for SCell.
 * @author Akvile Lukauskaite 1953648
 */
public class SCellTest {
    /**
     * Test constructor, getDirection, and toString.
     */
    @Test
    public void testConstructor() {
        System.out.println("SCell");
        int expResult = 6;
        SCell instance = new SCell(expResult);
        assertEquals(expResult, instance.getState(), "direction");
    }
}

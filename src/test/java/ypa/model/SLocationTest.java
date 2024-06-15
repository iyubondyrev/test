package ypa.model;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for {@code SLocation}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SLocationTest {

    /**
     * Test of getRow method, of class SLocation.
     */
    @Test
    public void testGetRow() {
        System.out.println("getRow");
        SLocation instance = new SLocation(1, 2);
        int expResult = 1;
        int result = instance.getRow();
        assertEquals(expResult, result, "Result");
    }

    /**
     * Test of getColumn method, of class SLocation.
     */
    @Test
    public void testGetColumn() {
        System.out.println("getColumn");
        SLocation instance = new SLocation(1, 2);
        int expResult = 2;
        int result = instance.getColumn();
        assertEquals(expResult, result, "Result");
    }

    /**
     * Test of shortString method, of class SLocation.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SLocation instance = new SLocation(1, 2);
        String expResult = "b 3";
        String result = instance.toString();
        assertEquals(expResult, result, "Result");
    }

    /**
     * Test of toString method, of class SLocation.
     */
    @Test
    public void testToStringLong() {
        System.out.println("toStringLong");
        SLocation instance = new SLocation(1, 2);
        String expResult = "{ row: b, column: 3 }";
        String result = instance.toStringLong();
        assertEquals(expResult, result, "Result");
    }

    /**
     * Test of SLocation(Scanner) constructor of class SLocation.
     */
    @Test
    public void testSLocationScanner() {
        System.out.println("SLocation(Scanner)");
        String expResult = "b 3";
        Scanner scanner = new Scanner(expResult);
        SLocation instance = new SLocation(scanner);
        String result = instance.toString();
        assertEquals(expResult, result, "toString");

        expResult = "z 10";
        instance = new SLocation(new Scanner(expResult));
        result = instance.toString();
        assertEquals(expResult, result, "toString");
    }

}

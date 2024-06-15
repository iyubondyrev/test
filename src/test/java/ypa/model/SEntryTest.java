package ypa.model;

import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code SEntry}.
 * 
 * @author Akvile Lukauskaite 1953648
 */
public class SEntryTest {
    
    /**
     * Test constructor, getDirection, and toString.
     */
    @Test
    public void testConstructor() {
        System.out.println("SEntry");
        String input = "a 2 13";
        String expResult = "(1, 2) 13";
        Scanner scanner = new Scanner(input);
        SEntry instance = new SEntry(scanner);
        assertEquals(expResult, instance.toString(), "toString");  
    }
    
    /**
     * Test scanEntries.
     */
    @Test
    public void testScanEntries() {
        System.out.println("scanEntries, plain");
        String expEntry0 = "(1, 2) 12";
        String expEntry1 = "(2, 1) 17";
        String expResult = "a 2 12" + "\n" + "b 1 17" + "\n";
        List<SEntry> result = SEntry.scanEntries(new Scanner(expResult));
        assertAll(
                () -> assertEquals(2, result.size(), "size"),
                () -> assertEquals(expEntry0, result.get(0).toString(), "get(0)"),
                () -> assertEquals(expEntry1, result.get(1).toString(), "get(1)")
        );
    }
    
    /**
     * Test scanEntries with more whitespace characters.
     */
    @Test
    public void testScanEntries2() {
        System.out.println("scanEntries, plain");
        String expEntry0 = "(1, 2) 12";
        String expEntry1 = "(2, 1) 17";
        String expResult = "a 2 12         " + "\n" + "b       1 17" + "\n";
        List<SEntry> result = SEntry.scanEntries(new Scanner(expResult));
        assertAll(
                () -> assertEquals(2, result.size(), "size"),
                () -> assertEquals(expEntry0, result.get(0).toString(), "get(0)"),
                () -> assertEquals(expEntry1, result.get(1).toString(), "get(1)")
        );
    }
}

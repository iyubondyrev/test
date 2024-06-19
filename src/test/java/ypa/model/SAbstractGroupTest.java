package ypa.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

class TestGroup extends SAbstractGroup {
    @Override
    public boolean isValid() {
        // Simple implementation for testing
        return true;
    }
}

/**
 * Test for SAbstractGroup class.
 */
public class SAbstractGroupTest {
    private TestGroup group;
    private SCell blockedCell;
    private SCell emptyCell;
    private SCell filledCell;

    /**
     * Setup environment for tests.
     */
    @BeforeEach
    public void setUp() {
        group = new TestGroup();
        blockedCell = new SCell(SCell.BLOCKED);
        emptyCell = new SCell(SCell.EMPTY);
        filledCell = new SCell(5);
    }

    @Test
    public void testAdd() {
        group.add(blockedCell);
        group.add(emptyCell);
        group.add(filledCell);

        assertEquals(3, group.getCount());
        assertEquals(1, group.getStateCount(SCell.BLOCKED));
        assertEquals(1, group.getStateCount(SCell.EMPTY));
        assertEquals(1, group.getStateCount(5));
        assertEquals(5, group.getTotal());
    }

    @Test
    public void testContains() {
        group.add(blockedCell);
        assertTrue(group.contains(blockedCell));
        assertFalse(group.contains(emptyCell));
    }

    @Test
    public void testIterator() {
        group.add(blockedCell);
        group.add(emptyCell);
        group.add(filledCell);

        Iterator<SCell> iterator = group.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(blockedCell, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(emptyCell, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(filledCell, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetCount() {
        assertEquals(0, group.getCount());
        group.add(blockedCell);
        assertEquals(1, group.getCount());
    }

    @Test
    public void testGetStateCount() {
        assertEquals(0, group.getStateCount(SCell.BLOCKED));
        group.add(blockedCell);
        assertEquals(1, group.getStateCount(SCell.BLOCKED));
    }

    @Test
    public void testGetTotal() {
        assertEquals(0, group.getTotal());
        group.add(filledCell);
        assertEquals(5, group.getTotal());
    }
}

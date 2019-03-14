package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class ArrayHeapMinPQTest {

    @Test
    public void testGetSmallest() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(5, 10);
        test.add(6, 8);
        test.add(2, 11);

        assertEquals(test.getSmallest(), (Integer) 6);
        assertEquals(test.size(), 3);
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(5, 10);
        test.add(6, 8);
        test.add(2, 11);
        test.changePriority(2, 7);
        assertEquals(test.getSmallest(), (Integer) 2);
        assertEquals(test.size(), 3);

    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(5, 15);
        test.add(6, 8);
        test.add(2, 19);
        test.add(7, 13);
        assertEquals(test.size(), 4);
        Integer temp = test.removeSmallest();
        assertEquals(test.size(), 3);

        assertEquals(temp, (Integer) 6);
        assertEquals(test.getSmallest(), (Integer) 7);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(5, 15);
        test.add(6, 8);
        test.add(2, 19);
        test.add(7, 13);
        assertEquals(test.size(), 4);

        assertTrue(test.contains(2));
        assertTrue(test.contains(6));
        assertTrue(test.contains(7));
        assertFalse(test.contains(17));

        test.removeSmallest();
        test.removeSmallest();
        assertEquals(test.size(), 2);

        assertFalse(test.contains(6));
        assertFalse(test.contains(7));
        assertTrue(test.contains(2));
        assertTrue(test.contains(5));
    }
}

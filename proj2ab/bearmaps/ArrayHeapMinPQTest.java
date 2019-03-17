package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
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
        test.add(10, 8);
        test.add(11, 8);

        assertEquals(test.getSmallest(), (Integer) 6);
        assertEquals(test.size(), 5);
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(5, 10);
        test.add(6, 8);
        test.add(2, 11);
        test.changePriority(2, 7);
        test.add(7, 5);
        test.add(8, 10);
        test.add(9, 12);
        assertEquals(test.getSmallest(), (Integer) 7);
        assertEquals(test.size(), 6);
        test.add(10, 20);
        test.removeSmallest();
        test.changePriority(10, 0);
        assertEquals(test.getSmallest(), (Integer) 10);
        test.changePriority(10, 9);
        assertEquals(test.getSmallest(), (Integer) 2);
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
        test.add(6, 17);
        test.add(7,10);
        assertTrue(test.contains(6));
        assertTrue(test.contains(7));
        assertFalse(test.contains(10));
        test.changePriority(2, 0);
        test.removeSmallest();
        assertFalse(test.contains(2));
        assertEquals(test.size(), 3);
    }

    @Test
    public void test() {

        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("Hi", 5);
        assertEquals(test.getSmallest(), "Hi");
        assertTrue(test.contains("Hi"));
        assertEquals(test.size(), 1);
        test.add("Hello", 4);
        test.add("bruh", 9);
        test.add("cmon", 2);
        test.add("cuh", 6);
        test.add("plz", 6);
        test.add("dnt", 8);
        test.add("do", 8);
        test.add("it", 9);
        test.add("n", 2);

        assertEquals(test.getSmallest(), "cmon");
        test.changePriority("cmon", 3);
        assertEquals(test.getSmallest(), "n");
    }

    @Test
    public void testTimeFast() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 0; i < Math.pow(10, 7); i++) {
            test.add(i, i);
        }
        Stopwatch timer1 = new Stopwatch();


        for (int i = 0; i < 10000; i++) {
            test.add((int) (i + Math.pow(10, 7)), 1);
        }

        test.changePriority(5000000, 5);
        test.contains(700000);
        test.removeSmallest();
        test.changePriority(700000, 5);
        test.removeSmallest();
        System.out.println("Fast Implementaiton Heap: "+ timer1.elapsedTime() + " seconds");
    }

    @Test
    public void testTimeNaive() {
        NaiveMinPQ<Integer> test = new NaiveMinPQ<>();
        for (int i = 0; i < Math.pow(10, 7); i++) {
            test.add(i, i);
        }


        Stopwatch timer1 = new Stopwatch();

        for (int i = 0; i < 10000; i++) {
            test.add((int) (i + Math.pow(10, 7)), 1);
        }

        test.changePriority(5000000, 5);
        test.contains(700000);
        test.removeSmallest();
        test.changePriority(700000, 5);
        test.removeSmallest();
        System.out.println("Slow Implementaiton Heap: "+ timer1.elapsedTime() + " seconds");
    }
}

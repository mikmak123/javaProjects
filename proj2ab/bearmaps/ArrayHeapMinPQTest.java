package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.lang.reflect.Array;

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
        Stopwatch timer1 = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            test.add(i, i);
            test.contains(i);
            test.getSmallest();
        }
        System.out.println("Fast Implementaiton 100,000 loops: "+ timer1.elapsedTime() + " seconds");

    }

    @Test
    public void testTimeNaive() {

        NaiveMinPQ<Integer> test = new NaiveMinPQ<>();
        Stopwatch timer1 = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            test.add(i, i);
            test.contains(i);
            test.getSmallest();
        }
        System.out.println("Slow Implementaiton 100,000 loops: "+ timer1.elapsedTime() + " seconds");
    }
}



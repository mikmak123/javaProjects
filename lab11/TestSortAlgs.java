import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;


public class TestSortAlgs {

    @Test
    public void testQuickSort() {

    }

    @Test
    public void testMergeSort() {

        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");

        Queue sorted = MergeSort.mergeSort(tas);

        Queue<String> test = new Queue<>();
        test.enqueue("Itai");
        test.enqueue("Joe");
        test.enqueue("Omar");

        for (String s : test) {
            assertEquals(s, sorted.dequeue());
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}

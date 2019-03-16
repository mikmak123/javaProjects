package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class test2 {
    public static void main(String[] args) {

        NaiveMinPQ<Integer> test = new NaiveMinPQ<>();
        Stopwatch timer1 = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            test.add(i, i);
            test.contains(i);
            test.getSmallest();
        }
        System.out.println(timer1.elapsedTime());
    }
}

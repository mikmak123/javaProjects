package bearmaps;


import edu.princeton.cs.algs4.Stopwatch;

public class test {


    public static void main(String[] args) {


        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        Stopwatch timer1 = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            test.add(i, i);
            test.contains(i);
            test.getSmallest();
        }
        System.out.println(timer1.elapsedTime());

    }
}

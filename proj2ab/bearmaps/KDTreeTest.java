package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static Random r = new Random(5000);

    @Test
    public void testInsert() {

        ArrayList<Point> temp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double x = Math.random() * 10;
            double y = Math.random() * 10;
            temp.add(new Point(x, y));
        }

        KDTree test = new KDTree(temp);

    }

    @Test
    public void testNearestSimple() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);

        KDTree test = new KDTree(List.of(p1, p2, p3, p4, p5, p6));

        assertEquals(test.nearest(0, 7), p5);

    }

    @Test
    public void testMore() {

        ArrayList<Point> cmon = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            cmon.add(new Point(r.nextDouble(), r.nextDouble()));
        }

        KDTree fast = new KDTree(cmon);
        NaivePointSet slow = new NaivePointSet(cmon);


        for (int i = 0; i < 10000; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            Point l = fast.nearest(x, y);
            Point z = slow.nearest(x, y);

            assertEquals(z, l);
        }

    }

    @Test
    public void testDup() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        Point p7 = new Point(2, 3);
        Point p8 = new Point(3, 3);
        Point p9 = new Point(1, 5);

        KDTree test = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));
        NaivePointSet test2 = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));
        assertEquals(test2.getSizeOf(), test.getSizeOf());

    }

    @Test
    public void testTimeFastvsNaive() {

        ArrayList<Point> time = new ArrayList<>();
        ArrayList<Point> point = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            time.add(new Point(r.nextDouble(), r.nextDouble()));
        }


        for (int i = 0; i < 10000; i++) {

            double x = r.nextDouble();
            double y = r.nextDouble();

            point.add(new Point(x, y));
        }
        KDTree fast = new KDTree(time);

        Stopwatch timer1 = new Stopwatch();
        for (Point p : point) {
            fast.nearest(p.getX(), p.getY());
        }
        double t1 = timer1.elapsedTime();

        System.out.println("Fast Implementaiton: " + t1 + " seconds");


        NaivePointSet slow = new NaivePointSet(time);

        Stopwatch timer2 = new Stopwatch();
        for (Point p : point) {
            slow.nearest(p.getX(), p.getY());
        }

        double t2 = timer2.elapsedTime();

        System.out.println("Naive Implementaiton: " + t2 + " seconds");

        System.out.println("KD Tree is: " + t2 / t1 + " times faster!");


    }
}

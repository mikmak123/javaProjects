package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

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
    public void testNearest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);

        KDTree test = new KDTree(List.of(p1, p2, p3, p4, p5, p6));

        assertEquals(test.nearest(0, 7), p5);

    }
}

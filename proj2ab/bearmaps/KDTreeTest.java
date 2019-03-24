package bearmaps;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void testInsert() {

        ArrayList<Point> temp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double x = Math.random()*10;
            double y = Math.random()*10;
            temp.add(new Point(x, y));
        }

        KDTree test = new KDTree(temp);

    }
}

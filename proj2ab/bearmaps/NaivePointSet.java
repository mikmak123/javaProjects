package bearmaps;
import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> store;
    private int sizeOf;

    public NaivePointSet(List<Point> points) {
        store = new ArrayList<>();
        sizeOf = 0;
        for (Point p : points) {
            if (store.contains(p)) {
                continue;
            } else {
                store.add(p);
                sizeOf++;
            }
        }
    }

    int getSizeOf() {
        return sizeOf;
    }

    @Override
    public Point nearest(double x, double y) {
        Point z = new Point(x, y);
        Point ret = store.get(0);
        double near = Point.distance(ret, z);
        for (Point p : store) {
            if (Point.distance(z, p) < near) {
                near = Point.distance(z, p);
                ret = p;
            }
        }
        return ret;
    }

}

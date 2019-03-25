package bearmaps;
import java.util.List;

public class NaivePointSet {

    private List<Point> store;

    public NaivePointSet(List<Point> points) {
        store = points;
    }

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

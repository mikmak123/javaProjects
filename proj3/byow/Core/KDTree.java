package byow.Core;

import java.util.List;

//@source took the implementation created in Proj 2
public class KDTree implements PointSet {
    private Node root;

    public KDTree(List<Point> points) {
        for (Point point: points) {
            root = put(point, root, true);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Node rtr = nearest(root, new Point(x, y), root, true);
        return rtr.point;
    }

    private Node nearest(Node n, Point goal, Node best, boolean horizontal) {
        if (n == null) {
            return best;
        }
        Node goalNode = new Node(goal);
        if (n.distance(goalNode) < best.distance(goalNode)) {
            best = n;
        }
        int comp = compare(goal, n.point, horizontal);
        Node good = n.right;
        Node bad = n.left;
        if (comp < 0) {
            good = n.left;
            bad = n.right;
        }
        best = nearest(good, goal, best, !horizontal);
        Node bestBadPoint = bestBad(n, goalNode, horizontal);
        if (bestBadPoint.distance(goalNode) < best.distance(goalNode)) {
            best = nearest(bad, goal, best, !horizontal);
        }
        return best;
    }

    private Node bestBad(Node n, Node goal, boolean horizontal) {
        Point rtr;
        if (horizontal) {
            rtr = new Point(n.point.getX(), goal.point.getY());
        } else {
            rtr = new Point(goal.point.getX(), n.point.getY());
        }
        return new Node(rtr);
    }

    private Node put(Point point, Node rt, boolean horizontal) {
        if (rt == null) {
            return new Node(point);
        }
        if (point.equals(rt.point)) {
            return rt;
        }
        int comp = compare(point, rt.point, horizontal);
        if (comp < 0) {
            rt.left = put(point, rt.left, !horizontal);
        } else {
            rt.right = put(point, rt.right, !horizontal);
        }
        return rt;
    }

    private int compare(Point p1, Point p2, boolean horizontal) {
        if (horizontal) {
            return Double.compare(p1.getX(), p2.getX());
        } else {
            return Double.compare(p1.getY(), p2.getY());
        }
    }

    private class Node {
        private Point point;
        private Node left; //also down
        private Node right; //also up

        private Node(Point p) {
            point = p;
        }

        private double distance(Node target) {
            return point.distance(point, target.point);
        }
    }

}

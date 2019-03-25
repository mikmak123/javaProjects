package bearmaps;

import java.util.List;

public class KDTree implements PointSet {

    private List<Point> store;
    private Node root;
    private int size;
    private int sizeOf;

    private class Node {
        private Point p;
        private Node left;
        private Node right;
        private int layout;

        Node(Point point, int lay) {
            p = point;
            layout = lay;
        }
    }
    private boolean hasLeft(Node n) {
        return n.left != null;
    }
    private boolean hasRight(Node n) {
        return n.right != null;
    }

    public KDTree(List<Point> points) {
        store = points;
        size = store.size();
        root = new Node(store.get(0), 0);
        sizeOf = 1;
        if (size > 1) {
            for (int i = 1; i < store.size(); i++) {
                insert(store.get(i), root, 1);
            }
        }
    }

    private double X(Node n) {
        return n.p.getX();
    }
    private double Y(Node n) {
        return n.p.getY();
    }

    private void insert(Point p, Node n, int c) {
        if (p.getX() == X(n) && p.getY() == Y(n)) {
            return;
        }
        if (n.layout % 2 == 0) {
            if (p.getX() <= X(n)) {
                if (!hasLeft(n)) {
                    n.left = new Node(p, c);
                    sizeOf++;
                } else {
                    insert(p, n.left, c + 1);
                }
            } else {
                if (!hasRight(n)) {
                    n.right = new Node(p, c);
                    sizeOf++;
                } else {
                    insert(p, n.right, c + 1);
                }
            }
        } else {
            if (p.getY() <= Y(n)) {
                if (!hasLeft(n)) {
                    n.left = new Node(p, c);
                    sizeOf++;
                } else {
                    insert(p, n.left, c + 1);
                }
            } else {
                if (!hasRight(n)) {
                    n.right = new Node(p, c);
                    sizeOf++;
                } else {
                    insert(p, n.right, c + 1);
                }
            }
        }
    }

    int getSizeOf() {
        return sizeOf;
    }

    private int compare(Node one, Node two) {
        if (one.layout % 2 == 0) {
            if (X(one) < X(two)) {
                return -1;
            } else if (X(one) == X(two)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (Y(one) < Y(two)) {
                return -1;
            } else if (Y(one) == Y(two)) {
                return 0;
            } else {
                return 1;
            }
        }
    }


    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root);
    }

    private Point nearest(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;
        if (n == null) {
            return best.p;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        if (compare(n, new Node(goal, 0)) <= 0) {
            goodSide = n.right;
            badSide = n.left;

        } else {
            goodSide = n.left;
            badSide = n.right;
        }
        best = new Node(nearest(goodSide, goal, best), 0);
        double one = Point.distance(new Point(X(n), goal.getY()), goal);
        double two = Point.distance(new Point(goal.getX(), Y(n)), goal);

        if (n.layout % 2 == 0) {
            if (one < Point.distance(best.p, goal)) {
                best = new Node(nearest(badSide, goal, best), 0);
            }
        }
        if (n.layout % 2 == 1) {
            if (two < Point.distance(best.p, goal)) {
                best = new Node(nearest(badSide, goal, best), 0);
            }
        }
        return best.p;
    }

}


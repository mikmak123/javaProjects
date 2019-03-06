package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] perc;
    private int openSites;
    private int top;
    private int bottom;
    private int len;
    WeightedQuickUnionUF track;


    public Percolation(int N) {
        perc = new boolean[N][N];
        track = new WeightedQuickUnionUF(N * N + 2);
        openSites = 0;
        top = N * N;
        bottom = N * N + 1;
        len = N;
    }

    private int convert(int i, int j) {
        return i * perc.length + j;
    }

    public void open(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (perc[row][col] == true) {
            return;
        } else {
            perc[row][col] = true;
            openSites++;
            int space = convert(row, col);
            int one = convert(row - 1, col);
            int two = convert(row + 1, col);
            int three = convert(row, col + 1);
            int four = convert(row, col - 1);

            for (int i = 0; i < len; i++) {
                if (space == i) {
                    track.union(top, convert(row, col));
                    if (space == 0) {
                        if (isOpen(row + 1, col)) {
                            track.union(space, two);
                        }
                    return;
                    } else if (space == len - 1) {
                        if (isOpen(row + 1, col)) {
                            track.union(space, two);
                        }
                        return;
                    } else {
                        if (isOpen(row + 1, col)) {
                            track.union(space, two);
                        }
                        if (isOpen(row, col + 1)) {
                            track.union(space, three);
                        }
                        if (isOpen(row, col - 1)) {
                            track.union(space, four);
                        }
                        return;
                    }
                }
            }
            for (int i = len * len - len; i < len * len; i++) {
                if (space == i) {
                    track.union(bottom, space);
                    if (space == len * len - len) {
                        if (isOpen(row - 1, col)) {
                            track.union(space, one);
                        }
                        return;
                    } else if (space == len * len - 1) {
                        if (isOpen(row - 1, col)) {
                            track.union(space, one);
                        }
                        return;
                    } else {
                        if (isOpen(row - 1, col)) {
                            track.union(space, one);
                        }
                        if (isOpen(row, col + 1)) {
                            track.union(space, three);
                        }
                        if (isOpen(row, col - 1)) {
                            track.union(space, four);
                        }
                        return;
                    }

                }
            }

            for (int i = len; i < len * (len - 1); i += len) {
                if (space == i) {
                    if (isOpen(row + 1, col)) {
                        track.union(space, two);
                    }
                    if (isOpen(row, col + 1)) {
                        track.union(space, three);
                    }
                    if (isOpen(row - 1, col)) {
                        track.union(space, one);
                    }
                    return;
                }
            }
            for (int i = len * 2 - 1; i < len * len - 1; i += len) {
                if (space == i) {
                    if (isOpen(row - 1, col)) {
                        track.union(space, one);
                    }
                    if (isOpen(row, col - 1)) {
                        track.union(space, four);
                    }
                    if (isOpen(row + 1, col)) {
                        track.union(space, two);
                    }
                    return;
                }
            }
            if (isOpen(row + 1, col)) {
                track.union(space, two);
            }
            if (isOpen(row - 1, col)) {
                track.union(space, one);
            }
            if (isOpen(row, col + 1)) {
                track.union(space, three);
            }
            if (isOpen(row, col - 1)) {
                track.union(space, four);
            }
                }
            }

    public boolean isOpen(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return perc[row][col];
    }


    public boolean isFull(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return track.connected(convert(row, col), top);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {

        return track.connected(top, bottom);
    }

    public static void main(String[] args) {

        Percolation test = new Percolation(10);
        test.open(0,0);
        test.open(1,0);
        test.open(2,2);
        test.open(1,2);
        test.open(0,1);
        test.open(3, 9);
        System.out.println(test.isOpen(0,0));
        System.out.println(test.isFull(0, 0));
        System.out.println(test.isFull(1,1));
        System.out.println(test.isFull( 3,3));
        System.out.println(test.percolates());
    }
}

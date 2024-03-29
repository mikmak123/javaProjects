package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] perc;
    private int openSites;
    private int top;
    private int bottom;
    private int len;
    private WeightedQuickUnionUF track;
    private WeightedQuickUnionUF second;


    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        perc = new boolean[N][N];
        track = new WeightedQuickUnionUF(N * N + 2);
        second = new WeightedQuickUnionUF(N * N + 2);
        openSites = 0;
        top = N * N;
        bottom = N * N + 1;
        len = N;
    }

    private int convert(int i, int j) {
        return i * perc.length + j;
    }

    private void sideRight(int row, int col) {
        int space = convert(row, col);
        int one = convert(row - 1, col); int two = convert(row + 1, col);
        int three = convert(row, col + 1); int four = convert(row, col - 1);

        if (isOpen(row - 1, col)) {
            track.union(space, one); second.union(space, one);
        }
        if (isOpen(row, col - 1)) {
            track.union(space, four); second.union(space, four);
        }
        if (isOpen(row + 1, col)) {
            track.union(space, two); second.union(space, two);
        }
    }

    private void sideLeft(int row, int col) {
        int space = convert(row, col);
        int one = convert(row - 1, col); int two = convert(row + 1, col);
        int three = convert(row, col + 1); int four = convert(row, col - 1);

        if (isOpen(row + 1, col)) {
            track.union(space, two); second.union(space, two);
        }
        if (isOpen(row, col + 1)) {
            track.union(space, three); second.union(space, three);
        }
        if (isOpen(row - 1, col)) {
            track.union(space, one); second.union(space, one);
        }
    }

    private void middle(int row, int col) {
        int space = convert(row, col);
        int one = convert(row - 1, col); int two = convert(row + 1, col);
        int three = convert(row, col + 1); int four = convert(row, col - 1);

        if (isOpen(row + 1, col)) {
            track.union(space, two); second.union(space, two);
        }
        if (isOpen(row - 1, col)) {
            track.union(space, one); second.union(space, one);
        }
        if (isOpen(row, col + 1)) {
            track.union(space, three); second.union(space, three);
        }
        if (isOpen(row, col - 1)) {
            track.union(space, four); second.union(space, four);
        }
    }
    private void bottomLeft(int row, int col) {
        int space = convert(row, col);
        int one = convert(row - 1, col); int two = convert(row + 1, col);
        int three = convert(row, col + 1); int four = convert(row, col - 1);
        if (isOpen(row - 1, col)) {
            track.union(space, one); second.union(space, one);
        }
        if (isOpen(row, col + 1)) {
            track.union(space, three); second.union(space, three);
        }
        if (isFull(row - 1, col) || isFull(row, col + 1)) {
            track.union(bottom, space);
        }
    }
    private void bottomRight(int row, int col) {
        int space = convert(row, col);
        int one = convert(row - 1, col); int two = convert(row + 1, col);
        int three = convert(row, col + 1); int four = convert(row, col - 1);

        if (isOpen(row - 1, col)) {
            track.union(space, one); second.union(space, one);
        }
        if (isOpen(row, col - 1)) {
            track.union(space, four); second.union(space, four);
        }
        if (isFull(row - 1, col) || isFull(row, col - 1)) {
            track.union(bottom, space);
        }
    }



    public void open(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (!perc[row][col]) {
            perc[row][col] = true; openSites++; int space = convert(row, col);
            int one = convert(row - 1, col); int two = convert(row + 1, col);
            int three = convert(row, col + 1); int four = convert(row, col - 1);
            for (int i = 0; i < len; i++) {
                if (space == i) {
                    track.union(top, convert(row, col)); second.union(space, top);
                    if (space == 0) {
                        if (len == 1) {
                            track.union(bottom, top); second.union(bottom, top);
                            return;
                        }
                        if (isOpen(row + 1, col)) {
                            track.union(space, two); second.union(space, two);
                        }
                        return;
                    } else if (space == len - 1) {
                        if (isOpen(row + 1, col)) {
                            track.union(space, two); second.union(space, two);
                        }
                        return;
                    } else {
                        if (isOpen(row + 1, col)) {
                            track.union(space, two); second.union(space, two);
                        }
                        if (isOpen(row, col + 1)) {
                            track.union(space, three); second.union(space, three);
                        }
                        if (isOpen(row, col - 1)) {
                            track.union(space, four); second.union(space, four);
                        }
                        return;
                    }
                }
            }
            for (int i = len * len - len; i < len * len; i++) {
                if (space == i) {
                    second.union(space, bottom);
                    if (space == len * len - len) {
                        bottomLeft(row, col);
                        return;
                    } else if (space == len * len - 1) {
                        bottomRight(row, col);
                        return;
                    } else {
                        if (isOpen(row - 1, col)) {
                            track.union(space, one); second.union(space, one);
                        }
                        if (isOpen(row, col + 1)) {
                            track.union(space, three); second.union(space, three);
                        }
                        if (isOpen(row, col - 1)) {
                            track.union(space, four); second.union(space, four);
                        }
                        if (isFull(row - 1, col) || isFull(row, col + 1) || isFull(row, col - 1)) {
                            track.union(space, bottom);
                        }
                        return;
                    }

                }
            }
            for (int i = len; i < len * (len - 1); i += len) {
                if (space == i) {
                    sideLeft(row, col);
                    return;
                }
            }
            for (int i = len * 2 - 1; i < len * len - 1; i += len) {
                if (space == i) {
                    sideRight(row, col);
                    return;
                }
            }
            middle(row, col);
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

        return second.connected(top, bottom);
    }

    public static void main(String[] args) {

    }
}

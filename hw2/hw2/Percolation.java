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
        top = N * N + 1;
        bottom = N * N + 2;
        len = N;
    }

    private int convert(int i, int j) {
        return i * perc.length + j;
    }

    public void open(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Your row or column is out of range");
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
                if (convert(row, col) == i) {
                    track.union(convert(row, col), len + 1);
                }
            }
            for (int i = len * len - len; i < len * len; i++) {
                if (convert(row, col) == i) {
                    track.union(convert(row, col), bottom);
                }
            }

            for (int i = len; i < len * (len - 1); i += len) {

                if (space == i) {
                    track.union(space, two);
                    track.union(space, four);
                    track.union(space, three);
                }
            }
            for (int i = len + 4; i < len * len - 1; i += len) {
                if (space == i) {
                    track.union(space, one);
                    track.union(space, four);
                    track.union(space, three);
                } else {
                    track.union(space, one);
                    track.union(space, two);
                    track.union(space, four);
                    track.union(space, three);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Your row or column is out of range");
        }
        return perc[row][col];
    }


    public boolean isFull(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Your row or column is out of range");
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

    }
}

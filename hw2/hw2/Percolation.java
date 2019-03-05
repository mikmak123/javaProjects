package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] perc;
    private int openSites;
    WeightedQuickUnionUF track;

    public Percolation(int N) {
        perc = new boolean[N][N];
        track = new WeightedQuickUnionUF((N + 2) * (N + 2));
        openSites = 0;
    }

    public void open(int row, int col) {
        if (row >= perc.length || col >= perc.length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Your row or column is out of range");
        }
        perc[row][col] = true;
        track.union((2 + perc.length) * (row + 1) + col + 2,
                (2 + perc.length) * (row - 1 + 1) + col + 2);
        track.union((2 + perc.length) * (row + 1) + col + 2,
                (2 + perc.length) * (row + 1 + 1) + col + 2);
        track.union((2 + perc.length) * (row + 1) + col + 2,
                (2 + perc.length) * (row + 1) + col - 1 + 2);
        track.union((2 + perc.length) * (row + 1) + col + 2,
                (2 + perc.length) * (row + 1) + col + 1 + 2);
        openSites++;
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
        for (int i = 1; i <= perc.length; i++) {
            if (track.connected((2 + perc.length) * (row + 1) + col + 2, i)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {

        for (int i = 0; i < perc.length; i++) {
            if (isFull(perc.length - 1, i)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}

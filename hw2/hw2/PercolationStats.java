package hw2;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int side;
    private Percolation fac;
    private int exp;
    private double[] fraction;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        side = N;
        exp = T;
        fraction = new double[exp];

        for (int i = 0; i < exp; i++) {
            fac = pf.make(N);
            while (!fac.percolates()) {
                int row = StdRandom.uniform(0, side);
                int col = StdRandom.uniform(0, side);
                if (!fac.isOpen(row, col)) {
                    fac.open(row, col);
                }
            }
            fraction[i] = (double) fac.numberOfOpenSites() / (side * side);
        }
    }

    public double mean() {
        return StdStats.mean(fraction);
    }
    public double stddev() {
        return StdStats.stddev(fraction);
    }
    public double confidenceLow() {
        double num = mean() - 1.96 * stddev();
        return num / Math.sqrt(exp);
    }
    public double confidenceHigh() {
        double num = mean() + 1.96 * stddev();
        return num / Math.sqrt(exp);
    }

}

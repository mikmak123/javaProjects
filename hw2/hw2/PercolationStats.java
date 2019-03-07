package hw2;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int side;
    private PercolationFactory fac;
    private int exp;
    private int[] fraction;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        side = N;
        exp = T;
        fac = pf;
        fraction = new int[exp];

        for (int i = 0; i < exp; i++) {
            Percolation test = pf.make(N);

            while (!test.percolates()) {

                int row = StdRandom.uniform(0, side);
                int col = StdRandom.uniform(0, side);
                if (!test.isOpen(row, col)) {
                    test.open(row, col);
                }
            }
            fraction[i] = test.numberOfOpenSites() / (side * side);
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

package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] dataRecord;
    // We want the fraction of open sites, so the type must be double!
    private int N;
    private int T;
    private PercolationFactory pf;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0
                || T <= 0) {
            throw new IllegalArgumentException("The grid size"
                    + " and the trial times must be positive!");
        }
        dataRecord = new double[N];
        this.pf = pf;
        this.acquireDataArray();
    }
    private void randomFillSite(Percolation pc) {
        // Given a Percolation model, gradually open its sites.
        // Just keep opening. My checking standard is that this finally percolates.
        int row = StdRandom.uniform(N);
        int col = StdRandom.uniform(N);
        pc.open(row, col);
    }
    private void acquireDataArray() {
        // Generate the whole array recording results of the T times of trial.
        for (int i = 0; i < T; i++) {
            dataRecord[i] = testOnSingleGrid();
        }
    }
    private double testOnSingleGrid() {
        if (N == 1) {
            return 1.0;
        }
        Percolation pc = pf.make(N);
        while (!pc.percolates()) {
            randomFillSite(pc);
        }
        // After the loop, it should exactly percolate.
        return (double) pc.numberOfOpenSites() / (N * N);
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(dataRecord);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        if (T == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(dataRecord);
    }
    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}

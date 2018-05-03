package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private double[] stats;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Cannot use these parameters");
        }

        this.T = T;
        stats = new double[T];

        for (int i = 0; i < stats.length; i++) {
            Percolation perc = pf.make(N);
            stats[i] = generateStat(perc, N);
        }
    }

    private double generateStat(Percolation p, int N) {
        while (!p.percolates()) {
            int row = StdRandom.uniform(0, N);
            int col = StdRandom.uniform(0, N);
            p.open(row, col);
        }

        return (double) p.numberOfOpenSites() / (N * N);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);

        /*double total = 0;
        for (int i = 0; i < stats.length; i++) {
            total += stats[i];
        }

        return total / T;*/
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stats);
        /*double total = 0;
        for (int i = 0; i < stats.length; i++) {
            total += Math.pow((stats[i] - mean()), 2);
        }

        return Math.sqrt(total / (T - 1));*/
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double secondary = (1.96 * stddev()) / (Math.sqrt(T));
        return mean() - secondary;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double secondary = (1.96 * stddev()) / (Math.sqrt(T));
        return mean() + secondary;
    }
}

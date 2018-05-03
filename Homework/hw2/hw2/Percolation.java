package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import java.lang.IllegalArgumentException;

public class Percolation {
    private int N;
    private int botTank;
    private int edge;
    private int topTank = 0;
    private int openSpaces;
    private boolean[][] tank;
    private WeightedQuickUnionUF unionSet;
    private WeightedQuickUnionUF unionSetNoBot;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Parameter cannot be a number <= 0");
        }

        this.N = N;
        botTank = (N * N) + 1;
        edge = (N * N);
        openSpaces = 0;

        tank = new boolean[N][N];
        for (int i = 0; i < tank.length; i++) {
            for (int j = 0; j < tank[i].length; j++) {
                tank[i][j] = false;
            }
        }

        unionSet = new WeightedQuickUnionUF(botTank + 1);
        unionSetNoBot = new WeightedQuickUnionUF(botTank + 1);
    }

    private int getCoord(int row, int col) {
        return (row * N) + col + 1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException("Cannot use these values for row and column");
        } else if (tank[row][col]) {
            return;
        }

        tank[row][col] = true;


        int coord = getCoord(row, col);
        //System.out.println(coord);
        if (checkTop(row, col)) {
            unionSet.union(topTank, coord);
            unionSetNoBot.union(topTank, coord);
        }

        unionTop(row, col);
        unionBottom(row, col);
        unionLeft(row, col);
        unionRight(row, col);

        if (checkBot(row, col)) {
            unionSet.union(botTank, coord);
        }

        openSpaces++;
    }

    private void unionTop(int row, int col) {
        try {
            if (isOpen(row - 1, col)) {
                unionSet.union(getCoord(row - 1, col), getCoord(row, col));
                unionSetNoBot.union(getCoord(row - 1, col), getCoord(row, col));
            }
        } catch (IndexOutOfBoundsException ex) {
            return;
        }
    }

    private void unionBottom(int row, int col) {
        try {
            if (isOpen(row + 1, col)) {
                unionSet.union(getCoord(row + 1, col), getCoord(row, col));
                unionSetNoBot.union(getCoord(row + 1, col), getCoord(row, col));
            }
        } catch (IndexOutOfBoundsException ex) {
            return;
        }
    }

    private void unionLeft(int row, int col) {
        try {
            if (isOpen(row, col - 1)) {
                unionSet.union(getCoord(row, col - 1), getCoord(row, col));
                unionSetNoBot.union(getCoord(row, col - 1), getCoord(row, col));
            }
        } catch (IndexOutOfBoundsException ex) {
            return;
        }
    }

    private void unionRight(int row, int col) {
        try {
            if (isOpen(row, col + 1)) {
                unionSet.union(getCoord(row, col + 1), getCoord(row, col));
                unionSetNoBot.union(getCoord(row, col + 1), getCoord(row, col));
            }
        } catch (IndexOutOfBoundsException ex) {
            return;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        //System.out.println(N - 1);
        if (row < 0 || row > (N - 1) || col < 0 || col > (N - 1)) {
            throw new IndexOutOfBoundsException("Cannot use these values for row and column");
        }

        return tank[row][col];
    }

    private boolean checkTop(int row, int col) {
        return getCoord(row, col) <= N;
    }

    private boolean checkBot(int row, int col) {
        int coord = getCoord(row, col);
        return coord >= (botTank - N) && coord <= edge;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException("Cannot use these values for row and column");
        }

        int coord = getCoord(row, col);
        return unionSetNoBot.connected(topTank, coord) && isOpen(row, col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSpaces;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionSet.connected(topTank, botTank);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        test.open(0, 2);
        test.open(1, 2);
        test.open(2, 2);
        test.open(2, 0);
        System.out.println(test.percolates());
        System.out.println(test.isFull(2, 0));
    }
}

package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
public class Percolation {
   public Percolation(int N)                // create N-by-N grid, with all sites initially blocked
   public void open(int row, int col)       // open the site (row, col) if it is not open already
   public boolean isOpen(int row, int col)  // is the site (row, col) open?
   public boolean isFull(int row, int col)  // is the site (row, col) full?
   public int numberOfOpenSites()           // number of open sites
   public boolean percolates()              // does the system percolate?
   public static void main(String[] args)   // use for unit testing (not required)
}
 */
public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final int size;
    private int numberOfOpen;
    private final boolean[][] blockArray;
    private final int virtualTop;
    private int xyTo1D(int row, int col) {
        /*
        int row: the row coordinate, from 0 to n-1;
        int col: the col coordinate, from 0 to n-1;
         */
        return row * size + col;
    }
    private void boundJudge(int row, int col) {
        if (row > size - 1
                || row < 0
                || col > size - 1
                || col < 0) {
            throw new IndexOutOfBoundsException("Indices must be within the size of the grid!");
        }
    }
    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        // Must be proportional to N^2
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be positive!");
        }
        this.size = N;
        this.uf = new WeightedQuickUnionUF(N * N + 1);
        this.blockArray = new boolean[N][N];
        this.virtualTop = N * N;
        this.numberOfOpen = 0;
        /*for (int j = 0; j < N; j++) {
            // connect the sites of the first row to the virtual "water source".
            // Worthy of noting: 1 site for water source is equivalent to a row of those!
            uf.union(virtualTop, xyTo1D(0, j));
        }
        * This is commented out, because openness is the deterministic condition.
        */

    }
    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        boundJudge(row, col);
        if (isOpen(row, col)) {
            return;
        }
        blockArray[row][col] = true;
        numberOfOpen += 1;
        // try to connect to surrounding blocks
        int currentSite1D = xyTo1D(row, col);
        if (row == 0) {
            uf.union(xyTo1D(row, col), virtualTop);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            // for the upper side one
            int upperNeighbor = xyTo1D(row - 1, col);
            if (!uf.connected(upperNeighbor, currentSite1D)) {
                uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        }
        if (col > 0 && isOpen(row, col - 1)) {
            // for the left side one
            int leftNeighbor = xyTo1D(row, col - 1);
            if (!uf.connected(leftNeighbor, currentSite1D)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
        if (col < size - 1 && isOpen(row, col + 1)) {
            // for the right side one
            int rightNeighbor = xyTo1D(row, col + 1);
            if (!uf.connected(rightNeighbor, currentSite1D)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
        if (row < size - 1 && isOpen(row + 1, col)) {
            // for the below one
            int underNeighbor = xyTo1D(row + 1, col);
            if (!uf.connected(underNeighbor, currentSite1D)) {
                uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }
    }
    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        boundJudge(row, col);
        return blockArray[row][col];
    }
    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        boundJudge(row, col);
        return uf.connected(virtualTop, xyTo1D(row, col)) && isOpen(row, col);
    }
    public int numberOfOpenSites() {
        // number of open sites
        return numberOfOpen;
    }
    public boolean percolates() {
        // does the system percolate?
        // not sure about this method.
        // A system is said to percolate if there is a full site
        // in the bottom row of the grid.
        // PROBLEMATIC!
        if (size == 1) {
            return isOpen(0, 0);
        }
        for (int j = 0; j < size; j++) {
            if (uf.find(xyTo1D(size - 1, j))
                    == virtualTop) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        // use for unit testing (not required)
    }
}

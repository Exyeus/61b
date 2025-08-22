package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.Objects;

public class Board implements WorldState {

    private final int[][] tiles;
    private final int size;

    /** Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     * @param tiles N-by-N array of tiles
     */
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.size = tiles[0].length;
        if (size == 0) {
            throw new IllegalArgumentException("Tiles must be able to contain");
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i Row number
     * @param j Column number
     */

    public int tileAt(int i, int j) {
        if (i < 0
                || j < 0
                || i > size - 1
                || j > size - 1) {
            throw new IllegalArgumentException("Indices must be within the range");
        }
        return tiles[i][j];
    }

    /**
     * @return The board size N
     */
    public int size() {
        return size;
    }

    /**
     * @return The neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        int blank = 0;
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == blank) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = blank;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = blank;
                }
            }
        }
        return neighbors;

    }

    /**
     * The numbers of the elements not on their deserved tile
     * @return hamming estimate
     */
    public int hamming() {
        int misplace  = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                if (indicesToCorresNum(i, j) != tileAt(i, j)) {
                    misplace += 1;
                }
            }
        }
        // Check the last row later
        for (int j = 0; j < size - 1; j++) {
            if (indicesToCorresNum(size - 1, j) != tileAt(size - 1, j)) {
                misplace += 1;
            }
        }
        return misplace;
    }

    /**
     * The moves taken, for elements to get to their destined tile,
     * not regarding any restrictions, which will guarantee the estimate
     * is less than the actual expense.
     * @return Manhattan distance as an estimate
     */
    public int manhattan() {
        // TO DO: Be cautious about the 0 at (N - 1, N - 1)
        int manhattanSum = 0;
        int tempTileValue;
        int deservedRowNumber, deservedColumnNumber;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Iterate through all the tiles.
                // For example:
                // 2 0
                // 1 3; 2 = tileAt(0, 0)
                tempTileValue = tileAt(i, j);
                if (tempTileValue != 0) {
                    deservedRowNumber = Math.floorDiv(tempTileValue - 1, 2);
                    deservedColumnNumber = (tempTileValue - 1) % size;
                    manhattanSum += (Math.abs(i - deservedRowNumber)
                            + Math.abs(j - deservedColumnNumber));
                }
            }
        }
        return manhattanSum;
    }

    /**
     *
     * @return Possible steps taken to reach the goal from current node
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * @param y Another board
     * @return return true, if the two boards have the same tiles.
     */
    @Override
    public boolean equals(Object y) {
        if (y == null || getClass() != y.getClass()) {
            return false;
        } else {
            return (this.size == ((Board) y).size
                    && Arrays.deepEquals(this.tiles, ((Board) y).tiles));
        }
    }

    /** Returns the string representation of the board.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    private int indicesToCorresNum(int i, int j) {
        if ((i == size) && (j == size)) {
            return 0;
        }
        return size * i + j + 1;

    }
    @Override
    public int hashCode() {
        /*int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result = result + 1;
                result = result * tiles[i][j];
            }
            result %= 0x7fffffff;
        }
        return result;*/
        return Objects.hash(size, Arrays.deepHashCode(tiles));
    }

}

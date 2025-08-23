package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<SearchNode> searchPQ;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        searchPQ = new MinPQ<>();
    }

    private class SearchNode implements Comparable<SearchNode> {
        public int item;
        public int priority;
        public SearchNode(int v) {
            item = v;
            priority = h(v);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { // 1. 自反性
                return true;
            }
            // 2. 考虑 null 和类检查 (或者使用 instanceof)
            // 使用 getClass() 保证严格的类匹配
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SearchNode other = (SearchNode) o; // 3. 类型安全地向下转型

            // 4. 比较字段 (安全地处理 null)
            return Objects.equals(item, other.item);
        }
        @Override
        public int hashCode() {
            int result = 2;
            result += item;
            result *= 97;
            result += priority;
            result *= 13;
            return result;
        }
        @Override
        public int compareTo(SearchNode sn) {
            return Integer.compare(priority, sn.priority);
        }
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t))
                + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO: Utilize PQ to simplify the searching process,
        //  and remember to use announce to display the advancements.
        searchPQ.insert(new SearchNode(s));

        if (s == t) {
            targetFound = true;
            return;
        }
        announce();
        while (!targetFound && !searchPQ.isEmpty()) {
            announce();
            SearchNode currentNode = searchPQ.delMin(); // Deletion is on!

            for (int neighbor : maze.adj(currentNode.item)) {
                if (neighbor == t) {
                    targetFound = true;
                    return;
                }
                // Add new nodes
                if (!marked[neighbor]) {
                    searchPQ.insert(new SearchNode(neighbor)); // Insertion is on!
                    marked[neighbor] = true;
                    distTo[neighbor] = distTo[currentNode.item] + 1;
                    edgeTo[neighbor] = currentNode.item;
                }

            }
            announce();
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}


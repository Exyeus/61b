package lab11.graphs;

import edu.princeton.cs.algs4.ResizingArrayQueue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int s;
    private final int t;
    private boolean targetFound = false;
    private final Maze maze;
    private final ResizingArrayQueue<Integer> bfsQueue;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        bfsQueue = new ResizingArrayQueue<>();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here.
        //  Don't forget to update distTo, edgeTo,
        //  and marked, as well as call announce()

        bfsQueue.enqueue(s);
        marked[s] = true;

        announce();

        while (!bfsQueue.isEmpty()) {

            int currentPosition = bfsQueue.dequeue();
            marked[currentPosition] = true;

            if (currentPosition == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }

            announce();
            // Search for its neighbors
            for (int neighbor : maze.adj(currentPosition)) {
                announce();
                // To detect the target as it appears.
                if (neighbor == t) {
                    targetFound = true;
                    marked[neighbor] = true;
                    return;
                }

                if (!marked[neighbor]) {
                    edgeTo[neighbor] = currentPosition;
                    distTo[neighbor] = distTo[currentPosition] + 1;
                    marked[neighbor] = true;
                    bfsQueue.enqueue(neighbor);
                    // 妈的，记得 enqueue !
                }
                /*if (!marked[neighbor] // Not visited
                        || (distTo[neighbor] > distTo[currentPosition] + 1 // Too long path taken
                        && marked[neighbor])) {

                    distTo[neighbor] = distTo[currentPosition] + 1;
                    edgeTo[neighbor] = currentPosition;
                    marked[neighbor] = true;
                }*/
            }
        }

    }


    @Override
    public void solve() {
        bfs();
    }
}


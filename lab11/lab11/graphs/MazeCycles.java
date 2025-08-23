package lab11.graphs;

import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;

import java.util.TreeSet;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    public Stack<Integer> dfsStack;
    private int s;
    private boolean cycleFounded = false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        dfsStack = new Stack<>();
        maze = m;
        s = 0;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        //  Remember, announce should be used only when the cycle is discovered

        dfs(0);

    }

    // Helper methods go here
    private void dfs(int start) {
        marked[start] = true;

        if (((TreeSet<Integer>) maze.adj(start)).isEmpty()) {
            return;
        }
        if (cycleFounded) {
            return;
        }
        for (int w : maze.adj(start)) {
            if (!marked[w] && edgeTo[start] != w) {
                announce();
                cycleFounded = true;
            }
            if (!marked[w]) {
                edgeTo[w] = start;
                distTo[w] = distTo[start] + 1;
                dfs(w);
            }
            if (cycleFounded) {
                return;
            }
        }
    }
}


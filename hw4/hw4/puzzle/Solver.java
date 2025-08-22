package hw4.puzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap; // Import HashMap for visited states
import java.util.List;
import java.util.Map;   // Import Map
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    // --- 核心修改：SearchNode 的定义 ---
    // SearchNode 需要知道 g, h, f，并根据 f 排序。
    // isVisited 字段在此标准 A* 实现中将被外部 Map 替换。
    private static class SearchNode implements Comparable<SearchNode> {
        public WorldState currentState;
        public int movesFromInitial; // This is 'g' in A*
        public SearchNode previousSearchNode;
        // private boolean isVisited; // Not needed here as we use an external map

        // A* 需要 g (movesFromInitial) 和 h (estimatedDistanceToGoal) 来计算 f
        private final int g; // Actual cost from start to this node
        private final int h; // Estimated cost from this node to goal
        private final int f; // Total estimated cost (f = g + h)

        // 构造函数需要 WorldState 来计算 h，并存储 g, h, f
        public SearchNode(WorldState currentState, int moves, SearchNode previous) {
            this.currentState = currentState;
            this.movesFromInitial = moves; // Keep for result
            this.previousSearchNode = previous;

            this.g = moves; // Set g
            // Calculate h. Assumes WorldState.estimatedDistanceToGoal() knows the goal.
            // If WorldState.estimatedDistanceToGoal() requires the goal state as an argument,
            // this part needs adjustment (e.g., passing goalState to Solver and then here).
            this.h = currentState.estimatedDistanceToGoal();
            this.f = this.g + this.h; // Calculate f
        }

        /**
         * For the comparable traits, to fit MinPQ storage!
         * A* prioritizes based on f = g + h.
         * @param y another SearchNode Object
         * @return The priority they will be called for search (based on f-value)
         */
        @Override
        public int compareTo(SearchNode y) {
            // PriorityQueue sorts in ascending order, so we compare f values.
            // If f values are equal, secondary sort by h is common, but not strictly required for correctness.
            // We prioritize lower f value.
            int fComparison = Integer.compare(this.f, y.f);
            if (fComparison != 0) {
                return fComparison;
            }
            // Optional: break ties with h value (heuristic)
            return Integer.compare(this.h, y.h);
        }

        // For using SearchNode (specifically currentState) as keys in HashMap,
        // we need equals and hashCode. These should operate on the currentState.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchNode that = (SearchNode) o;
            return this.currentState.equals(that.currentState);
        }

        @Override
        public int hashCode() {
            return this.currentState.hashCode();
        }
    }

    // MinPQ 存储 SearchNode
    private final MinPQ<SearchNode> searchPath;
    // movesToFinish: 最终步数
    private int movesToFinish;
    // resultWorldStates: 存储最终路径
    private final List<WorldState> resultWorldStates;

    // 变量：visitedMap 用于存储已经处理过的状态及其最优 g 值
    // Key: WorldState, Value: 最优 g 值 (movesFromInitial)
    private Map<WorldState, Integer> visitedMap;

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial The beginning state, in type WorldState
     */
    public Solver(WorldState initial) {
        this.searchPath = new MinPQ<>();
        this.resultWorldStates = new ArrayList<>();
        this.visitedMap = new HashMap<>(); // Initialize the visited map

        // 插入初始节点
        // 构造函数需要 WorldState 来计算 h
        SearchNode initialNode = new SearchNode(initial, 0, null);
        this.searchPath.insert(initialNode);

        // 运行 solve() 方法来计算结果
        this.solve();
    }

    /**
     * Solves the puzzle using the A* algorithm.
     * This method uses the searchPath (MinPQ) and updates
     * movesToFinish and resultWorldStates.
     */
    private void solve() {
        // Loop until the priority queue is empty (no solution) or the goal is found.
        while (!searchPath.isEmpty()) {
            // 1. 从优先队列中取出 f 值最小的节点（当前最优节点）
            SearchNode currentNode = searchPath.delMin();

            // 2. 检查当前状态是否已经被访问过，并且新路径（currentNode.g）比已记录的最优路径更长。
            //    如果visitedMap 包含 currentNode.currentState 并且其记录的 g 值小于 currentNode.g，
            //    说明我们已经找到一条更优的路径到达这个状态，所以当前路径是冗余的，跳过。
            //    注意：我们使用 '<' 而不是 '<=' 来允许找到多条等长最短路径，
            //    并且在第一次到达某状态时，它的 g 值总是最小的，会存入 visitedMap。
            if (visitedMap.containsKey(currentNode.currentState) && visitedMap.get(currentNode.currentState) < currentNode.g) {
                continue; // 已经有了一条更短的路径到达这个状态，跳过。
            }

            // 3. 标记当前状态为已访问（并记录当前的最优 g 值）。
            //    如果这个状态之前没被访问过，或者这次的 g 值更小，就更新visitedMap。
            //    因为我们先检查了visitedMap.get(currentNode.currentState) < currentNode.g，
            //    如果走到这里，说明 currentNode.g 是当前找到的到达 currentNode.currentState 的最优 g 值。
            visitedMap.put(currentNode.currentState, currentNode.g);

            // 4. 检查是否达到目标状态
            if (currentNode.currentState.isGoal()) {
                // 目标状态已找到！
                this.movesToFinish = currentNode.movesFromInitial; // 步数就是 g 值

                // 重构路径：从当前节点（目标）反向追溯到起始节点
                SearchNode pathTracer = currentNode;
                while (pathTracer != null) {
                    resultWorldStates.add(pathTracer.currentState);
                    pathTracer = pathTracer.previousSearchNode;
                }
                // 路径是反向的，需要反转列表
                Collections.reverse(resultWorldStates);
                return; // 搜索成功，退出 solve() 方法
            }

            // 5. 探索当前节点的所有邻居状态
            for (WorldState neighborState : currentNode.currentState.neighbors()) {
                // 计算邻居节点的新 g 值（假设每一步成本为 1）
                int newG = currentNode.movesFromInitial + 1;

                // 检查邻居状态是否已经以更优的路径访问过
                // 如果 visitedMap 包含 neighborState 并且其记录的 g 值小于等于 newG，
                // 说明我们已经找到到达 neighborState 的更优或等长路径，所以忽略当前产生的这个邻居节点。
                if (visitedMap.containsKey(neighborState) && visitedMap.get(neighborState) <= newG) {
                    continue; // 已经有了一条更短或等长的路径到达此邻居状态，跳过。
                }

                // 创建新的搜索节点（包含 g, h, f）
                SearchNode newNode = new SearchNode(neighborState, newG, currentNode);

                // 将新的节点添加到优先队列（开放列表）
                // MinPQ 会根据 SearchNode 的 compareTo 方法（即 f 值）自动排序。
                searchPath.insert(newNode);
            }
        }

        // 如果循环结束而没有找到目标状态，则说明无解。
        // 根据题目假设“Assumes a solution exists”，此处理论上不应执行到。
        // 如果题目允许无解情况，这里应设置 movesToFinish 为 -1 或抛出异常，
        // 并清空 resultWorldStates。
        this.movesToFinish = -1; // 表示无解 (根据题目描述，可以认为不会发生)
        this.resultWorldStates.clear();
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     * @return An int standing for the minimum number of moves to solve the puzzle starting
     *      * at the initial WorldState.
     */
    public int moves() {
        return movesToFinish;
    }

    /**
     * @return a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return resultWorldStates;
    }
}
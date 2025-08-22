package hw4.puzzle;

import java.util.*;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    // TODO: Review static, type casting, Comparable, etc.
    private static class SearchNode implements Comparable<SearchNode> {
        public WorldState currentState;
        public int movesFromInitial;
        public SearchNode previousSearchNode;

        // public boolean isVisited;

        private final int g;
        private final int h;
        private final int f;


        public SearchNode(WorldState currentState, int moves, SearchNode previous) {
            this.currentState = currentState;
            this.movesFromInitial = moves;
            this.previousSearchNode = previous;
            // this.isVisited = false;
            this.g = moves;
            this.h = currentState.estimatedDistanceToGoal();
            this.f = this.g + this.h;
        }

        /**
         * For the comparable traits, to fit MinPQ storage!
         * @param y another SearchNode Object
         * @return The priority they will be called for search
         */
        @Override
        public int compareTo(SearchNode y) {
            /*int thisPriority = currentState.estimatedDistanceToGoal();
            int yPriority = y.currentState.estimatedDistanceToGoal();
            return Integer.compare(thisPriority, yPriority);
        */
            // PriorityQueue sorts in ascending order
            // We prioritize lower value
            int fComparison = Integer.compare(this.f, y.h);
            if (fComparison != 0) {
                return fComparison;
            }

            return Integer.compare(this.h, y.h);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SearchNode that  = (SearchNode) o;
            return this.currentState.equals(that.currentState);
        }

        @Override
        public int hashCode() {
            return this.currentState.hashCode();
        }

    }

    private final MinPQ<SearchNode> searchPath;
    private int movesToFinish;
    private final List<WorldState> resultWorldStates;

    private Map<WorldState, Integer> visitedMap;



    /**
     * Based on given data, directly compute out results,
     * containing the number of moves and the Iterable<WorldState>
     * This should be executed within the Constructor method.
     * Attributes need to be updated after running this method:
     * * movesToFinish
     * * resultWorldStates
     */

    private void solve() {
        // WorldState can be Word or others. The methods are well override.
        // Note that the initial has been added to searchPath, we just begin!
        // TODO: This algorithm offers redundant steps. Try to fix this.
        //
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


/*SearchNode currentNode = searchPath.delMin();
currentNode.isVisited = true;
while (!currentNode.currentState.isGoal()) {
    // start to check its neighbors not visited
    for (WorldState state : currentNode.currentState.neighbors()) {
        // How to avoid visiting those visited nodes?
        if (!(state.equals(currentNode.currentState)
                && (currentNode.previousSearchNode != null
                && state.equals(currentNode.previousSearchNode.currentState)))) {
            SearchNode newNode = new SearchNode(state,
                    currentNode.movesFromInitial + 1, currentNode);
            searchPath.insert(newNode);
        }
    }
    currentNode = searchPath.delMin();
}

// If finished, then currentNode is the goal.
SearchNode readNode = currentNode;

// Update the steps taken to complete this transformation
this.movesToFinish = currentNode.movesFromInitial;

while (readNode.previousSearchNode != null) {
    resultWorldStates.add(readNode.currentState);
    readNode = readNode.previousSearchNode;
}

Collections.reverse(resultWorldStates); */

/*/ Create the container
this.resultWorldStates = new ArrayList<>();

// Specially handle the first node as a beginning!
SearchNode beginning = searchPath.delMin();
for (WorldState state : beginning.currentState.neighbors()) {
    SearchNode newNode = new SearchNode(state,
            beginning.movesFromInitial + 1, beginning);

    searchPath.insert(newNode);
}

// Now begin the formal searching.
SearchNode currentNode = searchPath.delMin();

while (!currentNode.currentState.isGoal()) {
    for (WorldState state : currentNode.currentState.neighbors()) {
        SearchNode newNode = new SearchNode(state,
                currentNode.movesFromInitial + 1, currentNode);
        searchPath.insert(newNode);

        resultWorldStates.add(currentNode.currentState);
        // Update the node, to start the next turn of searching.
    }
    currentNode = searchPath.delMin();
}

// End Condition: currentNode is the final goal state.
movesToFinish = currentNode.movesFromInitial;*/

    }

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
        this.visitedMap = new HashMap<>();
        this.searchPath.insert(new SearchNode(initial, 0, null));

        this.movesToFinish = 0;

        this.solve();
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

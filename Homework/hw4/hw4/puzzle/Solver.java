package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Stack<WorldState> movesSoFar;

    public Solver(WorldState initial) {
        movesSoFar = new Stack<>();

        MinPQ<SearchNode> tracker = new MinPQ<>();
        tracker.insert(new SearchNode(initial, 0, null));

        SearchNode goal = null;

        while (!tracker.isEmpty()) {
            SearchNode min = tracker.delMin();
            WorldState minWS = min.world();
            SearchNode prev = min.prev();
            if (minWS.isGoal()) {
                goal = min;
                break;
            } else {
                for (WorldState n : minWS.neighbors()) {
                    if (prev == null || (prev != null && !n.equals(prev.world()))) {
                        tracker.insert(new SearchNode(n, min.moves() + 1, min));
                    }
                }
            }
        }

        while (goal != null) {
            movesSoFar.push(goal.world);
            goal = goal.prev;
        }

    }

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState world;
        private int moves;
        private SearchNode prev;
        private int priority;

        private SearchNode(WorldState s, int m, SearchNode p) {
            world = s;
            moves = m;
            prev = p;
            priority = moves + s.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }

        public WorldState world() {
            return world;
        }

        public SearchNode prev() {
            return prev;
        }

        public int moves() {
            return moves;
        }
    }

    public int moves() {
        return movesSoFar.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return movesSoFar;
    }
}

package lab11.graphs;



import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean found;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = m.xyTo1D(sourceX, sourceY);
        t = m.xyTo1D(targetX, targetY);
        found = false;
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int s) {
        Queue<Integer> fringe = new ArrayDeque<>();
        marked[s] = true;
        fringe.add(s);
        announce();

        while(!fringe.isEmpty() && !found) {
            int v = fringe.remove();

            if (v == t) {
                found = true;
                break;
            }

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    fringe.add(w);
                    announce();
                    distTo[w] = distTo[v] + 1;


                }

            }
        }


    }


    @Override
    public void solve() {
        bfs(s);
    }
}


package FlowerPower.model.Datatypes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class aStar {
    // implements the A* algorithm

    // need to update gameboard so that cell type is path not empty, etc

    private final Graph graph;
 
    public aStar(Graph graph) {
        if (graph == null) throw new IllegalArgumentException("Graph must not be null");

        this.graph = graph;
    }

    /**
     * Returns the lowest-cost path from {@code start} to {@code goal}
     * as an ordered list of node indices (start-inclusive, goal-inclusive).
     *
     * @return path from start to goal, or an empty list if none exists
     */
    public List<Integer> path(int start, int goal) {
        validateNode(start);
        validateNode(goal);

        if (start == goal) return List.of(start);

        int V = graph.V();

        // gScore[v] = cheapest known cost from start to v
        double[] gScore = new double[V];
        Arrays.fill(gScore, Double.POSITIVE_INFINITY);
        gScore[start] = 0.0;

        // fScore[v] = gScore[v] + h(v)  — the value A* sorts by
        double[] fScore = new double[V];
        Arrays.fill(fScore, Double.POSITIVE_INFINITY);
        fScore[start] = heuristic(start, goal);

        // cameFrom[v] = the node immediately before v on the best path found so far
        int[] cameFrom = new int[V];
        Arrays.fill(cameFrom, -1);

        // Min-heap: [nodeIndex, fScore]
        PriorityQueue<int[]> open = new PriorityQueue<>(
                Comparator.comparingDouble(entry -> fScore[entry[0]]));
        open.offer(new int[]{start});

        boolean[] closed = new boolean[V];

        while (!open.isEmpty()) {
            int current = open.poll()[0];

            if (current == goal) return reconstructPath(cameFrom, goal);

            // Skip if we already settled this node via a cheaper route
            if (closed[current]) continue;
            closed[current] = true;

            for (int neighbour : graph.adj(current)) {
                if (closed[neighbour]) continue;

                double tentativeG = gScore[current] + graph.weight(current, neighbour);

                if (tentativeG < gScore[neighbour]) {
                    cameFrom[neighbour] = current;
                    gScore[neighbour]   = tentativeG;
                    fScore[neighbour]   = tentativeG + heuristic(neighbour, goal);
                    open.offer(new int[]{neighbour});
                }
            }
        }

        return Collections.emptyList(); // no path exists
    }


    /**
     * Returns the total cost of the path returned
     *
     * @return double representing the cost or null if there is no path
     */
    public double cost(int start, int goal) {
        List<Integer> p = path(start, goal);
        if (p.isEmpty()) return Double.POSITIVE_INFINITY;
        double total = 0.0;
        for (int i = 0; i < p.size() - 1; i++) {
            total += graph.weight(p.get(i), p.get(i + 1));
        }
        return total;
    }

    //________ HELPER METHODS ________

    /**
     * Returns the heuristic between nodes
     *
     * @return double euclidean distance
     */
    private double heuristic(int u, int v) {
        return graph.euclidean(u, v);
    }

    private List<Integer> reconstructPath(int[] cameFrom, int goal) {
        LinkedList<Integer> path = new LinkedList<>();
        int current = goal;
        while (current != -1) {
            path.addFirst(current);
            current = cameFrom[current];
        }
        return path;
    }

    /**
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
    */ 
    private void validateNode(int v) {
        if (v < 0 || v >= graph.V())
            throw new IllegalArgumentException("Node " + v + " is out of range");
    }

}

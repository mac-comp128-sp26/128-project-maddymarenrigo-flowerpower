package FlowerPower.model.Datatypes;

import java.util.List;

public class aStar {
    // implements the A* algorithm

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
        return null;
    }

    /**
     * Returns the total cost of the path returned
     *
     * @return double representing the cost or null if there is no path
     */
    public double cost(int start, int goal) {
        return 0.0;
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
        return null;
    }

    /**
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
    */ 
    private void validateNode(int v) {
        if (v < 0 || v >= graph.V())
            throw new IllegalArgumentException("Node " + v + " is out of range");
    }

}

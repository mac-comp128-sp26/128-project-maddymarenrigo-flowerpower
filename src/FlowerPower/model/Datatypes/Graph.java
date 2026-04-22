package FlowerPower.model.Datatypes;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int V;
    private int E;
    private double[][] matrix;   // 0.0 = no edge; > 0.0 = edge weight
    private double[] x;          // x-coordinate of each node
    private double[] y;          // y-coordinate of each node

    public Graph(int V){
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");

        this.V = V;
        this.E = 0;
        
        matrix = new double[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                matrix[i][j] = 0.0;
            }
        }
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Returns the weight of the edge
     *
     * @return the weight of the edge
     */
    public double weight(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        return matrix[v][w];
    }

    /**
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
    */ 
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * Adds the weighted edge v-w to this graph.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w, double weight) {
        validateVertex(v);
        validateVertex(w);
        if (weight <= 0) throw new IllegalArgumentException("Edge weight must be positive");

        if (matrix[v][w] == 0.0) E++;   // only count new edges

        matrix[v][w] = weight;
        matrix[w][v] = weight;
    }

    /** 
     * Returns true if there is an edge between v and w
     * 
     * @return true if there is an edge
     * */
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return matrix[v][w] != 0.0;
    }

    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param v the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public List<Integer> adj(int v) {
        validateVertex(v);
        List<Integer> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if(matrix[i][v] != 0.0) {
                adj.add(i);
            }
        }
        return adj;
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        int count = 0;
        for (int i = 0; i < V; i++) {
            if(matrix[i][v] == 1) {
                count++;
            }
        }
        return count; 
    }

    /**
     * Euclidean distance between two nodes — convenience helper for A*.
     * 
     * @return a double variable of the euclidean distance
     */
    public double euclidean(int u, int v) {
        double dx = x[u] - x[v];
        double dy = y[u] - y[v];
        
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Assigns the 2-D position of node v (used by A* algorithm)
     */
    public void setPosition(int v, double px, double py) {
        validateVertex(v);
        x[v] = px;
        y[v] = py;
    }

    /** 
     * Returns the x-coordinate of node v
     * */
    public double getX(int v) { 
        validateVertex(v); 
        return x[v]; 
    }
 
    /** 
     * Returns the y-coordinate of node v
     * */
    public double getY(int v) { 
        validateVertex(v); 
        return y[v]; 
    }
}

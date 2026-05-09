package FlowerPower.model.Datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Dijkstra {
    private Graph graph;

    public Dijkstra(Graph graph) {
        if (graph == null) throw new IllegalArgumentException("Graph must not be null");

        this.graph = graph;
    }

    public ArrayList<Integer> dijkstra(int src, int goal) {
        int V = graph.V();
        int[] dist = new int[V];
        int[] previous = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        // [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        dist[src] = 0;
        pq.offer(new int[]{0, src});
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int currentDist = top[0];
            int currentNode = top[1];
            if (currentDist > dist[currentNode]) {
                continue;
            }
            if (currentNode == goal) {
                break;
            }
            for (int neighbor : graph.adj(currentNode)) {
                int weight = (int) graph.weight(currentNode, neighbor);
                int newDist = dist[currentNode] + weight;
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist;
                    previous[neighbor] = currentNode;
                    pq.offer(new int[]{newDist, neighbor});
                }
            }
        }
        return reconstructPath(previous, src, goal);
    }

    /**
     * Rebuilds the shortest path using the previous array.
     */
    private ArrayList<Integer> reconstructPath(int[] previous, int src, int goal) {
        ArrayList<Integer> path = new ArrayList<>();
        int current = goal;
        // walk backwards from goal to src
        while (current != -1) {
            path.add(current);
            current = previous[current];
        }
        Collections.reverse(path);
        if (path.isEmpty() || path.get(0) != src) {
            return new ArrayList<>();
        }
        return path;
    }

}

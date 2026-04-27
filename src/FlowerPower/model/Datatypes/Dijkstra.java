package FlowerPower.model.Datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
    private final Graph graph;

    public Dijkstra(Graph graph) {
        if (graph == null) throw new IllegalArgumentException("Graph must not be null");

        this.graph = graph;
    }

    public ArrayList<Integer> dijkstra(Graph graph, int src) {
        int V = graph.V();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        dist[src] = 0;
        pq.offer(new int[]{0, src});
        while(!pq.isEmpty()) {
            int[] top = pq.poll();
            int d = top[0];
            int node = top[1];
            if(d > dist[node]) {
                continue;
            }
            for(int p : graph.adj(node)) {
                int weight = (int) graph.weight(node, p);
            }
        }

        return null;
    }

}

/** sort edges by smallest edge weight to largest; before adding in new edges, run Dijkstra's to 
 *  see if there is a shorter possible path to the target edge. If there is a shorter possible path, 
 *  then the configuration is impossible.
 */

import java.io.*;
import java.util.*;

public class graphservice
{
    public static class Graph
    {
        LinkedList<Edge>[] adj;
        public Graph(int N) {
            adj = new LinkedList[N];
            for (int i = 0; i < N; ++i)
                adj[i] = new LinkedList<>();
        }
        public void addEdge(int v, Edge e) {
            adj[v].add(e);
        }
    }
    public static class Edge implements Comparable<Edge>
    {
        int src;
        int dest;
        int weight;
        public Edge(int s, int d, int w) {
            src = s;
            dest = d;
            weight = w;
        }
        @Override
        public boolean equals(Object o) {
            if (o instanceof Edge) {
                Edge e = (Edge)o;
                return this.dest == e.dest;
            }
            return false;
        }
        @Override
        public int compareTo(Edge e) {
            return this.weight - e.weight;
        }
    }
    public static boolean dijkstra(Graph g, int lim, int start, int end) {
        PriorityQueue<Edge> vertexPQ = new PriorityQueue<>();
        vertexPQ.add(new Edge(-1, start, 0)); /** random sentinel edge */
        for (int i = 0; i < g.adj.length; ++i) {
            if (i != start)
                vertexPQ.add(new Edge(-1, i, Integer.MAX_VALUE));
        }

        boolean[] marked = new boolean[g.adj.length];
        long[] distTo = new long[g.adj.length];
        Arrays.fill(distTo, 1000000);
        distTo[start] = 0;

        while (vertexPQ.size() != 0) {
            Edge curr = vertexPQ.poll();
            marked[curr.dest] = true;
            for (Edge e : g.adj[curr.dest]) {
                if (!marked[e.dest] && e.weight + distTo[curr.dest] < distTo[e.dest]) {
                    update(g, vertexPQ, distTo, e, curr);
                }
            }
        }

        if (distTo[end] >= lim)
            return true;
        else
            return false;
    }
    private static void update(Graph g, PriorityQueue<Edge> vertexPQ, long[] distTo, Edge e, Edge curr) {
        vertexPQ.remove(e);
        vertexPQ.add(e);
        distTo[e.dest] = distTo[curr.dest] + e.weight;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("graphs_as_a_service_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("graphservice_out.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            Graph g = new Graph(N);
            Edge[] edges = new Edge[M];
            for (int j = 0; j < M; ++j) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                int w = Integer.parseInt(st.nextToken());
                edges[j] = new Edge(a, b, w);
            }
            Arrays.sort(edges);
            boolean possible = true;
            for (int j = 0; j < M; ++j) {
                if (dijkstra(g, edges[j].weight, edges[j].src, edges[j].dest) == false) {
                    possible = false;
                    break;
                }
                g.addEdge(edges[j].src, edges[j]);
                g.addEdge(edges[j].dest, new Edge(edges[j].dest, edges[j].src, edges[j].weight));
            }
            for (int j = 0; j < M; ++j) {
                if (dijkstra(g, edges[j].weight, edges[j].src, edges[j].dest) == false) {
                    possible = false;
                    break;
                }
            }
            pw.print("Case #" + (i + 1) + ": ");
            if (possible) {
                pw.println(M);
                for (int j = 0; j < M; ++j)
                    pw.println((edges[j].src + 1) + " " + (edges[j].dest + 1) + " " + edges[j].weight);
            }
            else
                pw.println("Impossible");
        }

        br.close();
        pw.close();
    }
}
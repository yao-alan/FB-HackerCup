/** rather than LCA, begin with a BFS and then DFS */

import java.io.*;
import java.util.*;

public class chapter2
{
    static int M;
    static boolean possible;
    public static class Station implements Comparable<Station>
    {
        int id;
        int dist; /** distance from starting vertex */
        long cost;
        public Station(int i) {
            id = i;
        }
        public Station(int i, long c) {
            id = i;
            cost = c;
        }
        public Station(int i, int d, long c) {
            id = i;
            dist = d;
            cost = c;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Station) {
                Station s = (Station)obj;
                return this.id == s.id && this.dist == s.dist && this.cost == s.cost;
            }
            return false;
        }
        @Override
        public int compareTo(Station s) {
            if (this.dist == s.dist) {
                if (this.cost < s.cost)
                    return -1;
                else if (this.cost == s.cost)
                    return 0;
                else
                    return 1;
            } else {
                if (this.dist < s.dist)
                    return -1;
                else
                    return 1;
            }
        }
    }
    public static class Graph
    {
        LinkedList<Integer>[] adj;
        Station[] stations;
        public Graph(int N) {
            adj = new LinkedList[N];
            stations = new Station[N];
            for (int i = 0; i < N; ++i) {
                adj[i] = new LinkedList<>();
                stations[i] = new Station(i);
            }
        }
        public void addEdge(int v1, int v2) {
            adj[v1].add(v2);
            adj[v2].add(v1);
        }
    }
    public static void dfs(Graph g, int curr, HashSet<Integer> path, int B, TreeSet<Station> pq, boolean[] visited, int branchDepth) {
        if (curr == B) {
            possible = true;
            return;
        }
        /** can use any value as long as tank runs out only upon reaching destination */
        Station s = pq.higher(new Station(-1, g.stations[curr].dist - 1, Long.MAX_VALUE));
        Station x = null;
        Station l = null;
        if (s != null && g.stations[curr].cost != 0)
            x = new Station(-1, g.stations[curr].dist + M - 2 * branchDepth, s.cost + g.stations[curr].cost);
        if (x != null && (pq.higher(new Station(-1, x.dist - 1, Long.MAX_VALUE)) == null || pq.higher(new Station(-1, x.dist - 1, Long.MAX_VALUE)).cost >= x.cost)) {
            pq.add(x);
            l = new Station(-1, x.dist, -1);
        }
        while (l != null && pq.lower(l) != null && x != null && pq.lower(l).cost >= x.cost)
            pq.remove(pq.lower(l));
        visited[curr] = true;
        int next = g.adj[curr].get(0);

        for (int n : g.adj[curr]) {
            /** on a branch rather than the main path */
            if (!visited[n] && !path.contains(n)) {
                /** enough gas to move to next node */
                if (pq.higher(new Station(-1, g.stations[n].dist - 1, Long.MAX_VALUE)) != null)
                    dfs(g, n, path, B, pq, visited, branchDepth + 1);
            }
            else if (!visited[n] && path.contains(n))
                next = n;
        }

            if (!visited[next]) {
                /** enough gas to move to next node */
                if (pq.higher(new Station(-1, g.stations[next].dist - 1, Long.MAX_VALUE)) != null)
                    dfs(g, next, path, B, pq, visited, branchDepth);
                /** can't move forward on main path, so combination is impossible */
                else
                    return;
            }
    }
    /** bfs to set values for each station as well as find path from A to B */
    public static HashSet<Integer> bfs(int start, int end, Graph g) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[g.adj.length];
        queue.add(start);

        HashSet<Integer> path = new HashSet<>();
        int[] parents = new int[g.adj.length];
        parents[start] = start;
        g.stations[start].dist = 0;
        while (queue.size() != 0) {
            int curr = queue.poll();
            visited[curr] = true;
            for (int n : g.adj[curr]) {
                if (visited[n])
                    continue;
                parents[n] = curr;
                g.stations[n].dist = g.stations[curr].dist + 1;
                queue.add(n);
            }
        }

        /** backtrace parents[] to reverse */
        int p = end;
        while (parents[p] != p) {
            path.add(p);
            p = parents[p];
        }
        path.add(p);
        return path;
    }

    public static void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("running_on_fumes_chapter_2_input.txt"));
                    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("running_on_fumes_chapter_2_output.txt")));
                    StringTokenizer st = new StringTokenizer(br.readLine());
        
                    int T = Integer.parseInt(st.nextToken());
                    for (int i = 0; i < T; ++i) {
                        st = new StringTokenizer(br.readLine());
                        int N = Integer.parseInt(st.nextToken());
                        M = Integer.parseInt(st.nextToken());
                        int A = Integer.parseInt(st.nextToken()) - 1;
                        int B = Integer.parseInt(st.nextToken()) - 1;
        
                        Graph g = new Graph(N);
                        TreeSet<Station> pq = new TreeSet<>();
                        pq.add(new Station(-1, M, 0));
        
                        for (int j = 0; j < N; ++j) {
                            st = new StringTokenizer(br.readLine());
                            int P = Integer.parseInt(st.nextToken()) - 1;
                            int C = Integer.parseInt(st.nextToken());
                            if (P != -1)
                                g.addEdge(j, P);
                            g.stations[j].cost = C;
                        }
                        HashSet<Integer> path = bfs(A, B, g);
                        possible = false;
                        boolean[] visited = new boolean[N];
                        dfs(g, A, path, B, pq, visited, 0);
                    
                        pw.print("Case #" + (i + 1) + ": ");
                        if (possible) {
                            long smallest = Long.MAX_VALUE;
                            Station s = null;
                            for (Station x : pq.tailSet(new Station(-1, g.stations[B].dist - 1, Long.MAX_VALUE))) {
                                if (x.cost < smallest) {
                                    smallest = x.cost;
                                    s = x;
                                }
                            }
                            pw.println(s.cost);
                        } else {
                            pw.println(-1);
                        }
                    }

                    br.close();
                    pw.close();
                }
                catch (IOException e) {
                    return;
                }
            }
        }, "1", 1 << 2300).start();
    }
}
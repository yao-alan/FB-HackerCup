/** priority queue + dp? O(N + M)? */

import java.io.*;
import java.util.*;

public class chapterone
{
    public static class Station implements Comparable<Station>
    {
        int dist;
        long cost;
        public Station(int dist, long cost) {
            this.dist = dist;
            this.cost = cost;
        }
        @Override
        public int compareTo(Station s) {
            if (this.cost < s.cost)
                return -1;
            else if (this.cost == s.cost)
                return 0;
            else
                return 1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("running_on_fumes_chapter_1_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("running_on_fumes_chapter_1_output.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[] stations = new int[N];
            for (int j = 0; j < N; ++j) {
                st = new StringTokenizer(br.readLine());
                int c = Integer.parseInt(st.nextToken());
                if (j != N - 1)
                    stations[j] = c;
                else
                    stations[j] = 0;
            }
            PriorityQueue<Station> pq = new PriorityQueue<>();
            pq.add(new Station(0, 0));
            long[] dp = new long[N];
            boolean impossible = false;
            for (int j = 1; j < N; ++j) {
                if (stations[j] == 0 && j != N - 1)
                    continue;
                while (pq.size() != 0 && j - pq.peek().dist > M)
                    pq.poll();
                if (pq.size() == 0) {
                    impossible = true;
                    break;
                }
                dp[j] = pq.peek().cost + stations[j];
                pq.add(new Station(j, dp[j]));
            }
            pw.print("Case #" + (i + 1) + ": ");
            if (impossible)
                pw.println(-1);
            else
                pw.println(dp[dp.length - 1]);
        }

        br.close();
        pw.close();
    }
}
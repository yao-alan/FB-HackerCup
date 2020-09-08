/** dp solution is NM log M + NM^2 */

import java.io.*;
import java.util.*;

public class progress
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("progress.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("progress.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int x = 1; x <= T; ++x) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()); int M = Integer.parseInt(st.nextToken());
            int[][] pies = new int[N + 1][M + 1];
            int[] indiv = new int[M + 1];
            for (int i = 1; i <= N; ++i) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= M; ++j) {
                    indiv[j] = Integer.parseInt(st.nextToken());
                }
                Arrays.sort(indiv);
                for (int j = 1; j <= M; ++j) {
                    pies[i][j] = pies[i][j - 1] + indiv[j]; 
                }
            }
            long[][] dp = new long[N + 1][N + 1];
            for (int i = 0; i <= N; ++i) {
                for (int j = 0; j <= N; ++j)
                    dp[i][j] = 10000000;
            }
            dp[0][0] = 0;
            /** number of days */
            for (int i = 1; i <= N; ++i) {
                /** total number of pies that can be bought as of the current day */
                for (int j = i; j <= M * i && j <= N; ++j) {
                    long min = 10000000;
                    for (int k = Math.max(i - 1, j - M); k <= j; ++k) {
                        min = (long)(Math.min(dp[i - 1][k] + pies[i][j - k] + Math.pow(j - k, 2), min));
                    }
                    dp[i][j] = min;
                }
            }
            pw.println("Case #" + x + ": " + dp[N][N]);
        }

        br.close();
        pw.close();
    }
}
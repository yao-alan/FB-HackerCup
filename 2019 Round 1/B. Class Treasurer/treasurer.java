import java.io.*;
import java.util.*;

public class treasurer
{
    static int[] powers;
    static long MOD = 1000000007L;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("class_treasurer_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("class_treasurer_output.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /** generate table of powers of 2 */
        powers = new int[1000001];
        powers[0] = 1;
        for (int i = 1; i < powers.length; ++i)
            powers[i] = (int)((powers[i - 1] * 2) % MOD);

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()); /** number of students */
            int K = Integer.parseInt(st.nextToken()); /** threshold value */
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            int[] students = new int[N];
            for (int j = 0; j < N; ++j) {
                if (s.charAt(j) == 'A')
                    students[j] = -1;
                else
                    students[j] = 1;
            }
            long ans = 0;
            int runningTotal = 0;
            for (int j = N - 1; j >= 0; --j) {
                runningTotal += students[j];
                if (runningTotal > K) {
                    runningTotal -= 2;
                    ans = (ans + powers[j + 1]) % MOD;
                }
                if (runningTotal < 0)
                    runningTotal = 0;
            }
            pw.print("Case #" + (i + 1) + ": ");
            pw.println(ans);
        }

        br.close();
        pw.close();
    }
}
/** 1. impossible if there is a # in the middle lane
 *  2. impossible if there is a # in the top lane and a # in the bottom lane that 
 *     are either in the same vertical column or neighboring vertical columns
 *  3. otherwise, a # in the top or bottom cancel out a junction point
 */

import java.io.*;
import java.util.*;

public class flow
{
    static long MOD = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("let_it_flow_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("let_it_flow_output.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] powers = new int[500]; powers[0] = 1;
        for (int i = 1; i < powers.length; ++i)
            powers[i] = (int)((powers[i - 1] * 2) % MOD);

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            char[][] houses = new char[N][3];
            for (int j = 0; j < 3; ++j) {
                st = new StringTokenizer(br.readLine());
                String s = st.nextToken();
                for (int k = 0; k < N; ++k)
                    houses[k][j] = s.charAt(k);
            }
            /** basic checks for impossibility */
            boolean finished = false;
            for (int j = 0; j < N; ++j) {
                if (houses[j][1] == '#') {
                    finished = true;
                    break;
                }
            }
            pw.print("Case #" + (i + 1) + ": ");
            if (finished || N % 2 == 1 || houses[0][0] == '#' || houses[N - 1][2] == '#') {
                pw.println(0);
                continue;
            }

            int junctions = N / 2 - 1;
            if (junctions == 0) {
                pw.println(1);
                continue;
            }
            for (int j = 1; j < N - 2; j += 2) {
                if ((houses[j][0] == '#' || houses[j + 1][0] == '#') &&
                    (houses[j][2] == '#' || houses[j + 1][2] == '#')) {
                    finished = true;
                    break;
                }
                else if (houses[j][0] == '#' || houses[j + 1][0] == '#' ||
                         houses[j][2] == '#' || houses[j + 1][2] == '#')
                    --junctions;
                
            }
            if (finished) {
                pw.println(0);
                continue;
            } else
                pw.println(powers[junctions]);
        }

        br.close();
        pw.close();
    }
}
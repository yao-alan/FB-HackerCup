/** every time three shards are fused, one A and one B is lost; if there are enough As and Bs to
 *  sustain a reduction to three shards, then the given combination is possible
 */

import java.io.*;
import java.util.*;

public class alchemy
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("alchemy_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("alchemy_output.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            int countA = 0;
            int countB = 0;
            for (int j = 0; j < N; ++j) {
                if (s.charAt(j) == 'A')
                    ++countA;
                else
                    ++countB;
            }
            pw.print("Case #" + (i + 1) + ": ");
            int reduction = (N - 3) / 2;
            if (countA > reduction && countB > reduction)
                pw.println("Y");
            else
                pw.println("N");
        }

        br.close();
        pw.close();
    }
}
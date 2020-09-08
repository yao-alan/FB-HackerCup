/** N^3 works fine */

import java.io.*;
import java.util.*;

public class travel
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("travel_restrictions_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("travel_restrictions_output.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            char[] inbound = new char[N];
            char[] outbound = new char[N];
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            for (int j = 0; j < N; ++j)
                inbound[j] = s.charAt(j);
            st = new StringTokenizer(br.readLine());
            s = st.nextToken();
            for (int j = 0; j < N; ++j)
                outbound[j] = s.charAt(j);

            pw.println("Case #" + (i + 1) + ":");
            /** outer loop is current starting point */
            for (int a = 0; a < N; ++a) {
                char[] ans = new char[N];
                /** check destinations to the left of start */
                for (int b = a; b >= 0; --b) {
                    if (b == a) {
                        ans[b] = 'Y';
                        continue;
                    } else {
                        int curr = a;
                        while (curr != b && outbound[curr] == 'Y') {
                            if (inbound[curr - 1] == 'Y')
                                --curr;
                            else
                                break;
                        }
                        if (curr != b)
                            ans[b] = 'N';
                        else
                            ans[b] = 'Y';
                    }
                }
                /** check destinations to the right of start */
                for (int b = a; b < N; ++b) {
                    if (b == a) {
                        ans[b] = 'Y';
                        continue;
                    } else {
                        int curr = a;
                        while (curr != b && outbound[curr] == 'Y') {
                            if (inbound[curr + 1] == 'Y')
                                ++curr;
                            else
                                break;
                        }
                        if (curr != b)
                            ans[b] = 'N';
                        else
                            ans[b] = 'Y';
                    }
                }
                /** printing answers */
                for (int j = 0; j < ans.length; ++j)
                    pw.print(ans[j]);
                pw.println();
            }
        }

        br.close();
        pw.close();
    }
}
import java.io.*;
import java.util.*;

public class interception
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("interception_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("interception_out.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            for (int j = 0; j < N + 1; j++) {
                st = new StringTokenizer(br.readLine());
            }
            pw.print("Case #" + (i + 1) + ": ");
            if (N % 2 == 1) {
                pw.println(1);
                pw.println(0.0);
            } else 
                pw.println(0);
        }

        br.close();
        pw.close();
    }
}
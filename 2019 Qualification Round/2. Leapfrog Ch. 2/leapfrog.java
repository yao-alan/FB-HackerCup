import java.io.*;
import java.util.*;

public class leapfrog
{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("leapfrog_ch__input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("leapfrog2.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            int emptySpaces = 0;
            int betaFrogs = 0;
            for (int j = 1; j < s.length(); ++j) {
                if (s.charAt(j) == '.')
                    emptySpaces++;
                else
                    betaFrogs++;
            }
            pw.print("Case #" + (i + 1) + ": ");
            if ((emptySpaces != 0 && betaFrogs >= 2) || emptySpaces == 1 && betaFrogs >= 1)
                pw.println("Y");
            else
                pw.println("N");
        }

        br.close();
        pw.close();
    }
}
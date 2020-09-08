import java.io.*;
import java.util.*;

public class leapfrog
{
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("leapfrog_ch__input.txt"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("leapfrog.txt")));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(in.readLine());
            String s = st.nextToken();
            int emptySpace = 0;
            int betaFrogs = 0;
            for (int j = 1; j < s.length(); ++j) {
                if (s.charAt(j) == '.')
                    ++emptySpace;
                else
                    ++betaFrogs;
            }
            out.print("Case #" + (i + 1) + ": ");
            if (emptySpace != 0 && betaFrogs != 0 && betaFrogs >= emptySpace)
                out.println("Y");
            else
                out.println("N");
        }

        in.close();
        out.close();
    }
}
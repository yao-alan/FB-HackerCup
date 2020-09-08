/** pretty sure in the worst case, only the middle operator needs to change (so answer will always
 *  be 0 or 1); can test this by simply testing combinations (say 0 0 and 0 1) */

import java.io.*;
import java.util.*;

public class X
{
    /** dijkstra's shunting-yard implementation */
    public static class Parser
    {
        LinkedList<Character> output;
        Stack<Character> operators;

        public Parser() {
            output = new LinkedList<>();
            operators = new Stack<>();
        }
        public void read(char c) {
            if (c == 'x' || c == 'X' || c == '0' || c == '1')
                output.add(c);
            else {
                /** diff in binary operator precedence doesn't matter due to parentheses here */
                if (c != ')')
                    operators.push(c);
                else {
                    char x;
                    while (operators.peek() != '(') {
                        x = operators.pop();
                        output.add(x);
                    }
                    x = operators.pop();
                }
            }
        }
        public LinkedList<Character> getOutput() {
            return output;
        }
    }
    public static int solve(LinkedList<Character> input, int x) {
        Stack<Integer> solveStack = new Stack<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) == '0' || input.get(i) == '1')
                solveStack.push(input.get(i) - '0');
            else if (input.get(i) == 'x')
                solveStack.push(x);
            else if (input.get(i) == 'X') {
                if (x == 0)
                    solveStack.push(1); 
                else
                    solveStack.push(0); 
            } else { /** input.get(i) is an operator */
                int a = solveStack.pop();
                int b = solveStack.pop();
                if (input.get(i) == '|')
                    solveStack.push(a | b);
                else if (input.get(i) == '&')
                    solveStack.push(a & b);
                else
                    solveStack.push(a ^ b);
            }
        }
        return solveStack.pop();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("mr_x_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("X.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /** Run the expression w/ x = 0 or 1 */
        int N = Integer.parseInt(st.nextToken());
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            Parser parser = new Parser();
            String exp = st.nextToken();
            for (int j = 0; j < exp.length(); ++j)
                parser.read(exp.charAt(j));
            LinkedList<Character> input = parser.getOutput();

            pw.print("Case #" + (i + 1) + ": ");
            if (solve(input, 0) == solve(input, 1))
                pw.println(0);
            else
                pw.println(1);
        }

        br.close();
        pw.close();
    }
}
/** For every tree, there are three points of interest: the left/right endpoints from being cut in 
 *  their respective directions, and the middle endpoint from a tree standing
 *  1. For every left endpoint, add in the tree number and a 0 (or possible starting number) to a hashmap
 *  2. For every middle point, add the tree's height to its value in the hashmap and then remove it; then
 *  add the tree back with either a 0 or a starting number
 *  3. For every right endpoint, add the tree's height to its value in the hashmap and then remove it
 *  4. To get different starting values, take the largest popped value that isn't from the tree itself
 *     and add it to the value of those trees being added in
 */

import java.io.*;
import java.util.*;

public class timber
{
    public static class Tree implements Comparable<Tree>
    {
        int id;
        int pos;
        int height;
        public Tree(int id, int pos, int height) {
            this.id = id;
            this.pos = pos;
            this.height = height;
        }
        @Override
        public int compareTo(Tree t) {
            return this.pos - t.pos;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("timber_input.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("timber_output.txt")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            Tree[] trees = new Tree[3 * N];
            for (int j = 0; j < N; ++j) { /** j will be used as tree number */
                st = new StringTokenizer(br.readLine());
                int middle = Integer.parseInt(st.nextToken());
                int height = Integer.parseInt(st.nextToken());
                trees[3 * j] = new Tree(j, middle - height, height);
                trees[3 * j + 1] = new Tree(j, middle, height);
                trees[3 * j + 2] = new Tree(j, middle + height, height);
            }
            Arrays.sort(trees);

            /** scan through the trees */
            HashMap<Integer, Integer> treeMap = new HashMap<>();
            int p = 0;
            long intervalSize = 0;
            while (p < 3 * N) {
                int last = trees[p].pos;
                int[] largest = {0, -1};
                int[] secondLargest = {0, -1};
                ArrayList<Tree> addIn = new ArrayList<>();
                while (p < 3 * N && trees[p].pos == last) { /** all have the same position */
                    if (treeMap.containsKey(trees[p].id)) {
                        treeMap.replace(trees[p].id, treeMap.get(trees[p].id) + trees[p].height);
                        int x = treeMap.remove(trees[p].id);
                        if (x > intervalSize)
                            intervalSize = x;
                        if (x > largest[0]) {
                            secondLargest[0] = largest[0];
                            secondLargest[1] = largest[1];
                            largest[0] = x;
                            largest[1] = trees[p].id;
                        } else if (x > secondLargest[0]) {
                            secondLargest[0] = x;
                            secondLargest[1] = trees[p].id;
                        }
                    }
                    addIn.add(trees[p]);
                    last = trees[p].pos;
                    ++p;
                }
                for (Tree t : addIn) {
                    if (t.id != largest[1])
                        treeMap.put(t.id, largest[0]);
                    else
                        treeMap.put(t.id, secondLargest[0]);
                }
            }
            pw.println("Case #" + (i + 1) + ": " + intervalSize);
        }

        br.close();
        pw.close();
    }
}
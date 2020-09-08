import java.io.*;
import java.util.*;

public class ethantraverse
{
    static class TreeNode
    {
        int id;
        TreeNode left;
        TreeNode right;
        public TreeNode(int id, TreeNode left, TreeNode right) {
            this.id = id; this.left = left; this.right = right;
        }
    }
    static class Tree
    {
        TreeNode root;
        ArrayList<Integer> preOrder;
        ArrayList<Integer> postOrder;
        public Tree(int[][] nodes) {
            root = new TreeNode(0, null, null);
            LinkedList<TreeNode> q = new LinkedList<>();
            q.add(root);
            /** altered bfs to construct tree */
            for (int i = 0; i < nodes.length; ++i) {
                TreeNode curr = q.poll();
                if (nodes[curr.id][0] != -1) {
                    curr.left = new TreeNode(nodes[curr.id][0], null, null);
                    q.add(curr.left);
                }
                if (nodes[curr.id][1] != -1) {
                    curr.right = new TreeNode(nodes[curr.id][1], null, null);
                    q.add(curr.right);
                }
            }
            preOrder = new ArrayList<>();
            postOrder = new ArrayList<>();
        }
        /** marks both dfs preorder and dfs postorder in a 2D array */
        public void findOrder() {
            dfs(root);
        }
        private void dfs(TreeNode curr) {
            if (curr == null)
                return;
            preOrder.add(curr.id);
            dfs(curr.left);
            dfs(curr.right);
            postOrder.add(curr.id);
        }
    }
    static class DisjointSet
    {
        int[] nodes;
        public DisjointSet(int N) {
            this.nodes = new int[N];
            Arrays.fill(nodes, -1);
        }
        public int find(int v) {
            if (nodes[v] < 0)
                return v;
            return nodes[v] = find(nodes[v]);
        }
        public void union(int v1, int v2) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 == root2)
                return;
            if (-1 * root1 > -1 * root2) {
                nodes[root1] += nodes[root2];
                nodes[root2] = root1;
            } else {
                nodes[root2] += nodes[root1];
                nodes[root1] = root2;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("ethantraverse.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("ethantraverse.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; ++i) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][] nodes = new int[N][2]; /** [N][0] is left, [N][1] is right */
            for (int j = 0; j < N; ++j) {
                st = new StringTokenizer(br.readLine());
                nodes[j][0] = Integer.parseInt(st.nextToken()) - 1;
                nodes[j][1] = Integer.parseInt(st.nextToken()) - 1;
            }
            Tree binTree = new Tree(nodes);
            binTree.findOrder();
            
            DisjointSet dsu = new DisjointSet(N);
            for (int j = 0; j < N; ++j) {
                dsu.union(binTree.preOrder.get(j), binTree.postOrder.get(j));
            }

            pw.print("Case #" + (i + 1) + ": ");
            int count = 0;
            for (int j = 0; j < N; ++j) {
                if (dsu.nodes[j] < 0)
                    ++count;
            }
            if (count < K)
                pw.println("Impossible");
            else {
                int unique = 0;
                HashMap<Integer, Integer> parents = new HashMap<>();
                for (int j = 0; j < N; ++j) {
                    if (dsu.nodes[j] < 0) {
                        if (!parents.containsKey(j)) {
                            ++unique;
                            parents.put(j, unique);
                        }
                        if (parents.get(j) > K)
                            pw.print(K + " ");
                        else
                            pw.print(parents.get(j) + " ");
                    } else {
                        if (!parents.containsKey(dsu.find(dsu.nodes[j]))) {
                            ++unique;
                            parents.put(dsu.find(dsu.nodes[j]), unique);
                        }
                        if (parents.get(dsu.find(dsu.nodes[j])) > K)
                            pw.print(K + " ");
                        else
                            pw.print(parents.get(dsu.find(dsu.nodes[j])) + " ");
                    }
                }
                pw.println();
            }
        }

        br.close();
        pw.close();
    }
}
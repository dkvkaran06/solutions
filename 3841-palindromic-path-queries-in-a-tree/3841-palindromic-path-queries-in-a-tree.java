import java.util.*;

class Solution {
    private int[] tin;
    private int[] tout;
    private int timer;
    private int[][] up;
    private int LOG;
    private int[] bit;
    private int n;
    private char[] currentChars;

    // Fixed return type to List<Boolean> matching the LeetCode template
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        this.currentChars = s.toCharArray();
        
        // Initialize tree adjacency list
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        // Initialize structures for LCA and Fenwick Tree
        tin = new int[n];
        tout = new int[n];
        timer = 1;
        LOG = (int) (Math.log(n) / Math.log(2)) + 1;
        up = new int[n][LOG];
        bit = new int[2 * n + 5];

        // Compute DFS layout and binary lifting table
        dfs(0, 0, adj);

        // Populate initial state of the Fenwick Tree
        for (int i = 0; i < n; i++) {
            int bitIndex = 1 << (currentChars[i] - 'a');
            updateRange(tin[i], tout[i], bitIndex);
        }

        // Process Queries
        List<Boolean> ansList = new ArrayList<>();
        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);
                
                // Remove old character mask contribution, apply new one
                int oldMask = 1 << (currentChars[u] - 'a');
                int newMask = 1 << (c - 'a');
                updateRange(tin[u], tout[u], oldMask ^ newMask);
                
                currentChars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int lcaNode = lca(u, v);
                
                // Extract root prefix masks using point queries
                int maskU = queryPoint(tin[u]);
                int maskV = queryPoint(tin[v]);
                int maskLca = 1 << (currentChars[lcaNode] - 'a');
                
                // XOR combined path mask
                int pathMask = maskU ^ maskV ^ maskLca;
                
                // Check if at most one bit is set
                ansList.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ansList;
    }

    private void dfs(int u, int p, List<Integer>[] adj) {
        tin[u] = timer++;
        up[u][0] = p;
        for (int i = 1; i < LOG; i++) {
            up[u][i] = up[up[u][i - 1]][i - 1];
        }

        for (int v : adj[u]) {
            if (v != p) {
                dfs(v, u, adj);
            }
        }
        tout[u] = timer - 1;
    }

    private boolean isAncestor(int u, int v) {
        return tin[u] <= tin[v] && tout[u] >= tout[v];
    }

    private int lca(int u, int v) {
        if (isAncestor(u, v)) return u;
        if (isAncestor(v, u)) return v;
        for (int i = LOG - 1; i >= 0; i--) {
            if (!isAncestor(up[u][i], v)) {
                u = up[u][i];
            }
        }
        return up[u][0];
    }

    // Fenwick Tree point updates (XOR version)
    private void updatePoint(int idx, int val) {
        for (; idx <= 2 * n + 2; idx += idx & -idx) {
            bit[idx] ^= val;
        }
    }

    // Range update via point modifications
    private void updateRange(int l, int r, int val) {
        updatePoint(l, val);
        updatePoint(r + 1, val);
    }

    // Fetch the value accumulated at a point
    private int queryPoint(int idx) {
        int sum = 0;
        for (; idx > 0; idx -= idx & -idx) {
            sum ^= bit[idx];
        }
        return sum;
    }
}

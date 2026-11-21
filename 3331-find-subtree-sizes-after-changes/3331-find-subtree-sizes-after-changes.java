import java.util.*;

class Solution {
    private List<Integer>[] adj;
    private Deque<Integer>[] lastSeen;
    private int[] ans;
    private String s;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.s = s;
        this.ans = new int[n];
        this.adj = new ArrayList[n];
        this.lastSeen = new ArrayDeque[26];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < 26; i++) {
            lastSeen[i] = new ArrayDeque<>();
        }

        // Build the initial tree adjacency list
        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        dfs(0, -1);
        return ans;
    }

    private void dfs(int u, int p) {
        ans[u] = 1; // Every node counts itself in its subtree
        int charIdx = s.charAt(u) - 'a';

        // Push current node onto its character stack
        lastSeen[charIdx].push(u);

        for (int v : adj[u]) {
            dfs(v, u);
        }

        // Backtrack: remove current node before assigning its size to its new parent
        lastSeen[charIdx].pop();

        // If an ancestor with the same character exists, attach u's subtree to it;
        // otherwise, attach it to its original parent p
        if (p != -1) {
            int newParent = lastSeen[charIdx].isEmpty() ? p : lastSeen[charIdx].peek();
            ans[newParent] += ans[u];
        }
    }
}

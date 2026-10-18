class Solution {
    private static final int MOD = 1_000_000_007;
    private long[][] table;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        table = new long[n + 1][n + 1];
        
        // Precompute Pascal's triangle for combinations C(n, k)
        for (int i = 0; i <= n; i++) {
            table[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                table[i][j] = (table[i - 1][j - 1] + table[i - 1][j]) % MOD;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        return (int) (dfs(list) - 1 + MOD) % MOD;
    }

    private long dfs(List<Integer> list) {
        if (list.size() <= 2) {
            return 1;
        }

        int root = list.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < root) {
                left.add(list.get(i));
            } else {
                right.add(list.get(i));
            }
        }

        long leftWays = dfs(left);
        long rightWays = dfs(right);

        int leftSize = left.size();
        int rightSize = right.size();

        // Ways to interleave left and right elements * ways(left) * ways(right)
        long waysCombine = table[leftSize + rightSize][leftSize];
        
        waysCombine = (waysCombine * leftWays) % MOD;
        waysCombine = (waysCombine * rightWays) % MOD;

        return waysCombine;
    }
}

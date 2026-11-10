import java.util.Arrays;

class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            int stepCost = costs[i - 1];
            for (int j = Math.max(0, i - 3); j < i; j++) {
                int jumpDist = i - j;
                dp[i] = Math.min(dp[i], dp[j] + stepCost + jumpDist * jumpDist);
            }
        }

        return dp[n];
    }
}

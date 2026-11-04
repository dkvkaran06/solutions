import java.util.Arrays;
import java.util.TreeMap;

public class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        
        // Step 1: Calculate the median cost for each window of size x
        long[] windowCosts = getWindowCosts(nums, x);
        int m = windowCosts.length; // total number of windows = n - x + 1
        
        // Step 2: DP to find the minimum cost to pick k non-overlapping windows
        long[][] dp = new long[k + 1][m + 1];
        for (int i = 1; i <= k; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE / 2); // Avoid overflow during addition
        }
        
        // dp[0][j] remains 0, as picking 0 windows costs 0 operations
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= m; j++) {
                // Option A: Skip the current window (j-1)
                dp[i][j] = dp[i][j - 1];
                
                // Option B: Pick the current window (j-1)
                // Since window indices are 1-based in DP, a window at index j spans 
                // from (j-1) to (j-1+x-1) in the original array.
                // The next valid non-overlapping window must be at least x steps behind.
                if (j - x >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - x] + windowCosts[j - 1]);
                } else if (i == 1) {
                    // First window being picked with no previous window constraint
                    dp[i][j] = Math.min(dp[i][j], windowCosts[j - 1]);
                }
            }
        }
        
        return dp[k][m];
    }
    
    private long[] getWindowCosts(int[] nums, int x) {
        int n = nums.length;
        long[] costs = new long[n - x + 1];
        
        TreeMap<Integer, Integer> lower = new TreeMap<>();
        TreeMap<Integer, Integer> upper = new TreeMap<>();
        int lowerSize = 0, upperSize = 0;
        long lowerSum = 0, upperSum = 0;
        
        for (int i = 0; i < n; i++) {
            // Insert new element into lower max-heap tracker
            add(lower, nums[i]);
            lowerSum += nums[i];
            lowerSize++;
            
            // Balance: Move the maximum element of lower to upper
            int maxLowerVal = lower.lastKey();
            remove(lower, maxLowerVal);
            lowerSum -= maxLowerVal;
            lowerSize--;
            
            add(upper, maxLowerVal);
            upperSum += maxLowerVal;
            upperSize++;
            
            // Maintain size property: lowerSize should be >= upperSize
            if (lowerSize < upperSize) {
                int minUpperVal = upper.firstKey();
                remove(upper, minUpperVal);
                upperSum -= minUpperVal;
                upperSize--;
                
                add(lower, minUpperVal);
                lowerSum += minUpperVal;
                lowerSize++;
            }
            
            // Handle element sliding out of the window frame
            if (i >= x) {
                int outNum = nums[i - x];
                if (lower.containsKey(outNum)) {
                    remove(lower, outNum);
                    lowerSum -= outNum;
                    lowerSize--;
                } else {
                    remove(upper, outNum);
                    upperSum -= outNum;
                    upperSize--;
                }
                
                // Re-balance sizes after eviction
                if (lowerSize < upperSize) {
                    int minUpperVal = upper.firstKey();
                    remove(upper, minUpperVal);
                    upperSum -= minUpperVal;
                    upperSize--;
                    
                    add(lower, minUpperVal);
                    lowerSum += minUpperVal;
                    lowerSize++;
                } else if (lowerSize - upperSize > 1) {
                    int maxLowerVal2 = lower.lastKey();
                    remove(lower, maxLowerVal2);
                    lowerSum -= maxLowerVal2;
                    lowerSize--;
                    
                    add(upper, maxLowerVal2);
                    upperSum += maxLowerVal2;
                    upperSize++;
                }
            }
            
            // Once a full window size is formed, calculate the optimal L1 cost (median distance)
            if (i >= x - 1) {
                long median = lower.lastKey();
                costs[i - x + 1] = (median * lowerSize - lowerSum) + (upperSum - median * upperSize);
            }
        }
        
        return costs;
    }
    
    private void add(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }
    
    private void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);
        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }
}

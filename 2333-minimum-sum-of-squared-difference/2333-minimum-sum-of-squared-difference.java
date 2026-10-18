class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int n = nums1.length;
        
        // The maximum possible difference given LeetCode's constraints is 100,000
        int[] buckets = new int[100_002];
        long totalDiff = 0;
        int maxDiff = 0;
        
        // Step 1: Populate frequency buckets with absolute differences
        for (int i = 0; i < n; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);
            if (diff > 0) {
                buckets[diff]++;
                totalDiff += diff;
                maxDiff = Math.max(maxDiff, diff);
            }
        }
        
        // Edge Case: If total operations can reduce all differences to 0
        if (totalDiff <= k) {
            return 0;
        }
        
        // Step 2: Greedily process from the largest differences downwards
        for (int i = maxDiff; i > 0; i--) {
            if (buckets[i] > 0) {
                // Determine how many elements we can decrement at this current level
                long take = Math.min(k, (long) buckets[i]);
                
                buckets[i] -= take;      // Remove them from current difference level
                buckets[i - 1] += take;  // Move them down to the lower difference level
                k -= take;               // Deduct from budget
                
                if (k == 0) break;       // Out of operations budget
            }
        }
        
        // Step 3: Calculate the final sum of squared differences
        long minSumSquare = 0;
        for (int i = 1; i <= maxDiff; i++) {
            if (buckets[i] > 0) {
                minSumSquare += (long) buckets[i] * (long) i * i;
            }
        }
        
        return minSumSquare;
    }
}

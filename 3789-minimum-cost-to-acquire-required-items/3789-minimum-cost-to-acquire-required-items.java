class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        // Option 1: Buy all need1 using cost1 and all need2 using cost2
        long a = (long) need1 * cost1 + (long) need2 * cost2;
        
        // Option 2: Cover both needs using costBoth for the maximum requirement
        long b = (long) costBoth * Math.max(need1, need2);
        
        // Option 3: Use costBoth for the overlapping minimum need, and individual costs for the remainder
        int mn = Math.min(need1, need2);
        long c = (long) costBoth * mn + (long) (need1 - mn) * cost1 + (long) (need2 - mn) * cost2;
        
        // Return the minimum of the three possible strategies
        return Math.min(a, Math.min(b, c));
    }
}

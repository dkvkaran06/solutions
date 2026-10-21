class Solution {
    public int minOperations(String s) {
        int n = s.length();
        char[] cs = s.toCharArray();
        
        // 1. Check if the string is already sorted
        boolean isSorted = true;
        char mn = cs[0];
        char mx = cs[0];
        
        for (int i = 1; i < n; i++) {
            if (cs[i] < cs[i - 1]) {
                isSorted = false;
            }
            mn = (char) Math.min(mn, cs[i]);
            mx = (char) Math.max(mx, cs[i]);
        }
        
        if (isSorted) {
            return 0;
        }
        
        // 2. Unsorted string of length 2 can never be sorted with proper substrings
        if (n == 2) {
            return -1;
        }
        
        // 3. One edge matches the extreme values
        if (cs[0] == mn || cs[n - 1] == mx) {
            return 1;
        }
        
        // 4. An extreme value resides somewhere in the middle
        for (int i = 1; i < n - 1; i++) {
            if (cs[i] == mn || cs[i] == mx) {
                return 2;
            }
        }
        
        // 5. Worst case (e.g., max is at the start and min is at the end)
        return 3;
    }
}

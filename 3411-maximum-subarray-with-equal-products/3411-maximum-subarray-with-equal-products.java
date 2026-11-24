class Solution {
    public int maxLength(int[] nums) {
        int maxVal = 0;
        int totalLcm = 1;
        
        // Calculate an upper bound for the product to prevent integer overflow
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
            totalLcm = lcm(totalLcm, num);
        }
        int maxProductLimit = totalLcm * maxVal;

        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            int prod = 1;
            int g = 0;
            int l = 1;

            for (int j = i; j < n; j++) {
                prod *= nums[j];
                
                // Stop early if product exceeds the maximum possible (lcm * gcd)
                if (prod > maxProductLimit) {
                    break;
                }

                g = gcd(g, nums[j]);
                l = lcm(l, nums[j]);

                if (prod == g * l) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }
}

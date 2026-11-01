#include <vector>
#include <numeric>
#include <algorithm>

class Solution {
public:
    int minMoves(std::vector<int>& nums) {
        // Find the minimum element using a built-in algorithm
        int min = *std::min_element(nums.begin(), nums.end());
        
        long long sum = 0;
        // Accumulate the sum using a 64-bit integer to prevent overflow
        for (int num : nums) {
            sum += num;
        }
        
        // Use .size() instead of .length() for C++ vectors
        return static_cast<int>(sum - (long long)min * nums.size());
    }
};

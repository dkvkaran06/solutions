import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSelectedElements(int[] nums) {
        // Sort the array to process numbers in non-decreasing order
        Arrays.sort(nums);
        
        // dp maps a value `x` to the max length of a consecutive sequence ending at `x`
        Map<Integer, Integer> dp = new HashMap<>();
        int maxConsecutive = 0;
        
        for (int num : nums) {
            // Option 1: Increment `num` to `num + 1` (extends a sequence ending at `num`)
            int lengthIfIncremented = dp.getOrDefault(num, 0) + 1;
            dp.put(num + 1, lengthIfIncremented);
            
            // Option 2: Keep `num` as is (extends a sequence ending at `num - 1`)
            int lengthIfKept = dp.getOrDefault(num - 1, 0) + 1;
            dp.put(num, lengthIfKept);
            
            // Update the global maximum
            maxConsecutive = Math.max(maxConsecutive, Math.max(lengthIfIncremented, lengthIfKept));
        }
        
        return maxConsecutive;
    }
}

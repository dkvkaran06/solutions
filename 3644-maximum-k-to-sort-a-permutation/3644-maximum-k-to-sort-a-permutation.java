class Solution {
    public int sortPermutation(int[] nums)
     {
        int n =nums.length;
        int maxK=-1;
        boolean sorted=true;
        for(int i=0;i<n;i++)
        {
            if(nums[i]!=i)
            {
                sorted=false;
                maxK &= nums[i];
            }
            
        }
        return sorted ?0:maxK;
     }
}
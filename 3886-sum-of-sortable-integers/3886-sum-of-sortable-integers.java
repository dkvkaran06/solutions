class Solution {
    public int sortableIntegers(int[] nums) {
        int n=nums.length;
        int ans =0;
        for (int k=1;k<=n;k++)
        {
            if(n%k!=0) 
            continue;
            boolean ok =true;
            int prevMax=-1;
            for(int j=0;j<n;j+=k)
            {
                int cnt=1;
                int mx=-1;
                int mn=Integer.MAX_VALUE;
                for(int idx=j;idx<j+k;idx++)
                {
                    mx=Math.max(mx,nums[idx]);
                    mn=Math.min(mn,nums[idx]);
                    if(idx>j&& nums[idx]<nums[idx-1])
                    {
                        cnt++;
                    }
                }
                if(cnt>2||mn<prevMax)
                {
                    ok=false;
                    break;
                }
                if(cnt==2 && nums[j+k-1]>nums[j])
                {
                    ok=false;
                    break;
                }
                prevMax=mx;
                
            }
            if(ok)
            {
                ans+=k;
            }
        }
        return ans;
    }
}
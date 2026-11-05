class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int distanceValueCount=0;
        for(int val:arr1)
        {
            if(isValidDistance(arr2,val,d))
            {
                distanceValueCount++;
            }
        }
        return distanceValueCount;
        
    }
    private boolean isValidDistance(int[] arr2,int val,int d)
    {
        int left=0;
        int right=arr2.length-1;
        int minInvalid=val-d;
        int maxInvalid=val+d;
        while(left<=right)
        {
            int mid=left+(right-left)/2;
            if(arr2[mid]>=minInvalid && arr2[mid]<=maxInvalid)
            {
                return false;
            }
            
        
        if(arr2[mid]<minInvalid)
        {
            left=mid+1;

        }
        else
        {
            right=mid-1;
        }
        }
        return true;
    }
}
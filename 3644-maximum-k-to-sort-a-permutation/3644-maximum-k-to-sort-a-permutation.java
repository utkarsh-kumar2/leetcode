class Solution {
    public int sortPermutation(int[] nums) {
        int count=-1;
        int n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i]!=i){
                count&=nums[i];

            }
        }
        return Math.max(count,0);
        
    }
}
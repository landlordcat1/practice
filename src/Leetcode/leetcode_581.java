package Leetcode;
//寻找最长无序子数组
class leetcode_581 {
    public static void main(String[] arg){
        int[] nums=new int[]{1,4,2,3,5,6};
        findUnsortedSubarray(nums);
    }
    public static int findUnsortedSubarray(int[] nums) {
        int r=0;
        int l=nums.length;
        for(int i=0;i<nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[j]<nums[i]){
                    r=Math.max(j,r);
                    l=Math.min(l,i);
                }
            }
        }
        return r-l<0?0:r-l+1;
    }
}
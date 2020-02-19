package Leetcode;

public class leetcode_last_first {
    public static void main(String[] arg){
        int[] a=new int[]{1,2,4,5,2,6};
        searchRange(a,2);
    }
        public static int[] searchRange(int[] nums, int target) {
            int left=0;
            int right=nums.length;
            int[] a=new int[]{-1,-1};
            if(right==0)
                return a;
            while(left<right){
                int mid=(left+right)/2;
                if(nums[mid]==target){
                    right=mid;
                }else if(nums[mid]<target){
                    left=mid+1;
                }else if(nums[mid]>target){
                    right=mid;
                }
            }
            if(left>nums.length-1) return a;
            if(nums[left]!=target) return a;
            a[0]=left;
            left=0;
            right=nums.length;

            while(left<right){
                int mid=(left+right)/2;
                if(nums[mid]==target){
                    left=mid+1;
                }else if(nums[mid]<target){
                    left=mid+1;
                }else if(nums[mid]>target){
                    right=mid;
                }
            }
            a[1]=left-1;
            for(int i=0;i<a.length;i++){
                System.out.println(a[i]);
            }
            return a;
        }
}

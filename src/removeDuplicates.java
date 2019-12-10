public class removeDuplicates {
    public static void main(String[] arg){
        int[] nums=new int[]{0,0,1,1,1,2,2,3,3,4};
       int k=1;
        for(int i=0;i<nums.length-1;i++)
        {
            if(nums[i]!=nums[i+1])
            {
              nums[k++]=nums[i+1];
            }
        }
        for (int i = 0; i < k; i++) {
            System.out.println(nums[i]);
        }
    }
}

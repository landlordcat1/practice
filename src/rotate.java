public class rotate {
    public static void main(String[] arg){
        int[] nums={1,2,3,4,5,6,7};
        int n=nums.length;
        int k=3;
        k%=n;
        for(int i=0;i< k;i++)
        {
            int t=nums[n-1];
            for(int j=n-1;j>0;j--)
            {
                nums[j]=nums[j-1];
            }
            nums[0]=t;
        }
        for (int i=0;i<nums.length;i++)
        {
            System.out.println(nums[i]);
        }
    }
}

public class removeElement {
    public static void main(String[] arg){
        int[] nums=new int[]{0,0,1,1,1,2,2,3,3,4};
        int k=0;
        int v=2;
        for(int i=0;i<nums.length;i++)
        {
            if(nums[i]!=v)
            {
                nums[k++]=nums[i];
            }
        }
        for (int i = 0; i < k; i++) {
            System.out.println(nums[i]);
        }
    }
}

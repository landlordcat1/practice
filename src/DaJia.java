public class DaJia {
    public static void main(String[] arg) {
        int[] nums = {1, 2, 3, 1};
        int[] m=new int[nums.length];
        m[0]=nums[0];
        m[1]=Math.max(nums[0],nums[1]);
        for(int i=2;i<nums.length;i++)
        {
            m[i]=Math.max(m[i-1],nums[i]+m[i-2]);
        }
        System.out.println(m[nums.length-1]);
    }
}

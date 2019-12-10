import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class permute1 {
    static List<List<Integer>> res=new ArrayList<>();
    public static void main(String[] arg){
        int[] nums={1,2,3};
        permute(nums);
    }
    public static List<List<Integer>> permute(int[] nums) {

        helper(nums,0);
        System.out.println(res);
        return res;
    }
    public static void helper(int[] nums, int i)
    {
           if(i==nums.length-1)
           {
               List<Integer> list=new LinkedList<Integer>();
               for(int j=0;j<nums.length;j++)
               {
                   list.add(nums[j]);
               }
               res.add(list);
           }
           for(int j=i;j<nums.length;j++)
           {
               swap(nums,i,j);
               helper(nums,i+1);
               swap(nums,i,j);

           }

    }
    private static void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}

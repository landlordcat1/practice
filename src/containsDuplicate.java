import java.util.HashMap;
import java.util.Map;

public class containsDuplicate {
    public static void main(String[] arg){
        int[] nums={1,1,1,3,3,4,3,2,4,2};
        Map<Integer,Integer> map=new HashMap<>();
        for(int i:nums)
        {
            if(!map.containsKey(i))
            {
                map.put(i,1);
            }
            else
            {
                System.out.println(false);
            }
        }
    }
}

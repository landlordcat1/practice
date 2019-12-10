import java.util.HashMap;
import java.util.Map;

public class only {
    public static void main(String[] arg) {
        int[] a = {2, 2, 1};
        singleNumber(a);
    }

    public static int singleNumber(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i:nums)
        {
            if(!map.containsKey(i))
            {
                map.put(i,1);
            }else
            {
                map.put(i,map.get(i)+1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue()==1)
            {
                //return entry.getKey();
                System.out.println(entry.getKey());
            }
        }
        return 0;
    }
}

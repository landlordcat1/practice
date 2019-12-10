import java.util.HashMap;
import java.util.Map;

public class mode {
    public static void main(String[] arg){
        Map<Integer,Integer> map = new HashMap<>();
        int[] a={3,2,3};
        for(int i:a)
        {
            if(!map.containsKey(i))
            {
                map.put(i,1);
            }else
            {
                map.put(i,map.get(i)+1);
            }
        }
        for(Map.Entry<Integer,Integer> entry:map.entrySet())
        {
            if(entry.getValue()>(a.length)/2)
            {
                System.out.println(entry.getKey());
            }
        }
    }
}

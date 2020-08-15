package toOffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class wangyi1 {
    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int sum = num;
        int count = 0;
        Map<String,Integer> map = new HashMap<>();
        if(num == 0){
            System.out.println(0);
            return;
        }
        while (num!=0){
            String s = scanner.next();
            if(map.containsKey(s)){
                map.put(s,map.get(s)+1);
            }else {
                map.put(s,1);
            }
            num--;
        }
        for(String string:map.keySet()){
            if(map.get(string) >sum*0.01){
                count++;
            }
        }
        System.out.println(count);
    }
}

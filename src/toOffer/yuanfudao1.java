package toOffer;

import com.sun.javafx.collections.MappingChange;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class yuanfudao1 {
   static class TimeNode{
       double time;
       boolean isStart;
       public TimeNode(double time,boolean isStart){
           this.time = time;
           this.isStart = isStart;
       }
   }
   public static void main(String[] arg){
       Scanner scanner = new Scanner(System.in);
       int num = scanner.nextInt();
       Map<Double,TimeNode> map = new TreeMap<>();
       for(int i =0;i<num;i++){
           double startTimes = scanner.nextInt();
           double endTimes = scanner.nextInt();
           TimeNode startNode = new TimeNode(startTimes,true);
           TimeNode endTime = new TimeNode(endTimes,false);
           map.put(startTimes,startNode);
           map.put(endTimes,endTime);
       }
       int count = 0;
        int max =Integer.MIN_VALUE;
        for(Double doublr:map.keySet()){
            count = map.get(doublr).isStart?count+1:count-1;
            max = Math.max(max,count);
        }
        System.out.println(max);
        scanner.close();
   }
}

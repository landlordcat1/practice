package toOffer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class test_29 {
    public static void main(String[] arg){
           int[] array=new int[]{4,5,1,6,2,7,3,8};
            GetLeastNumbers_Solution(array,4);
    }
    public static ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> result=new ArrayList<>();
        int length=input.length;
        if(k>length||k==0){
            return result;
        }
        PriorityQueue<Integer> maxHeap=new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for(int i=0;i<length;i++){
            if(maxHeap.size()!=k){
              maxHeap.offer(input[i]);
            }
            else if(maxHeap.peek()>input[i]){
                maxHeap.poll();
                maxHeap.offer(input[i]);
            }
        }
        for(Integer integer:maxHeap){
            result.add(integer);
        }
        System.out.println(result);
        return result;
    }
}

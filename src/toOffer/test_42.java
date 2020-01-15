package toOffer;

import java.util.ArrayList;


public class test_42 {
    public static void main(String[] arg){
        int[] a=new int[]{1,2,3,4,5};
        int sum=5;
        FindNumbersWithSum(a,sum);
    }
    public static ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
    ArrayList<Integer> result = new ArrayList<>();
        if(array==null||array.length<=1){
            return result;
        }
    int start=0;
    int end=array.length-1;
    while(start<end){
        if(array[start]+array[end]==sum){
            result.add(array[start]);
            result.add(array[end]);
            break;
        }else if(array[start]+array[end]<sum){
            start++;
        }else {
            end--;
        }
    }
        System.out.println(result);
    return result;
    }
}

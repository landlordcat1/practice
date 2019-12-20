package toOffer;

import java.util.ArrayList;

public class test_41 {
    public static void main(String[] arg){
        FindContinuousSequence(100);
    }
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
             ArrayList<ArrayList<Integer>> result=new ArrayList<>();
             for(int i=1;i<sum;i++){
                 int temp=0;
                 int j=i;
                 while (temp<sum){
                     temp+=j;
                     j++;
                 }
                 if(temp==sum){
                     ArrayList<Integer> array=new ArrayList<>();
                     for(int k=i;k<j;k++){
                         array.add(k);
                     }
                     result.add(array);
                 }
             }
        System.out.println(result);
             return result;
    }
}

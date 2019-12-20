package toOffer;

import java.util.HashMap;

public class test_40 {
    public static void main(String[] arg){
    }
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<array.length;i++){
            if(map.containsKey(array[i])){
                map.put(array[i],2);
            }else {
                map.put(array[i],1);
            }
        }
        int count=0;
        for(int i=0;i<array.length;i++){
            if(map.get(array[i])==1){
                num1[0]=array[i];
                count++;
            }
            else {
                num2[0]=array[1];
            }
        }
    }
}

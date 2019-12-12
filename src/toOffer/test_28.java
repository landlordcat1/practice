package toOffer;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//题目描述
//数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
// 例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
public class test_28 {
    public static void main(String[] arg){
         int[] array={1,2,3,2,2,2,5,4,2};
        MoreThanHalfNum_Solution(array);
    }
    public static int MoreThanHalfNum_Solution(int[] array) {
        if(array.length==0)
            return 0;
        if(array.length==1)
            return array[0];
        Arrays.sort(array);
        int i=array[array.length/2];
        int num=0;
        for (int j=0;j<array.length;j++){
            if(array[j]==i){
             num++;
            }
        }
        System.out.println((num>array.length/2)?array[i]:0);
        return (num>array.length/2)?array[i]:0;
    }

}

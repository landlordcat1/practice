package toOffer;

import java.util.*;

public class test_27 {
    public static void main(String[] arg){
        ArrayList<String> result=new ArrayList<>();
        permutation(0,new char[]{'a','a','b','c'},result);
        System.out.println(result);
    }
    public static void permutation(int index,char[] a,ArrayList<String> result){
        if(index==a.length-1){
            result.add(new String(a));
            return;
        }
        Set<Character> set=new HashSet<>();
        for(int i=index;i<a.length;i++) {
            if (!set.contains(a[i])) {
                set.add(a[i]);
                swap(a, index, i);
                permutation(index + 1, a, result);
                swap(a, index, i);
            }
        }
    }
    public static void swap(char[] a,int i,int j){
        char temp=a[i];
        a[i]=a[j];
        a[j]=temp;

    }
}

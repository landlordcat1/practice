package toOffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class bilibili2 {
    int m;
    public static void main(String[] arg){
        int[] array = {2,1,2,2,3};
        System.out.println(method(array));
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String [] arr = s.split("\\s");
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
        for(int i = 0;i<arr.length;i++){
            array[i] = Integer.parseInt(arr[i]);
        }

    }
    public static int method(int[] array){
        int[] a = new int[array.length];
        Arrays.fill(a,1);
        for(int i=1;i<a.length;i++){
               if(array[i]>array[i-1]){
                   a[i] = a[i-1]+1;
               }
        }
        for(int j=a.length-2;j>=0;j--){
            if(array[j]>array[j+1]){
                a[j] = a[j+1]+1;
            }
        }

        System.out.println("----------------------");
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
        int count = 0;
        for(int num:a){
            count+= num;
        }
        return count;
    }
}

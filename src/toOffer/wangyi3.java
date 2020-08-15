package toOffer;

import java.util.Scanner;

public class wangyi3 {
    public static void main(String[] arg){
        Scanner scanner = new Scanner(System.in);
        int num  = scanner.nextInt();
        int[] a = new int[num];
        for(int i=0;i<num;i++){
            a[i] = scanner.nextInt();
        }
        for(int i=0;i<a.length;i++){
            System.out.println(method(a[i]));
        }
    }
    public static int method(int n){
        if(n==1){
            return 1;
        }else if(n==2){
            return 2;
        }else if(n==3){
            return  4;
        }
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for(int i =4;i<dp.length;i++){
            dp[i] =(dp[i-1]+dp[i-2]+dp[i-3])%10007;
        }
        return dp[n];
    }
}

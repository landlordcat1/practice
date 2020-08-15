package toOffer;

import java.util.ArrayList;
import java.util.HashMap;

public class test_4 {
    public static void main(String[] arg) {
        int n = 3;
        Fibonacci(n);
    }

    public static int Fibonacci(int n) {
        int f1=1, f2=1, f = 0;
        if (n == 0)
            System.out.println("0");
        else if(n<=2){
            System.out.println("1");
        }
        else {
            for (int i = 3; i <= n; i++) {
                f = f1 + f2;
                f1 = f2;
                f2 = f;
            }
            System.out.println(f);
        }       return f;
    }
}
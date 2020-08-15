package toOffer;

import java.util.Scanner;
import java.util.function.BinaryOperator;

public class meituan_1 {
    public static void main(String[] arg){
        Scanner scanner=new Scanner(System.in);
        int m=scanner.nextInt();
        int n=scanner.nextInt();
        int a[]=new int[]{3,1,3,2,5};
        same(a,n);
    }
    public static void same(int[] a,int n){
        int[] b=new int[a.length];
        for(int i=0;i<a.length;i++){
            b[i]=a[i]|n;
        }
//        for(int i=0;i<b.length;i++){
//           b[i]=Biannary2Decimal(b[i]);
//        }
        majorityElement(b);
    }
    public  static  Integer Biannary2Decimal(int bi){
        String binStr = bi+"";
        Integer sum = 0;
        int len = binStr.length();
        for (int i=1;i<=len;i++){
            //第i位 的数字为：
            int dt = Integer.parseInt(binStr.substring(i-1,i));
            sum+=(int)Math.pow(2,len-i)*dt;
        }
        return  sum;
    }
    public static int majorityElement(int[] nums) {

        int n = nums.length;

        for(int i = 0 ;i< n;i++)
        {
            for(int j=0 ;j < n ;j++)
            {
                if(nums[i] > nums[j])
                {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }

        System.out.println(nums[n/2]);
        return nums[n/2];
    }
}

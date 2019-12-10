import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class countAndSay {
    public static void main(String[] arg) {
//        int n;
//        Scanner in=new Scanner(System.in);
//        n=in.nextInt();
//        String s="1";
//        StringBuilder sb=new StringBuilder();
//        int count;
//        char temp;
//        for(int i=1;i<n;i++)
//        {
//            count=1;
//            temp=s.charAt(0);
//            sb.delete(0,sb.length());
//            for(int j=1;j<s.length();j++)
//            {
//                if(temp!=s.charAt(j))
//                {
//                    sb.append(count).append(temp);
//                    count=0;
//                    temp=s.charAt(0);
//                }
//                count++;
//            }
//            sb.append(count).append(temp);
//            s=sb.toString();
//        }
//        System.out.println(s);
        int n;
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        StringBuilder s = new StringBuilder();
        String S = "1";
        int count;
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println("1");
        }
        for (int i = 1; i < n; i++) {
                 count = 1;
                char temp = S.charAt(0);
                s.delete(0,s.length());
            System.out.println("temp"+temp);
                for (int j = 1; j < S.length(); j++) {
                    if (S.charAt(j) == temp) {
                        count++;
                        System.out.println("count"+count);
                    } else {
                        s.append(count);
                        System.out.println("count1"+count);
                        s.append(temp);
                        count = 0;
                        temp = S.charAt(j);
                    }
                }
            s.append(count).append(temp);
            S=s.toString();
            }
       System.out.println(s);
        }
    }

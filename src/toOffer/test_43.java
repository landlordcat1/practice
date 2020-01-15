package toOffer;

public class test_43
{
    public static void main(String[] arg){
        String str="abcXYZefg";
        int n=3;
        LeftRotateString(str,n);
    }
    public static String LeftRotateString(String str,int n) {
        if (str == null || str.length() == 0 || n <= 0) {
            return str;
        }
        String p = str.substring(0, n);
        String q = str.substring(n, str.length());
        System.out.println(q+p);
        return q + p;
    }
}

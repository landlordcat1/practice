package toOffer;

public class test_49 {
    public static void main(String[] arg){
    StrToInt("+231444");
    }
    public static int StrToInt(String str) {
        if(str == null||str.length() == 0){
            return  0;
        }
        boolean flag=false;
        Integer result=0;
        char a=str.charAt(0);
        if(a=='+'){
            flag=false;
        }else if(a=='-'){
            flag=true;
        }else if(a>='0'&&a<='a'){
            result=a-'0';
        }
        for(int i=1;i<str.length();i++){
            char ch=str.charAt(i);
            if(ch>='0'&&ch<='9'){
                result *= 10;
                result += ch - '0';
            }else {
                return 0;
            }
        }
        if(flag){
            result = -result;
        }
       if(result>Integer.MAX_VALUE||result<Integer.MIN_VALUE){
            return 0;
       }
        System.out.println(result);
       return result;
    }
}

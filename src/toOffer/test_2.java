package toOffer;

public class test_2 {
    public static void main(String[] arg){
        StringBuffer str=new StringBuffer("We Are Happy.");
        for(int i=0;i<str.length();i++){
            if (str.charAt(i)==' '){
                str.replace(i,i+1,"%20");
            }
        }
        System.out.println(str);
    }
}

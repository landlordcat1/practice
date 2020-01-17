package toOffer;
/*
* 先把全部字符串翻转
* 再把单个单词翻转*/
public class test_44 {
    public static void main(String[] arg){
        String str="student a am I";
        ReverseSentence(str);
    }
    public static String ReverseSentence(String str) {
        if(str.length()<=0){
            return "";
        }
    StringBuffer str1=new StringBuffer(str);
    str1.reverse();
    StringBuffer result=new StringBuffer();
    int j=0;
    int blacksum=0;
    for(int i=0;i<str1.length();i++){
        if(str1.charAt(i)==' '&&(i!=str1.length()-1)){//不是最后一个单词
            blacksum++;
            StringBuffer str2=new StringBuffer(str1.substring(j,i));
            result.append(str2.reverse().toString()).append(" ");
            j=i+1;
        }
        if(blacksum!=0&&(i==str1.length()-1)){
             StringBuffer str3=new StringBuffer(str1.substring(j,i+1));
             result.append(str3.reverse());
        }
    }
//        if(blacksum==0)
//            return str;
    return result.toString();
    }
}

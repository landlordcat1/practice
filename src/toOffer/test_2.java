package toOffer;
/*
*  * Description:
 * 替换空格
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 * 思路:
 * 调函数,或者使用StringBuffer
*/
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

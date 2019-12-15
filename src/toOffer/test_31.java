package toOffer;

public class test_31 {
    public static void main(String[] arg){
        new test_31().NumberOf1Between1AndN_Solution(1);
    }
    public int NumberOf1Between1AndN_Solution(int n) {
           int count=0;
           for(int i=1;i<n;i++){
               int j=i;
               while (j!=0){
                   if(j%10==1){
                       count++;
                   }
                   j/=10;
               }
           }
        System.out.println(count);
           return count;
    }
}

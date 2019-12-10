public class Maxpofit {
    public static void main(String[] arg){
        int[] prices={7,1,5,3,6,4};
        int n=0;
        int sum=0;
        for(int i=1;i<prices.length;i++)
        {
            n=prices[i]-prices[i-1];
            if(n>0)
            {
                sum+=n;
            }
        }
        System.out.println(sum);

    }
}

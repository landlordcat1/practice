package toOffer;

public class test_37 {
    public static void main(String[] arg){
        int[] a=new int[]{1,2,2,2,3,4};
        GetNumberOfK(a,2);
    }
    public static int GetNumberOfK(int [] array , int k) {
        int count=0;
        for(int i=0;i<array.length;i++){
            if(array[i]==k){
                count++;
            }
        }
        System.out.println(count);
        return count;
    }
}

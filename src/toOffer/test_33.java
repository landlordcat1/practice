package toOffer;

public class test_33 {
    public static void main(String[] arg){
        GetUglyNumber_Solution(10);
    }
    public static int GetUglyNumber_Solution(int index) {
        if(index==0){
            return 0;
        }
        int[] array=new int[index];
        array[0]=1;
        int sub2=0,sub3=0,sub5=0;
        for(int i=1;i<array.length;i++){
            int num2=array[sub2]*2;
            int num3=array[sub3]*3;
            int num5=array[sub5]*5;
            array[i]=Math.min(Math.min(num2,num3),num5);
            if(array[i]==num2){
                sub2++;
            }
            if(array[i]==num3){
                sub3++;
            }
            if (array[i]==num5){
                sub5++;
            }
        }
//        for(int i=0;i<array.length;i++){
//            System.out.println(array[i]);
//        }
        return array[index-1];
    }
}

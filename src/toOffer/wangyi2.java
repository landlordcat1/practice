package toOffer;

import java.util.Scanner;

public class wangyi2 {
    public static int method(int[] array){
        int count = 0;
        while (true){
            //E
            int maxE = Integer.MIN_VALUE;
            int maxESub = 0;
            for(int i = 0;i<2;i++){
                if(maxE < array[i]){
                    maxE = array[i];
                    maxESub = i;
                }
            }
            if(maxE == 0){
                break;
            }
            if (array[0] == array[1]){
                maxESub = 0;
            }
            array[maxESub]--;

            //M
            int maxM = Integer.MIN_VALUE;
            int maxMSub = 0;
            for(int i = 0;i<4;i++){
                if(maxM < array[i]){
                    maxM = array[i];
                    maxMSub = i;
                }
            }
            if(maxM == 0){
                break;
            }
            if (array[1] == array[2] && array[2] == array[3]){
                maxMSub = 2;
            }
            array[maxMSub]--;
            //H
            int maxH = Integer.MIN_VALUE;
            int maxHSub = 0;
            for(int i = 0;i<5;i++){
                if(maxH < array[i]){
                    maxH = array[i];
                    maxHSub = i;
                }
            }
            if(maxH == 0){
                break;
            }
            if (array[3] == array[4]){
                maxHSub = 4;
            }
            array[maxHSub]--;

            count++;
        }
        return count;
    }
    public static void main(String[] arg){
        Scanner in = new Scanner(System.in);
        int[] array = new int[5];
        for(int i=0;i<5;i++){
            array[i] = in.nextInt();
        }
        System.out.println(method(array));
        in.close();
    }
}

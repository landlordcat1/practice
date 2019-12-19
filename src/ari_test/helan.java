package ari_test;

public class helan {
    public static void main(String[] arg){
        int[] array=new int[10];

    }
    public static int[] partition(int[] arr,int L,int R,int num){
        int less=L-1;
        int more=R+1;
        int index=L;
        while (index<more){
            if(arr[index]<num){
               swap(arr,++less,index);//与小于num值的后一个进行交换
            }else if(arr[index]>num){
                swap(arr,--more,index);
            }else {
                index++;
            }
        }
        return arr;
    }
    public static void swap(int[] a,int l,int r){
        int temp=a[l];
        a[l]=a[r];
        a[r]=temp;
    }
}

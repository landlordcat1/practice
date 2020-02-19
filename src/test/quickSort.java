package test;

public class quickSort {
    public static void main(String[] arg){
        int[] arr=new int[]{2,1,3,5,0,9};
        sort(arr,0,arr.length-1);
        for(int i=0;i< arr.length;i++){
            System.out.println(arr[i]);
        }

    }
    public static void sort(int[] a,int left,int right){
        if(left>right)
            return;
        int base=a[left];
        int i=left;
        int j=right;
        while (i!=j){
            while (a[j]>=base&&i<j){
                j--;
            }

            while (a[i]<=base&&i<j){
                i++;
            }
                        int t=a[i];
            a[i]=a[j];
            a[j]=t;
        }
        a[left]=a[i];//把相遇的位置赋给基准数
        a[i]=base;//把基准数赋给相遇的位置
        sort(a,left,i-1);//排序左半边
        sort(a,j+1,right);//排序右半边
    }
}

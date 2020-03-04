package test;

public class sort_1 {
    public static void main(String[] arg){
        int[] a=new int[]{0,16,3,1,5};
        //new sort_1().sort(a);
        new sort_1().select_sort(a);
        for(int i=0;i<a.length;i++)
        System.out.println(a[i]);
    }
    public void sort(int[] a){//冒泡
        for(int i=0;i<a.length;i++){
            for(int j=i;j<a.length-i-1;j++){
                if(a[j]>a[j+1]){
                    int t=a[j];
                    a[j]=a[j+1];
                    a[j+1]=t;
                }
            }
        }
    }
    public void select_sort(int[] a){//选择
        int min;
        for(int i=0;i<a.length-1;i++){
            min=i;
            for(int j=i;j<a.length;j++){
                if(a[j]<a[min]){
                    min=j;
                }
            }
            swap(a,i,min);
        }
    }
    public void swap(int[] a,int i,int j){
        int t=a[i];
        a[i]=a[j];
        a[j]=t;
    }
}

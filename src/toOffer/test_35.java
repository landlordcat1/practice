package toOffer;

public class test_35 {
    public static int count;
    public static void main(String[] arg){
        int[] a=new int[]{1,2,3,4,5,6,7,0};
        InversePairs(a);
    }
    public static int InversePairs(int [] array) {
        sort(array,0,array.length-1);
        return count;
    }
    public static void sort(int[] a,int left,int right){
        if (left== right){
            return;
        }
        int mid=(left+right)/2;
        sort(a,left,mid);
        sort(a,mid+1,right);
        merge(a,left,mid,right);
    }
    public static void merge(int[] array,int left,int mid,int right){
        int[] help=new int[right-left+1];
        int p1=left;
        int p2=mid+1;
        int i=0;
        while (p1<=mid&&p2<=right){
            if(array[p1]>array[p2]){
                count+=mid-p1+1;
                help[i++]=array[p1++];
            }else {
                help[i++]=array[p2++];
            }
        }
        while (p1<=mid){
            help[i++]=array[p1++];
        }
        while (p2<=right){
            help[i++]=array[p2++];
        }
        for(i=0;i<help.length;i++){
            array[left+i]=help[i];
        }
    }
}

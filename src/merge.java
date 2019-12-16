public class merge {
    public static void main(String[] arg){
        int[] a=new int[]{1,4,2,7,8,2};
        sortProcess(a,0,a.length-1);
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }
    public static void sortProcess(int[] a,int left,int right ) {
        if (left == right) {
            return;
        }
        int mid = (left + right) / 2;
        sortProcess(a, left, mid);
        sortProcess(a, mid + 1, right);
        Merge(a,left,mid,right);
    }
    public static void Merge(int[] a,int left,int mid,int right){
        int[] help=new int[right-left+1];
        int i=0;
        int p1=left;
        int p2=mid+1;
        while (p1<=mid&&p2<=right){
            help[i++]=a[p1]<a[p2]?a[p1++]:a[p2++];
        }
        while (p1<=mid){
            help[i++]=a[p1++];
        }
        while (p2<=right){
            help[i++]=a[p2++];
        }
        for(i=0;i<help.length;i++){
            a[left+i]=help[i];
        }
    }
}

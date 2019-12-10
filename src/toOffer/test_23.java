package toOffer;
import java.util.Arrays;
public class test_23 {
    public static void main(String[] arg){
        System.out.println(VerifySquenceOfBST(new int[]{3,4,9,5,12,11,10}));
    }
    public static boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length==0)
            return false;
        int root=sequence[sequence.length-1];
        int i=0;
        for(;i<sequence.length;i++){
            if(sequence[i]>=root) {
                break;
            }
        }
//        while (sequence[i]<root){
//            i++;
//        }
        for(int j=i+1;j<sequence.length;j++){
            if(sequence[j]<root)
                return false;
        }
        boolean left=VerifySquenceOfBST(Arrays.copyOfRange(sequence,0,i));
        boolean right=VerifySquenceOfBST(Arrays.copyOfRange(sequence,i,sequence.length-1));
        if(sequence.length==1){
            return true;
        }
        if(i==0){
            return right;
        }
        if(i==sequence.length-1){
            return left;
        }
        return left&&right;
    }
}

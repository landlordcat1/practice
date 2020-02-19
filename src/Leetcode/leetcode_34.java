package Leetcode;

public class leetcode_34 {
    public static void main(String[] arg){
        int[] a=new int[]{1,2,3,4,6};
        find(a,5);
    }
    public static int find(int[] a,int num){
        int left=0;
        int right=a.length-1;
        while (left<=right) {
            int mid=(left+right)/2;
            if (a[mid] == num) {
                System.out.println(mid);
                return mid;
            } else if (a[mid] < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(left);
        return left;
    }
    }


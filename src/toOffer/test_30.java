package toOffer;
//连续子数组的最大和
public class test_30 {
    public static void main(String[] arg) {
          int[] a={6,-3,-2,7,-15,1,2,2};
          FindGreatestSumOfSubArray(a);
    }

    public static int FindGreatestSumOfSubArray(int[] array) {
        int len = array.length;
        int[] d = new int[len];
        int max = array[0];
        d[0] = array[0];
        for (int i = 1; i < len; i++) {
            int newmax = d[i - 1] + array[i];
            if (newmax > array[i]) {
                d[i] = newmax;
            } else {
                d[i] = array[i];
            }
            if (d[i] > max)
                max = d[i];
        }
        return max;
    }
}
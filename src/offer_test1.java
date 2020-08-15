/*
 * Description:
 * 二维数组中的查找
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * 从左下角开始,判断num(i,j下标处)和target的关系
 * 如果target<num,那么i--
 * 如果target>num,那么j++
 *
*/

public class offer_test1 {
    public static void main(String[] arg) {
        int target = 5;
        int[][] a = new int[9][4];
        int k = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = k++;
            }
        }
      Find_2(target,a);
   //     Find(target,a);
    }
    public static boolean Find(int target,int [][]array){
        for(int i=0;i<array.length;i++){
            int start=0;
            int end=array[0].length-1;
            while (start<=end) {
                int mid = (start + end) / 2;
                if (target > array[i][mid]) {
                    start = mid + 1;
                } else if (target < array[i][mid]) {
                    end = mid - 1;
                } else {
                    System.out.println("true");
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean Find_2(int target,int[][] array){
        if (array == null || array.length == 0 || (array.length == 1 && array[0].length == 0)) return false;
        int i=array.length-1;
        int j=0;
        while (i>0&&j<array[0].length-1){
            if(target>array[i][j]){
                j++;
            }
            else if(target<array[i][j]){
                i--;
            }
            else {
                System.out.println("true");
                return true;
            }
        }
        return false;
    }
//    public static boolean Find_3(int target, int[][] array) {
//        //if (array == null || array.length == 0 || (array.length == 1 && array[0].length == 0)) return false;
//        for (int i = 0; i < array.length; i++) {
//            int begin = 0;
//            int end = array[0].length - 1;
//            while (begin <= end) {
//                int mid = (begin + end) / 2;
//                if (target > array[i][mid]) {
//                    begin = mid + 1;
//                } else if (target < array[i][mid]) {
//                    end = mid - 1;
//                } else {
//                    System.out.println("true");
//                    return true;
//                }
//            }
//
//        }
//        return false;
//    }

//    public static boolean Find(int target, int[][] array) {
//        for(int i=0;i<array.length;i++){
//            if(target<array[i][0])
//            {
//                for(int j=0;j<array[0].length;j++){
//                    if (target==array[i-1][j]){
//                        System.out.println("true");
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
}

package toOffer;

import java.util.ArrayList;
import java.util.Stack;

public class test_19 {
    public static void main(String[] arg) {
        int[][] a = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printMatrix(a);

    }

    public static ArrayList<Integer> printMatrix(int[][] matrix) {
//        int m = matrix.length;
//        int n = matrix[0].length;
//        ArrayList<Integer> result = new ArrayList<>();
//        if (matrix == null || m == 0 || n == 0)
//            return result;
        ArrayList<Integer> result = new ArrayList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return result;
        }
        while (true) {
            int up = 0;
            int down = matrix.length - 1;
            int left = 0;
            int right = matrix[0].length - 1;
            for (int i = left; i <= right; i++) {
                result.add(matrix[up][i]);
            }
            up++;
            if (up > down) {
                break;
            }
            for (int j = up; j <= down; j++) {
                result.add(matrix[j][right]);
            }
            right--;
            if (left > right) {
                break;
            }
            for (int k = right; k >= left; k--) {
                result.add(matrix[down][k]);
            }
            down--;
            if (up > down) {
                break;
            }
            for (int y = down; y >= up; y--) {
                result.add(matrix[y][left]);
            }
            left++;
            if (left > right) {
                break;
            }
                  }
        for(int i=0;i<result.size();i++){
            System.out.println(result.get(i));
        }
        return result;

//        ArrayList<Integer> list = new ArrayList<>();
//        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
//            return list;
//        }
//        int up = 0;
//        int down = matrix.length-1;
//        int left = 0;
//        int right = matrix[0].length-1;
//        while(true){
//            // 最上面一行
//            for(int col=left;col<=right;col++){
//                list.add(matrix[up][col]);
//            }
//            // 向下逼近
//            up++;
//            // 判断是否越界
//            if(up > down){
//                break;
//            }
//            // 最右边一行
//            for(int row=up;row<=down;row++){
//                list.add(matrix[row][right]);
//            }
//            // 向左逼近
//            right--;
//            // 判断是否越界
//            if(left > right){
//                break;
//            }
//            // 最下面一行
//            for(int col=right;col>=left;col--){
//                list.add(matrix[down][col]);
//            }
//            // 向上逼近
//            down--;
//            // 判断是否越界
//            if(up > down){
//                break;
//            }
//            // 最左边一行
//            for(int row=down;row>=up;row--){
//                list.add(matrix[row][left]);
//            }
//            // 向右逼近
//            left++;
//            // 判断是否越界
//            if(left > right){
//                break;
//            }
//        }
//        return list;
   }
}


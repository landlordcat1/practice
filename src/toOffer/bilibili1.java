package toOffer;

public class bilibili1 {
    public int minPathSum (int[][] grid) {
        int sub1 = grid.length;
        int sub2 = grid[0].length;

        for(int i=1;i<sub1;i++){
            grid[i][0] += grid[i-1][0];
        }
        for(int i=1;i<sub2;i++){
            grid[0][i] += grid[0][i-1];
        }
        for(int i=1;i<sub1;i++){
            for(int j=1;j<sub2;j++){
                int num = grid[i][j];
                grid[i][j] = Math.min(grid[i-1][j]+num,grid[i][j-1]+num);
            }
        }
        return grid[sub1-1][sub2-1];
        // write code here
    }
}

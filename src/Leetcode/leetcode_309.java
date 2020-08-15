package Leetcode;
/*
* 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

    你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。

示例:

输入: [1,2,3,0,2]
输出: 3
解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
*/
public class leetcode_309 {
    public static void main(String[] arg){
        int a[] = {1,2,3,0,2};
        maxProfit(a);
        System.out.println(maxProfit(a));

    }

  /*  public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int dp[][] = new int[prices.length][3];
    //两个维度 天数 和 0不持有 1持有 2 冷冻期
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;
        for(int i=1;i<prices.length;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][2]);

            dp[i][1] = Math.max(dp[i-1][0]-prices[i],dp[i-1][1]);

            dp[i][2] = dp[i-1][1]+prices[i];
        }
        return Math.max(dp[prices.length-1][0],dp[prices.length-1][2]);
    }*/

  public static int maxProfit(int[] prices){
      if(prices == null || prices.length == 0 ){
          return 0;
      }
      int dp[] = new int[prices.length+1];
      dp[0] = 0;
      dp[1] = 0;
      dp[2] = Math.max(0,prices[1]-prices[0]);
      for(int i=3;i<=prices.length;i++){
          int max = Integer.MIN_VALUE;
          for(int j=1;j<i;j++){
              if(j==1||j==2){
                  max = Math.max(max,prices[i-1]-prices[j-1]);
              }else {
                  max = Math.max(max,dp[j-2]+prices[i-1]-prices[j-1]);
              }
          }
          max = Math.max(max,dp[i-1]);
          dp[i] = max;
      }
      return dp[prices.length];
   }

}

package Leetcode;
class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

public class LeetCode_437 {
    int pathnumber;
    public static void main(String[] arg){

    }
    public int pathSum(TreeNode root, int sum) {
        if(root == null) return 0;
        Sum(root,sum);
        pathSum(root.left,sum);
        pathSum(root.right,sum);
        return pathnumber;
    }
    public void Sum(TreeNode root,int sum){
        if(root == null) return;
        sum-=root.val;
        if(sum == 0){
            pathnumber++;
        }
        Sum(root.left,sum);
        Sum(root.right,sum);
    }
}
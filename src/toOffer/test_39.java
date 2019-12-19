package toOffer;

public class test_39 {
    private static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }
    public static void main(String[] arg){

    }
    public boolean IsBalanced_Solution(TreeNode root) {
        return TreeDepth(root)!=-1;
    }
    public int TreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left=TreeDepth(root.left);
        if(left==-1){
            return -1;
        }
        int right=TreeDepth(root.right);
        if(right==-1){
            return -1;
        }
        return Math.abs(left-right)>1?-1:Math.max(left, right) + 1;
    }
}

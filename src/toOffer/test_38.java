package toOffer;
public class test_38 {
     public static class TreeNode {
     int val = 0;
     TreeNode left = null;
     TreeNode right = null;

     public TreeNode(int val) {
     this.val = val;
     }
     }

    public static void main(String[] arg){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.left = new TreeNode(6);
        root.left.right.left.right = new TreeNode(7);
        root.left.left.right = new TreeNode(8);
        System.out.println(TreeDepth(root));
    }

        public static int TreeDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return Math.max(TreeDepth(root.left) + 1, TreeDepth(root.right) + 1);
        }
}

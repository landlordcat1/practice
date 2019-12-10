package toOffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class test_24
{
    private static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }
        public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
           ArrayList<ArrayList<Integer>> a=new ArrayList<>();
           ArrayList<Integer> b=new ArrayList<>();
           InOrder(root,b,target,a);
           return a;
    }
    public void InOrder(TreeNode node,ArrayList<Integer> b, int target,ArrayList<ArrayList<Integer>> a){
        if(node==null)
            return;
        b.add(node.val);
        target-=node.val;
        if(target==0&&node.right==null&&node.left==null){
            a.add(new ArrayList<>(b));
        }
        InOrder(node.left,b,target,a);
        InOrder(node.right,b,target,a);
        b.remove(b.size()-1);
    }
    public static void main(String[] arg){
        TreeNode t1 = new TreeNode(10);
        t1.right = new TreeNode(5);
        t1.left = new TreeNode(12);
        t1.right.left = new TreeNode(4);
        t1.right.right = new TreeNode(7);
        t1.right.left.left = new TreeNode(3);
        System.out.println(new test_24().FindPath(t1, 22));
    }
}

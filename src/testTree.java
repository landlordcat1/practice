import java.util.*;

public class testTree
{
    private static int[] array={1,2,3,4,5,6,7,8,9};
    private static List<Node> nodeList=null;
    private static class Node{
        Node leftChild;
        Node rightChild;
        int data;
        int tag=0;
        Node(int newdata){
            leftChild=null;
            rightChild=null;
            data=newdata;
            tag=0;
        }
    }
    public static void CreatTree(){
        nodeList=new LinkedList<Node>();
        for(int nodeIndex=0;nodeIndex<array.length;nodeIndex++){
            nodeList.add(new Node(array[nodeIndex]));
        }
        for(int parentIndex=0;parentIndex<array.length/2;parentIndex++){
            nodeList.get(parentIndex).leftChild=nodeList.get(parentIndex*2+1);
            nodeList.get(parentIndex).rightChild=nodeList.get(parentIndex*2+2);
            int lastParentIndex=array.length/2-1;
            nodeList.get(lastParentIndex).leftChild=nodeList.get(parentIndex*2+1);
            if(array.length%2==1){
                nodeList.get(lastParentIndex).rightChild=nodeList.get(lastParentIndex*2+2);
            }
        }
    }
    private static void PreOrder(Node root){  //先序递归遍历
        if(root!=null){
            System.out.println(root.data);
            PreOrder(root.leftChild);
            PreOrder(root.rightChild);
        }
    }
    private static void InOrder(Node root){   //中序递归遍历
        if (root!=null){
            InOrder(root.leftChild);
            System.out.println(root.data);
            InOrder(root.rightChild);
        }
    }
    private static void PostOrder(Node root){   //后序递归遍历
        if (root!=null){
            PostOrder(root.leftChild);
            PostOrder(root.rightChild);
            System.out.println(root.data);
        }
    }
    private static void PreOrder1(Node root){     //先序非递归遍历
        Stack<Node> stack=new Stack<>();
        Node p=root;
        while (p!=null||!stack.empty()){
            while(p!=null){
                System.out.println(p.data);
                stack.push(p);
                p=p.leftChild;
            }
            if (!stack.empty()){
                p=stack.pop();
                p=p.rightChild;
            }
        }
    }
    private static void InOrder1(Node root) {
        Stack<Node> stack = new Stack<>();
        Node p = root;
        while (p != null || !stack.empty()) {
            while (p != null) {
                stack.push(p);
                p = p.leftChild;
            }
            if (!stack.empty()) {
                p = stack.pop();
                System.out.println(p.data);
                p = p.rightChild;
            }
        }
    }
    private static void PostOrder1(Node root) {
        Stack<Node> stack = new Stack<>();
        Node p = root;
        while (p != null || !stack.empty()) {
            while (p != null) {
                stack.push(p);
                p = p.leftChild;
            }
            if (!stack.empty()) {
               p=stack.peek();
               if(p!=null||p.tag==1){
                   p=stack.pop();
                   System.out.println(p.data);
                   p.tag=1;
                   p=null;
               }
               else p=p.rightChild;
            }
        }
    }
     private static void LevelOrder(Node root){
         Queue<Node> queue=new LinkedList<Node>() ;

         Node p=root;
         queue.add(p);
         while (!queue.isEmpty()){
             p=queue.remove();
             System.out.println(p.data);
             if(p.leftChild!=null)
                 queue.add(p.leftChild);
             if (p.rightChild!=null)
                 queue.add(p.rightChild);
         }

     }
//    public Node reConstructBinaryTree(int [] pre,int [] in) {
//        if(pre.length==0&&in.length==0)
//            return null;
//    }
    public static void main(String[] arg){
        CreatTree();
        Node root=nodeList.get(0);
//        PreOrder(root);
//        PreOrder1(root);
        PostOrder(root);
        PostOrder1(root);
        System.out.println("-------------");
        LevelOrder(root);
    }
}

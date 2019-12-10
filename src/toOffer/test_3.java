package toOffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class ListNode {
         int val;
         ListNode next = null;

         ListNode(int val) {
             this.val = val;
         }
     }
public class test_3 {

    public void main(String[] arg){
        ListNode N=new ListNode(2);
        printListFromTailToHead(N);
    }
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
          ArrayList<Integer> list=new ArrayList<>();
          ListNode p=listNode;
          ListNode h=listNode.next;
        ListNode temp=null;
        while (h!=null){
            temp=h.next;
            h.next=p;
            p=h;
            h=temp;
        }
        listNode.next=null;
        listNode=p;
        while(listNode!=null){
            list.add(listNode.val);
            listNode=listNode.next;
        }
        return list;
    }
}

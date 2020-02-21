package Leetcode;


import java.util.List;

//删除链表中重复的元素
//准备：
        //dummy节点，链表题基操
        //pre指针用于确定重复元素的最后一个位置
        //latter指针用于确定未重复元素的当前位置

public class leetcode_82 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public static void main(String[] arg){
    }
    public ListNode deleteDuplicates(ListNode head){
        if(head==null)
            return null;
        ListNode dummy=new ListNode(-1);
        dummy.next=head;
        ListNode pre=head;
        ListNode latter=dummy;
        while(pre!=null){
            if(pre.next!=null&&pre.val==pre.next.val){
                do{
                    pre=pre.next;
                }while(pre.next != null && pre.val == pre.next.val);
                latter.next=pre.next;
                pre=pre.next;
            }else {
                pre=pre.next;
                latter=latter.next;
            }

        }

        return head;
    }
}

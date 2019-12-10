[toc]
#### ArrayList和LinkedList的区别和联系
先来回顾一下List在Collection中的框架图：
![image](https://img-blog.csdn.net/20160413184734236?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)


从图中我们可以看出：

1. List是一个接口，他继承了与Collection接口，代表有序的队列。
2. AbstractList是一个抽象类，他继承与AbstractCollection。AbstractList实现了List接口中除了size(),get(int location)之外的方法。
3. AbstractSequentialList是一个抽象类，它继承与AbstrctList。AbstractSequentialList实现了“链表中，根据index索引值操作链表的全部方法”。
4. ArrayList、LinkedList、Vector和Stack是List的四个实现类，其中Vector是基于JDK1.0，虽然实现了同步，但是效率低，已经不用了，Stack继承与Vector，所以不再赘述。
5. LinkedList是个双向链表，它同样可以被当作栈、队列或双端队列来使用。

**我们知道了，通常情况下，ArrayList和LinkedList的区别有以下几点：**

1.ArrayList是实现了**基于动态数组**的数据结构，而LinkedList是**基于链表**的数据结构。
2.对于**随机访问get和set，ArrayList要优于LinkedList**,因为LinkedList要移动指针。
3.对于添加和删除操作add和remove，一般大家都会说LinkedList要比ArrayList快，因为ArrayList要移动数据。但是实际情况并不是这样。

**ArrayList想要get(int index)元素时，直接返回了index位置上的元素，而LinkedList需要通过for循环进行查找，虽然LinkedList已经在查找方式上做了优化，比如index<size/2，则从左边开始查找，反之从右边开始查找，但是还是比ArrayList要慢。**

**ArrayList想要在指定位置插入或者删除元素时，主要耗时的是System.arraycopy动作，会移动index后面的所有元素，LinkedList主要耗时的是要先通过for循环找到index，然后直接插入或删除。这就导致了两者并非一定谁快谁慢。**

**主要有两个因素决定了他们的效率，插入的数据量和插入的位置。**
**当数据量较小时，测试程序中，大约小于30的时候，两者效率差不多，没有显著区别；当数据量较大时，大约在容量的1/10处开始，LinkedList的效率就开始没有ArrayList效率高了，特别到一半以及后半的位置插入时，LinkedList效率明显要低于ArrayList，而且数据量越大，越明显。**
##### put

```
 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;//如果数组的长度为0，就会对它进行扩容
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);//如果table的在（n-1)&hash的值为空，就新建一个节点插入该位置
        else {//表示有冲突 开始处理冲突
            Node<K,V> e; K k;
            //检查第一个Node的值，p是不是要找的值
            //通过hash值和hashcode和equals的比较来判断是否相同，key值相同则覆盖
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)//判断如果p是TreeNode类型的就在红黑树中查找符合条件的结点并返回此节点
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {//如果都不是以上情况则就是向Node链表中添加结点
                for (int binCount = 0; ; ++binCount) {
                //指针为空就挂在后面
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //若结点数大于等于8
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }

```
##### hash

```
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

```
##### resize

```
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;//table赋予oldTab作为扩充前的table数据
        int oldCap = (oldTab == null) ? 0 : oldTab.length;//原来数组长度
        int oldThr = threshold;//原来数组的扩展长度
        int newCap, newThr = 0;
        //新的扩展长度和新的负载因子
        //正常的扩容状态
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
            //如果数组的长度大于极限长度，则不会再扩容
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)//如果新表大小小于数组极限大小并且 老表小于初始化大小
                newThr = oldThr << 1; // double threshold//数组长度扩大为原来的两倍
        }
          // 这里对应的就是带参数的构造的初始化
        else if (oldThr > 0) // initial capacity was placed in threshold//原来数组的扩展长度
            newCap = oldThr;//将传进来规定好的数组长度赋给新的数组长度
        else {               // zero initial threshold signifies using defaults
        // 若其他情况(无参构造的初始化,两个属性都为0,为初始化,是编译期自动加上的0)则将获取初始默认大小
            newCap = DEFAULT_INITIAL_CAPACITY;//新的初始化大小
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);//新扩容后的大小
        }
        if (newThr == 0) {//扩容后的数组的大小为0
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

```
##### 数组元素Node<K,V> 实现了Entry接口

```
//Node是单向链表，它实现了Map.Entry接口  
static class Node<k,v> implements Map.Entry<k,v> {  
    final int hash;  
    final K key;  
    V value;  
    Node<k,v> next;  
    //构造函数Hash值 键 值 下一个节点  
    Node(int hash, K key, V value, Node<k,v> next) {  
        this.hash = hash;  
        this.key = key;  
        this.value = value;  
        this.next = next;  
    }  
   
    public final K getKey()        { return key; }  
    public final V getValue()      { return value; }  
    public final String toString() { return key + = + value; }  //Map的toString方法
   
    public final int hashCode() {  
        return Objects.hashCode(key) ^ Objects.hashCode(value);  
    }  
   
    public final V setValue(V newValue) {  
        V oldValue = value;  
        value = newValue;  
        return oldValue;  
    }  
    //判断两个node是否相等,若key和value都相等，返回true。可以与自身比较为true  
    public final boolean equals(Object o) {  
        if (o == this)  
            return true;  
        if (o instanceof Map.Entry) {  
            Map.Entry<!--?,?--> e = (Map.Entry<!--?,?-->)o;  
            if (Objects.equals(key, e.getKey()) &&  
                Objects.equals(value, e.getValue()))  
                return true;  
        }  
        return false;  
    }
```


```
//这两个是限定值 当节点数大于8时会转为红黑树存储
static final int TREEIFY_THRESHOLD = 8;
//当节点数小于6时会转为单向链表存储
static final int UNTREEIFY_THRESHOLD = 6;
//红黑树最小长度为 64
static final int MIN_TREEIFY_CAPACITY = 64;
//HashMap容量初始大小
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
//HashMap容量极限
static final int MAXIMUM_CAPACITY = 1 << 30;
//负载因子默认大小
static final float DEFAULT_LOAD_FACTOR = 0.75f;
//Node是Map.Entry接口的实现类
//在此存储数据的Node数组容量是2次幂
//每一个Node本质都是一个单向链表
transient Node<K,V>[] table;
//HashMap大小,它代表HashMap保存的键值对的多少
transient int size;
//HashMap被改变的次数
transient int modCount;
//下一次HashMap扩容时被占用容量的大小
int threshold;
//存储负载因子的常量
final float loadFactor
//默认的构造函数
public HashMap() {
    this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
}
//指定容量大小
public HashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);DEFAULT_LOAD_FACTOR
}
//指定容量大小和负载因子大小
public HashMap(int initialCapacity, float loadFactor) {
    //指定的容量大小不可以小于0,否则将抛出IllegalArgumentException异常
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    //判定指定的容量大小是否大于HashMap的容量极限
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    //指定的负载因子不可以小于0或为Null，若判定成立则抛出IllegalArgumentException异常
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);

    this.loadFactor = loadFactor;
    // 设置“HashMap阈值”，当HashMap中存储数据的数量达到threshold时，就需要将HashMap的容量加倍。
    // 也就是说,HashMap在自定义初始容量时,会将下一次扩容时的值设置为容量的下一个2的幂数
    // 本身是没有立即分配HashMap容量的,在下一次插入时,经判断需要进行扩容,那么扩容到这个threshold
    // 从下一次开始,每次threshold都为loadFactor * 
    this.threshold = tableSizeFor(initialCapacity);
}
//传入一个Map集合,将Map集合中元素Map.Entry全部添加进HashMap实例中
public HashMap(Map<? extends K, ? extends V> m) {
    this.loadFactor = DEFAULT_LOAD_FACTOR;
    //此构造方法主要实现了Map.putAll()
    putMapEntries(m, false);
}
```
**HashMap的底层是数组＋链表的形式，实现了Map接口。**
**put操作：**

**1、先判断数组长度是否是0，如果是就对它进行扩容**

**2、先判断是否有hash冲突，如果没有hash冲突，就在此处new一个新的节点，如果有hash冲突，则开始处理冲突。**

（1）先判断第一个Node的值是否需要被覆盖（通过hashcode和equals判断是否相等），key值相等则会被覆盖。

（2）判断是否是TreeNode类型，如果p是TreeNode类型的就在红黑树中查找符合条件的结点并返回此结点。

（3）如果都不是以上情况就是向Node中添加结点，指针为空就挂在后面，若结点数大于8，就会转换成红黑树。

**3、如果结点存在就替换掉旧的value**

**4、如果数组满了就要扩容**

**get函数**

**1、先判断第一个结点是否是需要找的结点。**

**2、如果不是，就去树中找。**

**3、树中没有，就去链表中get**

**hash函数的实现**

**高16位不变，低16位和高16位做了一个异或。**

**resize函数**

**1、先判断是否超过最大值，超过最大值就不再扩充了**

**2、没有超过最大值，就扩充为原来的两倍。**

**3、计算新的resize上限**

**4、把每个值都移动到新的扩容的数组中去**
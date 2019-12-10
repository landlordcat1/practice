##### 继承结构与层次关系
###### ArrayList的继承结构：

```
             　ArrayList extends AbstractList
　　　　　　　AbstractList extends AbstractCollection
```
#### 分析：
##### 1）为什么要先继承AbstractList，而让ArrayList先实现List<E>？而不是让ArrayList直接实现List<E>?
##### 接口中全都是抽象的方法，而抽象类中可以有抽象方法，还可以有具体的实现方法。正是利用了这一点，让AbstractList是实现接口中一些通用的方法，而具体的类 如Array List就继承了这个类，拿到了一些通用的方法，然后再实现一些自己特有的方法。这样一来，让代码更简洁，就继承结构最底层的类中通用的方法都抽离出来。
##### 2）ArrayList实现了哪些接口？
##### List<E>接口，RandomAccess接口，　Cloneable接口，Serializable接口
#### 类中的属性

```
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    // 版本号
    private static final long serialVersionUID = 8683452581122892189L;
    // 缺省容量
    private static final int DEFAULT_CAPACITY = 10;
    // 空对象数组
    private static final Object[] EMPTY_ELEMENTDATA = {};
    // 缺省空对象数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    // 元素数组
    transient Object[] elementData;
    // 实际元素大小，默认为0
    private int size;
    // 最大数组容量
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
}
```
#### 构造方法
##### ArrayList里有三个构造方法：

```
ArrayList();
ArrayList(int);
ArrayList(Collection<? extends E>);
```
##### 无参构造方法

```
/**
    * Constructs an empty list with an initial capacity of ten.　　这里就说明了默认会给10的大小，所以说一开始arrayList的容量是10.
    */
　　　　//ArrayList中储存数据的其实就是一个数组，这个数组就是elementData，在123行定义的 private transient Object[] elementData;
　　 public ArrayList() {　　
        super();        //调用父类中的无参构造方法，父类中的是个空的构造方法
        this.elementData = EMPTY_ELEMENTDATA;//EMPTY_ELEMENTDATA：是个空的Object[]， 将elementData初始化，elementData也是个Object[]类型。空的Object[]会给默认大小10，等会会解释什么时候赋值的。
    }
```
##### 有参构造函数

```
/**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ArrayList(int initialCapacity) {
        super(); //父类中空的构造方法
        if (initialCapacity < 0)    //判断如果自定义大小的容量小于0，则报下面这个非法数据异常
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        this.elementData = new Object[initialCapacity]; //将自定义的容量大小当成初始化elementData的大小
    }
```
##### 有参构造方法（2）

```
//这个构造方法不常用，举个例子就能明白什么意思
    /*
        Strudent exends Person
         ArrayList<Person>、 Person这里就是泛型
        我还有一个Collection<Student>、由于这个Student继承了Person，那么根据这个构造方法，我就可以把这个Collection<Student>转换为ArrayList<Sudent>这就是这个构造方法的作用 
    */
     public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();    //转换为数组
        size = elementData.length;   //数组中的数据个数
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class) //每个集合的toarray()的实现方法不一样，所以需要判断一下，如果不是Object[].class类型，那么久需要使用ArrayList中的方法去改造一下。
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    }
```
#### 核心方法
#### add()方法(有四个)
- ###### add(E):boolean
- ###### add(int,E):void
- ###### add(int,Collection<?extends E>):boolean
- ###### add(Collection<? extends E>):boolean
##### 1.boolean add(E);//直接在末尾添加元素

```
/**
     * Appends the specified element to the end of this list.添加一个特定的元素到list的末尾。
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public boolean add(E e) {    
    //确定内部容量是否够了，size是数组中数据的个数，因为要添加一个元素，所以size+1，先判断size+1的这个个数数组能否放得下，就在这个方法中去判断是否数组.length是否够用了。
        ensureCapacityInternal(size + 1);  // Increments modCount!!
     //在数据中正确的位置上放上元素e，并且size++
        elementData[size++] = e;
        return true;
    }
```
##### ensureCapacityInternal(×××);确定内部容量的方法

```
private void ensureCapacityInternal(int minCapacity) {
        if (elementData == EMPTY_ELEMENTDATA) { //看，判断初始化的elementData是不是空的数组，也就是没有长度
    //因为如果是空的话，minCapacity=size+1；其实就是等于1，空的数组没有长度就存放不了，所以就将minCapacity变成10，也就是默认大小，但是带这里，还没有真正的初始化这个elementData的大小。
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
    //确认实际的容量，上面只是将minCapacity=10，这个方法就是真正的判断elementData是否够用
        ensureExplicitCapacity(minCapacity);
    }
```
##### ensureExplicitCapacity(×××)

```
  private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)//如果实际需要的容量大于数组的长度，就会扩容
            grow(minCapacity);
    }
```
##### grow(XXX)扩容数组

```
private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;  //将扩充前的elementData大小给oldCapacity
        int newCapacity = oldCapacity + (oldCapacity >> 1);//newCapacity就是1.5倍的oldCapacity
        if (newCapacity - minCapacity < 0)//这句话就是适应于elementData就空数组的时候，length=0，那么oldCapacity=0，newCapacity=0，所以这个判断成立，在这里就是真正的初始化elementData的大小了，就是为10.前面的工作都是准备工作。
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)//如果newCapacity超过了最大的容量限制，就调用hugeCapacity，也就是将能给的最大值给newCapacity
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
    //新的容量大小已经确定好了，就copy数组，改变容量大小
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```
##### 2.void add(int,E);在特定位置添加元素，也就是插入元素

```
  public void add(int index, E element) {
        rangeCheckForAdd(index);//检查index插入的位置是否合理

        ensureCapacityInternal(size + 1);  // Increments modCount!!//判断插入元素之后会不会越界
        System.arraycopy(elementData, index, elementData, index + 1,//插入元素之后，将所有元素都后移一位
                         size - index);
        elementData[index] = element;//将元素插入目标位置
        size++;
    }
```
##### rangeCheckForAdd(index)

```
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)   //插入的位置肯定不能大于size 和小于0
//如果是，就报这个越界异常
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
```
#### 删除方法
- ###### remove(int):E
- ###### remove(Object):boolean
- ###### removeAll(Collection<?>):boolean
- ###### removelf(Predicate<? super E>):boolean
- ###### removeRange(int ,int):void 
- ###### clear():void
- ###### fastRemove(int):void 
##### 1)remove(int):通过删除指定位置上的元素

```
public E remove(int index) {
        rangeCheck(index);//检查index的合理性

        modCount++;//这个作用很多，比如用来检测快速失败的一种标志。
        E oldValue = elementData(index);//通过索引直接找到该元素
        int numMoved = size - index - 1;//计算要移动的位数。
        if (numMoved > 0)
//这个方法也已经解释过了，就是用来移动元素的。
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
//将--size上的位置赋值为null，让gc(垃圾回收机制)更快的回收它。
        elementData[--size] = null; // clear to let GC do its work
//返回删除的元素。
        return oldValue;
    }
```
##### 2)remove(Object):这个方法可以看出来，ArrayList是可以存放null值得。

```
//感觉这个不怎么要分析吧，都看得懂，就是通过元素来删除该元素，就依次遍历，如果有这个元素，就将该元素的索引传给fastRemobe(index)，使用这个方法来删除该元素，
//fastRemove(index)方法的内部跟remove(index)的实现几乎一样，这里最主要是知道arrayList可以存储null值
     public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
```
##### 3)clear();将elementData中每个元素都赋值为null，等待这个垃圾回收将这个给回收掉，所以叫clear

```
public void clear() {
        modCount++;

        // clear to let GC do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }
```
##### 4）removeAll(collection c)

```
   public boolean removeAll(Collection<?> c) {
         return batchRemove(c, false);//批量删除
     }
```
##### 5)batchRemove(xx,xx)用于两个方法，一个removeAll():它只清楚指定集合中的元素,retainAll()用来测试两个集合是否有交集。

```
//这个方法，用于两处地方，如果complement为false，则用于removeAll如果为true，则给retainAll()用，retainAll（）是用来检测两个集合是否有交集的。
   private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData; //将原集合，记名为A
        int r = 0, w = 0;   //r用来控制循环，w是记录有多少个交集
        boolean modified = false;  
        try {
            for (; r < size; r++)
//参数中的集合C一次检测集合A中的元素是否有，
                if (c.contains(elementData[r]) == complement)
//有的话，就给集合A
                    elementData[w++] = elementData[r];
        } finally {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
//如果contains方法使用过程报异常
            if (r != size) {
//将剩下的元素都赋值给集合A，
                System.arraycopy(elementData, r,
                                 elementData, w,
                                 size - r);
                w += size - r;
            }
            if (w != size) {
//这里有两个用途，在removeAll()时，w一直为0，就直接跟clear一样，全是为null。
//retainAll()：没有一个交集返回true，有交集但不全交也返回true，而两个集合相等的时候，返回false，所以不能根据返回值来确认两个集合是否有交集，而是通过原集合的大小是否发生改变来判断，如果原集合中还有元素，则代表有交集，而元集合没有元素了，说明两个集合没有交集。
                // clear to let GC do its work
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }
```
#### set方法

```
public E set(int index, E element) {
        // 检验索引是否合法
        rangeCheck(index);
        // 旧值
        E oldValue = elementData(index);
        // 赋新值
        elementData[index] = element;
        // 返回旧值
        return oldValue;
    }
```
#### indexOf()方法 

```
// 从首开始查找数组里面是否存在指定元素
    public int indexOf(Object o) {
        if (o == null) { // 查找的元素为空
            for (int i = 0; i < size; i++) // 遍历数组，找到第一个为空的元素，返回下标
                if (elementData[i]==null)
                    return i;
        } else { // 查找的元素不为空
            for (int i = 0; i < size; i++) // 遍历数组，找到第一个和指定元素相等的元素，返回下标
                if (o.equals(elementData[i]))
                    return i;
        } 
        // 没有找到，返回空
        return -1;
    }
```
#### get方法

```
public E get(int index) {
        // 检验索引是否合法
        rangeCheck(index);

        return elementData(index);
    }
```
#### 
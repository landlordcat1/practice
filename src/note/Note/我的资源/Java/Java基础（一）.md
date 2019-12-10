[toc]
#### Java Object类方法
##### 需要重写
###### 基本描述
**(1)Object类位于Java.lang包中，JAVA.lang包中包含了JAVA最基础和核心的类，在编译时会自动导入；**

**(2)Object类是所有类的祖先。每个类都使用Object作为超类.所有对象(包括数组)都实现这个类中的方法。可以使用基本类型来指定任意类型的对象。**
****
###### Object的主要方法介绍
**hashcode()方法：(需要重写)**

hash值：JAVA的hashCode方法就是根据一定的规则将与对象相关的信息(比如对象的存储地址，对象的字段等)映射成一个数值，这个数值成为散列值。

**重写hashcode()方法的基本规则：**

- **在程序运行过程中，同一个对象多次调用hashcode()方法应该返回相同的值。**
- **当两个对象通过equals()方法比较返回true时，则两个对象的hashcode()方法返回相同的值。**
- **对象用作equals()方法比较标准的Field，都应该用来计算hashcode值。**


**equals()方法(需要重写)：比较两个对象是否相等**
```
public boolean equals(Object obj) {
       return (this == obj);
}　
```
**我们知道所有的对象都拥有标识(内存地址)和状态(数据)，同时“==”比较的两个对象的内存地址，所以说使用Object的equals()方法可以满足我们的一些基本要求，但我们必须清楚我们大部分时间都是进行两个对象的比较，这个时候Object的equals()方法就不可以了，实际上JDK中，String、Math等封装类都对equals()方法进行了重写。**


```
protected native Object clone() throws CloneNotSupportedException;
```

**toString()方法(需要重写)**

**返回String类型，是将对象以字符串的形式输出**
```
public String toString(){
}

```
##### 类加载相关

**Class getClass()**
加载-方法区-字节码对象

加载工具：类加载器ClassLoader(反射)

==加载的触发条件：==
- 实例化对象----new
- 静态方法/静态变量
- 使用子类时，父类需要加载
- Class.forName("类名"(-----java.lang.String包中))-----手动加载
- String.class-->获得类的字节码对象。

**Object clone():浅克隆-复制**

浅克隆：clone->复制引用
深克隆：对象的读写->复制对象
若要使用clone方法，则要求对象是可以克隆的，没有任何方法/属性，不常用。

**void finalize()-GC----垃圾回收**

内存泄漏：内存被无效对象占用

- GC：在程序运行时开始启动，不定时回收所标记的垃圾，也可以手动调用GC->System.gc();
- 没有引用的对象会被标记为垃圾，故原则上，JAVA中是不存在内存泄漏的。
- finalize()在对象回收时被调用。
##### 线程相关(5种)
**wait()x3**

```

    public final native void notify();  

      

    ublic final native void notifyAll();  

      

    public final native void wait(long timeout) throws InterruptedException;  

      

    ublic final void wait() throws InterruptedException {  

           wait(0);  

       }  

      

    public final void wait(long timeout, int nanos) throws InterruptedException {  

           if (timeout < 0) {  

               throw new IllegalArgumentException("timeout value is negative");  

           }  

      

           if (nanos < 0 || nanos > 999999) {  

               throw new IllegalArgumentException(  

                                   "nanosecond timeout value out of range");  

           }  

      

           if (nanos > 0) {  

               timeout++;  

           }  

      

           wait(timeout);  

       }  

```
**wait():如果对象调用了该方法，就会使持有该对象的进程把对象的控制器交出去，然后处于等待状态。**

**notify():如果对象调用了该方法，就会随机通知某个正在等待该对象控制器的线程可以继续运行**

**notifyAll():如果对象调用了该方法，就会通知所有正在等待该对象控制器的线程可以继续运行。** 

#### HashCode作用，如何重载hashcode方法
**HashCode：提高查找效率。**

**如何重载hashcode方法？**

**重写Object的equals(object obj)方法尽量要重写hashcode()方法**

**原因：equals方法本来就是为了确定对象的唯一性，所以在重写equals值得时候也要重写hashcode方法，保证对象的唯一性。**

#### JAVA泛型
“泛型”的意思就是泛指的类型(参数化类型)。
##### Java泛型介绍
###### Java泛型类
类结构是面向对象中最基本的元素，如果我们的类需要很好的扩展性，那么我们可以将其设置成泛型的。假设我们需要一个数据的包装类，通过传入不同类型的数据，可以存储相应类型的数据。
###### Java泛型方法
泛型方法既可以存在于泛型类中，也可以存在于普通的类中。如果使用泛型方法可以解决问题，那么应该尽量使用泛型方法。
泛型方法的基本特征：
- public与返回值中间非常重要，可以理解为声明此方法为泛型方法。
- 只有声明了的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
- 表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T.
- 与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
###### 泛型接口
泛型接口与泛型类的定义及使用基本相同。

```

//定义一个泛型接口

public interface Generator<T> {

    public T next();

}

```
当实现泛型接口的类，未传入泛型实参时：

```

/**

 * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中

 * 即：class FruitGenerator<T> implements Generator<T>{

 * 如果不声明泛型，如：class FruitGenerator implements Generator<T>，编译器会报错："Unknown class"

 */

class FruitGenerator<T> implements Generator<T>{

    @Override

    public T next() {

        return null;

    }

}

```
当实现泛型接口的类传入泛型实参时：

```

/**

 * 传入泛型实参时：

 * 定义一个生产器实现这个接口,虽然我们只创建了一个泛型接口Generator<T>

 * 但是我们可以为T传入无数个实参，形成无数种类型的Generator接口。

 * 在实现类实现泛型接口时，如已将泛型类型传入实参类型，则所有使用泛型的地方都要替换成传入的实参类型

 * 即：Generator<T>，public T next();中的的T都要替换成传入的String类型。

 */

public class FruitGenerator implements Generator<String> {

 

    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

 

    @Override

    public String next() {

        Random rand = new Random();

        return fruits[rand.nextInt(3)];

    }

}

```
###### 泛型通配符
我们知道Ingeter是Number的一个子类，同时在特性章节中我们也验证过Generic<Ingeter>与Generic<Number>实际上是相同的一种基本类型。同一泛型可以对应多个版本(因为参数类型时不确定的)，不同版本的泛型类实例时不兼容的。
```

public void showKeyValue1(Generic<Number> obj){

    Log.d("泛型测试","key value is " + obj.getKey());

}

Generic<Integer> gInteger = new Generic<Integer>(123);

Generic<Number> gNumber = new Generic<Number>(456);

 

showKeyValue(gNumber);

 

// showKeyValue这个方法编译器会为我们报错：Generic<java.lang.Integer> 

// cannot be applied to Generic<java.lang.Number>

// showKeyValue(gInteger);

```
我们将方法改为

```

public void showKeyValue1(Generic<?> obj){

    Log.d("泛型测试","key value is " + obj.getKey());

}

```
**此处"？"是类型实参，而不是类型形参。**
#### GC机制
##### 什么是GC？
Java虚拟机的垃圾回收机制
##### 什么时候发生GC？
- 宏观上看：GC发生的时间在整个Java程序的声明周期上是不可预估的，是由系统本身所决定，或者程序调用System.GC();
- - 微观上看：(分代回收)现在的虚拟机大多数都采用分代收集的算法，所谓"分代收集”就是 根据对象的存活对象周期的不同，讲对象划分为几块，一般将JAVA堆分为老生代和新生代，并根据各个年代的特点采用不同的、最优的回收策略，通常有minor GC和full GC。
- - (回收策略) minor GC：使用的是复制算法，针对新生代进行GC。
1. 由于JAVA中的大部分对象通常都是朝生夕灭的，新生代是GC垃圾回收最为频繁的一个区域，新生代分为Eden区和两个Survior区，其内存大小比例是8:1:1，绝大数新产生的对象会存储在新生代的Eden，大对象的话会直接放在老年代。
2. 每当Eden产生对象时，都会检查Eden区是否可以将这个对象存下，可以存下则存，如果不可以存下则会发生一次minor GC.将Eden区和一个survior from区所有活的对象复制到survior to区，随后回收Eden区和Survior区，回收后再存储对象。
3. 但如果是极端情况下，经过minor GC后还是无法放下这个对象，JVM就根据空间分配担保机制，如果允许担保就将Survior无法容纳的对象直接放入老年代。这个时候如果老年代还是分配不下就触发一次full GC,如果还是不行就会抛出OOM异常.
4. 新生代每经一次minor GC,对象的年龄就会加一，当对象的年龄大于一定的年龄，就会将这个对象移入老年代(默认是15岁，也可以通过参数设置)。但并不是永远要等到对象年龄大于指定年龄才把对象放入老年代，JVM会有一个**动态年龄对象判定**机制，如果在survior空间中相同年龄的所有对象的总和大于Survior空间的一半时，就将年龄大于等于该相同年龄的对象放入老年代。
5. 比较大的对象会直接放入老年代。
- - (回收策略) full gc:发生在老年代，使用的是标记算法。
1. 老年代的对象一般都是由新生代直接分配或是由大对象的直接分配。
2. 在升到老年代的对象大于老年代的剩余空间时发生full GC，对老年代进行清理，清理后再将新生代的对象升入老年代，如果这时老年代的空间还是不足以匛老年代的对象，就会触发OOM异常。
3. 或者由于空间分配担保机制强制full gc。在minor GC之前检查到minor GC不安全，而且虚拟机加不允许垃圾回收器冒险进行minor GC，这时就会触发一次full GC，尽量在老年代腾出足够大的空间，以防minor GC出现风险。
4. 如果为了担保而发生full Gc过后，还是无法腾出足够大的空间进行担保，那么只好在发起一次full Gc。但是为了避免频繁full gc,大多数情况还是将 HandlePromotionFailure参数打开，允许GC冒险的进行minor GC。
5. full GC的过程是扫描出存活对象，然后进行回收未标记的对象，回收后对空出的空间进行合并，要么标记下来用于下次进行分配，总之就是减少内存碎片带来的效率的损耗，如果发生过full GC之后还是无法存放对象，则将抛出OOM 异常。
- **（回收策略，垃圾回收算法）有标记-清除法、复制算法和标记整理法。**
1. **标记清除法：** 首先它先标记出所有需要回收的对象，标记完成后**同一将所有对象进行回收**。
**优缺点：（1）** 一个是清除后会产生大量不连续的空间，将**内存碎片化。** 如果要存储一个大的对象，可能虽然有很多未使用的空间，但没有那么大的连续空间，还是无法存储，发生OOM。**（2）** 第二个是其标记和清除的效率很低，GC时发生 GC停顿，再使用低效率的标记清除法对程序的影响还是挺大的。

2.**复制算法：将内存划分为两个等大的空间**，每次存活的对象整齐复制到另一空间，并将已使用的空间全部清理。**优缺点：** 内存分配就没有内存的碎片化问题，但是需要浪费一半的内存空间。

3.**标记整理法：** 和标记清除法相类似，但是不是对可回收对象进行清理，而是将内存的对象统一移动到内存的一端，然后清理到边界外的内存既可。
优缺点:较标记清除法不会出现内存的碎片化，但是随之带来的是每次GC都要多次执行复制操作，效率会很低。

**（空间分配担保机制）**
**1.** 新生代分配对象在eden区，当存储不下时会触发Minor gc ,清理Eden区和Survivor from去，并把存活对象转义到Survivor to区，但是经过Minor GC后，Survivor to区还是不足以存下存活的对象，这时候就会将存储不下的对象担保金老年代进行存放，但是如果老年代也存储不下，就会存在Minor GC 的风险。

**2.** 空间分配担保就是 在Minor GC 之前检查 是否存在这种风险，如果不存在则正常Minor GC ,如果存在 则根据 HandlePromotionFailure 的设置，询问JVM允不允许冒险的进行 Minor GC，如果允许则冒险进行 Minor GC，如果不允许冒险，则会将老年代进行 full gc ，在老年代腾出足够大的空间保证Minor GC 是安全的。

**3.** 但是如果为了Minor GC的安全性进行了Full GC，Full GC过后还是无法保证安全性，就会再次出发Full GC，直至保证Minor Gc安全才停止，这样会导致 Full GC的频繁进行，使系统的效率会很低，在一般情况下会打开HandlePromotionFailure 参数，允许风险的Minor gc进行，防止full gc 频繁进行。

**4.** 那么如何判断Minor gc是否存在风险呢？因为风险就是系统不确定如果Minor gc 后新生代还未分配的对象是否能都在老年代存储，这时只需要比较 老年代最大可用的连续空间 和 新生代所有对象的总空间即可，如果老年代最大连续空间还是较大的，说明即使新生代分配不下，在老年代还是可以将新生代的所有对象转移到老年代为新生对象腾出空间。
#### 对什么东西进行GC？
##### 引用计数法
##### 可达性分析法
##### GC-roots:
1.虚拟机栈中引用的对象（虚拟机栈中包含很对栈帧，而还存在的栈帧一定是现在还要使用的，不然会出栈，所以从虚拟机栈中的对象作为GC Roots可达的对象都是有用的对象）

2.方法区的类静态属性引用的变量

3.方法区中常量引用的对象

4.本地方法栈中 （Native方法）引用的对象

##### finalize
- ##### 对象的自我拯救：
GC标记之后，清理之前会先判断对象是否重写了finalize（）方法，如果重写了将执行对象的finalize()方法，可在该方法中将这个对象重新引用给GC roots对象实现这个对象的自我救赎。如果该类没有重写该方法，或者重写了但是没有赋给可达对象，这个对象也是会被认为是垃圾对象。而且对象的该方法只会执行一次，如果第二次这个对象被标记为垃圾对象，将不再执行该方法，就会被回收。

**直接内存的垃圾回收：** 对于Java的直接内存，是独立于堆内存之外的内存区域，它不由JVM进行管理，而由操作系统来进行管理，但是JVM在Full GC时也会对其进行垃圾回收。但是它不会像新生代和老年代一样，当内存满了会告诉JVM要进行垃圾回收，直接内存只能是跟着堆空间的Full gc顺带着一起垃圾回收，所有在很多时候直接内存会满而没办法及时的回收。解决办法只能是当它抛出异常时，及时catch到，然后立马调用System.gc（）;通知虚拟机进行全局的垃圾回收
。
##### 强引用、弱引用、软引用、虚引用
- 强引用: 就代码中普通的引用，只要强引用存在，这个对象就不会被回收；
- 软引用： 一些引用但非必须的对象。系统在内存发出溢出之前就会将其回收；（SoftReference类）；
- 弱引用：非必须的对象，只能生存到下一次GC;（WeakReference类）；
- 虚引用：虚引用有一个很重要的用途就是用来做堆外内存的释放（PhantomReference）
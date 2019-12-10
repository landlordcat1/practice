[toc]
#### 初识ViewRoot和DecorView
ViewRoot对应于ViewRootImpl类，它是连接WindowManager和DecorView的纽带。View的三大流程均是由ViewRoot完成的。在ActivityThread中，当Activity对象被创建完毕后，会将DecorView添加到Window中，同时会创建ViewRootImpl对象，并将ViewRootImpl对象和DecorView建立关联

View的绘制流程是从ViewRoot的performTraversals方法开始的，它经过measure、layout和draw三个过程才能最终将一个View绘制出来，其中measure==用来测量View的宽和高==，layout用来==确定父容器中的位置==，而draw则==负责将View绘制在屏幕上==。

performTraversals的大致流程
![image](http://i.caigoubao.cc/625949/TIM%E5%9B%BE%E7%89%87201909252sw01824.jpg)

如图所示：
performTravelsals会依次调用peformMeasure、performLayout和performDraw三个方法，这三个分别完成顶级View的measure、layout和draw这三大流程，其中在performMeasure中会调用measure方法，在measure方法中调用onMeasure方法，在onMeasure方法中则会对所有的子元素进行measure过程，这个时候measure流程就从父容器传递到子元素中，这样就完成了依次measure过程。接着子元素会重复父容器的measure过程，如此反复就完成了整个View树的遍历

measure过程决定了View的宽和高，Measure完成以后，可以通过getMeasuredWidth和getMeasureHeight方法来获取到View测量后的宽个高，在几乎所有的情况下它都等同于View最终的宽和高。

Layout则可以确定view的位置。

Draw过程则决定了view的显示。

#### MeasureSpec

##### MeasureSpec
MeasureSpec代表一个32位的int值，高2位代表SpecMode,指的是测量模式，低30位代表SpecSize，而SpecSize指的是某种测量模式下的规格大小。

MeasureSpec通过将SpecMode和SpecSize打包成一个int值来避免过多的对象内存分配，为了方便操作，提供了打包和解包的方法。

SpecMode有三类，每一类都代表特殊的含义，如下所示：

###### UNSPECIFIED
父容器不对View有任何限制，要多大给多大，这一情况一般用于徐彤内部，表示一种测量的状态。
###### EXACTLY
父容器已经检测出View所需要的精确大小，这个时候View的最终大小就是SpecSize所指定的值。

###### AT_MOST

父容器制定了一个可用大小即SpecSize，View的大小不能大于这个值，具体是什么值要看不同View的具体实现。

##### MeasureSpec和LayoutParams的对应关系
系统内部是通过MeasureSpec来进行View的测量，但是正常情况下我们使用View指定MeasureSpec，尽管如此，但是我们可以给View设置LayoutParams.在View测量的时候，系统会将LayoutParams在父容器的约束下转换成对应的MeasureSpec，然后根据这个MeasureSpec来确定View测量后的宽和高。注意：MeasureSpec并不是只由LayoutSpec来决定，是由LayoutParams和父容器一起才能决定View的MeasureSpec。

对于DecorView来讲，其MeasureSpec由窗口的尺寸和自身的LayoutParams来共同决定。对于普通的DecorView，其MeasureSpec由父容器的Measure和自身的LayoutParams来共同决定。
#### View的工作流程

##### measure
###### View的measure过程
View的measure由其measure方法来完成，measure方法是一个final类型的方法，子类不能重写measure方法，在Meassure方法中只会调用View的onMeasure方法，因此只需要看onMeasure的实现既可。View的onMeasure方法如下所示，

```
protected void onMeasure(int WidthMeasureSpec,int heightMeasureSpec){
    setMeasuredDimension(getDefalutSize(getSuggestedMinimumWidth(),widthMeasureSpec),getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec));
}

```

setMeasuredDimension方法设置View的宽/高的测量值，因此我们只需要看getDefaultSize这个方法既可。这个方法就是根据MeasureSpec来确定View的长度和高度。


如果View没有设置背景，那么View的宽度为mMinWidth,而android:minWidth这个属性所指定的值，因此View的宽度即为androidWidth属性所属的值。

如果View设置了背景，则View的宽度为max（MMinWidth，mBackground.getMinimumWidth()).前者代表的方法我们已经知道了，后者代表背景的最小宽度。

==直接继承View的自定义控件需要重写onMeasure方法并设置wrap_content时的自身大小，否则在布局中使用wrap_content就相当于使用match_partent.==



###### ViewGroup的measure过程

对于ViewGroup来说，除了完成自己的measure过程以外，还会遍历所有子元素的遍历方法，各个子元素再去递归执行这个过程。和View不同的是，ViewGroup是一个抽象类，因此他没有重写View的onMeasure方法，但是他提供了一个叫做messureChild的方法。它的思想是取出子元素的LayoutParams,然后再通过getChildMeasureSpec来创建子元素的MeasureSpec


如果我们再Activity已启动的时候就做一件任务，但是这一件任务是需要获取某个View的宽/高，存在的问题是，View的measure过程和Activity的生命周期方法不是同步在执行的。那么将有四种方法来解决这个问题
①  Activity/View#onWindowFocusChanged.
②  view.post(runnable).
③  ViewTreeObserver。
④  view.measure(int widthMeasurespec,int heightMeasureApec)
##### layout过程
layout方法是来确定view本身的位置，而onlayout方法是用来确定每一个子元素的位置，layout的方法的大致流程如下：首先会通过setFrame方法啊来设定View的四个顶点的位置，即初始化mleft、mRight、mTop和mBottom这四个值，View的四个顶点一旦确定，那么View在父容器中的位置也确定了；接着会调用onLayout方法，这个方法的用途是父元素确定子元素的位置。
 
##### draw过程
View的绘制流程分为以下几步：
1）绘制背景 background.draw(canvas)
2）绘制自己(onDraw).
3）绘制children(dispatchDraw).
4) 绘制装饰(onDrawScrollBars)


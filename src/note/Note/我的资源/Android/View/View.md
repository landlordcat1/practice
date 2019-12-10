[toc]
### View
#### View基础知识
**<font size=2>View是Android所有控件的基类，不论是button还是TextView还是复杂的RelativeLayout和ListView，他们的共同基类都是View，在安卓的设计中，viewgroup也继承了view。<font>**


**<font size=2>View的主要位置主要由四个顶点来确定，分别对应view 的四个属性：top、left、right、bottom，分别是左上角横坐标，左上角纵坐标，右上角横坐标和右上角纵坐标。<font>**


**<font size=3 color=gray>MotionEvent和TouchSlop<font>**


**<font size=2 color=black>MontionEvent**


**<font size=2>在手指接触屏幕后产生的一系列事情之后，典型的事件类型有以下几种：①ACTION_DOWN手指刚接触屏幕e②ACYION_MOVE手指在屏幕上移动③ACTION_UP手指从屏幕上松开的一瞬间<font>**

**<font size=2 color=black>TouchSlop**

**<font size=2>Toushslop是指系统所能识别出的最小滑动距离，当手指在屏幕上滑动时，如果距离小于这个常量，那么系统将不会认为你进行了滑动操作。**

**<font size=3> VelocityTracker、GestureDetector和Scoller**

**<font size=2> VelocityTracker是指速度追踪，用于追踪手指在滑动过程中的速度，包括水平和数值方向的速度**

```

 @Override

    public boolean onTouchEvent(MotionEvent event) {

        /****************速度追踪器*******************/

        VelocityTracker velocityTracker = VelocityTracker.obtain();

        //监听移动时间

        velocityTracker.addMovement(event);

        //设置时间间隔为1秒

        velocityTracker.computeCurrentVelocity(1000);

 

        //速度=（终点位置-起点位置）/时间段,注意当从右往左滑动时速度会出现负数

 

        //横行的每秒滑动的速度

        int xVelocity = (int) velocityTracker.getXVelocity();

        //竖向每秒滑动的速度

        int yVelocity = (int) velocityTracker.getYVelocity();

        System.out.println("X每秒滑动的速度:" + xVelocity);

        System.out.println("Y每秒滑动的速度:" + yVelocity);

 

        //不需要的时候重置并且回收内存

        velocityTracker.clear();

        velocityTracker.recycle();

        return true;

    }

```
**<font size=3>GestureDetector**

**<font size=2>手势检测，用于辅助检测用户的单机、滑动、长按、双击等行为。要使用GestureDetector如下过程:**

```

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener ,GestureDetector.OnDoubleTapListener{

 

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

 

    @Override

    public boolean onTouchEvent(MotionEvent event) {

 

        //创建一个GestureDetector（手势检测）对象,并且实现OnGestureListener接口（还可以实现OnDoubleTapListener从而实现对双击的监听）

        GestureDetector mGestureDetector=new GestureDetector(this);

        //解决长按屏幕后无法拖动的现象

        mGestureDetector.setIsLongpressEnabled(false);

        boolean consume=mGestureDetector.onTouchEvent(event);

 

        return consume;

    }

 

    /********************************下面的6个方法是由OnGestureListener实现的***************************/

    //手指轻轻触摸屏幕的一瞬间，由1个ACTION_DOWN触发

    @Override

    public boolean onDown(MotionEvent motionEvent) {

        System.out.println("MainActivity.onDown");

        return false;

    }

 

    //手指轻轻触摸屏幕，尚未松开或者拖动，由1个ACTION_DOWN触发

    //注意和onDown的区别，它强调的是有没有松开或者拖动的状态

    @Override

    public void onShowPress(MotionEvent motionEvent) {

        System.out.println("MainActivity.onShowPress");

    }

 

    //手指（轻轻接触屏幕后）松开，伴随着一个ACTION_UP而触发，这是单击行为

    @Override

    public boolean onSingleTapUp(MotionEvent motionEvent) {

        System.out.println("MainActivity.onSingleTapUp");

        return false;

    }

 

    //手指按下屏幕并拖动，由1个ACTION_DOWN，多个ACTION_MOVE触发，这是拖动行为

    @Override

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        System.out.println("MainActivity.onScroll");

        return false;

    }

 

    //长按

    @Override

    public void onLongPress(MotionEvent motionEvent) {

        System.out.println("MainActivity.onLongPress");

    }

    //用户按下屏幕、快速滑动后松开，由1个ACTION_DOWN，多个ACTION_MOVE和一个AACTION_UP触发，这是表明快速滑动的行为

    @Override

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        System.out.println("MainActivity.onFling");

        return true;

    }

 

 

    /**************************************下面的3个方法是由OnDoubleTapListener接口实现的*******************************/

 

    

    //严格的单击行为

    //注意它与onSingleTapUp的区别，如果触发了onSingleTapConfirmed，那么后面不可能再紧接着是另一个单击行为，即这只可能是单击，而不可能是双击当中的一次单击

    @Override

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

        return false;

    }

 

    //双击，由2个连续的单击组成，它不可能和onSingleTapConfirmed共存

    @Override

    public boolean onDoubleTap(MotionEvent motionEvent) {

        System.out.println("MainActivity.onDoubleTap");

        return false;

    }

 

    //表示发生了双击行为，在双击的期间，ACION_DOEM，ACTION_MOVE和ACTION_UP都会触发此回调

    @Override

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {

        return false;

    }

}

```
上面我们说了很多的方法，但是在实际的开发过程中我们比较常用的：onSingleTapUp（单击），onFling（快速的滑动），onScroll（拖动），onLongPress（长按），onDoubleTap（双击）。另外我们在实际的开发过程中也不一定会使用到GestureDetector（手势检测），因为如果我们在View的onTouchEvent就有我们所需的监听。总之：如果是只监听滑动相关的事件，直接在onTouchEvent中实现。但是如果需要监听双击行为的话，就必须使用GestureDetector。


**<font size=3>Scroller**

**<font size=2> 弹性滑动对象，用于实现View的弹性滑动。我们都知道，当使用View的scrollTo/scrollBy方法进行滑动时，这个过程是瞬间完成的，但是没有过度效果的滑动用户体验不好。这时就可以使用Scroller来实现过度效果，这时其滑动的过程就不是瞬间完成的，而是有时间间隔完成。Scroll本身无法让View弹性滑动，它需要和View的computeScroll方法配合使用。**

#### View的滑动
##### 使用scollTo/scollBy
操作简单 适合对View内容的滑动
##### 使用动画
操作简单，主要适用于没有交互的View和实现复杂的动画效果
##### 改变布局参数
操作稍微复杂，适用于有交互的View
#### 弹性滑动
##### 通过动画
##### 使用延时策略
##### 使用Scroller
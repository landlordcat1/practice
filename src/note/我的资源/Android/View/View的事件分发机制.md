#### 点击事件的传递规则
##### 在介绍点击事件之前，我们要明白，我们这次分析的事件是点击事件，即MotionEvent。而所谓的事件分发即对MotionEvent的事件分发过程。即当一个MotionEvent产生了以后，系统需要把这个传递给指定的view，这就是传递事件分发的整个过程。点击事件的分发过程需要以下三个很重要的方法来完成：dispatchTouchEvent、onInterceptTouchEvent和onTouchEvent。
我们先来介绍以下几个方法：

###### public Boolean dispatchTouchEvent(MotionEvent ev)
##### 用来进行当前事件的分发。如果事件能够传递给当前view，那么此方法一定会被调用，返回结果受当前View事件的onTouchEvent和下级的dispatchTouchEvent事件的影响。<font color=red>表示是否消耗该事件。<font>

###### public Boolean onInterceptTouchEvent（MotionEvent event)

##### <font color=black>在上述方法的内部调用，用来判断是否拦截了某个事件，如果当前View拦截了某个事件,那么在同一个事件序列中，此方法不会在被调用，返回结果则是是否会拦截当前事件。

###### public Boolean onTouchEvent（MotionEvent event）

##### 在dispatchTouchEvent方法中调用，用来处理点击事件，表示当前事件是否被消耗，如果被消耗，则在同一个事件序列中，当前view无法再次收到事件。


```
public boolean dispatchTouchEvent(MotionEvent ev){
    boolean consume=flase;
    if(onIntercreptTouchEvent)(ev){
    consume=onTouchEvent(ev);
    }else{
        consume=child.dispatchTouchEvent(ev);
    }
    return consume;
}
```
##### 点击事件的传递规则，对于一个viewgroup来说，点击事件产生后，首先会传递给它，这时它的dispatchEvent事件就会被调用，如果onInterceptTouchEvent方法返回true，就代表他要拦截此事件，接着事件就会交给这个ViewGroup处理，即它的onToughEvent方法会被调用。如果这个viewGroup的onInterceptTouchEvent返回false，则代表他不会拦截此事件，那么这个事件就会传递子事件的dispatchTouchEvent方法就会被调用，如此反复直到事件最终被处理。

##### 当一个View有onTouchListener,那么onTouchListener中的onTouch方法会被回调。这时事件如何处理还要看onTouch的返回值，如果onTouch的返回值是false，则onTouchEvent方法就会被调用，如果当前onTouchEvent方法中设置了OnclickListener,则onclick方法会被调用。而如果onTouch返回的是true，则onTouchEvent方法就不会被调用。


##### 当一个点击事件产生后，它的传递过程遵循以下程序：Activity-->Window-->view,即事件总是先传给Activity，然后传递给Window，最后才会传递给顶层View。如果一个View的onTouchEvent事件返回flase,
如果所有的元素都不处理这个事件，则Activity的OnTouchEvent方法会被调用。
![image](http://i.caigoubao.cc/625949/944365-79b1e86793514e99.png)
![image](http://i.caigoubao.cc/625949/3983615-c178d12df564f2e3.png)

##### 关于事件传递的机制，这里给出一些结论：

##### ①同一事件，从手指接触屏幕的那一刻开始到手指离开屏幕的那一刻结束，在这个过程中产生的一系列事件，这个事件序列以down事件开始，中间有含有数量不定的move事件，最终以up结束。

##### ②正常情况下，一个事件序列只能被一个View拦截且消耗，因为一个元素一旦拦截了某次事件，那么同一事件序列内的所有事件都会直接交给它处理，因此同一个事件中的事件不能分别由两个View处理。

##### ③某个View一旦被拦截后，那么这个事件序列都只能由他来处理，并且他的onInterceptToughEvent不会被调用。即某个View被拦截成功后，那么系统就会把同一个事件序列内的其他方法都直接交给它处理。而不会调用View的onIntercreptToughEvent方法区询问他是否需要拦截。

##### ④某个View一旦开始处理事件，如果她不消耗ACTION_DOWN事件（onTouchEvent)返回为false，那么同一事件序列的事件都不会交给他处理，并且事件将重新上交给他的父元素进行处理，即父元素中的onTouchEvent会被调用。意思是一个事件一旦交给一个View处理，那么它一定会被消耗，否则同一事件序列下剩下的事件就不会交给他处理了。

##### ⑤如果View不消耗除ACTION_DOWN之外的事件，那么这个点击事件将会消失，此时父元素的TouchEvent事件不会被调用，并且当前View可以持续收到后续的事件。


##### ⑥ViewGroup默认不拦截任何事件。

##### ⑦View没有onInterceptToughEvent方法，一旦有时间传递给他，那么它的TouchEvent事件就会被调用。


##### ⑧View的onTouchEvent事件默认会消耗，即返回true，除非他是不可点击的。

##### ⑨View的enable属性不影响onTouchEvent的默认返回值。

##### ⑩onclick发生的前提是当前View是可点击的，并且他收到了down和up的事件。

##### 11.事件传递过程是由外向内，即事件先传递给父元素，然后再有父元素传给子View。

![image](http://i.caigoubao.cc/625949/944365-eeebede55f55b040.png)
![image](http://i.caigoubao.cc/625949/Activity.jpg)
![image](http://i.caigoubao.cc/625949/View.jpg)
![image](http://i.caigoubao.cc/625949/ViewGroup.jpg)
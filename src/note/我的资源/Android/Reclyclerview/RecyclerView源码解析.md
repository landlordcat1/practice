#### 思路
##### 当打开RecyclerView的源码会发现特别复杂，感觉无从下手，不会像Volley和OKhttp从发起开始到发起结束，目标性那么明确。那么既然RecyclerView继承的是ViewGroup，也就是说RecyclerView其实就是和LinearLayout等布局一样的一个自定义ViewGroup。
既然涉及到自定义组件，那么当我们自己来实现一个自定义ViewGroup，最重要的步骤无非是下面几点：

```
1.重写onMeasure用于确定自定义ViewGroup的大小
2.重写onLayout用于布局view的位置
所以源码的分析也应该从这里开始进行分析
```
#### OnMeasure

```
protected void onMeasure(int widthSpec, int heightSpec) {
        if (mLayout == null) {
            //layoutManager没有设置的话，直接走default的方法，所以会为空白
            defaultOnMeasure(widthSpec, heightSpec);
            return;
        }
        if (mLayout.mAutoMeasure) {
            final boolean skipMeasure = widthMode == MeasureSpec.EXACTLY
                    && heightMode == MeasureSpec.EXACTLY;
            //如果测量是绝对值，则跳过measure过程直接走layout
            if (skipMeasure || mAdapter == null) {
                return;
            }
            if (mState.mLayoutStep == State.STEP_START) {
                //mLayoutStep默认值是 State.STEP_START
                dispatchLayoutStep1();
                //执行完dispatchLayoutStep1()后是State.STEP_LAYOUT
            }
             ..........
            //真正执行LayoutManager绘制的地方
            dispatchLayoutStep2();
            //执行完后是State.STEP_ANIMATIONS
             ..........
            //宽高都不确定的时候，会绘制两次
            // if RecyclerView has non-exact width and height and if there is at least one child
            // which also has non-exact width & height, we have to re-measure.
            if (mLayout.shouldMeasureTwice()) {
             ..........
                dispatchLayoutStep2();
             ..........            }
        } else {
            if (mHasFixedSize) {
                mLayout.onMeasure(mRecycler, mState, widthSpec, heightSpec);
                return;
            }
             ..........
            mLayout.onMeasure(mRecycler, mState, widthSpec, heightSpec);
             ..........
            mState.mInPreLayout = false; // clear
        }
    }
```
##### 分析onMeasure的过程

```
if (mLayout == null) {
            //layoutManager没有设置的话，直接走default的方法，所以会为空白
            defaultOnMeasure(widthSpec, heightSpec);
            return;
        }
```

###### 这里的mlayout其实就是我们给RecyclerView设置的LayoutManager对象，这一段代码其实就是很好的解释了为什么我们有时候初次使用RecyclerView的时候忘记设置LayoutMananger之后，RecyclerView会没有按照我们所想的那样显示出来。
###### 如果mLayout为null的话，会走defaultOnMeasure方法。

```
void defaultOnMeasure(int widthSpec, int heightSpec) {
        // calling LayoutManager here is not pretty but that API is already public and it is better
        // than creating another method since this is internal.
        final int width = LayoutManager.chooseSize(widthSpec,
                getPaddingLeft() + getPaddingRight(),
                ViewCompat.getMinimumWidth(this));
        final int height = LayoutManager.chooseSize(heightSpec,
                getPaddingTop() + getPaddingBottom(),
                ViewCompat.getMinimumHeight(this));
        setMeasuredDimension(width, height);
    }
```
###### 可以看到这里的chooseSize方法其实就是根据宽高的Mode值直接调用setMeasureDimension（width，height）设置宽高了，可以发现这里其实是没有进行child的测量就直接return结束了onMeasure过程的，这也就解释了为什么我们没有设置LayoutMananger会导致显示空白了。

```
final boolean skipMeasure = widthMode == MeasureSpec.EXACTLY
                    && heightMode == MeasureSpec.EXACTLY;
            //如果测量是绝对值，则跳过measure过程直接走layout
            if (skipMeasure || mAdapter == null) {
                return;
            }
```
##### 这里解释的很清楚，如果宽和高的测量值是绝对值时，直接跳过onMeasure方法，那么子View没有绘制，会造成空白的情况，但是实际情况是当我们给RecyclerView设置绝对值大小的时候，子View仍可以正常绘制出来。这个问题在后面会解答（onLayout里执行子View的绘制）

```
 if (mState.mLayoutStep == State.STEP_START) {
                //mLayoutStep默认值是 State.STEP_START
                dispatchLayoutStep1();
                //执行完dispatchLayoutStep1()后是State.STEP_LAYOUT
            }
```
##### 接下来就要开始绘制的准备了，这里可以看到判断mLayoutStep，这里mLayoutStep的默认值其实就是State.STEP_START，并且每次绘制流程结束后，会重置为State.STEP_START.接下来执行dispatchLayoutStep1();方法dispatchLayoutStep1();其实没有必要过多分析，因为分析源码主要是对于绘制思想的理想，如果过多的纠结每一行的含义，那么会陷入很大的困扰中。这里放上官方对dispatchLayoutStep1()的注释

```
/**
     * The first step of a layout where we;
     * - process adapter updates
     * - decide which animation should run
     * - save information about current views
     * - If necessary, run predictive layout and save its information
     */
    /**
     * 1.处理Adapter的更新
     * 2.决定那些动画需要执行
     * 3.保存当前View的信息
     * 4.如果必要的话，执行上一个Layout的操作并且保存他的信息
     */
```
###### 接下来就是我们真正执行LayoutMananger绘制的地方dispathLayoutStep2().

```
private void dispatchLayoutStep2() {
        ....
        //重写的getItemCount方法
        mState.mItemCount = mAdapter.getItemCount();
        ....
        // Step 2: Run layout
        mState.mInPreLayout = false;
        mLayout.onLayoutChildren(mRecycler, mState);
         ....
    }
```
RecyclerView将View绘制交给了LayoutManager，这里就是最有力的体现。将RecyclerView内部持有的LayoutManager的onLayoutChilren方法，单从方法的名字其实就可以看出。这里我们就进入LayoutManager里看一看。

```
public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // layout algorithm:
        //找寻锚点
        // 1) by checking children and other variables, find an anchor coordinate and an anchor
        // item position.
        //两个方向填充，从锚点往上，从锚点往下
        // 2) fill towards start, stacking from bottom
        // 3) fill towards end, stacking from top
        // 4) scroll to fulfill requirements like stack from bottom.
        // create layout state
        ....
        // resolve layout direction
        //判断绘制方向,给mShouldReverseLayout赋值,默认是正向绘制，则mShouldReverseLayout是false
        resolveShouldLayoutReverse();
        final View focused = getFocusedChild();
        //mValid的默认值是false，一次测量之后设为true，onLayout完成后会回调执行reset方法，又变为false
        if (!mAnchorInfo.mValid || mPendingScrollPosition != NO_POSITION
                || mPendingSavedState != null) {
        ....
            //mStackFromEnd默认是false，除非手动调用setStackFromEnd()方法，两个都会false，异或则为false
            mAnchorInfo.mLayoutFromEnd = mShouldReverseLayout ^ mStackFromEnd;
            // calculate anchor position and coordinate
            //计算锚点的位置和偏移量
            updateAnchorInfoForLayout(recycler, state, mAnchorInfo);
        ....
        } else if (focused != null && (mOrientationHelper.getDecoratedStart(focused)
                >= mOrientationHelper.getEndAfterPadding()
                || mOrientationHelper.getDecoratedEnd(focused)
                <= mOrientationHelper.getStartAfterPadding())) {
         ....
        }
         ....
        //mLayoutFromEnd为false
        if (mAnchorInfo.mLayoutFromEnd) {
            //倒着绘制的话，先往上绘制，再往下绘制
            // fill towards start
            // 从锚点到往上
            updateLayoutStateToFillStart(mAnchorInfo);
            ....
            fill(recycler, mLayoutState, state, false);
            ....
            // 从锚点到往下
            // fill towards end
            updateLayoutStateToFillEnd(mAnchorInfo);
            ....
            //调两遍fill方法
            fill(recycler, mLayoutState, state, false);
            ....
            if (mLayoutState.mAvailable > 0) {
                // end could not consume all. add more items towards start
            ....
                updateLayoutStateToFillStart(firstElement, startOffset);
                mLayoutState.mExtra = extraForStart;
                fill(recycler, mLayoutState, state, false);
             ....
            }
        } else {
            //正常绘制流程的话，先往下绘制，再往上绘制
            // fill towards end
            updateLayoutStateToFillEnd(mAnchorInfo);
            ....
            fill(recycler, mLayoutState, state, false);
             ....
            // fill towards start
            updateLayoutStateToFillStart(mAnchorInfo);
            ....
            fill(recycler, mLayoutState, state, false);
             ....
            if (mLayoutState.mAvailable > 0) {
                ....
                // start could not consume all it should. add more items towards end
                updateLayoutStateToFillEnd(lastElement, endOffset);
                 ....
                fill(recycler, mLayoutState, state, false);
                ....
            }
        }
        ....
        layoutForPredictiveAnimations(recycler, state, startOffset, endOffset);
        //完成后重置参数
        if (!state.isPreLayout()) {
            mOrientationHelper.onLayoutComplete();
        } else {
            mAnchorInfo.reset();
        }
        mLastStackFromEnd = mStackFromEnd;
    }
```
##### 简单来说
###### 其实可以总结缩略为：

```
先寻找页面当前的锚点
以这个锚点为基准，向上或者向下填充
填充完后，如果有剩余的填充大小，再填充一次
```
##### 寻找锚点
```
        resolveShouldLayoutReverse();
        final View focused = getFocusedChild();
        //mValid的默认值是false，一次测量之后设为true，onLayout完成后会回调执行reset方法，又变为false
        if (!mAnchorInfo.mValid || mPendingScrollPosition != NO_POSITION
                || mPendingSavedState != null) {
        ....
            //mStackFromEnd默认是false，除非手动调用setStackFromEnd()方法，两个都会false，异或则为false
            mAnchorInfo.mLayoutFromEnd = mShouldReverseLayout ^ mStackFromEnd;
            // calculate anchor position and coordinate
            //计算锚点的位置和偏移量
            updateAnchorInfoForLayout(recycler, state, mAnchorInfo);
        ....
        } else if (focused != null && (mOrientationHelper.getDecoratedStart(focused)
                >= mOrientationHelper.getEndAfterPadding()
                || mOrientationHelper.getDecoratedEnd(focused)
                <= mOrientationHelper.getStartAfterPadding())) {
         ....
        }
```
##### 首先执行的resolveShouldLayoutReverse();从方法的命名上可以理解为是否要倒着绘制

```
 //判断绘制方向,给mShouldReverseLayout赋值,默认是正向绘制，则mShouldReverseLayout是false
private void resolveShouldLayoutReverse() {
        // A == B is the same result, but we rather keep it readable
        if (mOrientation == VERTICAL || !isLayoutRTL()) {
            //默认mReverseLayout是false，构造函数，可以通过setReverseLayout来设置
            mShouldReverseLayout = mReverseLayout;
        } else {
            mShouldReverseLayout = !mReverseLayout;
        }
    }
```
可以看到这里我注释写的很清楚，如果我们没有手动调用setReverseLayout()方法，默认情况下，是不会倒着绘制的。

接下来对于几个变量的注释来这里解释一下。


### RecycleView和ListView
 #### ListView的优点
- #### ListView实现添加HeaderView和FooderView有直接的方法
- #### 分割线可以直接设置
- #### ListView实现onItemClickListence和onItemLongClickListence有直接的方法
#### ReclyView的优点
- #### 封装了ViewHodler,效率更高
- #### 可以增加Item动画，侧滑功能等
- #### 支持局部更新，可见才更新，不可见不更新
- #### 插件式实现，各个功能模块化，解耦性强，使用起来比较方便
#### 但是RecyclerView不会替代ListView，因为使用场景不同；但会替代很多开源框架：横向ListView、瀑布流等
#### 关于ReclyView:
##### 整体上看RecyclerView架构，提供了一种插拔式的体验，高度的解耦，异常的灵活，通过设置它提供的不同的LayoutManager，ItemDecoration,ItemAnimator实现令人膛目的效果。
- ##### 你想要的控制其显示的方式，请通过布局管理器LayoutManager
- ##### 你想要控制item间 的间隔（可绘制），请通过ItemDecoration、
- ##### 你想要控制Item增删的动画，请通过ItemAnimator
- ##### 你想要控制点击、长按事件。。。

### RecyclerView提供了三种内置的LayoutManager：

```
1.LinearLayoutManager:线性布局，横向或者纵向滑动列表
2.GirdLayoutManager:表格布局
3.StaggeredGridLayoutManager:流式布局,例如瀑布流效果
```
- ##### 除了上面的三种布局之外，我们还可以继承RecyclerView.LayoutManager实现一个自定义的LayoutManager.
- ##### Animations(动画)效果：

```
RecyclerView对于Item的添加和删除是默认开启动画的。我们当然也可以通过RecyclerView.ItemAnimator类定制动画，然后通过RecyclerView.setItemAnimator()方法来进行使用。
```
- ##### RecyclerView相关类：

类名  | 说明 |
---|---
RecyclerView.Adapter | 可以托管数据集合，为每一项Item创建视图并且绑定数据
RecyclerView.ViewHolder | 承载Item视图的子布局
RecyclerView.LayoutManager | 负责Item视图的布局的显示管理
RecyclerView.ItemDecoration | 给每一项Item视图添加子View,例如可以进行画分隔线之类的
RecyclerView.ItemAnimator | 负责处理数据添加或者删除时候的动画效果
#### 具体使用方法：
##### Recyclerview.Adapter

```
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private Context context;
    private List<String> data;

    public RecyclerviewAdapter(Context context,List<String> data){
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(data.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("这里是点击每一行item的响应事件",""+position+item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }
    }
}

```
##### RecyclerView.ViewHolder

```
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;//存储list_Item的View
    private View mConvertView;//list_Item
    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView=itemView;
        mViews=new SparseArray<View>();
    }
    //获取实例
    public static ViewHolder get(Context context,ViewGroup parent,int layoutId) {
        View itemView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder holder=new ViewHolder(context,itemView,parent);
        return holder;
    }
    public <T extends View> T getView(int viewId) {
        View view=mViews.get(viewId);
        if(view==null) {
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }
}

```
##### Recyclerview的LayoutManager
##### RecyclerView提供了三种内置的LayoutManager：

```
1.LinearLayoutManager:线性布局，横向或者纵向滑动列表
2.GirdLayoutManager:表格布局
3.StaggeredGridLayoutManager:流式布局,例如瀑布流效果
```
- ##### 除了上面的三种布局之外，我们还可以继承RecyclerView.LayoutManager实现一个自定义的LayoutManager.
- ##### Animations(动画)效果：

```
RecyclerView对于Item的添加和删除是默认开启动画的。我们当然也可以通过RecyclerView.ItemAnimator类定制动画，然后通过RecyclerView.setItemAnimator()方法来进行使用。
```
##### 重要方法
- onLayoutChildren(RecyclerView.Recycler recycler,RecyclerView.State state)

这个方法是进行子对象布局时执行的，它决定了RecyclerView的子对象放在什么位置，RecyclerView的子对象放在什么位置，recycler是RecyclerView的回收池，state是RecyclerView的状态信息。当界面刷新的时候也会调用这个方法，需要注意的是该方法在初始化的时候回执行两遍。
- canScrollVertically()/canScrollHorizontally()

是否可以竖直/水平滑动，返回bool值。
##### 常用API
- recycler.getViewForPosition(position)
获取位置为position的View
- getPosition(View view)
获取View的位置
更多API链接
[](https://note.youdao.com/)https://juejin.im/entry/59c45d625188254f58412a97
##### RecyclerView.ItemDecoration
##### 常用方法[：](https://note.youdao.com/)https://www.jianshu.com/p/dda4645c824f

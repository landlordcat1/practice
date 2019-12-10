## RecycleView和fragment结合实现
#### 在写停车场项目时，遇到了底部导航栏与recycleview相结合的问题，也查到了一些关于这个问题的解决方法。总结了一些比较靠谱的方法。
### 一、流程
#### 具体的流程大概如下：
#### 1.在fragment的布局文件中加入RecyclerView的控件；
#### 2.在fragment中定义使用
#### 3设置RecyclerView的适配器
#### 4.自定义实体类
###### 在fragment的布局文件中加入RecyclerView的控件

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout 
xmlns:android="http://schemas.android.com/apk/res/android"

  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:fitsSystemWindows="true"
  android:orientation="vertical">

  <android.support.v7.widget.RecyclerView
      android:id="@+id/collect_recyclerView"
      android:layout_width="match_parent"
      android:layout_height="match_parent">
  </android.support.v7.widget.RecyclerView>
</LinearLayout>
```
###### 在fragment中定义使用

```
public class CollectFragment extends Fragment {
 private View view;//定义view用来设置fragment的layout
 public RecyclerView mCollectRecyclerView;//定义RecyclerView
 //定义以goodsentity实体类为对象的数据集合
 private ArrayList<Park> parklist = new ArrayList<Park>();
 //自定义recyclerveiw的适配器
 private CollectRecycleAdapter mCollectRecyclerAdapter;

 @Nullable
 @Override
 public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     //获取fragment的layout
     view = inflater.inflate(R.layout.collect_page, container, false);
     //对recycleview进行配置
     initRecyclerView();
     //模拟数据
     initData();
     return view;
 }

 /**
  * TODO 模拟数据
  */
 private void initData() {
     for (int i=0;i<10;i++){
         Park park=new Park();
         park.setName("西邮停车场");
         park.setId("100");
         parkList.add(park);
     }
 }

 /**
  * TODO 对recycleview进行配置
  */

 private void initRecyclerView() {
     //获取RecyclerView
     mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
     //创建adapter
     mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), goodsEntityList);
     //给RecyclerView设置adapter
     mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
     //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
     //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
     mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
     //设置item的分割线
     mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
     //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
     mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
         @Override
         public void OnItemClick(View view, Park data) {
             //此处进行监听事件的业务处理
             Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
         }
     });
 }

}
```
###### 设置RecyclerView的适配器

```
package com.example.wangrui.homeactivity2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangrui.homeactivity2.R;

import java.util.ArrayList;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.myViewHodler> {
    private Context context;
    private ArrayList<Park> parkList;

    //创建构造函数
    public ParkAdapter(Context context, ArrayList<Park> parkList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.parkList = parkList;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_item,parent,false);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {
        //根据点击位置绑定数据
        Park data = parkList.get(position);

        //自定义recyclerveiw的适配器
//        holder.mItemGoodsImg;
        holder.mItemParkName.setText(data.getName());//获取实体类中的name字段并设置
         holder.mItemParkId.setText(data.getId());
        holder.mItemParkaddress.setText(data.getAddress());
        holder.mItemParkcar_number.setText(data.getCar_number());
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return parkList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private TextView mItemParkName;
        private TextView mItemParkcar_number;
        private TextView mItemParkId;
        private TextView mItemParkaddress;
        public myViewHodler(View itemView) {
            super(itemView);
            mItemParkName =  itemView.findViewById(R.id.park_name);
            mItemParkId =  itemView.findViewById(R.id.id);
            mItemParkcar_number=itemView.findViewById(R.id.car_number);
            mItemParkaddress=itemView.findViewById(R.id.address);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                     onItemClickListener.OnItemClick(v, parkList.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        void OnItemClick(View view, Park data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
```
###### 自定义实体类

```
package com.example.wangrui.homeactivity2;

import java.util.ArrayList;

public class Park {
         String name;
         String id;
         String address;
         String car_number;
         String image_path;
        public Park(String name,String id,String address,String car_number)
        {
            this.name=name;
            this.id=id;
            this.address=address;
            this.car_number=car_number;
            // this.image_path=image_path;
        }
        public Park(){

          }
        public String getName() {
            return name;
        }
        public String getId()
        {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public String getCar_number() {
            return car_number;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setName(String name) {
            this.name = name;
        }
        public void SetId(String id){
            this.id=id;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }

}

```

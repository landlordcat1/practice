#### 设计模式
- [ ] 通过桥接模式，使RecyclerView将布局方式独立成LayoutManager，实现对布局的定制化。
- [ ] 通过组合模式，使RecyclerView使用dispatchLayout对Item View进行布局绘制。
- [ ] 通过适配者模式，ViewHolder将RecyclerView与ItemView联系起来，使得
RecyclerView方便操作ItemView。
- [ ] 通过观察者模式，给ViewHolder注册观察者，当调用notifyDataSetChanged时，就重新绘制。
![image](http://i.caigoubao.cc/625949/8e70865e3265732c9c04bed703e075be.png)
###### RecyclerView的职责就是将Datas中的数据以一定的规则展示在上面，但RecyclerView知识一个ViewGroup，它只认识View，不清楚Data数据的具体结果，所以两个陌生人之间想构建通话，我们很容易想到适配器模式，因此，RecyclerView需要一个Adapter(适配器模式)来与data进行交流。如上所示，RecyclerView表示只会和ViewHolder进行接触，而Adapter的工作就是将Data转换为RecyclerView认识的ViewHolder，因此RecyclerView就间接地认识了Datas。尽管Adapter已经将Datas转换为RecyclerView熟知的View，但RecyclerView不想自己管理这些子View，因此，它雇佣了一个叫做LayoutManager的事情来帮其完成其布局。

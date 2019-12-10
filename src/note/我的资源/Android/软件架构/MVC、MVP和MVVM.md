[toc]
#### MVC
MVC是软件常见的架构之一。

MVC的意思是，软件可以分为三个部分。
- 视图(View)：用户界面
- 控制器(Controller)：业务逻辑
- 模型(Model)：数据保存
![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020104.png)
各部分的通信方式如下：
![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020105.png)

1.View传送指令到Controller.

2.Controller完成业务逻辑后，要求Model改变状态。

3.Model将新的数据发送到View，用户得到反馈。

所有的通信方式都是单向的。


互动模式：

接受用户指令时，MVC可以分为两种方式。一种是通过View接受指令，传递给Controller。

![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020106.png)

另一种是直接通过controller接受指令。

![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020107.png)

实例：Backbone.js
![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020108.png)

1.用户可以向View发送指令(DOM事件)，再由View直接要求Model改变状态。

2.用户也可以直接向Controller发送指令(通过URL触发hashChange事件)，再由Controller发送给View。

3.Controller非常薄，只起到路由的作用，而View非常厚，业务逻辑都部署在View。所以，Backbone索性取消了Controller,只保留了一个Router(路由器)。
#### MVP
MVP模式将Controller改名为Presenter，同时改变了通信方向。
![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020109.png)

1.各部分之间的通信，都是双向的。

2.View与Model不发生联系，都通过Presenter传递。

3.View非常薄，不部署任何业务逻辑，称为“被动视图”，即没有任何主动性，而Presenter非常厚，所有逻辑都部署在那里。

#### MVVM

MVVM模式将presenter改名为ViewModel,基本上与MVP模式一致。
![image](https://www.ruanyifeng.com/blogimg/asset/2015/bg2015020110.png)
唯一的区别，它采用双向绑定（data-binding）：View的变动，自动反映在ViewModel,反之亦然。
活动生命周期：

四种状态：
- 运行状态：(位于任务栈栈顶)
> 活动位于任务栈栈顶时，这时活动处于运行状态。回收运行状态的活动时会造成极差的用户体验。
- 暂停状态：(依然在用户可见状态，但因已经失去焦点，此时Activity无法与用户进行交互)
>  当一个活动不再处于栈顶位置时，被另一个非全屏的活动所覆盖（比如一个Dialog），那么该活动就失去了焦点，它将会暂停（但它仍然保留所有的状态和成员信息，并且仍然是依附在++WindowsManager++上），在系统内存积极缺乏的时候会将它杀死。
- 停止状态：(用户看不到当前的界面，也无法与用户进行交互，活动完全被覆盖)
>  如果一个活动被另一个全屏活动完全覆盖，那么该活动处于停止状态（状态和成员信息会保留，但是Activity已经不再依附于WindowManager了）。同时，在系统缺乏资源的时候会将它杀死（它会比暂停状态的活动先杀死）。
- 销毁状态：(当前活动被销毁，等待被系统回收)
> 如果一个活动在处于停止或者暂停的状态下，系统内存缺乏时会将其结束（finish）或者杀死（kill）。这种非正常情况下，系统在杀死或者结束之前会调用onSaveInstance（）方法来保存信息，同时，当Activity被移动到前台时，重新启动该Activity并调用onRestoreInstance（）方法加载保留的信息，以保持原有的状态。


##### 生存期：

 Activity类中定义了==7种回调方法==，覆盖了生命周期的每一个环节：
 
- [ ] onCreate():活动正在被创建
- 在活动第一次创建的时候被调用，一般进行静态操作：创建view，将数据绑定到list上，如果Activity之前处于被冻结状态的话，那么此状态需要由Bundle来提供。可以在此方法中完成该活动的初始化操作，比如加载布局，绑定事件等。
- [ ] onStart():活动正在被启动，即将开始
- 此时活动已经可见了，但是没有出现在前台，还无法和用户之间案进行交互。在活动由不可见变为可见的时候调用。
- [ ] onResume():活动已经可见了，显示在了前台并且开始活动
- 在活动准备好与用户进行交互的时候调用，此时活动一定处于返回栈的栈顶，并且处于运行状态了。
- [ ] onPause():表示活动正在停止
- 在系统准备启动或者是恢复另一个活动的时候调用。通常会此方法中将一些消耗CPU的资源释放掉，以及保存一些关键数据，但在执行此方法的时候一定要快速，否则会影响到处于栈顶的新的栈顶活动的使用。
>  ==在活动之间跳转的时候，一定是旧的活动的onPause()方法先执行完毕之后，才会开始执行新的活动的onResume.==
- [ ] onStop():活动即将停止
- 在活动完全不可见的时候使用，可以做一些释放资源的回收工作。与onPause()方法的主要区别为：在启动一个的对话框的活动时。onPause()方法会得到执行，但是onStop()方法就不会被调用。
- [ ] onDestroy():
- 在活动被销毁的时候被调用，之后将活动处于被销毁状态。
- [ ] onRestart():
- 活动在处于停止状态变为运行状态之前被调用，也就是活动被重新启动了。

>  onFreeze()：当前活动被暂停而启动其他的活动的时候，会调用此方法。
(在其他的活动显示之前)。可以使用此方法保存当前的用户状态(一般来说的当前实例的用户对象).暂停之后，为了回收资源提供给当前处于前台的活动，系统会在需要的时候停止或者是kill掉此应用。如果以后如果活动启动一个新的实例与用户进行交互，之前所保存的状态就会通过onCreate()方法传递给新的实例。
##### onStart和onResume，onPause和onStop的区别；


活动的==三种生存期==：

- [ ] ==完整生存期：==
- 活动由onCreate()方法开始到onDestroy()方法之间所经历的就是完整生存期。一般活动会在onCreate()方法中完成各种的初始化操作，而在onDestroy()方法中完成释放内存的操作。Q并且只能调用一次。
- [ ] ==可见生存期：==
- 活动在onStart()方法和onStop()之间所经历的就是可见生存期。在可见生存期内，活动对于用户总是可见的，即便有些可能无法与用户进行交互。但是可以使用这两种方法实现合理的管理用户可见的一些资源。比如onStart()方法中对资源进行加载，而在onStop()方法对资源进行释放，从而保证处于停止状态的活动不会占用太多的内存。
- [ ] ==前台生存期：==
- 在onResume和onPause()方法之间所经历的就是前台生存期。在前台生存期内，活动总是处于运行状态，此时的活动可以和用户进行交互，平时见到最多的就是该状态下的活动。      



#### 常见的几种情况：
##### 创建:
- onCreate -  启动onStart – 开始 onResume – 暂停 onPause – 结束 onStop – 销毁onDestroy

###### 必调用的三个方法：
- 主Activity(A)启动，点击启动子Activity(B)，子Actvity退出，返回主Activity调用顺序如下：
AAA –> onFreeze() –> onPause() –> B onCreate() -> B onStart() -> B onResume –> onStop() –> onRestart() –> onStart()->onResume()
- （2）用户点击Home，Actvity调用顺序如下
AAA –> onFreeze() –> onPause() –> onStop() — Maybe –> onDestroy()
- ( 3 ) 用户点击back键，Activity调用顺序如下：
AAA-> onPause() –> onStop() –> onDestroy() ->onCreate()->onStart()->onResume()
- （4）调用finish()， Activity调用顺序如下 
AAA –> onPause() –> onStop() –> onDestroy() 
- （5）在Activity上显示dialog， Activity调用顺序如下 
AAA -> onPause()
- （6）在父Activity上显示透明的或非全屏的activity，Activity调用顺序如下 
AAA –> onFreeze() –> onPause() 
- （7）设备进入睡眠状态，Activity调用顺序如下 
AAA –> onFreeze() –> onPause()


##### 异常生命周期：

此处涉及两种：当资源相关的系统配置发生改变或者是系统内存不足的时候。

1，资源相关的系统配置发生了改变导致的Activity被杀死之后重建。
2，当系统内存不足的时候会按照低优先级的Activity先被杀死。   

###### Acitivity的切换过程

```
graph TD
A(Activity的启动)-->B(onCreat)
B-->C(onStart)
C-->D(onResume)
D-->E(Activity的运行)
E-->|新Activity启动|F(onPause)
F-->|用户返回原Activity|C
F-->|Activity已经不可见|G(onStop)
G-->|用户返回原Activity onReStart|C
G-->|高优先级的应用需要内存 应用被杀死 用户返回原Activity|B
G-->|Activity正在停止或者即将被销毁|H(onDestroy)
H-->I(Activity销毁)

```
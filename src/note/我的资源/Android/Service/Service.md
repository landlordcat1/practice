## Service
### 1.Service简单概述
#### Service(服务)是一个一种可以在后台执行长时间运行操作而没有用户界面的应用组件。服务可由其他应用组件启动（如Activity），服务一旦被启动将在后台一直运行，即使启动服务的组件（Activity）已销毁也不受影响。 此外，组件可以绑定到服务，以与之进行交互，甚至是执行进程间通信 (IPC)。 例如，服务可以处理网络事务、播放音乐，执行文件 I/O 或与内容提供程序交互，而所有这一切均可在后台进行，Service基本上分为两种形式：
- #### 启动状态
#### 当应用组件（如Activity）通过调用startService（）启动服务时，服务处于启动状态。一旦启动，服务即可在后台无限期运行，及时启动服务的组件已被销毁也不收影响，除非手动调用才能停止服务，已启动的服务通常是==执行单一操作==，而且==不会==将结果返回到调用方。
- #### 绑定状态
#### 当应用组件通过调用bindService（）绑定到服务时，服务即处于==绑定==状态。绑定服务提供了一个客户端-服务器接口，允许组件与服务进行交互、发送请求、获取结果，甚至是利用进程间通信（IPC)跨进程执行这些操作。仅当与另一个应用组件绑定时，绑定服务才会运行。多个组件可以同时绑定到该服务，但全部取消绑定后，该服务将会被销毁。
--------------
### 2.Service启动服务
#### 要创建服务，必须创建Service的子类（或者视同它的一个现有子类如IntentService）.在实现中，我们需要重写一些回调方法，以处理服务生命周期的某些关键过程。

```
public class SimpleService extends Service{
    *绑定服务时才会调用
    *必须要实现的方法
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    /**
    *首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用onStartCommand()或onBind()之前).
    如果服务已经在运行，则不会调用此方法，此方法只被调用一次。
    */
    public void onCreate(){
        Sysout.out.println("onCreat invoke");
        super.onCreate();
    }
    /**
    *每次通过startService()方法启动Service时都会被回调。
    */
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        System.out.println("onStartCommand invoke");
        return super.onStartCommand(intent,flags,startId);
    }
       /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        System.out.println("onDestroy invoke");
        super.onDestroy();
    }

```
##### SimpleService继承了Service类，并重写了onBind方法，该方法是必须重写的，但是由于此时是启动状态的服务，则该方法无须实现，返回null即可，只有在绑定状态的情况下才需要实现该方法并返回一个IBinder的实现类，接着重写了onCreat、onStartCommand、onDestroy三个主要的生命周期方法。关于这几个方法说明如下：
- #### onBind()
   当一个组件想通过==bindService==()与服务绑定时，系统将调用此方法。在此方法的实现中，必须返回一个IBinder接口的实现类，供客户端用来与服务进行通信。无论是启动状态还是绑定状态，此方法必须重写，但在启动状态的情况下直接返回null。
- #### onCreat()
  首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用onStartCommand()或onBind()之前）。如果服务已在运行，则不会调用此方法，该方法只调用一次。
- #### onStartCommand()
当一个组件（如Activity）通过调用startService()请求启动服务时，系统将调用此方法。一旦执行此方法，服务即会启动并可在后台无限期运行。如果自己实现此方法，则需要在服务工作完成后，通过调用stopSelf（）或者stopService（）来停止服务.==在绑定状态时，无需实现此方法。==
- #### onDetroy()
当服务不在使用且将被销毁时，系统将调用此方法。服务应该实现此方法来清理所有资源，如线程，注册的侦听器、接听器等，这是服务接收的最后一个调用。
- #### onStartCommand(Intent intent,int flags,int startid)方法详解
 ##### intent:
启动时，启动组件传递过来的Intent，如Activity可利用Intent封装所需要的参数并传递给Service
 ##### flags：
 表示启动请求时是否有额外数据，可选值有0，START_FLAG_REDELIVERY,START_FLAG_RETRY,0代表没有，他们具体含义如下：
###### 1.START_FLAG_REDELIVERY
这个值代表了onStartCommmand方法的返回值为START_REDELIVER_INTENT，而且在上一次服务被杀死前会去调用stopSelf方法停止服务。其中START_REDELIVER_INTENT意味着当Service因内存不足而被系统kill后，则会==重建==服务，并通过传递给服务的最后一个Intent调用onStartCommand(),此时Intent是有值的。
###### 2.START_FLAG_RETRY 
该flag代表onStartcommand调用后一直没有返回值，会尝试重新去调用onStartCommand().
##### startID：
指明当前服务的唯一ID，与stopSelfResult(int startld)配合使用，stopSelfResult可以更安全地根据ID停止服务。
它有三种可选值，==START_STICKY，START_NOT_STICKY，START_REDELIVER_INTENT==
###### START_STICKY ：
当Service因内存不足被系统kill后，系统会尝试重新创建此Service，一旦创建成功后会回调onStartCommand方法，但其中的Intent为==null==。
###### START_NOT_STICKY
当Service被系统kill后，系统不会重新创建此Service。
###### START_REDELIVER_INTENT
 当Service因内存不足而被系统kill后，则会重建服务，并通过传递给服务的最后一个 Intent 调用 onStartCommand()，任何挂起 Intent均依次传递。与START_STICKY不同的是，其中的传递的Intent将是==非空==，是==最后一次调用==startService中的intent。
### 3.Service绑定服务
#### 绑定服务时Service中的另一种变形，当Service处于绑定状态时，其代表着客户端—服务器接口中的服务器。提供绑定的服务时，我们必须提供一个IBinder接口的实现类，该类用以提供客户端用来与服务进行交互的编程接口，该接口可以通过三种方法定义接口：
- ###### 扩展Binder类
  如果服务时提供给自有应用专用的，并且Service（服务端）和客户端在相同的进程中进行（常见情况），则应通过扩展Binder类并从onBind（）返回它的一个实例来创建接口。
###### 使用Messager
Messenger可以翻译为信使，通过它可以在==不同的进程==中共传递Message对象(Handler中的Messager，==因此 Handler 是 Messenger 的基础==)，在Message中可以存放我们需要传递的数据，然后在进程间传递。如果需要让接口跨不同的进程工作，则可使用 Messenger 为服务创建接口，客户端就可利用 Message 对象向服务发送命令。同时客户端也可定义自有 Messenger，以便服务回传消息。这是执行进程间通信 (IPC) 的最简单方法，因为 Messenger 会在单一线程中创建包含所有请求的队列，也就是说Messenger是以==串行==的方式处理客户端发来的消息，这样我们就不必对服务进行线程安全设计了。
###### 使用AIDL
由于Messenger是以串行的方式处理客户端发来的消息，如果当前有大量消息同时发送到Service(服务端)，Service仍然只能一个个处理，这也就是Messenger跨进程通信的缺点了，因此如果有大量并发请求，Messenger就会显得力不从心了，这时AIDL（Android 接口定义语言）就派上用场了， 但实际上Messenger 的跨进程方式其底层实现 就是AIDL，只不过android系统帮我们封装成透明的Messenger罢了 。因此，如果我们想让服务同时处理多个请求，则应该使用 AIDL。 在此情况下，服务必须具备多线程处理能力，并采用线程安全式设计。使用AIDL必须创建一个定义编程接口的 .aidl 文件。Android SDK 工具利用该文件生成一个实现接口并处理 IPC 的抽象类，随后可在服务内对其进行扩展。
##### 扩展Binder
 如果我们的服务仅供本地应用使用，不需要跨进程工作，则可以实现自有Binder类，让客户端通过该类直接访问服务中的公共方法。其使用开发步骤如下：
- 创建BindService服务端，继承自Service并在类中，创建一个实现IBinder接口的实例对象并提供给公共方法给客户端调用。
- 从onBind（）回调方法返回此Binder实例。
- 在客户端中，从onServiceConnected()回调方法接收Binder，并使用提供的方法调用绑定服务。

==注意==：此方式只有在客户端和服务位于同一应用和进程内才有效，如对于需要将Activity绑定到在后台播放音乐的自有服务的音乐应用，此方式非常有效。
##### 使用Messager
IBinder应用在同一进程的通信，我们接着来了解服务与远程进程通信，而不同进程间的通信，最简单的方式就是使用Messenger服务提供通信接口。

Messenger使用步骤：

1.服务实现一个Handler。由其接收来自客户端的每个调用的回调。

2.Handler用于创建Messenger对象（对Handler的引用）

3.Messenger创建一个IBinder，服务onBind（）使其返回服务端

4.客户端使用Ibinder将Messenger（引用服务的Handler）实例化，然后使用Messenger将Message对象发送给服务。

5.服务在其Handler中（在handleMessage()方法中)接收每个Message。

关于绑定服务的注意点
1.多个客户端可同时连接到一个服务。但是onbind（）方法只被调用一次（在第一次记客户端绑定时调用）。
2.通常情况下我们应该在客户端生命周期的引入和退出时刻设置绑定和取消绑定操作。
- 如果只需要在acitivity可见时交互，则应在onstart（）期间绑定，在onstop()期间取消绑定。
- 如果希望 Activity 在后台停止运行状态下仍可接收响应，则可在 onCreate() 期间绑定，在 onDestroy() 期间取消绑定。
- 3.==注意==，切勿在Activity的onresume()和onPause期间绑定和取消绑定，因为每一次生命周期转换都会发生这些问题，反复解绑和绑定时不合理的。

原博客地址：
https://blog.csdn.net/javazejian/article/details/52709857
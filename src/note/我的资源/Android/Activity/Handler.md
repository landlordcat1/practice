### 概述：
##### 关于Handler:Handler是用来结合线程的消息队列来发送、处理“Message对象”和“Runnable对象”的工具。每一个Handler实例之后会关联一个线程和该线程的消息队列。当你创建一个Handler的时候，从这时开始，它就会自动关联到所在的线程/消息队列，然后就会陆续的把Message/Runnable分发到消息队列，并在它们出队的时候处理掉。
#### Looper,Message,MessageQueue,Handler的关系
#### Looper
##### 每一个线程只有一个looper,每个线程在初始化Looper之后，然后Looper会维护好该线程的消息队列，用来存放Handler发送的message,并处理消息队列出队的Message。它的特点是它跟它的线程是绑定的，处理消息也是在Looper所在的线程去处理，所以当我们在主线程创建Handler是，它就会跟主线程 唯一的Looper绑定，从而我们使用Handler在子线程发送消息时。，最终也在主线程处理达到了异步的效果。
#### MessageQueue
##### MessageQueue是一个消息队列，用来存放Handler发送的消息。每个线程最多只有一个MessageQueue。MessageQueue最终都是由Looper来管理，而主线程创建时，会创建一个默认 的Looper对象。而Looper对象的创建，将自动创建一个MessaQueue。其他而非主线程，不会自动创建Looper。
#### Message
##### 消息对象，就是MessageQueue里面存放的对象，一个MessageQueue可以包括多个Message。当我们需要发送一个Message时，我们一般不建议使用new Message（）的形式来创建，更推荐使用Message.obtain（）来获取Message实例，因为在Message类里定义了一个消息池，当消息池里存在未使用的消息时，便返回，如果没有未使用的消息，则通过new的方式创建返回，所以使用Message。obtain（）的方法来获取实例可以大大减少当有Message对象而产生的垃圾回收问题。
##### 通俗一点讲：当我们的子线程想修改Activity中的UI组件时,我们可以新建一个Handler对象,通过这个对象向主线程发送信息;而我们发送的信息会先到主线程的MessageQueue进行等待,由Looper按==先入先出==顺序取出,再根据message对象的what属性分发给对应的Handler进行处理！
![image](http://i.caigoubao.cc/625949/917743-20160520195457373-1290367512-jpg.png)
### API
#### handler的构造函数
- Handler（）
- Handler(Handler.Callback callback)：传入一个实现的Handler.Callback接口，接口只需要实现handleMessage方法。
- Handler(Looper looper)：将Handler关联到任意一个线程的Looper，在实现子线程之间通信可以用到。
- Handler(Looper looper, Handler.Callback callback)
##### 主要方法
- void dispatchMessage (Message msg)

```
 public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            // 如果有Runnbale，则直接执行它的run方法
            handleCallback(msg);
        } else {
            //如果有实现自己的callback接口
            if (mCallback != null) {
                //执行callback的handleMessage方法
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            //否则执行自身的handleMessage方法
            handleMessage(msg);
        }
    }
    
    private static void handleCallback(Message message) {
        message.callback.run();
    }
```
- Looper getLooper ()
  
拿到Handler相关联的Looper
- String getMessageName (Message message)

获取Message的名字，默认名字为message.what的值。
- ==void handleMessage (Message msg)==

处理消息。
- boolean hasMessages (int what)

判断是否有Message的what值为参数what。
- boolean hasMessages (int what, Object object)

判断是否有Message的what值为参数what，obj值为参数object。
- Message obtainMessage (int what, Object obj)

从消息池中拿到一个消息并赋值what和obj，其他重载函数同理。
- ==boolean post== (Runnable r)

将Runnable对象加入MessageQueue。
- boolean post (Runnable r)

将Runnbale加入到消息队列的队首。但是官方不推荐这么做，因为很容易打乱队列顺序。
- boolean postAtTime (Runnable r, Object token, long uptimeMillis)

在某个时间点执行Runnable r。
- boolean postDelayed (Runnable r, long delayMillis)

当前时间延迟delayMillis个毫秒后执行Runnable r。
- void removeCallbacks (Runnable r, Object token)

移除MessageQueue中的所有Runnable对象。
- void removeCallbacksAndMessages (Object token)

移除MessageQueue中的所有Runnable和Message对象。
- void removeMessages (int what)

移除所有what值得Message对象。
- ==boolean sendEmptyMessage== (int what)

直接拿到一个空的消息，并赋值what，然后发送到MessageQueue。
- boolean sendMessageDelayed (Message msg, long delayMillis)

在延迟delayMillis毫秒之后发送一个Message到MessageQueue。
#### Handler的用法

```
protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//创建handler对象
		handler = new Handler() {
		//重载handler的handlemessage方法
			@Override
			public void handleMessage(Message msg) {
				Log.i("TAG", "处理了事件");
				super.handleMessage(msg);
			}
		};

		Thread t1 = new Thread() {
			@Override
			public void run() {
			//新开一个线程，向主线程发送消息
				Message obtain = Message.obtain();
				handler.sendMessage(obtain);
				super.run();
			}
		};
		t1.start();
	}

```
非主线程创建handler：

```
Thread thread = new Thread() {
			public void run() {
			//在非主线程中使用handler要首先创建Looper
				Looper looper = Looper.myLooper();
				//将Looper作为线程私有数据保存起来
				looper.prepare();
				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						String TAG = "abc";
						Log.i(TAG, "handle message");
						super.handleMessage(msg);
					}
				};
			//新建一个线程，用来发送Message
				Thread thread = new Thread() {
					public void run() {
						Message obtain = Message.obtain();
						handler.sendMessage(obtain);
					};

				};
				thread.start();
				//最后一定要调用此方法，looper才算真正运行起来了，因为这个方法是无限循环，后面的方法可能不会得到执行，除非其他地方调用了quit方法
				looper.loop();

			};

		};
		thread.start();

```

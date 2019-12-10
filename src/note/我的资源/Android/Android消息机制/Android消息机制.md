[toc]
#### Android的消息机制概述
**Android的消息机制是指Handler的运行机制以及Handler所附带的MessageqQueue和Looper的工作过程**。
**主线程就是UI线程**

1. 
**Handler的工作原理：
Handler创建的时候会采用当前线程的Looper来构建内部的消息循环系统，如果线程没有Looper，那么就会报错。**

**Handler发送消息的两种方法：**

**post：post方法将一个Runnable投递到Handler内部的Looper中去处理**

**send：sendMessage即往消息队列中加一个message，这个消息同样会在Looper中被处理，post方法最终也是由send方法来实现的。**
****
#### Android的消息机制分析
##### ThreadLocal的工作原理
**ThreadLocal是一个线程内部的数据存储类，通过他可以在指定的线程中存储数据，数据存储以后，只能在指定线程中可以获取到存储的数据，而对于其他线程则无法获取到数据。**
**当某些数据是以线程为作用域的时候并且不同线程需要拥有不同范本的时候，就可以采用ThreadLocal。**


 **ThreadLocal另一个使用场景是复杂逻辑下的传递对象，比如监听器的传递，我们需要监听器贯穿整个线程的执行过程。这个时候我们就可以使用ThreadLocal，采用ThreadLocal可以让线程内的全局对象而存在，在线程内部通过get方法就可以获取到监听器。**
 
 **ThreadLocal的set方法：首先会通过values方法获取当前线程中的ThreadLocal数据，ThreadLocal的值在table数组中的存储位置总是ThreadLocal的reference字段所标识的对象的下一个位置。**
##### 消息队列的工作原理

**实际上它是通过一个单链表的数据结构来维护消息列表，单链表的插入和删除上比较有优势。它的enqueueMessage主要操作就是单链表的插入操作，next放法是一个无限循环的方法，如果消息队列中没有消息，那么next方法会一直阻塞在那里。**
##### Looper的工作原理
**Looper可以通过Looper。prepare()方法来创建一个Looper，接着通过Looper.loop()来开启消息循环。Looper的退出是quit和quitSafely来退出一个Looper。二者的区别是，quit会直接退出，而quitSafely只是设置了一个标记，然后消息队列中的消息安全处理完之后就会退出。**

**Looper最重要的方法是loop方法，只有调用了loop方法后，消息循环系统才会真的起作用，loop方法是一个死循环，唯一跳出循环的方式是MessageQueue的next方法返回了null，即调用了quit方法退出后，next的返回就是null。loop方法会调用MessageQueue的next方法获取新消息，而next方法是一个阻塞操作，当没有消息时，next方法会一直阻塞在那里，这会导致loop方法也会一直阻塞在那里。如果MessageQueue的next方法返回了新消息，Looper就会处理这条消息：msg.target.disapatchMessage(msg),这里的msg.target是发送这条消息的Handler对象，这样Handler发送的消息最终又交给它的dispatchMessage方法来处理。**

##### Handler的工作原理

**Handler发送消息的过程就是往消息队列中插入了一条消息，MessageQueue的next方法返回的就是这条消息给Looper，然后Looper收到消息就开始处理，最终消息由Looper交给Handler处理，即Handler的dispatchMessage方法会被调用**

**Handler处理消息的过程如下**
**首先会检查Message的callback是否为null。不为null就通过handleCallback来处理消息。Message的callback是一个Runnable对象，实际上就是Handler的post方法传递的Runnable参数，其次检查MCallback是否为null，不为null就调用MCallback的handleMessage方法来处理消息。最后，调用Handler的handMessage方法来处理消息。**
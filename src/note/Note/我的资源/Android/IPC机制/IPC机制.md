[toc]
#### 1.Android IPC简介
IPC是Inter—Process—Communication的缩写，含义为进程间通信或跨进程通信。

线程是CPU调度的最小单元，线程是一种有限的系统资源。
而进程一般是指一个执行单元，在PC或者移动设备上指一个程序或者一个应用。一个进程可以只有一个线程，成为主线程。在安卓里面主线程也叫UI线程，在UI线程里才能操作界面元素。

在安卓里面有一个特殊的名字加ANR，即程序无响应。解决这个问题就需要用到线程， 把一些耗时的任务放到线程里既可。
#### 2.Android中的多进程模式
##### 2.1开启多进程模式
在Android中多进程是指一个应用中存在多个进程的情况。在安卓中使用多进程只有一种方法，那就是给四个组件（Activity，Service，Receiver，ContentProvider)在AndroidMainfest中指定android：process属性。
Android系统会为每一个应用分配一个唯一的UID，具有相同UID的应用才能共享数据。
##### 2.2多进程模式的运行机制
Android为每一应用都分配了一个单独的虚拟机，或者说，为每个进程都分配了一个虚拟机，或者说为每一个进程分配了一个独立的虚拟机。多进程会造成如下方面的问题：

1）静态成员和单例模式完全失效

2）线程同步机制完全失效

3）SharePreferences的可靠性下降

4）Application会被多次创建
##### 2.3IPC基础概念介绍
Serializable和Parcelable接口可以完成对象的序列化过程，当我们需要通过Intent和Binder传输数据式就需要使用Parcelable和Serializable。
###### Serializable
Serializable是Java所提供的一个序列化接口，它是一个空接口，为对象提供标准的序列化和反序列化操作。操作也特别简单，只需要采用ObjectOutputSteam和ObjectInputSteam既可轻松实现。serialVersionUID是为了判断序列化的类的版本和当前类的版本是否相同。如果相同则可以成功序列化。
###### Parcelable
Parcelable也是一个接口，一个类的对象就可以实现序列化并可以通过Intent和Binder传递。
Parcel内部包装了可序列化的数据，可以在Binder中自由传输。

```
public class Album implements Parcelable {

    /**
     * 负责反序列化
     */
    private static final Creator<Album> CREATOR = new Creator<Album>() {
        /**
         * 从序列化对象中，获取原始的对象
         * @param source
         * @return
         */
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        /**
         * 创建指定长度的原始对象数组
         * @param size
         * @return
         */
        @Override
        public Album[] newArray(int size) {
            return new Album[0];
        }
    };



    private final String mId;
    private final String mCoverPath;
    private final String mDisplayName;
    private final long mCount;


    Album(String id, String coverPath, String displayName, long count) {
        mId = id;
        mCoverPath = coverPath;
        mDisplayName = displayName;
        mCount = count;
    }

    Album(Parcel source) {
        mId = source.readString();
        mCoverPath = source.readString();
        mDisplayName = source.readString();
        mCount = source.readLong();
    }

    /**
     * 描述
     * 返回的是内容的描述信息
     * 只针对一些特殊的需要描述信息的对象,需要返回1,其他情况返回0就可以
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCoverPath);
        dest.writeString(mDisplayName);
        dest.writeLong(mCount);
    }

```

##### Binder

Binder是Android的一个类，它实现了IBinder接口。从IPC角度来说，Binder是Android中的一种跨进程通信方式，Binder还可以理解为一种虚拟的物理设备，它的设备驱动是/dev/binder，改通信方式在Linux中没有；从AndroidFramework角度来说，Binder是ServiceManager连接各种Manager的桥梁，从Android应用层来说，Binder是客户端和服务端进行通信的媒介，当binderService的时候，服务端就会返回一个包含了一个服务端业务调用的Binder对象，通过这个对象，客户端就可以获取到服务端提供的数据和服务，这里的服务包括普通服务和基本的AIDL服务。
Binder中有两个重要的方法：linkToDeath和unlinkToDeath。Binder运行在服务端的过程中，如果服务端进程由于某种原因异常终止，这个时候我们服务端的Binder连接断裂（成为Binder死亡），会导致我们远程调用失败。这两个方法就来判断Binder是否死亡，死亡将会给我们发通知。
#### Android的IPC的方式
##### 使用Bundle
四大组件中的三大组件都是支持在Intent中传递Bundle数据的，由于Bundle实现了Parcelable接口，所以它方便在不同的进程间传输。
##### 使用文件共享
共享文件是一种很不错的进程间通讯方式。两个进程通过读写同一个文件来交换数据，在Windows上，一个文件如果被加上了排斥所导致其他线程无法对其访问，包括读写，Android基于Linux，使得并发读写文件可以没有限制的进行。
文件共享对文件格式是没有要求的，文本文件，xml都可以。
##### 使用messager
Message是一种轻量级的IPC方案，它的底层实现是AIDL。Messager的使用方法很简单，它对AIDL做了封装，使得我们可以更简单的进行进程间通信。同时，由于它一次就处理一个请求，因此我们在服务端不用考虑线程同步的问题，这是因为服务端中不存在并发执行的情形，实现一个Messager有如下几个步骤，分为服务端和客户端：

1.服务端进程：

首先我们需要在服务端创建一个Service来处理客户端的连接请求，同时创建一个Handler并用它来创建一个Messager对象 然后再Service的onBind中返回这个Messager对象底层的Binder既可。

2.客户端进程：

客户端进程中，首先要绑定服务端Service，绑定成功后用服务端返回的IBinder对象创建一个Messager，通过这个Messager就可以向服务端发送消息了，发消息类型为Message对象。如果服务端需要回应客户端，还需要和客户端一样，创建一个Handler。
##### OKhttp3中的设计模式
###### ①Builder模式
###### ②工厂模式
###### ③观察者模式
###### ④单例模式
###### ⑤策略者模式
###### ⑥责任链模式
##### 分析
##### 1.OkhttpClient
使用OKhttp的时候需要初始化一个OKhttp对象。OKhttp支持两种构造方式。

1.默认方式

```
public OKhttpClient(){//这种方式不需要配置任何参数，也就是说参数是默认的，调用的都是下面的构造函数。
    this(new Bulider());
}
```
2.builder模式，通过Bulider配置参数，最后通过bulider()方法
返回一个OKHttpClient实例。


```
public OkHttpClient build() {
      return new OkHttpClient(this);
    }
```
##### 2.Request
构建完OKhttpClient后就要构建一个Request对象。
```
Request(Builder builder) {
    this.url = builder.url;
    this.method = builder.method;
    this.headers = builder.headers.build();
    this.body = builder.body;
    this.tag = builder.tag != null ? builder.tag : this;
  }
```
当我们在构建一个request需要用Bulider模式进行创建。
###### Bulider源码

```
public Builder newBuilder() {
    return new Builder(this);
  }
//builder===================
public Builder() {
      this.method = "GET";
      this.headers = new Headers.Builder();
    }

Builder(Request request) {
      this.url = request.url;
      this.method = request.method;
      this.body = request.body;
      this.tag = request.tag;
      this.headers = request.headers.newBuilder();
    }
public Request build() {
      if (url == null) throw new IllegalStateException("url == null");
      return new Request(this);
    }
```
##### 3.异步请求
同步请求和异步请求的执行流程除了异步之外，基本都是一致的。
###### 构建完request后，我们就需要构建一个call，一般都是这样的Call call=cilent.newCall(request);

```
/**
   * Prepares the {@code request} to be executed at some point in the future.
   */
  @Override public Call newCall(Request request) {
    //工厂模式
    return RealCall.newRealCall(this, request, false /* for web socket */);
  }
```
实质上调用的是RealCall中的newRealCall方法。但是这里需要注意一点，那就是方法前面的@Override注解，看到这个注解我们就要意识到，这个方法不是继承就是实现接口。
```
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {...}
```
可以看到OKhttpClient实现了Call.Factory接口。

```
//Call.java
interface Factory {
    Call newCall(Request request);
  }
```
从接口源码我们也可以看出，这个接口其实并不复杂，仅仅是定义一个newCall用于创建Call的方法，这里其实用到了工厂模式的思想，将构建的细节交给具体实现，顶层只需要拿到Call对象即可。
回到主流程，我们继续看RealCall中的newRealCall方法。

```
final class RealCall implements Call {
...
static RealCall newRealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
    // Safely publish the Call instance to the EventListener.
    RealCall call = new RealCall(client, originalRequest, forWebSocket);
    call.eventListener = client.eventListenerFactory().create(call);
    return call;
  }
...
}
```

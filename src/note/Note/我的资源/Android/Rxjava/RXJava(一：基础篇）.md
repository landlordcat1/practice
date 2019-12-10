#### 基础
##### RXJava最核心的两个东西是Observables(被观察者，事件源)和Subscrilbers(观察者)。Observables发出的一系列事件，Subscribers处理这些事件。
##### 一个Observable可以发出另个或者多个事件，直到结束或者出错。每发出一个事件，就会调用Subscriber的onNext方法，最后调用Subscrilber.onNext()或者Subscrilber.onError()结束。
###### 如果一个Observerble没有任何的的Subscriber，那么这个Observable是不会发出任何事件的。
#### Hello world
创建一个Observable对象很简单，直接调用Observable.create既可

```
Observable<String> myObservable=Observable.create(
new Observable.OnSubscribe<String>(){
@Override
public void call(Subscriber<? super String> sub)
{
sub.onNext("hello world!");
sub.onCompleted();
}
}
);
```
##### 这里定义的Observable对象仅仅发出一个Hello World字符串，然后就结束了。接着我们创建一个Subscriber来处理Observable对象发出的字符串。

```
Subscriber<String> mySubscriber = new Subscriber<String>() {
    @Override
    public void onNext(String s) { System.out.println(s); }
 
    @Override
    public void onCompleted() { }
 
    @Override
    public void onError(Throwable e) { }
};
```
通过subscribe函数就可以将我们定义的myObservable对象和mySubscriber对象关联起来，这样就完成了subscriber对observable的订阅。

```
myObservable.subscribe(mySubscriber);
```
接下来看看如何简化Subscriber，上面的例子中，我们其实并不关心OnComplete和OnError，我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类。

```
Action1<String> onNextAction = new Action1<String>() {
    @Override
    public void call(String s) {
        System.out.println(s);
    }
};
```
##### 更简洁的代码
首先来看看如何简化Observable对象的创建过程。RxJava内置了很多简化创建Observable对象的函数，比如==Observable.just==就是用来创建只发出一个事件就结束的Observable对象，上面创建Observable对象的代码可以简化为一行。

```
Observable<String> myObservable = Observable.just("Hello, world!");
```
接下来看如何简化Subscriber,上面的例子中，我们不关心OnComplete和onError，我们只需要在onNext的时候做一些处理，这时候可以用Action1类。

```
Action1<String> onNextAction = new Action1<String>() {
    @Override
    public void call(String s) {
        System.out.println(s);
    }
};
```
subscribe方法有一个重载版本，接受三个Action1类型的参数，分别对应OnNext，OnComplete， OnError函数。

```
myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
```
这里我们并不关心onError和onComplete，所以只需要第一个参数就可以

```
myObservable.subscribe(onNextAction);
// Outputs "Hello, world!"
```
上述的代码最终可以写成这样

```
Observable.just("Hello, world!")
    .subscribe(new Action1<String>() {
        @Override
        public void call(String s) {
              System.out.println(s);
        }
    });
```

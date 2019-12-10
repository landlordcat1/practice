###### 在默认情况下当我们启动一个同一个人Activity的时候，系统会创建任务栈把他们一一放入任务栈中，当我们单机back键时，会发现这些Activity会一一回退。任务栈是一种“后进后出”的栈结构，当这个栈为空时，就会回收这个栈。为了解决打开同一个Activity而创建多个实例的问题。安卓提供了启动模式来解决这个问题。目前有四种启动模式：standrad:标准模式，singleTop：栈顶复用模式，singleTask：栈内复用模式，singleInstance：单实例模式

#### ==stardard==:标准模式：是系统默认的默认模式。每次启动一个Activity都会创建一个新的实例，不管这个实例是否存在。不管这个实例是否已经存在。被创建的实例的生命周期符合典型Activity的生命周期。==一个任务栈中可以有多个实例，每个实例也可以属于不同的栈==。谁启动了这个Activity，那么这个Activity就运行在启动它的那个Activity的栈中。 
#### ==singleTop==:栈顶复用模式。这种模式下，如果Activity已经位于任务栈的栈顶，则这个Activity不会被重新创建，但会调用Activity的onNewIntent方法，通过此方法可以取出它当前请求的信息，但是onCreate方法和onStart方法不被系统调用，因为没有被改变。 但是如果Activity的实例已经存在但不在栈顶，新的Activity仍然会重建新的Activity。

#### ==singleTask==:栈内复用模式。这种模式下，只要一个Activity在一个栈中存在，那么多次启动该Activity都不会重新创建，和singleTop一样，系统会调用onNewIntent（）方法。当一个活动请求启动时，会先在栈中检查是否存在A需要的任务栈。？                            
#### 如果不存在，就重新创建一个任务栈，然后创建A的实例后把A放在栈中。如果存在A所需的任务栈，这时要看在栈中是否有实例存在，如果有实例存在，那么系统就会把A调到栈顶创建它的onNewIntent方法。

#### ==singleInstance==：是一种加强的singleTask模式，他除了具有singleTask模式的所有特性之外，还加强了一点，具有此种模式的Activity只能单独的位于任务栈中。
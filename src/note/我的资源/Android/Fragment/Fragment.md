### 关于Fragment
- fragment是什么
- fragment为何要用
- fragment生命周期
- fragment通信
- fragment使用
#### fragment是什么
##### Fragment是一个有自己生命周期的控件，它有自己的处理事件能力，有自己的生命周期，又必须依赖于Activity，能互相通信和托管。
#### fragment为何要用（fragment的优势）
- 代码复用：特别适用于模块化的开发，因为一个Fragment可以被多个Activity嵌套，有个共同的业务模块就可以复用了，是模块化UI的良好组件。
- Activity用来管理Fragment。Fragment的生命周期是寄托到Activity中，Fragment可以被Attach添加和Detach释放
- 可控性，Fragment可以像普通对象那样自由的创建和控制，传递参数更加容易和方便，也不用处理系统相关的事情，显示方式、替换、不管是整体还是部分，都可以做相应的修改。
- Fragment是view controllers,它们包含可测试的，解耦的业务逻辑块，由于Fragment是构建在views之上的，而views很容易实现动画效果，因此fragment在屏幕切换时具有更好的控制。
#### fragment生命周期
```
graph TD
A[添加一个碎片]-->B[onAttach]
B-->C[onCreate]
C-->D[onCreateView]
D-->E[onActivityCreated]
E-->F[onStart]
F-->G[onResum]
G-->H(碎片已激活)
H-->|用户点击返回键或者碎片替换|I[onPause]
I-->J[onStop]
J-->K[onDestoryView]
K-->D
K-->L[onDestroy]
L-->M[onDetach]
M-->N[碎片已销毁]
```
- [ ] onAttach()
- Fragment和Activity建立关联的时候调用，被附加到Activity中去。
- [ ] onDetach()
- Fragment和Activity解除关联的时候调用
- [ ] onCreate()
- 系统在创建Fragment时调用此方法。可以初始化一段资源文件。
- [ ] onCreateView()
系统会在Fragment首次绘制其用户界面时调用此方法。 要想为Fragment绘制 UI，从该方法中返回的 View 必须是Fragment布局的根视图。如果Fragment未提供 UI，您可以返回 null。
- [ ] onViewCreated()
- 在Fragment被绘制后，调用此方法，可以初始化控件资源。
- [ ] onActivityCreated()
- 当onCreate()，onCreateView()，onViewCreated()方法执行完后调用，也就是Activity被渲染绘制出来后。
- [ ] onPause()
- 系统将此方法作为用户离开Fragment的第一个信号（但并不总是意味着此Fragment会被销毁）进行调用。 通常可以在此方法内确认在当前用户会话结束后仍然有效的任何更改（因为用户可能不会返回）。
- [ ] onDestroyView()
Fragment中的布局被移除时调用。
> 但是需要注意的一点是：除了onCreateView，其他的所有方法如果重写了，必须调用父类对该方法的实现。
#### fragment如何使用
##### 静态使用
1、继承Fragment,重写onCreateView决定Fragment的布局
2.在Activity中声明此Fragment，就当普通的View一样
首先是布局文件：fragment1.xml

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:background="#00ff00" >  
  
    <TextView  
        android:layout_width="wrap_content"  
        android:layout_height="wrap_content"  
        android:text="This is fragment 1"  
        android:textColor="#000000"  
        android:textSize="25sp" />  
  
</LinearLayout>  
```

可以看到，这个布局文件非常简单，只有一个LinearLayout，里面加入了一个TextView。我们再新建一个fragment2.xml ：

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:background="#ffff00" >  
  
    <TextView  
        android:layout_width="wrap_content"  
        android:layout_height="wrap_content"  
        android:text="This is fragment 2"  
        android:textColor="#000000"  
        android:textSize="25sp" />  
  
</LinearLayout>  
```

然后新建一个类Fragment1，这个类是继承自Fragment的：

```
 public class Fragment1 extends Fragment {  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        return inflater.inflate(R.layout.fragment1, container, false);  
    }  
} 
```

可以看到，在onCreateView()方法中加载了fragment1.xml的布局。同样fragment2.xml也是一样的做法，新建一个Fragment2类：

```
public class Fragment2 extends Fragment {  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        return inflater.inflate(R.layout.fragment2, container, false);  
    }  
}  
```

然后打开或新建activity_main.xml作为主Activity的布局文件，在里面加入两个Fragment的引用，使用android:name前缀来引用具体的Fragment：

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:baselineAligned="false" >  
  
    <fragment  
        android:id="@+id/fragment1"  
        android:name="com.example.fragmentdemo.Fragment1"  
        android:layout_width="0dip"  
        android:layout_height="match_parent"  
        android:layout_weight="1" />  
  
    <fragment  
        android:id="@+id/fragment2"  
        android:name="com.example.fragmentdemo.Fragment2"  
        android:layout_width="0dip"  
        android:layout_height="match_parent"  
        android:layout_weight="1" />  
  
</LinearLayout>  
```

最后新建MainActivity作为程序的主Activity，里面的代码非常简单，都是自动生成的：

```
public class MainActivity extends Activity {  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
    }  
}  
```
##### 动态用法
还是在静态用法代码的基础上修改，打开activity_main.xml，将其中对Fragment的引用都删除，只保留最外层的LinearLayout，并给它添加一个id，因为我们要动态添加Fragment，不用在XML里添加了，删除后代码如下：

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:id="@+id/main_layout"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:baselineAligned="false" >  
  
</LinearLayout>  
```

然后打开MainActivity，修改其中的代码如下所示：

```
public class MainActivity extends Activity {  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        Display display = getWindowManager().getDefaultDisplay();  
        if (display.getWidth() > display.getHeight()) {  
            Fragment1 fragment1 = new Fragment1();  
            getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commit();  
        } else {  
            Fragment2 fragment2 = new Fragment2();  
            getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commit();  
        }  
    }  
  
}  
```
动态添加Fragment主要分四步：
1.获取到FragmentManager,在Activity中可以直接通过getFragmentManager得到。
2.开启一个事务，通过调用beginTransaction方法开启。
3.向容器中加入Fragment，一般调用replace方法实现，需要传入容器的ID和Fragment的实例。
4.提交事务，调用commit提交。
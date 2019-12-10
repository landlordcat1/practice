#### Intent
##### Intent是Android程序中各组件之间交互的一种重要方式，它不仅可以指明当前组件想要执行的动作，还可以在不同组件之间传递数据。Intent一般可被用于启动活动、启动服务以及发送广播等场景
##### Intent 大致可以分为两种：显式Intent和隐式Intent

```
Intent intent=new Intent（FirstActivity.this,SecondActivity.class);
startActivity(intent);
```
我们先构建一个出一个Intent，传入FirstActivity.this作为上下文，传入SecondActivity.class作为目标活动。

##### 隐式Intent

```
Intent intent=new Intent(Intent.ACTION_VIEW);
intent.setData(URi.parse("http://www.baidu.com"));
startAcitivity(intent);
```
##### 向下一个活动传递数据
###### firstacitivity

```
String data=“hello world”；
Intent intent=new Intent（FirstActivity.this,SecondAcitivity.class);
intent.putExtra("extra_data",data);
startAcitivity(intent);
```
###### secondacitivity

```
Intent intent=getIntent();
String data=intent.getStringExtra("extra_data");
```
##### 返回数据给上一个活动
###### firstacitivity

```
Intent intent=new intent（FirstAcitivity.this,SecondAcitivity.this);
startAcitivityForResult(intent,1);
```
###### secondacitivity

```
Intent intent=new Intent();
intent.putExtra("data_return","Hello World");
setResult(RESULT_OK,intent);
finish();
```
###### firstacitivity
重写onActivityResult


```
onActivityResult(int requestCode,int resultCode,Intent data)
{
    String returnData=data.getStringExtra("data return");
}
```
//不通过按钮只通过back键传值
在SecondAcitivity中通过重写onBackPressed（）

```
public void onBackPressed()
{
    Intent intent=new Intent();
    intent.putExtra("data_return","Hello world");
    setResult(RESULT_OK,intent);
    finish();
}
```

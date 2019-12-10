##### Java中任何一个对象都具备equals和hashcode这两个方法，因为他们是在Object类中定义的。equals方法用来判断两个对象是否“相同”，如果相同则返回“true”，否则则返回“false”。hashcode（）方法则是返回一个int数，在Object类中的默认实现是“将该对象的内部地址转换为一个整数”。接下来有两个关于这两个方法的重要规范：

###### 规范一：若重写equals（Object obj）方法，有必要重写hashcode（）方法，确保通过equals（Object object）返回true而hashcode（）返回两个不相等的值，编译和运行都不会报错的。不过这样违反了JAVA规范，程序也就埋下了BUG。

###### 规范二：如果equals（Object obj）返回false，即两个对象不相同，并不要求这两个对象调用的hashcode()返回两个不相等的值。
#### “==”
##### 1.‘==’是用来比较两个变量（基本类型和对象类型）的值是否是相等的，如果两个 变量是基本类型的，那很容易，直接比较值就可以了。如果两个变量是对象类型的，那么它还是比较值，只是它比较的是这两个对象在栈中的引用（即地址）。对象是放在堆中的，栈中存放对象的引用（地址）。‘ == ’是对栈中的值进行比较的，如果要对堆中对象的内容进行比较，就要重写equals方法了。
##### 2.Object的equals方法和‘==’是等价的。通常我们会重写equals方法，让equals方法重写两个对象的内容，而不是比较对象的引用（地址），因为往往任何对象的hashcode都是不相等的。
##### 3.Object类中的hashcode是返回对象在内存中地址转换成一个int值（可以当做地址看）。所以如果没有重写hashcode方法，任何对象的hashcode都是不相等的。
##### 4.String、Integer、Boolean、Double等这些类都重写的equals和hashcode方法，这两个方法是根据对象的 内容来比较和计算hashcode的。所以只要对象的基本类型值相同，那么hashcode就一定相同。
##### 5.equals（）相等的两个对象，hashcode（）一般是相等的，最好在重写equals（）方法时，重写hashcode方法。
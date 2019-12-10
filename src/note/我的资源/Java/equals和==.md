#### equals和==
##### 在比较基本类型时，“==" 比较的是字面值，在比较引用类型（对象）时，“ ==”比较的是内存地址。
##### 而equals一直比较的是字面值的大小
```
Integer a=16;
Integer b=16;
System.out.println(a==b);
//结果是TRUE
```
原因是

```
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.x)];
    return new Integer(i);
    }
    //范围是-127--128

```
即如果在这个范围内，Integer就会直接从数组中拿东西。


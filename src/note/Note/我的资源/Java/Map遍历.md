方法一 在for-each循环中使用entries来遍历

这是最常见的并且在大多数情况下也是最可取的遍历方式。在键值都需要时使用。

```
Map<Integer, Integer> map = new HashMap<Integer, Integer>();
 
for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
 
    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
 
}

```
##### Map提供了一些常用方法，如keySet()、entrySet()等方法。
##### keySet()方法返回值是Map中key值的集合；entrySet()的返回值也是返回一个Set集合，此集合的类型为Map.Entry。
##### Map.Entry是Map声明的一个内部接口，此接口为泛型，定义为Entry<K,V>。它表示Map中的一个实体（一个key-value对）。接口中有getKey(),getValue方法。


注意：for-each循环在java 5中被引入所以该方法只能应用于java 5或更高的版本中。如果你遍历的是一个空的map对象，for-each循环将抛出NullPointerException，因此在遍历前你总是应该检查空引用。

 

方法二 在for-each循环中遍历keys或values。

如果只需要map中的键或者值，你可以通过keySet或values来实现遍历，而不是用entrySet。

```

Map<Integer, Integer> map = new HashMap<Integer, Integer>();
 
//遍历map中的键
 
for (Integer key : map.keySet()) {
 
    System.out.println("Key = " + key);
 
}
 
//遍历map中的值
 
for (Integer value : map.values()) {
 
    System.out.println("Value = " + value);
 
}

```


该方法比entrySet遍历在性能上稍好（快了10%），而且代码更加干净。

 

方法三使用Iterator遍历

使用泛型：


```
Map<Integer, Integer> map = new HashMap<Integer, Integer>();
 
Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();
 
while (entries.hasNext()) {
 
    Map.Entry<Integer, Integer> entry = entries.next();
 
    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
 
}

```

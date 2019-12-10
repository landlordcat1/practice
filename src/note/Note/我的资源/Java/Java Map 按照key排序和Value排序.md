### 1.按键排序
##### jdk内置的Java.util包下的TreeMap<K,V>既可满足此类需求，TreeMap（Comparator<? super K> comparator) 传入我们自定义的比较器既可实现按键排序。
实现代码

```
public static Map<Integer,Integer> sortMapByKey(Map<Integer,Integer> map)
{
    if(map==null||map.isEmpty())
    {
        return null;
    }
    Map<Integer,Integer> sortMap=new TreeMap<Integer,Integer>
    {
        new MapKeyComparator()
    };
    sortMap.putAll(map);
    return sortMap;
}
class MapKeyComparator implements Comparator<Integer>{
    public int compare(Integer a,Integer b)
    {
        return a.compareTo(b);
    }
}

```
### 2.按值排序

```
public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}
```
比较器类

```
class MapValueComparator implements Comparator<Map.Entry<String, String>> {

    @Override
    public int compare(Entry<String, String> me1, Entry<String, String> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}
```

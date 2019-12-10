### 缓存机制
#### 缓存概念
##### 服务器缓存
服务端缓存又分为代理服务器缓存和反向代理服务器缓存。常见的CDN就是服务器缓存。当浏览器重复访问一张图片地址的时候，CDN会判断这个请求会不会有缓存，如果有的话就直接返回这个缓存的请求回复，而不再需要让请求达到真正的服务地址，这么做的目的是为了减轻服务端的运算压力。
##### 客户端缓存
当客户端首次请求服务器的接口时，如果服务器返回的数据正常，那么客户端将数据返回到本地，当客户端访问同一个地址的时候，客户端会检查本地有没有缓存，如果有缓存的话，查看数据是否过期，如果没有过期则直接用本地缓存。
#### 和客户端有关的缓存设置
在OKhttp开发中我们常见的有以下几个
- max-age
- no-cache
- max-stale
###### 服务器支持缓存
##### 如果服务器支持缓存，请求返回的Response会带有这样的Header:Cache-Control, max-age=xxx,这种情况下我们只需要手动给okhttp设置缓存就可以让okhttp自动帮你缓存了。这里的max-age的值代表了缓存在你本地存放的时间，可以根据实际需要来设置其大小。

##### okhttp已经内置了缓存，默认是不使用的，如果我们想要缓存是需要我们手动设置的。

```
httpClientBuilder
    .cache(cache)
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
```
###### 服务器不支持缓存
##### 如果服务器不支持缓存可能就没有指定这个头部，或者指定的值是如no-store等，但我们还是想本地使用缓存，这种情况下我们就需要使用Interceptor来重写Response的头部信息，让OKhttp支持缓存。

```
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response response1 = response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            //cache for 30 days
            .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
            .build();
        return response1;
    }
}
```
##### 然后将该Intercepter作为一个NetworkInterceptor加入到okhttpClient中：

```
httpClientBuilder
    .addNetworkInterceptor(new CacheInterceptor())
    .cache(cache)
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
```
OKhttp官方文档缓存方法

```
/**强制使用网络请求*/
public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();

/**强制性使用本地缓存，如果本地缓存不满足条件，则会返回code为504*/
public static final CacheControl FORCE_CACHE = new Builder()
  .onlyIfCached()
  .maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS)
  .build();
```
### 连接池

```
1.频繁的进行Socket连接（TCP三次握手）和断开Socket（TCP四次分手）是非常消耗网络资源和
浪费时间的，HTTP的keepalive连接对于降低延迟和提升速度是非常重要的作用的
2.复用连接就需要对连接进行管理，这就引入了连接池的概念。
3.OKhttp支持5个并发KeepAlive，默认链路生命为5分钟（链路空闲后，保持存活的时间），
连接池有ConnectionPool实现，对连接进行回收和管理。
```
#### 连接池构造方法
###### 构造方法中，设置了每个地址的最大空闲数maxldleConnection以及默认的每个链接的存活时间keepAliveDurationNs

```
public final class ConnectionPool {
    //最大的空闲连接数--每个地址的最大空闲连接数
    private final int maxIdleConnections;
    //连接持续时间
    private final long keepAliveDurationNs;

    //默认每个地址的最大连接数是5个
    //默认每个连接的存活时间为5分钟
    public ConnectionPool() {
        this(5, 5, TimeUnit.MINUTES);
    }
    
    public ConnectionPool(int maxIdleConnections, long keepAliveDuration, TimeUnit timeUnit) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationNs = timeUnit.toNanos(keepAliveDuration);

        // Put a floor on the keep alive duration, otherwise cleanup will spin loop.
        if (keepAliveDuration <= 0) {
            throw new IllegalArgumentException("keepAliveDuration <= 0: " + keepAliveDuration);
        }
    }

}
```
##### 设置连接池

```
OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //创建具有自定义设置的共享实例
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
    static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableList(
   public Builder() {
   ...
        //连接池 管理HTTP和HTTP / 2连接的重用以减少网络延迟。
        //默认每个地址的最大连接数是5个
        //默认每个连接的存活时间为5分钟
        connectionPool = new ConnectionPool();
   ...
   }
}
```
##### 连接的存储和删除
###### 双端队列
连接池中维持了一个双端队列Deque来存储连接

```
private final Deque<RealConnection> connections = new ArrayDeque<>();
```
###### 连接的存储
将连接加入到双端队列

```
public final class ConnectionPool {
    void put(RealConnection connection) {
        assert (Thread.holdsLock(this));
        //没有任何连接时,cleanupRunning = false;
        // 即没有任何链接时才会去执行executor.execute(cleanupRunnable);
        // 从而保证每个连接池最多只能运行一个线程。
        if (!cleanupRunning) {
            cleanupRunning = true;
            executor.execute(cleanupRunnable);
        }
        connections.add(connection);
    }
}
```

```
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
    static {
        Internal.instance = new Internal() {
          ...
            @Override
            public void put(ConnectionPool pool, RealConnection connection) {
                pool.put(connection);
            }
          ...
        }
    }
}
```
###### 连接的清理
##### 连接池中维护了一个线程池，这线程池只开启了一个线程用来清理链接。整体流程如下：
- ##### 查询此链接内部的StreanAllocation的引用数量
- ##### 标记空闲连接
- ##### 如果空闲连接超过5个或者keepalive事件大于5分钟，则将该连接清理掉
- ##### 返回此连接的到期时间，供下次进行清理
- ##### 全部是活跃连接，则下次进行清理
- ##### 没有任何连接，跳出连接
- ##### 关闭连接，返回时间为0，立即进行下次清理

```
public final class ConnectionPool {
    /**
     * Background threads are used to cleanup expired connections. There will be at most a single
     * thread running per connection pool. The thread pool executor permits the pool itself to be
     * garbage collected.
     * 后台线程用于清理过期的连接。 每个连接池最多只能运行一个线程。 线程池执行器允许池本身被垃圾收集。
     */
    private static final Executor executor = new ThreadPoolExecutor(0 /* corePoolSize */,
            Integer.MAX_VALUE /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));
    //corePoolSize=0 maximumPoolSize=Integer.MAX_VALUE 使用SynchronousQueue直接提交队列,
    // 在执行execute会立马交由复用的线程或新创建线程执行任务
    //清理连接，在线程池executor里调用。
    private final Runnable cleanupRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                //执行清理，并返回下次需要清理的时间。
                // 没有任何连接时,cleanupRunning = false;

                long waitNanos = cleanup(System.nanoTime());
                if (waitNanos == -1) return;
                if (waitNanos > 0) {
                    long waitMillis = waitNanos / 1000000L;
                    waitNanos -= (waitMillis * 1000000L);
                    synchronized (ConnectionPool.this) {
                        //在timeout时间内释放锁
                        try {
                            ConnectionPool.this.wait(waitMillis, (int) waitNanos);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        }
    };
```
###### cleanup(long now)
##### 在符合条件下将连接清理出双端队列：

```
    //对该池执行维护，如果它超出保持活动限制或空闲连接限制，则驱逐空闲时间最长的连接。
    //返回纳秒级的持续时间，直到下次预定调用此方法为止。 如果不需要进一步清理，则返回-1。
    long cleanup(long now) {
        int inUseConnectionCount = 0;
        int idleConnectionCount = 0;
        RealConnection longestIdleConnection = null;
        long longestIdleDurationNs = Long.MIN_VALUE;

        // Find either a connection to evict, or the time that the next eviction is due.
        synchronized (this) {
            //遍历所有的连接，标记处不活跃的连接。
            for (Iterator<RealConnection> i = connections.iterator(); i.hasNext(); ) {
                RealConnection connection = i.next();

                // If the connection is in use, keep searching.
                //1. 查询此连接内部的StreanAllocation的引用数量。
                if (pruneAndGetAllocationCount(connection, now) > 0) {
                    inUseConnectionCount++;
                    continue;
                }

                idleConnectionCount++;

                // If the connection is ready to be evicted, we're done.
                //2. 标记空闲连接。
                long idleDurationNs = now - connection.idleAtNanos;
                if (idleDurationNs > longestIdleDurationNs) {
                    longestIdleDurationNs = idleDurationNs;
                    longestIdleConnection = connection;
                }
            }

            if (longestIdleDurationNs >= this.keepAliveDurationNs
                    || idleConnectionCount > this.maxIdleConnections) {
                // We've found a connection to evict. Remove it from the list, then close it below (outside
                // of the synchronized block).
                //3. 如果空闲连接超过5个或者keepalive时间大于5分钟，则将该连接清理掉。
                connections.remove(longestIdleConnection);
            } else if (idleConnectionCount > 0) {
                // A connection will be ready to evict soon.
                //4. 返回此连接的到期时间，供下次进行清理。
                return keepAliveDurationNs - longestIdleDurationNs;
            } else if (inUseConnectionCount > 0) {
                // All connections are in use. It'll be at least the keep alive duration 'til we run again.
                //5. 全部都是活跃连接，5分钟时候再进行清理。
                return keepAliveDurationNs;
            } else {
                // No connections, idle or in use.
                //6. 没有任何连接，跳出循环。
                cleanupRunning = false;
                return -1;
            }
        }
        //7. 关闭连接，返回时间0，立即再次进行清理。
        closeQuietly(longestIdleConnection.socket());

        // Cleanup again immediately.
        return 0;
    }
```
###### 查询此连接内部的StreamAllocation的引用数量（连接是否可用）
###### 引用计数法来判断

```
public final class RealConnection extends Http2Connection.Listener implements Connection {
    /**
     * Current streams carried by this connection.
     * 由此连接携带的当前流。
     */
    public final List<Reference<StreamAllocation>> allocations = new ArrayList<>();
}
```

```
public final class ConnectionPool {
    /**
     * Prunes any leaked allocations and then returns the number of remaining live allocations on
     * {@code connection}. Allocations are leaked if the connection is tracking them but the
     * application code has abandoned them. Leak detection is imprecise and relies on garbage
     * collection.
     * *修剪任何泄漏的分配，然后返回{@code connection}上剩余的实时分配数量。
     * 如果连接正在跟踪它们，但是应用程序代码已经放弃它们，则分配会泄漏。
     * 泄漏检测不准确，依靠垃圾收集。
     */


    private int pruneAndGetAllocationCount(RealConnection connection, long now) {
        //虚引用列表
        List<Reference<StreamAllocation>> references = connection.allocations;
        //遍历虚引用列表
        for (int i = 0; i < references.size(); ) {
            Reference<StreamAllocation> reference = references.get(i);
            //如果虚引用StreamAllocation正在被使用，则跳过进行下一次循环，
            if (reference.get() != null) {
                //引用计数
                i++;
                continue;
            }

            // We've discovered a leaked allocation. This is an application bug.
            StreamAllocation.StreamAllocationReference streamAllocRef =
                    (StreamAllocation.StreamAllocationReference) reference;
            String message = "A connection to " + connection.route().address().url()
                    + " was leaked. Did you forget to close a response body?";
            Platform.get().logCloseableLeak(message, streamAllocRef.callStackTrace);
            //否则移除该StreamAllocation引用
            references.remove(i);
            connection.noNewStreams = true;

            // If this was the last allocation, the connection is eligible for immediate eviction.
            // 如果所有的StreamAllocation引用都没有了，返回引用计数0
            if (references.isEmpty()) {
                connection.idleAtNanos = now - keepAliveDurationNs;
                return 0;
            }
        }
        //返回引用列表的大小，作为引用计数
        return references.size();
    }
}
```

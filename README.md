### POTATAO 基于TCP传输协议的点对点远程调用框架


POTATAO 是基于websocket传输协议的点对点远程调用框架，框架通过`@Prpc/@PrpcKey`来加载服务，使用`URL`的方式来调用服务。  

#### 使用示例
1 建立远程调用服务，如下：
```java
/**
 * Created by mengdadou on 17-9-25.
 */
@Potato(pool = 100)
// rpc服务描述和处理池
public class PotatoService {
	private static Logger logger = LoggerFactory.getLogger(PotatoService.class);

	@PotatoRpc(path = "/user/v1/getName")
	// 业务url，全局唯一
	public String getNameV1(long userId) {
		System.out.println(String.format("accept userId %s", userId));
		return "this is v1 li ming" + userId;
	}

	@PotatoRpc(path = "/user/v2/getName")
	public String getNameV2(@PotatoCtx PotatoCtxBean ctx, long userId) {
		System.out.println(String.format("accept userId %s from %s", userId, ctx.getRemote()));
		return "this is v2 li ming" + userId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
```

2 启动server服务，远程调用
```java
/**
 * Created by mengdadou on 17-10-16.
 */
public class PotatoTest {
    private static Logger logger = LoggerFactory.getLogger(PotatoTest.class);

    public static void main(String[] args) throws RemoteException, ExecutionException, TimeoutException, InterruptedException {
        PotatoRpc.start();

        for (int i = 0; i < 5; i++) {
            Response sync = CallHelper.sync("potato://127.0.0.1/a/user/v2/getName", i);
            logger.info(sync.getData().toString());
        }
    }
}

```
输出：
```
accept userId 0 from 127.0.0.1
2018-05-18 14:55:14,058 INFO o.m.t.s.PotatoTest [main] >>> this is v2 li ming0
accept userId 1 from 127.0.0.1
2018-05-18 14:55:14,060 INFO o.m.t.s.PotatoTest [main] >>> this is v2 li ming1
accept userId 2 from 127.0.0.1
2018-05-18 14:55:14,062 INFO o.m.t.s.PotatoTest [main] >>> this is v2 li ming2
accept userId 3 from 127.0.0.1
2018-05-18 14:55:14,064 INFO o.m.t.s.PotatoTest [main] >>> this is v2 li ming3
accept userId 4 from 127.0.0.1
2018-05-18 14:55:14,066 INFO o.m.t.s.PotatoTest [main] >>> this is v2 li ming4
```

#### 框架结构

potato 传输层使用Netty接触信息传输，调用层使用Java注解加载和调用服务

框架由两部分组成：

1 potato-net 网络处理，

支持自定义

* 可以自定义TCP Conn事件处理逻辑（可以定义黑白名单认证等）；
* 可以自定义Data时间处理逻辑（处理Request/Response，当你只使用一个message exchange的时候）；
* 可以自定义编码格式，默认使用的PROTOSTUF

2 potato-rpc 调用处理，

支持自定义

* 可以自定义URL解析，默认是potato://127.0.0.1/channelID/path...，其中channelID可以是点对点之间建立多个用到通信，path为业务唯一标识；
* 可以自定义ID生成器，默认使用snowflake算法实现，因此需要配置机器ID；
# 分布式事务示例

模拟用户采购商品业务实现，整个业务包含三个微服务:

- 库存服务（storage）: 扣减给定商品的库存数量。
- 账户服务（account）: 用户账户金额扣减。
- 订单服务（order）: 订单交易服务。

本案例采用`Seata`实现分布式事务，业务过程为订单保存时同时扣除商品库存及账户金额，任何一个环节
出错则回滚所有的服务的数据。

> 开始前请阅读[seata官方文档](http://seata.io/zh-cn/docs/overview/what-is-seata.html)。

## 1、依赖环境

> 本示例使用到的相关依赖环境。

### 1.1、数据库

本案例使用`PostgresQL9.6`数据库。

> [PostgresQL9.6官方文档](https://www.postgresql.org/docs/9.6/index.html)

### 1.2、Seata服务端

本案例使用`Seata1.4.1`服务端并采用容器化方式运行，使用`PostgresQL9.6`数据库作为事务日志存储。

> `Seata`服务端默认端口为`8091`。

### 1.3、Seata客户端

本案例的微服务采用`spring-boot`方式进行配置，每个工程都导入如下依赖：

```xml
<dependency>
  <groupId>io.seata</groupId>
  <artifactId>seata-spring-boot-starter</artifactId>
  <version>1.4.1</version>
</dependency>
```

> 每个`Seata`客户端需要连接至`Seata`服务端。

### 1.4、`Docker`环境

为了方便依赖环境的搭建，请先安装好`Docker`环境。

## 2、开发指南

> 本案例使用`Seata`最简单的`AT`数据源代理模式，此方法基于本地`ACID`事务的关系型数据库并且使用`AOP`机制实现，
> 非常适用于`Java`通过`JDBC`访问数据库（本案例采用`Java`、`SpringBoot`、`MyBatisPlus`、`Postgres`）。
> 具体请参见<http://seata.io/zh-cn/docs/overview/what-is-seata.html>。

### 2.1、组件角色

本案例中订单服务（`order`）为作为交易服务中间件（`TM`角色）调用库存服务（`storage`，`RM`角色）与账户服务（`account`，`RM`角色）。

> 使用`Seata`客户端进行分布式事务应用开发时，我们通常在交易服务上中使用`io.seata.spring.annotation.GlobalTransactional`进行注解，
> 注解了`GlobalTransactional`的服务负责全局事务管理（`TM`），其他未注解`GlobalTransactional`的服务我们称之为资源管理务器（`RM`）。

**关于`TM`与`RM`名词**

> `X/Open`的组织定义了分布式事务的模型，这里面有几个角色，`AP`（`Application`，应用程序），`TM`（`Transaction Manager`，事务管理器），
> `RM`（`Resource Manager`，资源管理器），`CRM`（`Communication Resource Manager`，通信资源管理器）。

### 2.2、事务传播

> 使用`Seata`客户端时，服务之间的调用需要传递`XID`（全局事务`ID`），通过`XID`的传递才能完成全局分布式事务管理。

本示例使用`retrofit2`调用其他微服务接口，因此使用了`okhttp3`的请求拦截器添加`XID`请求头来传递全局事务`ID`，参见如下代码：

```java
new okhttp3.Interceptor() {
  @Override
  public Response intercept(Chain chain) throws IOException {
    String xid = RootContext.getXID();
    if (StringUtils.isEmpty(xid)) {
      return chain.proceed(chain.request());
    }
    // 传播分布式事务ID至其他微服务服务接口
    okhttp3.Request.Builder new_req_builder = chain.request().newBuilder();
    new_req_builder.removeHeader(RootContext.KEY_XID).addHeader(RootContext.KEY_XID, xid);
    return chain.proceed(new_req_builder.build());
  }
}
```

> 具体请参见<order/src/main/com/snz1/seatas/order/client/ClientConfig.java>文件中的代码。

### 2.3、开发测试

开始之前请先准备好`Docker`环境，然后根据您的操作系统类型下载工具文件：

- [Windows amd64](http://gitlab.snz1.cn:2007/download/snz1dp/snz1dpctl-windows-amd64.exe)
- [Linux amd64](http://gitlab.snz1.cn:2007/download/snz1dp/snz1dpctl-linux-amd64)
- [MacOS amd64](http://gitlab.snz1.cn:2007/download/snz1dp/snz1dpctl-darwin-amd64)

下载好工具文件后，把保存至`~/.snz1dp/bin`（Window下为`%HOMEDRIVE%\%HOMEPATH%\.snz1dp\bin`）
目录并重命名为`snz1dpctl`（`Windows`下为`snz1dpctl.exe`），然后把该目录加入可执行文件搜索目录`PATH`环境变量中。

> 如果你想直接运行本示例的服务来直接测试分布式事务效果，则可以直接运行以下命令一键启动依赖及示例服务：

```cmd
snz1dpctl profile setup http://gitlab.snz1.cn:2007/download/seata/example.yaml
snz1dpctl standalone start profile
```

***准备开发依赖***

进入任何一个子模块目录中执行以下命令可以启动相关依赖服务：

```cmd
cd storage
snz1dpctl make standalone develop
```

也可以通过以下命令停止所有的依赖服务：

```cmd
cd storage
snz1dpctl make standalone stop-all -y
```

***VSCode调试***

使用`VSCode`打开`workspace.code-workspace`文件，然后分别启动`storage`、`account`、`order`中的`Application`。

***命令运行***

新开三个不同的命令行，然后分别进入`storage`、`account`、`order`子目录中执行启动命令，命令如下所示：

```cmd
cd storage
snz1dpctl make run
```

```cmd
cd account
snz1dpctl make run
```

```cmd
cd order
snz1dpctl make run
```

> 需要退出则在命令行窗口中按下`CTRL+C`组合键。

***独立运行***

如果需要以容器方式运行`storage`、`account`、`order`服务，请在命令行进入子模块子执行启动命令，命令如下所示：

```cmd
cd storage
snz1dpctl make standalone restart
```

```cmd
cd account
snz1dpctl make standalone restart
```

```cmd
cd order
snz1dpctl make standalone restart
```

> 如果需要停止服务容器，请执行`snz1dpctl make standalone stop`。

### 2.4、测试事务

启动完所有的服务后，在浏览器中打开<http://localhost:9192/seatas/example/order/swagger-ui.html>地址进行接口测试。

测试参数请使用以下内容：

- `account_id` 账户号，可用值为1001、1002，传入1002时主动抛出异常（回滚)，每个账户初始化时默认金额为`10000`；
- `commodity_code` 商品代码：可用值为2001，服务初始化时默认1000数量；
- `count` 下单数量，一个数量默认金额为`5`元；

或使用`CURL`进行测试：

```cmd
curl -X POST "http://localhost:9192/seatas/example/order/debit?account_id=1001&commodity_code=2001&count=1"
```

> 使用账号`1001`请求正确完成所有事务。

```cmd
curl -X POST "http://localhost:9192/seatas/example/order/debit?account_id=1002&commodity_code=2001&count=1"
```

> 使用账号`1002`请求时会因为异常抛出导致所有事务回滚。

# Seata示例工程

模拟用户采购商品业务实现，整个业务包含三个微服务:

- 库存服务（storage）: 扣减给定商品的库存数量。
- 账户服务（account）: 用户账户金额扣减。
- 订单服务（order）: 根据采购请求生成订单。

本案例采用Seata实现AT模式的分布式事务，具体请参考[seatas官方文档](http://seata.io/zh-cn/docs/overview/what-is-seata.html)。

## 1、依赖环境

### 1.1、数据库

本案例使用`PostgresQL9.6`数据库。

> [PostgresQL9.6官方文档](https://www.postgresql.org/docs/9.6/index.html)

### 1.2、Seata服务端

本案例使用`Seata1.4.1`服务端并采用容器化方式运行，使用`PostgresQL9.6`数据库作为事务日志存储。

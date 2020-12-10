## 作业
1. （选做）分析前面作业设计的表，是否可以做垂直拆分。
    > 已完成
2. （必做）设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。
并在新结构在演示常见的增删改查操作。代码、sql和配置文件，上传到github。
    > 使用shardingsphere-proxy作为代理，将product表分为2个库，每个库16张表
    > 
    > 代码详情：[springboot-shardingsphere-proxy](../springboot-shardingsphere-proxy/src/main/java/com/yx1)
    > 
    > ss-proxy 部署使用文档：[ss-proxy使用说明.md](ss-proxy使用说明.md)
3. （选做）模拟1000万的订单单表数据，迁移到上面作业2的分库分表中。
    > 未完成，本周时间不足

4. （必做）基于hmily TCC或ShardingSphere的Atomikos XA实现一个简单的分布式事务应用demo（二选一），提交到github。
    
    > 未完成
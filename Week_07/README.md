## 作业：
1. （选做）用今天课上学习的知识，分析自己系统的 SQL 和表结构
    > 已完成
2. （必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
    > 1. 单条循环插入：效率最慢，1000条耗时：           131 s
    > 2. 单条循环插入，加上事物，100万条耗时：          304 s
    > 3. 拼接sql，每10w拼接一条，100万条耗时：          255 s
    > 4. 批量操作，100万条耗时：                       181 s
    > 5. 批量操作 + 多线程，100万条耗时：               264 s
    > 6. 使用只有两个字段的表，批量操作，100万条耗时：    24 s
    > 
    > 
    > 作业详情链接： [Week_07/src/main/java/com/yx/JdbcInsert.java](../Week_07/src/main/java/com/yx/JdbcInsert.java)
4. （选做）使用不同的索引或组合，测试不同方式查询效率。
    > 已完成
6. （选做）尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）。
    > 使用号段模式完成
    > 
    > 作业详情链接：[Week_07/src/main/java/com/yx/IdGenerate.java](../Week_07/src/main/java/com/yx/IdGenerate.java)

1. （选做）配置一遍异步复制，半同步复制、组复制。
    > 本地安装两台mysql8.0.21
    > 
    > 使用mysql5.7作为 master，两台mysql8.0.21作为slave完成数据同步
2. （必做）读写分离-动态切换数据源版本1.0 — 2.0
    > 使用springboot + mybatis + druid 完成
    >   1. 使用自定义注解，添加注解的方法走slave
    >   2. 不加注解的方法，默认走master
    >   3. 配置多个slave，并添加轮询算法
    >   4. 完成测试
    > 
    > 作业详情链接：[springboot-reading-writing-separation/src/main/java/com/yx](../springboot-reading-writing-separation/src/main/java/com/yx)

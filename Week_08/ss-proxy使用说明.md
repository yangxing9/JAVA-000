1. 下载压缩包
   
   1. 到官网下载最新版本5.x
   ```
    https://shardingsphere.apache.org/document/current/cn/downloads/
    ```
   2. 解压后目录如下：
   ```
        + bin
        + conf
        + lib
        + licenses
        LICENSE
        NOTICE
        README.txt
    ```
   3. 进入conf目录下，修改配置文件
   - 修改 server.yaml文件
       ```yaml
           authentication:
             users:
               root:
                 password: root
               sharding:
                 password: sharding 
                 authorizedSchemas: sharding_db
           
           props:
             max-connections-size-per-query: 1
             acceptor-size: 16  # The default value is available processors count * 2.
             executor-size: 16  # Infinite by default.
             proxy-frontend-flush-threshold: 128  # The default value is 128.
               # LOCAL: Proxy will run with LOCAL transaction.
               # XA: Proxy will run with XA transaction.
               # BASE: Proxy will run with B.A.S.E transaction.
             proxy-transaction-type: LOCAL
             proxy-opentracing-enabled: false
             proxy-hint-enabled: false
             query-with-cipher-column: true
             sql-show: true
             check-table-metadata-enabled: false
        ```
   - 修改config-sharding.yaml文件
       ```yaml
        schemaName: sharding_db
        #
        dataSourceCommon:
          username: root
          password: Yx959130!
          connectionTimeoutMilliseconds: 30000
          idleTimeoutMilliseconds: 60000
          maxLifetimeMilliseconds: 1800000
          maxPoolSize: 50
          minPoolSize: 1
          maintenanceIntervalMilliseconds: 30000
        #
        dataSources:
          ds_0:
            url: jdbc:mysql://127.0.0.1:3307/ds_0?serverTimezone=UTC&useSSL=false
          ds_1:
            url: jdbc:mysql://127.0.0.1:3308/ds_1?serverTimezone=UTC&useSSL=false
        
        
        rules:
        - !SHARDING
          tables:
            product:
              actualDataNodes: ds_${0..1}.product_${0..15}
              tableStrategy:
                standard:
                  shardingColumn: product_id
                  shardingAlgorithmName: t_order_inline
              keyGenerateStrategy:
                column: product_id
                keyGeneratorName: snowflake
        #    t_order_item:
        #      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
        #      tableStrategy:
        #        standard:
        #          shardingColumn: order_id
        #          shardingAlgorithmName: t_order_item_inline
        #      keyGenerateStrategy:
        #        column: order_item_id
        #        keyGeneratorName: snowflake
        #  bindingTables:
        #    - t_order,t_order_item
          defaultDatabaseStrategy:
            standard:
              shardingColumn: product_id
              shardingAlgorithmName: database_inline
        #  defaultTableStrategy:
        #    none:
        #  
          shardingAlgorithms:
            database_inline:
              type: INLINE
              props:
                algorithm-expression: ds_${product_id % 2}
            t_order_inline:
              type: INLINE
              props:
                algorithm-expression: product_${product_id % 16}
        
          keyGenerators:
            snowflake:
              type: SNOWFLAKE
              props:
                worker-id: 123
                max-vibration-offset: 15
       ```
   4. 启动
   
       ```shell script
        start.bat 3309
        # 可以加上端口
        ```
   5. 创建表
        ```sql  
            CREATE TABLE IF NOT EXISTS `product` (
             `product_id` bigint(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
             `product_name` varchar(128) NOT NULL COMMENT '商品名称',
             `product_code` varchar(11) NOT NULL COMMENT '商品编码',
             `price` decimal(8,2) NOT NULL COMMENT '商品价格',
             `descript` varchar(128) DEFAULT NULL COMMENT '商品描述',
             `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
             `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
             `product_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '商品状态，0：下架，1：上架，2：售空',
             `spu_id` bigint(16) NOT NULL COMMENT '商品spu id',
             `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0：否，1：是',
             `product_img` varchar(512) NOT NULL COMMENT '商品图片链接',
             `inventory` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品库存',
             PRIMARY KEY (`product_id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';
        ```
      
采坑：

    1. 使用sqlyog连接会报错，可以使用Navicat连接或者命令行
    2. 连接后，显示的表就一张，实际有2个库，每个库16张表
    3. 创建表结构信息不要使用自增属性，应当交给ss自己处理
    4. 使用雪花算法，需要配置 max-vibration-offset 属性，否则生成出来的id取模后不是0就是1，配置的值为： 分表的数量 - 1
    5. 分库分表键应当选取得当
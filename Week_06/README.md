## 作业：
1. 基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交DDL 的 SQL 文件到 Github（后面2周的作业依然要是用到这个表结构）

   > 详情见： [电商交易场景初始化表](init.sql)
   
   > 用户表
    ```sql
       CREATE TABLE `users` (
         `id` bigint(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
         `name` varchar(16) NOT NULL COMMENT '真实姓名',
         `nick_name` varchar(16) NOT NULL COMMENT '昵称',
         `password` varchar(16) NOT NULL COMMENT '密码',
         `phoneNumber` varchar(11) NOT NULL COMMENT '手机号码',
         `identity_card_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '证件类型',
         `identity_card_no` varchar(20) NOT NULL COMMENT '证件号码',
         `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
         `gender` tinyint(1) NOT NULL COMMENT '性别',
         `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
         `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
         `integral` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
         PRIMARY KEY (`id`)
       ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表'
    ```
   > 商品表
    ```sql
       CREATE TABLE `product` (
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
       ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表'
    ```
   > 商品spu表
    ```sql
       CREATE TABLE `spu` (
         `id` bigint(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
         `spu_code` varchar(16) NOT NULL COMMENT 'spu编码',
         `brand_id` bigint(16) NOT NULL COMMENT '品牌id',
         `store_id` bigint(16) NOT NULL COMMENT '店铺id',
         `cat_id` bigint(16) NOT NULL COMMENT '品类id',
         `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
         `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
         PRIMARY KEY (`id`)
       ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品spu'

    ```
   > 主订单表
   ```sql
    CREATE TABLE `order_master` (
      `order_id` bigint(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单id',
      `order_code` varchar(16) NOT NULL COMMENT '订单编码',
      `user_id` bigint(16) NOT NULL COMMENT '订单用户id',
      `shop_id` bigint(16) NOT NULL COMMENT '商户id',
      `address_id` bigint(16) NOT NULL COMMENT '用户地址信息',
      `payment_method` tinyint(2) NOT NULL DEFAULT '1' COMMENT '支付方式',
      `order_money` decimal(8,2) NOT NULL COMMENT '订单金额',
      `district_money` decimal(8,2) NOT NULL COMMENT '优惠金额',
      `shipping_money` decimal(8,2) NOT NULL COMMENT '运费',
      `payment_money` decimal(8,2) NOT NULL COMMENT '支付金额',
      `courier_id` bigint(16) NOT NULL COMMENT '快递信息id',
      `courier_code` varchar(16) NOT NULL COMMENT '快递单号',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `pay_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
      `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
      `delivery_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发货时间',
      `deal_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '成交时间',
      `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态0：新下单',
      `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态0：未支付',
      `logistics_id` bigint(16) NOT NULL COMMENT '物流信息id',
      `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
      `snapshot` varchar(512) NOT NULL COMMENT '订单快照',
      PRIMARY KEY (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单主表'
   ```
    >订单详情表
    ```sql
       CREATE TABLE `order_detail` (
         `order_tetail_id` bigint(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单详情id',
         `order_id` bigint(16) NOT NULL COMMENT '订单id',
         `product_id` bigint(16) NOT NULL COMMENT '商品id',
         `product_name` varchar(64) NOT NULL COMMENT '商品名称',
         `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
         `product_quantity` int(8) unsigned NOT NULL COMMENT '购买数量',
         `product_icon` varchar(512) NOT NULL COMMENT '商品小图',
         `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
         `spu_id` bigint(20) NOT NULL COMMENT 'spu id',
         `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
         PRIMARY KEY (`order_tetail_id`)
       ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表'
    ```
2. 函数式编程总结：
    > 详情见： [函数式编程总结](lambda.md)   

3. mysql学习总结：
    > 详情见： [mysql总结](mysql.md)
package com.yx.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/3 0003 14:57
 */
@Configuration
public class Config {

    @Bean
    public DataSource dataSource() throws SQLException {
        //数据源Map
        Map<String, DataSource> dsMap = new HashMap<>();
        //配置主库
        HikariDataSource masterDs = new HikariDataSource();
        masterDs.setDriverClassName("com.mysql.cj.jdbc.Driver");
        masterDs.setJdbcUrl("jdbc:mysql://193.112.206.83:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        masterDs.setMinimumIdle(4);
        masterDs.setMaximumPoolSize(8);
        masterDs.setConnectionInitSql("SET NAMES utf8mb4");
        masterDs.setUsername("root");
        masterDs.setPassword("Yangxing959130!");
        dsMap.put("master_ds", masterDs);
        //配置读库1
        HikariDataSource replicaDs1 = new HikariDataSource();
        replicaDs1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        replicaDs1.setJdbcUrl("jdbc:mysql://127.0.0.1:3307/test?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        replicaDs1.setMinimumIdle(4);
        replicaDs1.setMaximumPoolSize(8);
        replicaDs1.setConnectionInitSql("SET NAMES utf8mb4");
        replicaDs1.setUsername("root");
        replicaDs1.setPassword("root");
        dsMap.put("replica_ds_1", replicaDs1);
        //配置读库2
        HikariDataSource replicaDs2 = new HikariDataSource();
        replicaDs2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        replicaDs2.setJdbcUrl("jdbc:mysql://127.0.0.1:3308/test?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        replicaDs2.setMinimumIdle(4);
        replicaDs2.setMaximumPoolSize(8);
        replicaDs2.setConnectionInitSql("SET NAMES utf8mb4");
        replicaDs2.setUsername("root");
        replicaDs2.setPassword("root");
        dsMap.put("replica_ds_2", replicaDs2);
        //配置读写分离规则
        List<ReplicaQueryDataSourceRuleConfiguration> configurations = new ArrayList<>();
        configurations.add(new ReplicaQueryDataSourceRuleConfiguration("ds", "master_ds", Arrays.asList("replica_ds_1", "replica_ds_2"), "load_balancer"));
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalancers = new HashMap<>();
        loadBalancers.put("load_balancer", new ShardingSphereAlgorithmConfiguration("ROUND_ROBIN", new Properties()));
        ReplicaQueryRuleConfiguration ruleConfiguration = new ReplicaQueryRuleConfiguration(configurations, loadBalancers);
        //创建DS
        return ShardingSphereDataSourceFactory.createDataSource(dsMap, Arrays.asList(ruleConfiguration), new Properties());
    }

}

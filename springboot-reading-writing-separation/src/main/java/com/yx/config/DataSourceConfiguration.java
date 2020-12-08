package com.yx.config;

import com.yx.enums.DbType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 16:22
 */
@Configuration
@EnableTransactionManagement //开启事物spring提供的注解
@Slf4j
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // 和下面@Primary作用一样，防止循环依赖问题
public class DataSourceConfiguration extends MybatisAutoConfiguration {

    @Value("${druid.type}")
    private Class<? extends DataSource> dataSourceType;

    public DataSourceConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    @Bean
    @Override
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        log.info("==========================1==================");
        return super.sqlSessionFactory(dataSource());
    }

    @Bean(name = "dataSource")
    public AbstractRoutingDataSource dataSource() {
        log.info("==========================2==================");
        MasterSlaveRoutingDataSource proxy = new MasterSlaveRoutingDataSource();
        Map<Object, Object> targetDataResources = new HashMap<>();
        targetDataResources.put(DbType.MASTER, masterDataSource());
        targetDataResources.put(DbType.SLAVE1, slave1DataSource());
        targetDataResources.put(DbType.SLAVE2, slave2DataSource());
        proxy.setDefaultTargetDataSource(masterDataSource());
        proxy.setTargetDataSources(targetDataResources);
        proxy.afterPropertiesSet();
        return proxy;
    }

    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "druid.master")
    @Primary
    public DataSource masterDataSource(){
        log.info("==========================3==================");
        DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("========MASTER: {}=========", masterDataSource);
        return masterDataSource;
    }

    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "druid.slave1")
    public DataSource slave1DataSource(){
        log.info("==========================4==================");
        DataSource slave1DataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("========SLAVE1: {}=========", slave1DataSource);
        return slave1DataSource;
    }

    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "druid.slave2")
    public DataSource slave2DataSource(){
        log.info("==========================5==================");
        DataSource slave2DataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("========SLAVE2: {}=========", slave2DataSource);
        return slave2DataSource;
    }
}


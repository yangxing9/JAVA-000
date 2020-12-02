package com.yx.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 16:24
 */
public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }
}

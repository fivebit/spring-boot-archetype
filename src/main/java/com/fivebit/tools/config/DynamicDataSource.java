package com.fivebit.tools.config;

import com.fivebit.tools.components.Slog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * Created by fivebit on 2017/12/19.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Autowired
    private Slog log;
    //private static Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("========:"+DatabaseContextHolder.getCustomerType());
        return DatabaseContextHolder.getCustomerType();
    }
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}

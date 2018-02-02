package com.fivebit.tools.config;

import com.fivebit.tools.components.Slog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by fivebit on 2017/12/19.
 */
@Aspect
@Order(-1)          // 保证该AOP在@Transactional之前执行
@Component
public class DataSourceInterceptor {
    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();
    private static Logger log = LoggerFactory.getLogger(DataSourceInterceptor.class);
    @Before("execution(* com.fivebit.tools.service.*.*(..))")
    public void setCommonDataSource(JoinPoint jp) {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if(targetMethod.getClass().isAnnotationPresent(TargetDataSource.class)) {
            TargetDataSource commonDb = targetMethod.getAnnotation(TargetDataSource.class);
            dataSourceHolder.set(DatabaseContextHolder.getCustomerType());
            if (commonDb != null) {
                DatabaseContextHolder.setCustomerType("dataSource");
            }
        }
    }
    @After("execution(* com.fivebit.tools.service.*.*(..))")
    public void setBackDataSource(JoinPoint jp){
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if(targetMethod.getClass().isAnnotationPresent(TargetDataSource.class)) {
            TargetDataSource commonDb = targetMethod.getAnnotation(TargetDataSource.class);
            if (commonDb != null) {
                DatabaseContextHolder.setCustomerType(dataSourceHolder.get());
            } else {
            }
            dataSourceHolder.remove();
        }
    }
    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        String dsId = ds.value();
        if (!DatabaseContextHolder.containsDataSource(dsId)) {
            log.error("数据源[{}]不存在，使用默认数据源 > {}", ds.value(), point.getSignature());
        } else {
            log.debug("Use DataSource : {} > {}", ds.value(), point.getSignature());
            dataSourceHolder.set(DatabaseContextHolder.getCustomerType());
            DatabaseContextHolder.setCustomerType(ds.value());
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        log.debug("Revert DataSource : {} > {}", ds.value(), point.getSignature());
        DatabaseContextHolder.setCustomerType(dataSourceHolder.get());
        dataSourceHolder.remove();
    }

}

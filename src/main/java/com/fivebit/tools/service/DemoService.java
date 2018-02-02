package com.fivebit.tools.service;

import com.fivebit.tools.components.Slog;
import com.fivebit.tools.components.Sredis;
import com.fivebit.tools.config.TargetDataSource;
import com.fivebit.tools.dao.DemoDao;
import com.fivebit.tools.entity.DemoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fivebit on 2017/11/27.
 */
@Service
public class DemoService {
    @Autowired
    private Slog log;

    @Autowired
    private Sredis redis;

    @Autowired
    private DemoDao demoDao;
    public String synctaobao(){
        redis.addString("synctaobao","value");
        return redis.getString("synctaobao");
    }
    public DemoEntity getByDb(Integer userId){
        return demoDao.get(userId);
    }
    @TargetDataSource("ds1")
    public DemoEntity getByDb1(Integer userId){
        return demoDao.get(userId);
    }
    @TargetDataSource("ds2")
    public DemoEntity getByDb2(Integer userId){
        return demoDao.get(userId);
    }
}

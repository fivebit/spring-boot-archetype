package com.fivebit.tools.dao;

import com.fivebit.tools.entity.DemoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by fivebit on 2017/11/27.
 */
@Component
public interface DemoDao {
    public DemoEntity get(@Param("userId")Integer userId);
}

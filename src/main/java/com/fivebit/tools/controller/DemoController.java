package com.fivebit.tools.controller;

import com.fivebit.tools.components.Slog;
import com.fivebit.tools.entity.DemoEntity;
import com.fivebit.tools.handler.AppException;
import com.fivebit.tools.service.DemoService;
import com.fivebit.tools.utils.Utils;
import com.fivebit.tools.vo.DemoVo;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by fivebit on 2017/11/27.
 */
@RestController
@RequestMapping(value = "/demo/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class DemoController {
    @Autowired
    private Slog log;
    @Value("${topzedu.task-thread-nums}")
    private Integer taskNums ;
    @Autowired
    private DemoService synctaobaoService;
    @PostMapping("/entry")
    public ResponseEntity<String> getValueByKey(@Validated @RequestBody DemoVo synctaobaoVo) throws AppException {
        StopWatch watch = new StopWatch();
        watch.start();

        String value = synctaobaoService.synctaobao();
        watch.split();
        log.debug("get value by key:"+synctaobaoVo.toString()+" value:"+value+" taskNums:"+taskNums);
        watch.stop();
        log.info("====="+watch.toString()+" =="+watch.getSplitTime());
        return ResponseEntity.ok(Utils.getRespons());
    }
    @GetMapping("/get/{userId}")
    public ResponseEntity<String> getDataByDiffSource(@PathVariable("userId")Integer userId){
        DemoEntity item = synctaobaoService.getByDb(userId);
        log.info("==0====:"+item.toString());
        DemoEntity item1 = synctaobaoService.getByDb1(userId);
        log.info("==1====:"+item1.toString());
        DemoEntity item2 = synctaobaoService.getByDb2(userId);
        log.info("==2===:"+item2.toString());
        DemoEntity item3 = synctaobaoService.getByDb1(userId);
        log.info("==3===:"+item3.toString());
        DemoEntity item4 = synctaobaoService.getByDb(userId);
        log.info("==4===:"+item4.toString());
        return ResponseEntity.ok(Utils.getRespons(item));

    }
    @GetMapping("/g/{userId}")
    public ResponseEntity<String> get(@PathVariable("userId")Integer userId){
        DemoEntity item4 = synctaobaoService.getByDb(userId);
        log.info("==4===:"+item4.toString());
        return ResponseEntity.ok(Utils.getRespons(item4));

    }
}

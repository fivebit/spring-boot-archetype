package com.fivebit.tools.task;

import com.fivebit.tools.components.Slog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fivebit on 2017/11/27.
 */
@Service
public class TaskService {
    private static int MAX_THREADS;
    @Autowired
    private Slog log;

    @Value("${topzedu.task-thread-nums}")
    public void setMaxThreads(int num) {
        MAX_THREADS = num;
    }

    private static ScheduledThreadPoolExecutor workerPool;

    public <T> Future<T> submit(Callable<T> callable) {
        return workerPool.submit(callable);
    }


    public static int getActiveCount() {
        return workerPool.getActiveCount();
    }

    public static long getTaskCount() {
        return workerPool.getTaskCount();
    }

    public static long getCompletedTaskCount() {
        return workerPool.getCompletedTaskCount();
    }

    @PostConstruct
    private void init() {
        log.info("TaskService init...");
        workerPool = new ScheduledThreadPoolExecutor(MAX_THREADS, new WorkThreadFactory());
        // 初始化阶段化的定时任务
    }

    @PreDestroy
    private void destroy() {
        log.info("TaskService destroying...");
        workerPool.shutdownNow();
    }

    private class WorkThreadFactory implements ThreadFactory {

        private final AtomicInteger num = new AtomicInteger(0);
        private final String THREAD_NAME_PREFIX = "task-worker-";

        @Override
        public Thread newThread(Runnable r) {
            String threadName = THREAD_NAME_PREFIX + num.getAndIncrement();
            Thread thread = new Thread(r, threadName);
            thread.setPriority(Thread.MAX_PRIORITY);
            return thread;
        }

    }
}

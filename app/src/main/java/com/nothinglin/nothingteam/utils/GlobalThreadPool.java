package com.nothinglin.nothingteam.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 全局线程池，不要使用new thread的方法，容易内存泄露，我现在还不知道什么是内存泄露
 */
public class GlobalThreadPool {
    //线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static GlobalThreadPool globalThreadPool = new GlobalThreadPool();

    public static GlobalThreadPool getInstance(){
        return globalThreadPool;
    }

    public GlobalThreadPool() {
    }

    //定义全局线程池
    public ExecutorService getGlobalThreadPool(){
        return executorService;
    }

}

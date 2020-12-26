package com.yql.queue;

/**
 * @author yangqianlei
 * @date 2020/12/26
 */
@FunctionalInterface
public interface DelayRunnable {
    public abstract void delayRun(Object data);
}

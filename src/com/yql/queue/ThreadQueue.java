package com.yql.queue;

/**
 * @author yangqianlei
 * @date 2020/12/26
 */
public class ThreadQueue extends Thread {
    private QueueData queueData;
    private DelayRunnable delayRunnable;

    public void setQueueData(QueueData queueData) {
        this.queueData = queueData;
    }

    public void setDelayRunnable(DelayRunnable delayRunnable) {
        this.delayRunnable = delayRunnable;
    }


    @Override
    public void run() {
        this.delayRunnable.delayRun(this.queueData.getData());
    }
}

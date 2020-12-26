package com.yql.queue;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yangqianlei
 * @date 2020/12/26
 */
public class DelayRunnableQueue implements Runnable {
    private String queueName;
    private boolean closed;
    private final int timeElapsed;
    private final TimeUnit timeUnit;
    private final DelayRunnable func;
    private ArrayDeque<QueueData> queueData = new ArrayDeque<>();


    public DelayRunnableQueue(int timeElapsed, TimeUnit timeUnit, DelayRunnable func) {
        this.queueName = "DelayRunnableQueue";
        this.timeElapsed = timeElapsed;
        this.timeUnit = timeUnit;
        this.func = func;

        System.out.println("初始化队列中，开始创建队列执行线程");
        new Thread(this).start();
    }

    public synchronized boolean push(Object data) {
        QueueData qd = new QueueData();
        qd.setData(data);
        qd.setCreated(new Date());
        return queueData.add(qd);
    }

    public synchronized QueueData pop() {
        return this.queueData.poll();
    }

    public void close() {
        this.closed = true;
    }

    public void setName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void run() {

        QueueData qd;

        while (true) {

            if (this.closed) {
                System.out.printf("队列[%s]被关闭，退出执行中\n", this.queueName);
                return;
            }

            try {

                Thread.sleep(100);

                if (this.queueData.size() == 0) {
                    continue;
                }

                qd = this.queueData.getFirst();
                if (qd.getCreated().getTime() +
                        (timeElapsed * timeUnit.toMillis(1)) >
                        System.currentTimeMillis()) {
                    continue;
                }

                qd = this.pop();
                ThreadQueue threadQueue = new ThreadQueue();
                threadQueue.setQueueData(qd);
                threadQueue.setDelayRunnable(this.func);
                threadQueue.start();

            } catch (Exception e) {
                System.out.printf("执行队列[%s]时发生异常，队列任务结束并退出 %s\n", this.queueName, e.toString());
                return;
            }
        }
    }
}

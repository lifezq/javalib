package com.yql.queue;

import java.util.Date;

/**
 * @author yangqianlei
 * @date 2020/12/26
 */
public class QueueData {
    private Object data;
    private Date created;

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return this.created;
    }
}
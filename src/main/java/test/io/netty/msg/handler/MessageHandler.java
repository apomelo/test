package test.io.netty.msg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.io.netty.msg.AbstractMessage;
import test.io.netty.msg.Message;
import test.io.netty.queue.ITaskQueue;
import test.io.netty.queue.QueueMonitor;
import test.io.netty.task.IDriverTask;

public abstract class MessageHandler<M extends AbstractMessage> implements IDriverTask {
    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    protected Message msg;
    protected int queueId;
    private ITaskQueue<IDriverTask> taskQueue;
    protected MessageFilter filter;

    public MessageHandler() {
    }

    public void run() {
        try {
            if (QueueMonitor.isOpen()) {
                long time = System.currentTimeMillis();
                if (this.filter != null && !this.filter.before(this)) {
                    return;
                }

                this.doAction((M) this.msg);
                int total = (int) (System.currentTimeMillis() - time);
                QueueMonitor.monitor(this, time, total);
            } else {
                if (this.filter != null && !this.filter.before(this)) {
                    return;
                }

                this.doAction((M) this.msg);
            }
        } catch (Throwable var4) {
            log.error("命令执行错误", var4);
        }

    }

    public abstract void doAction(M var1);

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getQueueId() {
        return this.queueId;
    }

    public ITaskQueue<IDriverTask> getTaskQueue() {
        return this.taskQueue;
    }

    public void setTaskQueue(ITaskQueue<IDriverTask> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public Message getMsg() {
        return this.msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public void setFilter(MessageFilter filter) {
        this.filter = filter;
    }
}

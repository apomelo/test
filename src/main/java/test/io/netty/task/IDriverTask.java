package test.io.netty.task;

import test.io.netty.queue.IQueue;
import test.io.netty.queue.ITaskQueue;

public interface IDriverTask extends ITask {
    int getQueueId();

    void setQueueId(int queueId);

    IQueue<IDriverTask> getTaskQueue();

    void setCommandQueue(ITaskQueue<IDriverTask> taskQueue);
}

package example.io.netty.task;

import example.io.netty.queue.IQueue;
import example.io.netty.queue.ITaskQueue;

public interface IDriverTask extends ITask {
    int getQueueId();

    void setQueueId(int queueId);

    IQueue<IDriverTask> getTaskQueue();

    void setCommandQueue(ITaskQueue<IDriverTask> taskQueue);
}

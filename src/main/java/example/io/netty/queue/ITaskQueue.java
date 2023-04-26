package example.io.netty.queue;

import example.io.netty.task.ITask;

public interface ITaskQueue<E extends ITask> extends IQueue<E> {
    E poll();

    boolean offer(E e);

    void clear();

    int size();

    boolean isRunning();

    void setRunning(boolean isRunning);

    void setName(String name);

    String getName();
}

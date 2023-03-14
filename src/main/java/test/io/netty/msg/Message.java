package test.io.netty.msg;

public interface Message {
    /**
     * 解码方法
     */
    void decode(byte[] data);

    /**
     * 编码方法
     */
    byte[] encode();

    int length();

    void setLength(int length);

    int getId();

    void setSequence(short sequence);

    short getSequence();

    /**
     * 接口中可以定义 static 方法, 可通过接口名称.方法名() 调用, 实现类不能继承 static 方法；
     * 接口中可以定义 default 方法, default 修饰的方法有方法体, 表示这个方法的默认实现, 子类可以直接调用, 可以选择重写或者不重写
     * 当实现类实现的多个接口中, 有方法签名相同的 default 方法时, 必须重写该方法
     */
    default int getHostId() {
        return 0;
    }
}

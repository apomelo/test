package test.io.netty.msg;

public interface MessagePool {
    Message getMessage(int messageId);
}

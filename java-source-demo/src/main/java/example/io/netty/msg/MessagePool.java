package example.io.netty.msg;

public interface MessagePool {
    Message getMessage(int messageId);
}

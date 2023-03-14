package test.io.netty.msg.handler;

public interface MessageFilter {
    boolean before(MessageHandler var1);

    boolean after(MessageHandler var1);
}

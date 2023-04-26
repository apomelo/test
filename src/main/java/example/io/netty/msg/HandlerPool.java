package example.io.netty.msg;

import example.io.netty.msg.handler.MessageHandler;

public interface HandlerPool {
    MessageHandler<? extends AbstractMessage> getHandler(int var1);
}

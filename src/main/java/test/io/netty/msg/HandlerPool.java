package test.io.netty.msg;

import test.io.netty.msg.handler.MessageHandler;

public interface HandlerPool {
    MessageHandler<? extends AbstractMessage> getHandler(int var1);
}

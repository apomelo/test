package test.io.netty.msg.consumer;

import io.netty.channel.Channel;
import test.io.netty.msg.Message;

public interface NetworkConsumer {
    void consume(Channel channel, Message message);
}

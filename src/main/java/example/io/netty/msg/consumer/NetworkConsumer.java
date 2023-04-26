package example.io.netty.msg.consumer;

import io.netty.channel.Channel;
import example.io.netty.msg.Message;

public interface NetworkConsumer {
    void consume(Channel channel, Message message);
}

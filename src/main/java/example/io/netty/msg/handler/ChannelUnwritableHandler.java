package example.io.netty.msg.handler;

import io.netty.channel.Channel;
import example.io.netty.msg.Message;

public interface ChannelUnwritableHandler {
    /**
     * channel 不可写入的时候做的操作
     * 建议用 {@link Channel#isWritable()} 做判断
     */
    void onUnwritable(Channel channel, Message message);
}

package test.io.netty.channel.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;

public interface MessageInboundEventListener {
    void onConnected(ChannelHandlerContext ctx);

    void onDisconnected(ChannelHandlerContext ctx);

    void onExceptionOccur(ChannelHandlerContext ctx, Throwable cause);

    void idle(ChannelHandlerContext ctx, IdleState state);

    default void onChannelWritabilityChanged(ChannelHandlerContext ctx) {
    }
}

package test.io.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import test.io.netty.channel.listener.MessageInboundEventListener;
import test.io.netty.msg.Message;
import test.io.netty.msg.consumer.NetworkConsumer;

@Slf4j
public class MessageInboundHandler extends ChannelInboundHandlerAdapter {
    private NetworkConsumer consumer;
    protected MessageInboundEventListener listener;
    private boolean idleCheck = false;

    public MessageInboundHandler(NetworkConsumer consumer, MessageInboundEventListener listener, boolean idleCheck) {
        this.consumer = consumer;
        this.listener = listener;
        this.idleCheck = idleCheck;
    }

    public NetworkConsumer getConsumer() {
        return this.consumer;
    }

    public void setConsumer(NetworkConsumer consumer) {
        this.consumer = consumer;
    }

    public MessageInboundEventListener getListener() {
        return this.listener;
    }

    public void setListener(MessageInboundEventListener listener) {
        this.listener = listener;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Message) {
            this.consumer.consume(ctx.channel(), (Message) msg);
        } else {
            ReferenceCountUtil.release(msg);
            log.info("不支持的msg类型:{}", msg.getClass().getName());
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.listener.onExceptionOccur(ctx, cause);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onConnected(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onDisconnected(ctx);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (this.idleCheck && evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            IdleState state = idleStateEvent.state();
            if (state == IdleState.READER_IDLE) {
                this.listener.idle(ctx, state);
            }
        }

    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        this.listener.onChannelWritabilityChanged(ctx);
    }

    public boolean isIdleCheck() {
        return this.idleCheck;
    }

    public void setIdleCheck(boolean idleCheck) {
        this.idleCheck = idleCheck;
    }
}

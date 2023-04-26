package example.io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @author C
 * @date 2023/3/6
 */
public class WebSocketOutboundHandler extends ChannelOutboundHandlerAdapter {
    public WebSocketOutboundHandler() {
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            ctx.write(new BinaryWebSocketFrame(((ByteBuf) msg)), promise);
        }
        super.write(ctx, msg, promise);
    }
}

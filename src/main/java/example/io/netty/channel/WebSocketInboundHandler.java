package example.io.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @author C
 * @date 2023/3/6
 */
public class WebSocketInboundHandler extends ChannelInboundHandlerAdapter {
    public WebSocketInboundHandler() {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            super.channelRead(ctx, ((BinaryWebSocketFrame) msg).content());
        } else {
            super.channelRead(ctx, msg);
        }
    }
}

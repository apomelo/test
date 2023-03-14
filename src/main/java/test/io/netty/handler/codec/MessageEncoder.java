package test.io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.io.netty.msg.Message;
import test.io.netty.msg.pack.MessagePackage;

public class MessageEncoder extends MessageToByteEncoder<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEncoder.class);

    public MessageEncoder() {
    }

    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        if (msg.getId() == -1) {
            ByteBuf buffer = null;

            try {
                buffer = MessagePackage.packageMsgGroup(msg);
                if (buffer != null) {
                    out.writeBytes(buffer);
                    LOGGER.debug("编码消息成功:{}", msg);
                } else {
                    LOGGER.debug("编码消息失败:{}", msg);
                }
            } finally {
                if (buffer != null) {
                    ReferenceCountUtil.release(buffer);
                }

            }
        } else {
            MessagePackage.packageMsg(out, msg);
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

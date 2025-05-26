package example.io.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import example.io.netty.msg.Message;
import example.io.netty.msg.MessagePool;

@Slf4j
public class MessageDecoder extends LengthFieldBasedFrameDecoder {
    private static boolean msgUnregisterLog;
    private MessagePool msgPool;

    static {
        String msgUnregisterLogStr = System.getProperty("msg.log.MsgUnregister", "false");
        msgUnregisterLog = Boolean.parseBoolean(msgUnregisterLogStr);
    }

    private MessageDecoder(MessagePool msgPool, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.msgPool = msgPool;
    }

    public MessageDecoder(MessagePool msgPool) {
        this(msgPool, 1048576, 0, 4, -4, 0);
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf frame) throws Exception {
        frame = (ByteBuf) super.decode(ctx, frame);
        if (frame == null) {
            return null;
        } else {
            Message res;
            try {
                int length = frame.readInt();
                int id = frame.readInt();
                short sequence = frame.readShort();
                Message message = this.msgPool.getMessage(id);
                if (message == null) {
                    if (msgUnregisterLog) {
                        log.error("未注册的消息,id:" + id);
                    }
                    return null;
                }

                byte[] bytes = null;
                int remainLength = frame.readableBytes();
                if (remainLength > 0) {
                    bytes = new byte[remainLength];
                    frame.readBytes(bytes);
                } else if (remainLength == 0) {
                    bytes = new byte[0];
                }

                message.setLength(length);
                message.setSequence(sequence);
                if (bytes != null) {
                    message.decode(bytes);
                }

                log.debug("解析消息:" + message);
                res = message;
            } catch (Throwable e) {
                log.error(ctx.channel() + "消息解码异常", e);
                return null;
            } finally {
                ReferenceCountUtil.release(frame);
            }
            return res;
        }
    }
}

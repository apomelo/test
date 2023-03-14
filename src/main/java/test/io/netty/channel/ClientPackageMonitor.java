package test.io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import test.io.netty.msg.Message;

@Slf4j
public class ClientPackageMonitor extends ChannelInboundHandlerAdapter {
    private static final AttributeKey<Integer> PACKAGE_COUNT = AttributeKey.newInstance("p_c");
    private static final AttributeKey<Long> PACKAGE_LAST_TIME = AttributeKey.newInstance("p_l_t");
    private static final AttributeKey<Short> PACKAGE_LAST_SEQUENCE = AttributeKey.newInstance("P_l_s");
    public static final ClientPackageMonitor.MonitorOption DEFAULT_MONITOR_OPTION = new ClientPackageMonitor.DefaultOpenMonitorOption();
    private ClientPackageMonitor.MonitorOption option;

    public ClientPackageMonitor(ClientPackageMonitor.MonitorOption option) {
        this.option = option;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (this.option.rateValidate() && !this.rateValidate(ctx)) {
            ctx.close();
        } else if (this.option.sequenceValidate() && !this.sequenceValidate(ctx, msg)) {
            ctx.close();
        } else {
            super.channelRead(ctx, msg);
        }
    }

    private boolean rateValidate(ChannelHandlerContext ctx) {
        Long lastTime = ctx.channel().attr(PACKAGE_LAST_TIME).get();
        if (lastTime == null) {
            lastTime = 0L;
        }

        Integer count = ctx.channel().attr(PACKAGE_COUNT).get();
        if (count == null) {
            count = 0;
        }

        long curTime = System.currentTimeMillis();
        if (curTime - lastTime > 1000L) {
            if (count > this.option.rateMax() / 2) {
                log.info("客户端连接每秒请求超过{}个,请合理调整业务逻辑,channel:{}", count, ctx.channel().toString());
            }

            lastTime = curTime;
            count = 0;
        }

        count = count + 1;
        ctx.channel().attr(PACKAGE_COUNT).set(count);
        ctx.channel().attr(PACKAGE_LAST_TIME).set(lastTime);
        if (count > this.option.rateMax()) {
            log.info("客户端连接每秒请求超过{}个,主动断开连接,channel:{}.", this.option.rateMax(), ctx.channel().toString());
            if (this.option.rateExceed(ctx.channel())) {
                return false;
            }
        }

        return true;
    }

    private boolean sequenceValidate(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        short sequence = message.getSequence();
        Short lastSequence = ctx.channel().attr(PACKAGE_LAST_SEQUENCE).get();
        if (lastSequence == null) {
            lastSequence = (short) 0;
        }

        int div;
        if (lastSequence == 32767) {
            div = -(sequence + lastSequence);
        } else {
            div = sequence - lastSequence;
        }

        if (div != 1) {
            log.info("消息包不连续,断开连接,msgId:{},channel:{}", ((Message) msg).getId(), ctx.channel().toString());
            if (this.option.notSequential(ctx.channel())) {
                return false;
            }
        }

        ctx.channel().attr(PACKAGE_LAST_SEQUENCE).set(sequence);
        return true;
    }

    public interface MonitorOption {
        boolean rateValidate();

        int rateMax();

        boolean rateExceed(Channel var1);

        boolean sequenceValidate();

        boolean notSequential(Channel var1);
    }

    public static class DefaultOpenMonitorOption implements MonitorOption {
        public DefaultOpenMonitorOption() {
        }

        public boolean rateValidate() {
            return false;
        }

        public int rateMax() {
            return 30;
        }

        public boolean rateExceed(Channel channel) {
            return true;
        }

        public boolean sequenceValidate() {
            return false;
        }

        public boolean notSequential(Channel channel) {
            return true;
        }
    }
}

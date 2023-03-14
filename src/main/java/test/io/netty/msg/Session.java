package test.io.netty.msg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import test.io.netty.msg.handler.ChannelUnwritableHandler;

import java.net.InetSocketAddress;

public class Session {
    private ChannelUnwritableHandler handler;
    private static final ChannelUnwritableHandler DEFAULT = new ChannelUnwritableHandler() {
        public void onUnwritable(Channel channel, Message message) {
        }
    };
    private Channel channel;
    private long id;
    private int state;
    private Object value;

    public Session() {
        this.handler = DEFAULT;
    }

    public Session(ChannelUnwritableHandler handler) {
        this.handler = handler;
    }

    public ChannelFuture close() {
        return this.channel.close();
    }

    public void sendMessage(Message msg) {
        if (this.channel.isWritable()) {
            this.channel.writeAndFlush(msg);
        } else {
            this.handler.onUnwritable(this.channel, msg);
        }
    }

    public String getIp() {
        return this.channel == null ? "" : ((InetSocketAddress) this.channel.remoteAddress()).getHostString();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

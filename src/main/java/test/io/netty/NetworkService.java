package test.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import test.io.netty.channel.ClientPackageMonitor;
import test.io.netty.channel.MessageInboundHandler;
import test.io.netty.channel.WebSocketInboundHandler;
import test.io.netty.channel.WebSocketOutboundHandler;
import test.io.netty.handler.codec.MessageDecoder;
import test.io.netty.handler.codec.MessageEncoder;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NetworkService {

    private int bossLoopGroupCount;
    private int workerLoopGroupCount;
    private int port;
    private ServerBootstrap bootstrap;
    private int state;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private static final byte STATE_STOP = 0;
    private static final byte STATE_START = 1;

    public void start() {
        try {
            ChannelFuture f = this.bootstrap.bind(this.port);
            f.sync();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }

        this.state = 1;
        log.info("Server on port:{} is start", this.port);
    }

    public NetworkService(NetworkServiceBuilder builder) {
        this.bossLoopGroupCount = builder.getBossLoopGroupCount();
        this.workerLoopGroupCount = builder.getWorkerLoopGroupCount();
        this.port = builder.getPort();
        this.bossGroup = new NioEventLoopGroup(this.bossLoopGroupCount);
        this.workerGroup = new NioEventLoopGroup(this.workerLoopGroupCount);
        final SslContext sslCtx;
        if (builder.isSsl()) {
            try {
                sslCtx = SslContextBuilder.forServer(new File(builder.getSslKeyCertChainFile()), new File(builder.getSslKeyFile())).build();
            } catch (SSLException var4) {
                throw new RuntimeException("sslCtx create failed.", var4);
            }
        } else {
            sslCtx = null;
        }

        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(this.bossGroup, this.workerGroup);
        this.bootstrap.channel(NioServerSocketChannel.class);
        this.bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        this.bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        if (builder.getSoRecBuf() > 0) {
            this.bootstrap.childOption(ChannelOption.SO_RCVBUF, builder.getSoRecBuf());
        } else {
            this.bootstrap.childOption(ChannelOption.SO_RCVBUF, 131072);
        }

        if (builder.getSoSendBuf() > 0) {
            this.bootstrap.childOption(ChannelOption.SO_SNDBUF, builder.getSoSendBuf());
        } else {
            this.bootstrap.childOption(ChannelOption.SO_SNDBUF, 131072);
        }

        if (builder.getWriteBufferWaterMark() != null) {
            this.bootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, builder.getWriteBufferWaterMark());
        }

        this.bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pip = ch.pipeline();
                if (sslCtx != null) {
                    SslHandler sslHandler = sslCtx.newHandler(ch.alloc());
                    pip.addLast("sslHandler", sslHandler);
                }

                if (builder.isWebSocket()) {
                    pip.addLast(new HttpServerCodec());
                    pip.addLast(new HttpObjectAggregator(65536));
                    pip.addLast(new WebSocketServerProtocolHandler("/", true));
                    pip.addLast(new WebSocketInboundHandler());
                    pip.addLast(new WebSocketOutboundHandler());
                }

                if (builder.getIdleMaxTime() > 0) {
                    pip.addLast("Idle", new IdleStateHandler(builder.getIdleMaxTime(), 0, 0));
                }

                pip.addLast("NettyMessageDecoder", new MessageDecoder(builder.getMsgPool()));
                pip.addLast("NettyMessageEncoder", new MessageEncoder());
                if (builder.getClientPackageMonitorOption().rateValidate() || builder.getClientPackageMonitorOption().sequenceValidate()) {
                    pip.addLast("ClientPackageMonitor", new ClientPackageMonitor(builder.getClientPackageMonitorOption()));
                }

                for (ChannelHandler handler : builder.getChannelHandlerList()) {
                    pip.addLast(handler);
                }

                MessageInboundHandler executor = new MessageInboundHandler(builder.getConsumer(), builder.getNetworkEventlistener(), builder.getIdleMaxTime() > 0);
                pip.addLast("NettyMessageExecutor", executor);
            }
        });
    }

    public void stop() {
        this.state = 0;
        Future<?> bf = this.bossGroup.shutdownGracefully();
        Future wf = this.workerGroup.shutdownGracefully();

        try {
            bf.get(5000L, TimeUnit.MILLISECONDS);
            wf.get(5000L, TimeUnit.MILLISECONDS);
        } catch (Exception var4) {
            log.info("Netty服务器关闭失败", var4);
        }

        log.info("Netty Server on port:{} is closed", this.port);
    }

    public boolean isRunning() {
        return this.state == 1;
    }
}

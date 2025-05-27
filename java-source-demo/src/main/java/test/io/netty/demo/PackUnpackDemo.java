package test.io.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author C
 * @date 2023/4/10
 */
public class PackUnpackDemo {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> new Server().start(8888)).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> new Client().start(8888)).start();
    }


    @Slf4j
    private static class Server {
        public void start(int port) {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workGroup = new NioEventLoopGroup(4);
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
//                                // 特殊分隔符分包解码器
//                                // 加入之后本例子中不会发生拆包粘包问题
//                                pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("!".getBytes())));
                                // 向pipeline加入解码器
                                pipeline.addLast("decoder", new StringDecoder());
                                // 向pipeline加入编码器
                                pipeline.addLast("encoder", new StringEncoder());
                                // 加入自己的业务处理handler
                                pipeline.addLast(new ChatServerHandler());
                            }
                        });
                log.info("server启动...");
                ChannelFuture channelFuture = bootstrap.bind(port).sync();
                // 关闭通道
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.info("exception: ", e);
            } finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }
    }

    @Slf4j
    private static class Client {
        public void start(int port) {
            EventLoopGroup workGroup = new NioEventLoopGroup(4);
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
//                                // 加入特殊分隔符分包解码器 (在本例子中无用, 本例子客户端不需要解码器)
//                                pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("!".getBytes())));
//                                // 向pipeline加入解码器 (在本例子中无用, 本例子客户端不需要解码器)
//                                pipeline.addLast("decoder", new StringDecoder());
                                // 向pipeline加入编码器
                                pipeline.addLast("encoder", new StringEncoder());
//                                // 加入自己的业务处理handler (在本例子中无用, 本例子客户端不需要业务处理器)
//                                pipeline.addLast(new ChatServerHandler());
                            }
                        });
                log.info("client启动...");
                ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).sync();
                // 得到 channel
                Channel channel = channelFuture.channel();
                int count = 0;
                while (count++ < 1000) {
                    channel.writeAndFlush("你好,我是Netty!");
                }
                log.info("发送次数: {}", count - 1);
                // 睡眠一段时间等待消息发送完
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.info("exception: ", e);
            } finally {
                workGroup.shutdownGracefully();
            }
        }
    }

    @Slf4j
    private static class ChatServerHandler extends SimpleChannelInboundHandler<String> {
        AtomicInteger count = new AtomicInteger(0);

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.info(msg);
            // 根据 "你" 判断消息发送情况, 结果会小于实际发送次数, 因为 "你" 的字节发生了拆包
            log.info("count: {}", count.addAndGet(appearNumber(msg, "你")));
        }

        /**
         * 获取指定字符串出现的次数
         *
         * @param srcText  源字符串
         * @param findText 要查找的字符串
         * @return 出现次数
         */
        private int appearNumber(String srcText, String findText) {
            int count = 0;
            Pattern p = Pattern.compile(findText);
            Matcher m = p.matcher(srcText);
            while (m.find()) {
                count++;
            }
            return count;
        }
    }
}

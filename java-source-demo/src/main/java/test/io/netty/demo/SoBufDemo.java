package test.io.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.AbstractNioChannel;
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

import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author C
 * @date 2023/4/11
 */
public class SoBufDemo {
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
                        .childOption(ChannelOption.TCP_NODELAY, true)
                        .childOption(ChannelOption.SO_SNDBUF, 1024 * 1024)
//                        .childOption(ChannelOption.SO_RCVBUF, 32)
                        .childOption(ChannelOption.SO_RCVBUF, 1024 * 1024)
                        .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(1024 * 512, 1024 * 1024))
                        .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
//                                // 加入特殊分隔符分包解码器
//                                // TODO: 如果不加入分包解码器, 服务端会按默认情况分段输出
//                                pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 1024, Unpooled.copiedBuffer("!".getBytes())));
                                // 向pipeline加入编码器
                                pipeline.addLast("encoder", new StringEncoder());
                                // 向pipeline加入解码器
                                pipeline.addLast("decoder", new StringDecoder());
                                //加入自己的业务处理handler
                                pipeline.addLast(new ChatServerHandler());
                                log.info("server SO_SNDBUF: {}", ch.config().getSendBufferSize());
                                log.info("server SO_RCVBUF: {}", ch.config().getReceiveBufferSize());
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
                        .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                        .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                        .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(1024 * 512, 1024 * 1024))
                        .handler(new LoggingHandler(LogLevel.DEBUG))
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                // 向pipeline加入编码器
                                pipeline.addLast("encoder", new StringEncoder());
                            }
                        });
                log.info("client启动...");
                ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).sync();
                // 得到 channel
                Channel channel = channelFuture.channel();
                StringBuilder stringBuilder = new StringBuilder("你好,");
                for (int i = 0; i < 300000; i ++) {
                    stringBuilder.append("a");
                }
                channel.writeAndFlush(stringBuilder.append("!").toString());
                // 在服务端加入以 "!" 分隔符分包解码器的情况下
                // 如果最后写入的不是 "!", 而是 ".", 那么服务端不会输出前面的内容
                channel.writeAndFlush(stringBuilder.append("该内容不会输出.").toString());
                log.info("发送结束!!!");
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
        /**
         * 如果上面没有加入 {@link io.netty.handler.codec.DelimiterBasedFrameDecoder} 分包器, 数据长度递增输出, 最大以 65536 长度输出
         * 原因: 读取数据时, 创建的 {@link io.netty.buffer.ByteBuf} capacity 最大是 65536
         * 源码解读:
         * 1. 在 {@link io.netty.channel.nio.NioEventLoop#processSelectedKey(SelectionKey, AbstractNioChannel)}
         *      中的 "unsafe.read();" 语句中, 最终走到 {@link io.netty.channel.nio.AbstractNioByteChannel.NioByteUnsafe#read()}
         * 2. ByteBuf 是在 read() 方法语句 "byteBuf = allocHandle.allocate(allocator);" 中进行分配
         * 3. allocate() 方法用的是 {@link io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle#allocate(ByteBufAllocator)}
         * 4. 但是 ByteBuf 的 initialCapacity 是用的 {@link io.netty.channel.AdaptiveRecvByteBufAllocator.HandleImpl#guess()} 返回值
         * 5. guess() 中获取的 initialCapacity 是根据 AdaptiveRecvByteBufAllocator 的初始化字段 minIndex, maxIndex, initial
         *      从 {@link io.netty.channel.AdaptiveRecvByteBufAllocator#SIZE_TABLE} 中取得
         * 6. {@link io.netty.channel.AdaptiveRecvByteBufAllocator} 是在上面 read() 方法语句 "final RecvByteBufAllocator.Handle allocHandle = recvBufAllocHandle();" 中获取
         * 7. allocHandle 最终是在 {@link io.netty.channel.AdaptiveRecvByteBufAllocator#newHandle()} 中获取
         * 8. 由 newHandle() 方法可以看到用了类 AdaptiveRecvByteBufAllocator 中 minIndex, maxIndex, initial 3 个变量
         * 9. allocator 是在 read() 方法里的 ChannelConfig 中获取, 追踪到具体实现类是在 {@link io.netty.channel.DefaultChannelConfig#setRecvByteBufAllocator(RecvByteBufAllocator)} 类的方法中设置
         * 10. 继续向上追踪, 可以看到 allocator 是在 {@link io.netty.channel.DefaultChannelConfig#DefaultChannelConfig(Channel)} 中 new 出来的
         * 11. {@link io.netty.channel.AdaptiveRecvByteBufAllocator#AdaptiveRecvByteBufAllocator()} 中
         *      使用了 DEFAULT_MINIMUM, DEFAULT_INITIAL, DEFAULT_MAXIMUM 来初始化 minIndex, maxIndex, initial 3 个变量
         *
         * 如果上面加入 {@link io.netty.handler.codec.DelimiterBasedFrameDecoder} 分包器, 数据最大以 2147483647 长度输出,
         * 因为本例子数据长度远远小于 2147483647, 所以可以一次性完整输出
         * 原因: 读取数据时 {@link io.netty.handler.codec.ByteToMessageDecoder#channelRead(ChannelHandlerContext, Object)} 创建了新的 ByteBuf
         * 源码解读:
         * 1. 创建 ByteBuf 是在 channelRead() 方法的语句 "cumulation = cumulator.cumulate(ctx.alloc(), first ? Unpooled.EMPTY_BUFFER : cumulation, (ByteBuf) msg);"
         * 2. 由 channelRead() 方法中的语句 "callDecode(ctx, cumulation, out);" 可以看出, 传入下一步的是 cumulation 而不是原来的 msg
         * 3. {@link io.netty.handler.codec.ByteToMessageDecoder#MERGE_CUMULATOR} 中的 cumulate() 是把数据由 msg 读取到 cumulation 的过程
         *
         * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
         *                      belongs to
         * @param msg           the message to handle
         * @throws Exception
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.info(msg);
            log.info("msg length: {}", msg.length());
        }
    }
}

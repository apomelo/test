package test.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author C
 * @date 2022/5/4
 */
public class NioServerTest {
    private static final Logger logger = LoggerFactory.getLogger(NioServerTest.class);

    public static void main(String[] args) {
        NioServerTest nioServerTest = new NioServerTest();
        nioServerTest.init();
        nioServerTest.listen();
    }

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private final int port = 8888;

    public void init() {
        try {
            // 创建通道管理对象Selector
            selector = Selector.open();
            // 创建通道对象Channel
            listenChannel = ServerSocketChannel.open();
            // 将通道设置为非阻塞
            listenChannel.configureBlocking(false);
            // 绑定端口
            InetSocketAddress inetSocketAddress = new InetSocketAddress(this.port);
            listenChannel.socket().bind(inetSocketAddress);

            // 将通道与通道管理器绑定，并为通道注册 OP_ACCEPT 事件（接收事件）
            // 注册事件后，当事件到达时，selector.select()会返回一个key，如果该事件没有到达selector.select()会一直阻塞
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            logger.warn("exception=", e);
        }
    }

    public void listen() {
        // 等待读取数据
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                logger.warn("exception=", e);
            }
            // 将通道中的数据放入集合中
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 已经拿到数据，将迭代器中的数据删除，避免出错
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        this.accept(key);
                    } else if (key.isReadable()) {
                        this.read(key);
                    } else if (key.isWritable() && key.isValid()) {
                        this.write(key);
                    }
                } catch (IOException e) {
                    try {
                        key.channel().close();
                    } catch (IOException ex) {
                        logger.warn("exception=", e);
                    }
                    logger.info("客户端断开了连接...");
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        logger.info("等待client连接...");
        // ServerSocketChannel只支持OP_ACCEPT操作
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        // accept()方法产生的SocketChannel只支持OP_READ, OP_WRITE操作
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        SelectionKey rwKey = socketChannel.register(key.selector(), SelectionKey.OP_READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        rwKey.attach(byteBuffer);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = socketChannel.read(byteBuffer);
        while (read > 0) {
            byteBuffer.flip();
            byte[] data = new byte[read];
            byteBuffer.get(data, 0, read);
            byteArrayOutputStream.write(data);
            byteBuffer.compact();
            read = socketChannel.read(byteBuffer);
        }
        if (read == -1) {
            socketChannel.close();
        }
        String msg = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        logger.info("client {} msg is: {}", socketChannel.getRemoteAddress(), msg);
        // 转发消息给其它客户端(排除自己)
        transferInfo(msg, socketChannel);
    }

    private void transferInfo(String msg, SocketChannel selfChannel) {
        // 服务器转发消息
        logger.info("服务器转发消息中...");
        // 遍历所有注册到selector的socketChannel并排除自身
        for (SelectionKey key : selector.keys()) {
            // 获取通道
            Channel targetChannel = key.channel();
            // 排除自身
            if (targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
                // 将msg存储到buffer中
                ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                byte[] data = msg.getBytes(StandardCharsets.UTF_8);
                // 判断剩余空间是否足够，不够则扩容
                if (byteBuffer.remaining() < data.length) {
                    byteBuffer = reCapacity(byteBuffer, data.length);
                }
                byteBuffer.put(data);
                // 注册写事件
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                // 绑定Buffer
                key.attach(byteBuffer);
            }
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        // 转换为读模式
        byteBuffer.flip();
        // 如果通道中还有数据就把它写进ByteBuffer中
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }
        // 转换为写模式
        byteBuffer.compact();
        // 发送完了就取消写事件，否则下次还会进入该分支
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
    }

    private ByteBuffer reCapacity(ByteBuffer byteBuffer, int length) {
        int position = byteBuffer.position();
        int destCapacity = byteBuffer.capacity();
        // 每次扩大两倍
        // 进阶：可以设置阈值，超过阈值只扩大指定大小（步进方式），并判断是否达到Integer.MAX_VALUE
        while (destCapacity - position < length) {
            destCapacity <<= 1;
        }
        ByteBuffer destByteBuffer = ByteBuffer.allocate(destCapacity);
        byteBuffer.flip();
//        // 调用本方法后 destByteBuffer 会写入整个 byteBuffer 的长度
//        byteBuffer.rewind();
        destByteBuffer.put(byteBuffer);
        byteBuffer.clear();
        return destByteBuffer;
    }
}

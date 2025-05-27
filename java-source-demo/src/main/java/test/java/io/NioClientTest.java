package test.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * @author C
 * @date 2022/5/4
 */
public class NioClientTest {
    private static final Logger logger = LoggerFactory.getLogger(NioClientTest.class);

    public static void main(String[] args) {
        NioClientTest nioServerTest = new NioClientTest();
        nioServerTest.init();
        nioServerTest.listen();
    }

    private Selector selector;
    private SelectionKey key;
    private final String host = "127.0.0.1";
    private final int port = 8888;

    public void init() {
        try {
            // 创建通道管理对象Selector
            selector = Selector.open();
            // 创建通道对象Channel
            SocketChannel socketChannel = SocketChannel.open();
            // 将通道设置为非阻塞
            socketChannel.configureBlocking(false);
            // 连接服务端
//            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
            socketChannel.connect(inetSocketAddress);
            /**
             * 不能在这里使用socketChannel.finishConnect()方法判断是否连接成功，应该放在select()的循环里
             * 原因：finishConnect在连接成功时会消耗一次OP_CONNECT事件，select阻塞了一下后，只有返回0了，导致循环直接退出
             */

            // 将通道与通道管理器绑定，并为通道注册 OP_CONNECT 事件（连接事件）
            // 注册事件后，当事件到达时，selector.select()会返回一个key，如果该事件没有到达selector.select()会一直阻塞
            key = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            logger.warn("exception=", e);
        }
    }

    public void listen() {
        // 等待输入
        Executors.newSingleThreadExecutor().execute(() -> {
            // 发送数据给服务器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                byte[] data = scanner.nextLine().getBytes(StandardCharsets.UTF_8);
                // 判断剩余空间是否足够，不够则扩容
                if (byteBuffer.remaining() < data.length) {
                    byteBuffer = reCapacity(byteBuffer, data.length);
                }
                // 如果没有经过上面的扩容，要写入的数据大于剩余可用空间，会报错
                byteBuffer.put(data);

                // 注册写事件
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                // 绑定Buffer
                key.attach(byteBuffer);
            }
        });
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
                    if (key.isConnectable()) {
                        this.connect(key);
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
                    logger.info("服务器出现异常！！！");
                    // 直接退出，也可以写重连逻辑
                    System.exit(-1);
                }
            }
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);
        String info = "this is connect message from client.";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();
        byteBuffer.put(info.getBytes(StandardCharsets.UTF_8));
        // 注册读和写事件
        key.interestOps(key.interestOps() | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        // 绑定Buffer
        key.attach(byteBuffer);
//        // 直接写
//        socketChannel.write(byteBuffer);
//        socketChannel.close();
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
        logger.info("server {} msg is: {}", socketChannel.getRemoteAddress(), msg);
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

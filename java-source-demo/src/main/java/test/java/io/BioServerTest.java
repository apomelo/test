package test.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author C
 * @date 2022/4/12
 */
public class BioServerTest {
    private static final Logger logger = LoggerFactory.getLogger(BioServerTest.class);

    public static void main(String[] args) throws IOException {
        // 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        for (; ; ) {
            logger.info("等待连接中...");
            //监听,等待客户端连接
            Socket socket = serverSocket.accept();
            logger.info("连接到一个客户端");
            executorService.execute(() -> handler(socket));
        }
    }

    // 编写一个handler方法,和客户端通讯
    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        logger.info("当前线程信息: " + Thread.currentThread().getName());
        try {
            // 通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (inputStream.read(bytes) != -1) {
                logger.info("{} : 发送信息为 : {}", Thread.currentThread().getName(), new String(bytes, 0, bytes.length));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.info("关闭连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

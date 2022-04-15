package test.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.ApplicationConfig;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author C
 * @date 2022/4/8
 */
public class IoTest {
    private static final Logger logger = LoggerFactory.getLogger(IoTest.class);

    public static void main(String[] args) {
        String filePath = ApplicationConfig.getInstance().getFilePath();
    }

    private static StringBuilder readIo(String prePath) {
        InputStream is = null;
        StringBuilder stringBuilder = new StringBuilder();
        int mark = -1;
        String fileName = "io.txt";
        String filePath = prePath + fileName;
        try {
            is = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[1024];
            int read = is.read(buffer);
            while (read != mark) {
                for (int i = 0; i < read; i++) {
                    stringBuilder.append((char)buffer[i]);
                }
                read = is.read(buffer);
            }

            logger.info("文件 {} 的内容是: {}", fileName, stringBuilder);
        } catch (IOException e) {
            logger.info("exception=", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
        }
        return stringBuilder;
    }

    public StringBuilder readNio(String prePath) {
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();
        int mark = -1;
        String fileName = "io.txt";
        String filePath = prePath + fileName;
        try {
            fis = new FileInputStream(filePath);
            FileChannel channel = fis.getChannel();
            // 分配空间
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 从channel中读取数据到buffer
            int read = channel.read(byteBuffer);

            while (read != mark) {
                // 翻转缓冲区，position设置为0，limit设置为之前position的值
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    stringBuilder.append((char)byteBuffer.get());
                }

                byteBuffer.compact();
                read = channel.read(byteBuffer);
            }

            logger.info("文件 {} 的内容是: {}", fileName, stringBuilder);
        } catch (IOException e) {
            logger.info("exception=", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
        }
        return stringBuilder;
    }

    /**
     * FileChannel不可以设置为非阻塞模式，他只能在阻塞模式下运行。
     * 原因：磁盘io相对于网络io，获取io状态是很快的，拿到文件描述符就知道了文件是否可读写
     */
    private static void writeNio(String prePath, StringBuilder stringBuilder) {
        String fileName = "io-nio-out.txt";
        String filePath = prePath + fileName;
        FileOutputStream fos = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            // 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
            logger.info("初始化容量: {}, limit：{}", byteBuffer.capacity(), byteBuffer.limit());
            fos = new FileOutputStream(filePath);
            FileChannel channel = fos.getChannel();
            channel.write(byteBuffer);

            int length = 0;
            while ((length = channel.write(byteBuffer)) != 0) {
                // 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
                logger.info("写入长度: {}", length);
            }
        } catch (IOException e) {
            logger.info("exception=", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
        }
    }
}

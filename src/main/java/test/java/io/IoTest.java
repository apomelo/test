package test.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author C
 * @date 2022/4/8
 */
public class IoTest {
    private static final Logger logger = LoggerFactory.getLogger(IoTest.class);

    public static void main(String[] args) {
    }

    private static StringBuilder readIO() {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        int mark = -1;
        try {
            inputStream = new BufferedInputStream(new FileInputStream("io.txt"));
            byte[] buffer = new byte[1024];
            int read = inputStream.read(buffer);
            while (read != mark) {
                for (int i = 0; i < read; i++) {
                    stringBuilder.append((char)buffer[i]);
                }
                read = inputStream.read(buffer);
            }

            logger.info("文件 io.txt 的内容是: {}", stringBuilder);
        } catch (IOException e) {
            logger.info("exception=", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder;
    }

    public StringBuilder readNIO() {
        FileInputStream fileInputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        int mark = -1;
        try {
            fileInputStream = new FileInputStream("io.txt");
            FileChannel channel = fileInputStream.getChannel();
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

            logger.info("文件 io.txt 的内容是: {}", stringBuilder);
        } catch (IOException e) {
            logger.info("exception=", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder;
    }

    private static void writeNIO(StringBuilder stringBuilder) {
        String fileName = "io-nio-out.txt";
        FileOutputStream fos = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            fos = new FileOutputStream(fileName);
            FileChannel channel = fos.getChannel();
            channel.write(byteBuffer);
        } catch (IOException e) {
            logger.info("exception=", e);
        }
    }

    private static void writeNIO2(StringBuilder stringBuilder) {
        String filename = "out.txt";
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(new File(filename));
            FileChannel channel = fos.getChannel();
            ByteBuffer src = stringBuilder.toString();
            // 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
            System.out.println("初始化容量和limit：" + src.capacity() + ","
                    + src.limit());
            int length = 0;

            while ((length = channel.write(src)) != 0) {
                /*
                 * 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
                 */
                System.out.println("写入长度:" + length);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

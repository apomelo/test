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
        StringBuilder stringBuilder1 = readIo(filePath);
        writeIo(filePath, stringBuilder1);
        StringBuilder stringBuilder2 = readNio(filePath);
        writeNio(filePath, stringBuilder2);
        channelCopy(filePath);
        transferFromOrTo(filePath);
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
            int readLength;
            ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
            while ((readLength = is.read(buffer)) != mark) {
                byteArrayOutputStream.write(buffer, 0, readLength);
            }
            stringBuilder.append(byteArrayOutputStream.toString("UTF-8"));
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

    private static void writeIo(String prePath, StringBuilder stringBuilder) {
        String fileName = "io-io-out.txt";
        String filePath = prePath + fileName;
        OutputStream os = null;
        try {
            byte[] bytes = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
            logger.info("bytes长度: {}", bytes.length);
            os = new BufferedOutputStream(new FileOutputStream(filePath));
            os.write(bytes);
            logger.info("写入文件: {}", bytes.length);
        } catch (IOException e) {
            logger.info("exception=", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
        }
    }

    private static StringBuilder readNio(String prePath) {
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
            int read;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((read = channel.read(byteBuffer)) != mark) {
                // 翻转缓冲区，position设置为0，limit设置为之前position的值
                byteBuffer.flip();
                byte[] temp = new byte[read];
//                byte[] temp = new byte[byteBuffer.remaining()];
                byteBuffer.get(temp);
                byteArrayOutputStream.write(temp);

                byteBuffer.compact();
            }

            stringBuilder.append(byteArrayOutputStream.toString("UTF-8"));
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

    private static void channelCopy(String prePath) {
        String inFileName = "io.txt";
        String outFileName = "io-channel-copy.txt";
        String inFilePath = prePath + inFileName;
        String outFilePath = prePath + outFileName;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis= new FileInputStream(inFilePath);
            fos = new FileOutputStream(outFilePath);
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                //清空重置
                byteBuffer.clear();
            }
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
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
        }
    }

    private static void transferFromOrTo(String prePath) {
        String inFileName = "io.txt";
        String outFileName1 = "io-transfer-from.txt";
        String outFileName2 = "io-transfer-to.txt";
        String inFilePath = prePath + inFileName;
        String outFilePath1 = prePath + outFileName1;
        String outFilePath2 = prePath + outFileName2;
        FileInputStream fis = null;
        FileOutputStream fos1 = null;
        FileOutputStream fos2 = null;
        try {
            fis= new FileInputStream(inFilePath);
            fos1 = new FileOutputStream(outFilePath1);
            fos2 = new FileOutputStream(outFilePath2);
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel1 = fos1.getChannel();
            FileChannel outChannel2 = fos2.getChannel();
            // transferFrom方法，从哪拷贝，从哪个位置开始拷贝多长
            outChannel1.transferFrom(inChannel, 0, inChannel.size());
            // transferTo方法，拷贝到哪儿，从哪个位置开始拷贝多长
            inChannel.transferTo(0, inChannel.size(), outChannel2);
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
            if (fos1 != null) {
                try {
                    fos1.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
            if (fos2 != null) {
                try {
                    fos2.close();
                } catch (IOException e) {
                    logger.info("exception=", e);
                }
            }
        }
    }
}

package test.java.io;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.ApplicationConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by C on 2019/4/10.
 */
public class FileTest {

    private static final Logger logger = LoggerFactory.getLogger(FileTest.class);

    public static void main(String[] args) throws IOException {
        test1();
//        test2();
    }

    private static void test1() throws IOException {
//        String pre = new String(("D:" + File.separator).getBytes(), "UTF-8");
        String pre = ApplicationConfig.getInstance().getFilePath();
        String name1 = new String("测试1.raw".getBytes(), "UTF-8");
        String name2 = new String("测试2.raw".getBytes(), "GBK");
        String name3 = new String("测试3.raw".getBytes(), "ISO-8859-1");
        String name4 = new String("测试4.raw".getBytes(), Charset.forName("US-ASCII"));
        String path1 = pre + name1;
        String path2 = pre + name2;
        String path3 = pre + name3;
        String path4 = pre + name4;

        logger.info("path1 >>>>>> {}", path1);
        logger.info("path2 >>>>>> {}", path2);
        logger.info("path3 >>>>>> {}", path3);
        logger.info("path4 >>>>>> {}", path4);

        FileUtils.writeByteArrayToFile(new File(path1), new byte[100]);
        FileUtils.writeByteArrayToFile(new File(path2), new byte[100]);
        FileUtils.writeByteArrayToFile(new File(path3), new byte[100]);
        FileUtils.writeByteArrayToFile(new File(path4), new byte[100]);
    }

    private static void test3() throws IOException {
        String pre = "D:" + File.separator;
        String name1 = "测试1.raw";
        String name2 = "测试2.raw";
        String name3 = "测试3.raw";
        String name4 = "测试4.raw";
        String path1 = new String((pre + name1).getBytes(), "UTF-8");
        String path2 = new String((pre + name2).getBytes(), "GBK");
        String path3 = new String((pre + name3).getBytes(), "ISO-8859-1");
        String path4 = new String((pre + name4).getBytes(), Charset.forName("US-ASCII"));

        FileUtils.writeByteArrayToFile(new File(path1), new byte[100]);
        FileUtils.writeByteArrayToFile(new File(path2), new byte[100]);
        FileUtils.writeByteArrayToFile(new File(path3), new byte[100]);
        FileUtils.writeByteArrayToFile(new File(path4), new byte[100]);
    }
}

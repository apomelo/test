package test.java.io;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by C on 2018/12/24.
 */
public class ByteArrayOutputStreamTest {

    public static void main(String[] args) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        System.out.println(byteArrayOutputStream.size());
        System.out.println(Arrays.toString(byteArrayOutputStream.toByteArray()));
        byte[] b = new byte[30];
        byteArrayOutputStream.write(b, 0, b.length);
        System.out.println(byteArrayOutputStream.size());
        System.out.println(Arrays.toString(byteArrayOutputStream.toByteArray()));
        byteArrayOutputStream.reset();
        System.out.println(byteArrayOutputStream.size());
        System.out.println(Arrays.toString(byteArrayOutputStream.toByteArray()));
    }

}

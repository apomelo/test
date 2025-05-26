package test.base.bytes;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * Created by C on 2019/1/25.
 */
public class ByteTest {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        byte[] srcArray = new byte[]{1,2,3,4,5,6,7};
        byte[] copyArray = new byte[0];
        System.out.println(srcArray.length);
        System.out.println(copyArray.length);
        byte[] destArray = ArrayUtils.addAll(srcArray, copyArray);
        System.out.println(Arrays.toString(destArray));
    }
}

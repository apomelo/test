package com.jnitest.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2018/10/24.
 */
public class JniTest {

    public static Logger logger = LoggerFactory.getLogger(JniTest.class);

    native public void testVoid();

    native public boolean testBoolean(boolean isTrue);

    native public char testChar(char c);

    native public byte testByte(byte b);

    native public short testShort(short s);

    native public int testInt(int i);

    native public long testLong(long l);

    native public float testFloat(float f);

    native public double testDouble(double d);

    native public String testString(String str);

    native public byte[] testByteArray(byte[] bytes);

    native public boolean testMixed(byte[] bytes, boolean isBoolean, String s);

    native public void testOnCalledOne();

    native public void testOnCalledTwo();

    /**
     * bytes数组传进去之后经过处理，原数组不变（不经过ReleaseByteArrayElements方法）
     * @param data
     * @return 返回修改后的数据
     */
    native public byte[] testByteArrayReference1(byte[] data);

    /**
     * bytes数组传进去之后经过处理，原数组更改（经过ReleaseByteArrayElements方法）
     * @param data
     * @return 返回修改后的数据
     */
    native public byte[] testByteArrayReference2(byte[] data);

    public void onTestVoid() {
        logger.info("This is onTestVoid callback function. Message: []");
        return;
    }

    public boolean onTestBoolean(boolean isTrue) {
        logger.info("This is onTestBoolean callback function. Message: [isTrue={}]", isTrue);
        return isTrue;
    }

    public char onTestChar(char c) {
        logger.info("This is onTestChar callback function. Message: [c={}]", c);
        return c;
    }

    public byte onTestByte(byte b) {
        logger.info("This is onTestByte callback function. Message: [b={}]", b);
        return b;
    }

    public short onTestShort(short s) {
        logger.info("This is onTestShort callback function. Message: [s={}]", s);
        return s;
    }

    public int onTestInt(int i) {
        logger.info("This is onTestInt callback function. Message: [i={}]", i);
        return i;
    }

    public long onTestLong(long l) {
        logger.info("This is onTestLong callback function. Message: [l={}]", l);
        return l;
    }

    public float onTestFloat(float f) {
        logger.info("This is onTestFloat callback function. Message: [f={}]", f);
        return f;
    }

    public double onTestDouble(double d) {
        logger.info("This is onTestDouble callback function. Message: [d={}]", d);
        return d;
    }

    public String onTestString(String str) {
        logger.info("This is onTestString callback function. Message: [str={}]", str);
        return str;
    }

    public byte[] onTestByteArray(byte[] bytes) {
        logger.info("This is onTestByteArray callback function. Message: [bytes={}]", bytes);
        return bytes;
    }

    public byte[] onTestMixed(byte[] bytes, boolean isBoolean, String s) {
        logger.info("This is onTestMixed callback function. Message: [bytes={}, isBoolean={}, s={}]", bytes, isBoolean, s);
        return bytes;
    }

}

package com.jnitest;

import com.jnitest.jni.JniTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2018/10/24.
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        testJniTest();
    }

    public static void testJniTest() {
        System.loadLibrary("jniTest");
        JniTest jniTest = new JniTest();

        jniTest.testVoid();
        jniTest.testBoolean(true);
        jniTest.testChar('c');
        jniTest.testByte((byte) 5);
        jniTest.testShort((short) 6);
        jniTest.testInt(7);
        jniTest.testLong(8);
        jniTest.testFloat((float) 9.0);
        jniTest.testDouble(10.0);
        jniTest.testString("c");
        jniTest.testByteArray(new byte[6]);
        jniTest.testMixed(new byte[6], true, "c");
//        jniTest.testOnCalledOne();   // 不会回调到类OnCalledOne的函数中
//        jniTest.testOnCalledTwo();   // 回调到了类JniTest的函数中，随机回调了一个, 甚至有时会发生错误！！！

        byte[] srcBytes1 = {1,2,3,4,5,6};
        logger.info("srcBytes1 = {}", srcBytes1);
        byte[] destBytes1 = jniTest.testByteArrayReference1(srcBytes1);
        logger.info("srcBytes1 = {}", srcBytes1);
        logger.info("destBytes1 = {}", destBytes1);

        byte[] srcBytes2 = {1,2,3,4,5,6};
        logger.info("srcBytes2 = {}", srcBytes2);
        byte[] destBytes2 = jniTest.testByteArrayReference2(srcBytes2);
        logger.info("srcBytes2 = {}", srcBytes2);
        logger.info("destBytes2 = {}", destBytes2);
    }
}

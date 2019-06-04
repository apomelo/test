package test.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by C on 2019/1/14.
 */
public class StringRegexTest {
    private static final Logger logger = LoggerFactory.getLogger(StringRegexTest.class);

    public static void main(String[] args) {
        String sentence = "你好，请问你是？我是xxx；句号前的句子。叹号前的句子！结束句。";
        test1(sentence);
        test2(sentence);
        test3(sentence);
        test4(sentence);
        test5(sentence);
        test6(sentence);
        test7(sentence);
        test8(sentence);
        test9(sentence);
        test10(sentence);
        test11(sentence);
        test12();
        test13();
        test14();
    }

    /**
     * 以？为分隔符，不包含？
     * @param sentence
     */
    public static void test1(String sentence) {
        String regex = "(?:？)";
        String[] clause = sentence.split(regex);
        System.out.println("test1: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以？为分隔符，？拼接在后面的语句上
     * @param sentence
     */
    public static void test2(String sentence) {
        String regex = "(?=？)";
        String[] clause = sentence.split(regex);
        System.out.println("test2: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以非？为分隔符，？拼接在前面的语句上
     * @param sentence
     */
    public static void test3(String sentence) {
        String regex = "(?!？)";
        String[] clause = sentence.split(regex);
        System.out.println("test3: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以？为分隔符，？拼接在前面的语句上
     * @param sentence
     */
    public static void test4(String sentence) {
        String regex = "(?<=？)";
        String[] clause = sentence.split(regex);
        System.out.println("test4: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以非？为分隔符，？拼接在后面的语句上
     * @param sentence
     */
    public static void test5(String sentence) {
        String regex = "(?<!？)";
        String[] clause = sentence.split(regex);
        System.out.println("test5: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以？为分隔符，不包含？
     * @param sentence
     */
    public static void test6(String sentence) {
        String regex = "(?:；|。|！|？)";
        String[] clause = sentence.split(regex);
        System.out.println("test6: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以？为分隔符，？拼接在后面的语句上
     * @param sentence
     */
    public static void test7(String sentence) {
        String regex = "(?=；|。|！|？)";
        String[] clause = sentence.split(regex);
        System.out.println("test7: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以非；|。|！|？为分隔符，；|。|！|？拼接在前面的语句上
     * @param sentence
     */
    public static void test8(String sentence) {
        String regex = "(?!；|。|！|？)";
        String[] clause = sentence.split(regex);
        System.out.println("test8: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以；|。|！|？为分隔符，；|。|！|？拼接在前面的语句上
     * @param sentence
     */
    public static void test9(String sentence) {
        String regex = "(?<=；|。|！|？)";
        String[] clause = sentence.split(regex);
        System.out.println("test9: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 以非；|。|！|？为分隔符，；|。|！|？拼接在后面的语句上
     * @param sentence
     */
    public static void test10(String sentence) {
        String regex = "(?<!；|。|！|？)";
        String[] clause = sentence.split(regex);
        System.out.println("test10: ");
        System.out.println(Arrays.toString(clause));
    }

    /**
     * 匹配所有标点符号
     * @param sentence
     */
    public static void test11(String sentence) {
        String regex = "[\\pP\\p{Punct}]";
        String s = sentence.replaceAll(regex, "");
        System.out.println("test11: ");
        System.out.println("s: " + s);
        System.out.println("sentence: " + sentence);
    }

    /**
     * 以最后一个“-”分割为两个数组
     */
    public static void test12() {
        String sentence = "aaa-bbb-ccc-ddd-1";
        String regex = "-";
        int index = sentence.lastIndexOf(regex);
        String s1 = sentence.substring(0, index);
        String s2 = sentence.substring(index + 1, sentence.length());
        System.out.println("test12: ");
        System.out.println("s1: " + s1);
        System.out.println("s2: " + s2);
        System.out.println("sentence: " + sentence);
    }

    /**
     * 以//（双斜杠）为分隔符
     */
    public static void test13() {
        String sentence = "FastDfs://M00/1C/00/CgpRJVxuCIOAOUMcAAEDjL6MUa0904.WAV";
        String regex = "//";
        String[] split = sentence.split(regex);
        System.out.println("test13: ");
        System.out.println("s1: " + split[0]);
        System.out.println("s2: " + split[1]);
        System.out.println("sentence: " + sentence);
    }

    /**
     * 替换非字母、数字和下划线的字符串
     */
    public static void test14() {
        String sentence = "#_1234asdf#*你好123";
        String regex = "\\W";
        String result = sentence.replaceAll(regex, "");
        System.out.println("test14: ");
        System.out.println("result: " + result);
        System.out.println("sentence: " + sentence);
    }
}

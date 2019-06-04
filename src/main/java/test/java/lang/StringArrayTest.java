package test.java.lang;

import java.util.Arrays;

/**
 * Created by C on 2019/1/15.
 */
public class StringArrayTest {
    public static void main(String[] args) {
        String[] strs = {"123", "234", "345"};
        System.out.println(Arrays.toString(strs));
        strs[1] = "qqq";
        System.out.println(Arrays.toString(strs));
        String a = "1,";
        String[] as = a.split(",");
        System.out.println(Arrays.toString(as));
    }
}

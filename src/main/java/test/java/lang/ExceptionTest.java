package test.java.lang;

/**
 * Created by C on 2018/9/25.
 */
public class ExceptionTest {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    public static void test1() {
        int i = 1;
        try {
            i = i / 0;
        } catch (Exception e) {
//            System.out.println("1111111, " + e.getMessage());
//            System.out.println("1111111, " + e.getCause().getMessage());
//            System.out.println("1111111, " + e.getCause());
            System.out.println("1111111, " + e);
        }
    }

    public static void test2() {
        int i = 1;
        try {
            i = i / 0;
        } finally {
            System.out.println("1111111");
        }
    }

    public static void test3() {
        int i = 1;
        try {
            i = i * 0;
        } finally {
            System.out.println("222222");
        }
    }
}

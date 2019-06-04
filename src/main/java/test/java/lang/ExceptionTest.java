package test.java.lang;

/**
 * Created by C on 2018/9/25.
 */
public class ExceptionTest {

    public static void main(String[] args) {
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

}

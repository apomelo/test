package test.base.compare;

/**
 * Created by C on 2018/12/13.
 */
public class Compare {

    public static void main(String[] args) {
        byte b = 0;
        b = 1;
        int i = 1;
        boolean result = (b == i);
        System.out.println(result);
        System.out.println(b);
        System.out.println(i);

        char c = 0;
        int i2 = 0;
        System.out.println((c == i2));
    }

}

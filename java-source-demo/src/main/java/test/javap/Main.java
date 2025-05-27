package test.javap;

/**
 * @author C
 * @date 2022/7/29
 */
public class Main {
    private int m;

    public static void main(String[] args) {
    }

    public int inc() {
        return m + 1;
    }

    public int foo() {
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }
    }
}

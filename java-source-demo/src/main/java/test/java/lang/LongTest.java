package test.java.lang;

/**
 * @author C
 * @date 2022/5/21
 */
public class LongTest {

    public static void main(String[] args) {
        testCacheLineEffect();
    }

    private static void testCacheLineEffect() {
        //考虑一般缓存行大小是64字节，一个 long 类型占8字节
        long[][] arr;

        arr = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long sum = 0L;
        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i += 1) {
            for (int j = 0; j < 8; j++) {
                sum = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int j = 0; j < 8; j += 1) {
            for (int i = 0; i < 1024 * 1024; i++) {
                sum = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}

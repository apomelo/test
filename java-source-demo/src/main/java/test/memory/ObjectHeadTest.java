package test.memory;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import util.ObjectUtil;

/**
 * @author C
 * @date 2023/6/24
 */
@Slf4j
public class ObjectHeadTest {
    public static void main(String[] args) {
        testArray();
        testObject();
    }

    /**
     * 打印数组内存占用信息
     * 分析数组对象的对象头占用以及对其所用的空间
     */
    private static void testArray() {
        printObjectMemory(new boolean[10]);
        printObjectMemory(new boolean[16]);
        printObjectMemory(new byte[10]);
        printObjectMemory(new byte[16]);
        printObjectMemory(new int[10]);
        printObjectMemory(new int[16]);
    }

    /**
     * 打印对象内存占用信息
     * 分析单个对象的对象头占用以及对其所用的空间
     */
    private static void testObject() {
        printObjectMemory(new BooleanObject1());
        printObjectMemory(new CharObject1());
        printObjectMemory(new ByteObject1());
        printObjectMemory(new ShortObject1());
        printObjectMemory(new IntObject1());
        printObjectMemory(new LongObject1());
        printObjectMemory(new FloatObject1());
        printObjectMemory(new DoubleObject1());
    }

    private static void printObjectMemory(Object object) {
        String className = ObjectUtil.getName(object);
        log.info("{} object head: {}", className, ClassLayout.parseInstance(object).toPrintable());
    }
}

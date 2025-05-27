package test.memory;

import lombok.extern.slf4j.Slf4j;
import util.ObjectUtil;

@Slf4j
public class ObjectMemoryTest {
    private static final int SIZE = 100_000;

    public static void main(String[] args) throws Exception {
        // boolean 占用内存
        printMemory(BooleanObject80.class);
        // boolean 占用内存
        printMemory(BooleanObjectArray80.class);
        // char 占用内存
        printMemory(CharObject80.class);
        // char 占用内存
        printMemory(CharObjectArray80.class);
        // byte 占用内存
        printMemory(ByteObject80.class);
        // byte 占用内存
        printMemory(ByteObjectArray80.class);
        // short 占用内存
        printMemory(ShortObject80.class);
        // int 占用内存
        printMemory(IntObject80.class);
        // long 占用内存
        printMemory(LongObject80.class);
        // float 占用内存
        printMemory(FloatObject80.class);
        // double 占用内存
        printMemory(DoubleObject80.class);
    }

    private static void printMemory(Class<? extends Memory> memClass) {
        String className = ObjectUtil.getName(memClass);
        // boolean 占用内存
        Memory[] memories = new Memory[SIZE];
        System.gc();
        long startMem = getMemory();
        for (int i = 0; i < SIZE; i++) {
            try {
                memories[i] = memClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        System.gc();
        long endMem = getMemory();
        log.info("{} total size: {}", className, endMem - startMem);
        log.info("{} average size: {}", className, (endMem - startMem) / ((double) SIZE));
        // 下面这个调用保证上面 gc 的时候不会被回收
        emptyInvoke(memories);
    }

    private static long getMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void emptyInvoke(Memory[] memories) {
    }
}

package example.io.netty.msg.pack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PackageCounter {
    private static boolean msgCount;
    private static Map<Integer, AtomicLong> downPackageSizeMap;
    private static Map<Integer, AtomicLong> downPackageCountMap;
    private static Map<Integer, AtomicLong> upPackageSizeMap;
    private static Map<Integer, AtomicLong> upPackageCountMap;

    static {
        String msgCountStr = System.getProperty("game.log.MsgCount", "false");
        msgCount = Boolean.parseBoolean(msgCountStr);
        downPackageSizeMap = new ConcurrentHashMap<>();
        downPackageCountMap = new ConcurrentHashMap<>();
        upPackageSizeMap = new ConcurrentHashMap<>();
        upPackageCountMap = new ConcurrentHashMap<>();
    }

    public PackageCounter() {
    }

    public static boolean isCounting() {
        return msgCount;
    }

    public static void setMsgCount(boolean msgCount) {
        PackageCounter.msgCount = msgCount;
    }

    public static void up(int msgId, int size) {
        update(msgId, size, upPackageCountMap, upPackageSizeMap);
    }

    public static void down(int msgId, int size) {
        update(msgId, size, downPackageCountMap, downPackageSizeMap);
    }

    private static void update(int msgId, int size, Map<Integer, AtomicLong> downPackageCountMap, Map<Integer, AtomicLong> downPackageSizeMap) {
        AtomicLong packageCount = downPackageCountMap.get(msgId);
        AtomicLong packageSize;
        if (packageCount == null) {
            packageCount = new AtomicLong(0L);
            packageSize = downPackageCountMap.putIfAbsent(msgId, packageCount);
            if (packageSize != null) {
                packageCount = packageSize;
            }
        }

        packageCount.incrementAndGet();
        packageSize = downPackageSizeMap.get(msgId);
        if (packageSize == null) {
            packageSize = new AtomicLong(0L);
            AtomicLong oldPackageSize = downPackageSizeMap.putIfAbsent(msgId, packageSize);
            if (oldPackageSize != null) {
                packageSize = oldPackageSize;
            }
        }

        packageSize.accumulateAndGet(size, Long::sum);
    }

    public static Map<Integer, AtomicLong> getDownPackageSizeMap() {
        return downPackageSizeMap;
    }

    public static Map<Integer, AtomicLong> getDownPackageCountMap() {
        return downPackageCountMap;
    }

    public static Map<Integer, AtomicLong> getUpPackageSizeMap() {
        return upPackageSizeMap;
    }

    public static Map<Integer, AtomicLong> getUpPackageCountMap() {
        return upPackageCountMap;
    }
}

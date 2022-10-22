package test.com.google.common.hash;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.LoggerUtils;

public class BloomFilterTest {
    private static final Logger logger = LoggerFactory.getLogger(BloomFilterTest.class);

    private static final int total = 10_000_000;
    private static final int excludeNum = 10_000;
    private static final BloomFilter<Integer> bf1 = BloomFilter.create(Funnels.integerFunnel(), total);
    private static final BloomFilter<Integer> bf2 = BloomFilter.create(Funnels.integerFunnel(), total, 0.0001);

    public static void main(String[] args) {
        optimalNumOfBits(total, 0.03);
        optimalNumOfBits(total, 0.001);
        optimalNumOfBits(total, 0.0001);
        optimalNumOfBits(total, 0.00001);
//        test(bf1, 1);
//        test(bf2, 2);
    }

    /**
     * 计算最优bits数组长度
     * @param n - expected insertions (must be positive)
     * @param p – false positive rate (must be 0 < p < 1)
     * @return
     */
    private static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        long optimalNum = (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));

        long x = optimalNum >> 3;
        long k = (x >> 10) % 1024;
        long m = (x >> 20) % 1024;
        long g = (x >> 30) % 1024;
        logger.info("预期最大数值={}, 期望概率={}, 最优bits数组长度={}, 最优hash执行次数={}, 占用空间 {}g {}m {}k 字节",
                n, p, optimalNum, optimalNumOfHashFunctions(n, optimalNum), g, m, k);
        return optimalNum;
    }

    /**
     * 计算最优hash执行次数
     * @param n - expected insertions (must be positive)
     * @param m – total number of bits in Bloom filter (must be positive)
     * @return
     */
    private static int optimalNumOfHashFunctions(long n, long m) {
        // (m / n) * log(2), but avoid truncation due to division!
        int optimalNum = Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
//        logger.info("预期最大数值={}, 最优bits数组长度={}, 最优hash执行次数={}", n, m, optimalNum);
        return optimalNum;
    }

    private static void test(BloomFilter<Integer> bf, int id) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        int errorCount1 = 0;
        for (int i = 0; i < total; i++) {
            if (!bf.mightContain(i)) {
                errorCount1++;
            }
        }
        logger.info("测试{} - 匹配不上的数量={}, 概率={}", id, errorCount1, errorCount1 * 1.0 / total);

        // 匹配不在过滤器中的10000个值，有多少被匹配上
        int errorCount2 = 0;
        for (int i = 0; i < excludeNum; i++) {
            int excludeNum = total + i;
            excludeNum = RandomUtils.nextInt(total, total * 2);
            if (bf.mightContain(excludeNum)) {
                errorCount2++;
            }
        }
        logger.info("测试{} - 误匹配上的数量={}, 概率={}", id, errorCount2, errorCount2 * 1.0 / total);
    }
}
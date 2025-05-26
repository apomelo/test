package example.hash.bloomfliter;

import java.util.BitSet;

public class BloomFilter {
    /**
     * BitSet初始分配2^24个bit
     */
    private static final int DEFAULT_SIZE = 1 << 25;
    /**
     * 不同哈希函数的种子，一般应取质数
     */
    private static final int[] seeds = new int[]{5, 7, 11, 13, 31, 37, 61};
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    /**
     * 哈希函数对象
     */
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    /**
     * 将字符串标记到bits中
     */
    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    /**
     * 判断字符串是否已经被bits标记
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    /**
     * 哈希函数类
     */
    public static class SimpleHash {
        static final int MAXIMUM_CAPACITY = 1 << 30;

        private int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.setCap(cap);
            this.seed = seed;
        }

        /**
         * 禁止子类重写
         */
        final void setCap(int cap) {
            int n = cap - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
            this.cap = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        }

        /**
         * hash函数，采用简单的加权和hash
         * 更多 hash 函数参考： {@link util.hash.HashUtils}
         */
        public int hash(String value) {
            int result = 0;
            for (int i = 0; i < value.length(); i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }
}

package example.game.config;

import com.google.common.collect.HashBasedTable;

/**
 * @author C
 * @date 2023/9/1
 */
public class BuffConfigCache {
    private BuffConfigCache() {
        initData();
    }

    public static BuffConfigCache getInstance() {
        return Instance.instance;
    }

    private static class Instance {
        private final static BuffConfigCache instance = new BuffConfigCache();
    }

    private HashBasedTable<Integer, Integer, BuffConfig> buffTable = HashBasedTable.create();

    private void initData() {
        buffTable.put(1, 1, new BuffConfig(1, 1, 1, 1, 1, 1));
        buffTable.put(2, 1, new BuffConfig(2, 2, 1, 2, 3, 2));
        buffTable.put(3, 1, new BuffConfig(3, 3, 1, 1, 10, 3));
    }

    public BuffConfig getBuffConfig(int buffId, int buffLevel) {
        return buffTable.get(buffId, buffLevel);
    }
}

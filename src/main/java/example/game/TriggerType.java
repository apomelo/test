package example.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author C
 * @date 2023/9/1
 */
public enum TriggerType {
    ROUND_START(1),
    ROUND_END(2),
    HURT_BEFORE(3),
    HURT_AFTER(4),
    ;
    private int type;
    TriggerType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    private static Map<Integer, TriggerType> cache = new HashMap<>();
    static {
        for (TriggerType triggerType : values()) {
            cache.put(triggerType.getType(), triggerType);
        }
    }

    public static TriggerType getTriggerType(int type) {
        return cache.get(type);
    }
}

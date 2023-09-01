package example.game;

import lombok.Data;

/**
 * @author C
 * @date 2023/8/30
 */
@Data
public class Buff {
    private int id;
    private int level;
    private int sourceId;
    private int targetId;
    private int startRound;
    private int expireRound;
    private TriggerType triggerType;
    private int effectId;
}

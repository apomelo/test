package example.game.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author C
 * @date 2023/9/1
 */
@Data
@AllArgsConstructor
public class BuffConfig {
    private int id;
    private int buffId;
    private int buffLevel;
    private int triggerType;
    private int effectRound;
    private int effectId;
}

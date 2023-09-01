package example.game;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author C
 * @date 2023/8/31
 */
@Data
public class BattleUnit {
    private int id;
    private boolean attacker;
    private boolean ignoreAttack;
    private UnitType unitType;
    // <attrId, attrValue>
    private Map<Integer, Long> attrMap = new HashMap<>();
    // <buffId, buffLevel>
    private Map<Integer, Integer> buffMap = new HashMap<>();
}

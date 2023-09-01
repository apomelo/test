package example.game;

import lombok.Data;

import java.util.List;

/**
 * @author C
 * @date 2023/8/31
 */
@Data
public class BattleFrame {
    private int round;
    private BattleAction battleAction;
    private long actionParam;
    private int castId;
    private int targetId;
    private List<BattleUnitState> battleUnitStateList;
}

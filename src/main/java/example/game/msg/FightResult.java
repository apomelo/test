package example.game.msg;

import lombok.Data;

import java.util.List;

/**
 * @author C
 * @date 2023/8/31
 */
@Data
public class FightResult {
    private FightResultState fightResultState;
    private List<FightUnitBean> fightUnitBeanList;
    private List<FightFrame> fightFrameList;
}

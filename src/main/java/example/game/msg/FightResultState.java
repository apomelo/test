package example.game.msg;

import lombok.Data;

import java.util.List;

/**
 * @author C
 * @date 2023/8/31
 */
@Data
public class FightResultState {
    private boolean isAttackerWin;
    private List<FightBean> fightBeanList;
}

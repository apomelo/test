package example.game.msg;

import java.util.List;

/**
 * @author C
 * @date 2023/8/30
 */
public class FightUnitBean {
    private int id;
    private boolean attacker;
    private FightBeanType fightBeanType;
    private int monsterId;
    private int monsterLevel;
    private List<EquipBean> equipBeanList;
    private List<FashionBean> fashionBeanList;
}

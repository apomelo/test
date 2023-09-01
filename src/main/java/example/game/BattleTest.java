package example.game;
import example.game.UnitType;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C
 * @date 2023/9/1
 */
public class BattleTest {
    public static void main(String[] args) {
        List<BattleUnit> attackerList = new ArrayList<>();
        BattleUnit attacker = new BattleUnit();
        attacker.setId(1);
        attacker.setAttacker(true);
        attacker.setUnitType(UnitType.HERO);
        attacker.getAttrMap().put(1, 100L);
        attacker.getAttrMap().put(2, 100L);
        attacker.getAttrMap().put(3, 20L);
        attacker.getAttrMap().put(4, 100L);
        attacker.getBuffMap().put(1, 1);
        attacker.getBuffMap().put(2, 1);
        attackerList.add(attacker);
        List<BattleUnit> defenderList = new ArrayList<>();
        BattleUnit defender = new BattleUnit();
        defender.setId(2);
        defender.setAttacker(false);
        defender.setUnitType(UnitType.HERO);
        defender.getAttrMap().put(1, 100L);
        defender.getAttrMap().put(2, 100L);
        defender.getAttrMap().put(3, 20L);
        defender.getAttrMap().put(4, 100L);
        defender.getBuffMap().put(1, 1);
        defender.getBuffMap().put(3, 1);
        defenderList.add(defender);
        BattleField battleField = new BattleField(attackerList, defenderList);
        battleField.battle();
        battleField.printResult();
    }
}

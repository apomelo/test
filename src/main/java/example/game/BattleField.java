package example.game;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import example.game.config.BuffConfig;
import example.game.config.BuffConfigCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

/**
 * @author C
 * @date 2023/8/31
 */
@Slf4j
public class BattleField {
    private static final int maxRound = 5;
    private List<BattleUnit> attackerList = new ArrayList<>();
    private List<BattleUnit> defenderList = new ArrayList<>();
    private Map<Integer, BattleUnit> unitMap = new HashMap<>();
    private int seed;
    private int round;
    private boolean attackerWin;
    private List<BattleFrame> battleFrameList = new ArrayList<>();
    private List<Buff> buffList = new ArrayList<>();
    private Map<Integer, List<Buff>> buffTriggerMap = new HashMap<>();

    public BattleField(List<BattleUnit> attackerList, List<BattleUnit> defenderList) {
        this.attackerList.addAll(attackerList);
        this.defenderList.addAll(defenderList);
        attackerList.forEach(attacker -> unitMap.put(attacker.getId(), attacker));
        defenderList.forEach(defender -> unitMap.put(defender.getId(), defender));
        this.seed = RandomUtils.nextInt(1, Integer.MAX_VALUE >> 2);
    }

    public void battle() {
        initBattle();
        doBattle();
    }

    private void doBattle() {
        for (round = 1; round <= maxRound; round++) {
            doBattleRound();
        }
    }

    private void doBattleRound() {
        // 回合前触发
        triggerBuff(TriggerType.ROUND_START);

        // 战斗
        // 获取出手顺序
        unitMap.values().stream().max(Comparator.comparing(v -> v.getAttrMap().get(AttributeConst.AGILITY.getValue())));

        // 回合结束触发
        triggerBuff(TriggerType.ROUND_END);

        // 回合结束检查失效buff
        checkBuff();
    }

    private void triggerBuff(TriggerType triggerType) {
        List<Buff> buffs = buffTriggerMap.get(triggerType.getType());
        if (buffs == null) return;

        buffs.forEach(buff -> {
            int effectId = buff.getEffectId();
            switch (effectId) {
                case 1: { // 加血 10%
                    int targetId = buff.getTargetId();
                    BattleUnit battleUnit = unitMap.get(targetId);
                    if (battleUnit.isIgnoreAttack()) {
                        break;
                    }
                    long hp = battleUnit.getAttrMap().get(AttributeConst.HP.getValue());
                    long hpMax = battleUnit.getAttrMap().get(AttributeConst.HP_MAX.getValue());
                    long hpRise = (long) Math.min(hpMax * 0.1, hpMax - hp);
                    battleUnit.getAttrMap().merge(AttributeConst.HP.getValue(), hpRise, Long::sum);

                    // 构建战斗帧
                    List<BattleUnitState> battleUnitList = new ArrayList<>();
                    BattleUnitState battleUnitState = new BattleUnitState();
                    battleUnitState.setId(targetId);
                    battleUnitState.getAttrMap().put(AttributeConst.HP.getValue(), hpRise);
                    battleUnitList.add(battleUnitState);
                    buildBattleFrame(BattleAction.BUFF, hpRise, buff.getSourceId(), buff.getTargetId(), battleUnitList);
                    break;
                }
                case 2: { // 加攻击 10%
                    int targetId = buff.getTargetId();
                    BattleUnit battleUnit = unitMap.get(targetId);
                    long atk = battleUnit.getAttrMap().get(AttributeConst.ATK.getValue());
                    long atkRise = (long) (atk * 0.1);
                    battleUnit.getAttrMap().merge(AttributeConst.ATK.getValue(), atkRise, Long::sum);

                    // 构建战斗帧
                    List<BattleUnitState> battleUnitList = new ArrayList<>();
                    BattleUnitState battleUnitState = new BattleUnitState();
                    battleUnitState.setId(targetId);
                    battleUnitState.getAttrMap().put(AttributeConst.ATK.getValue(), atkRise);
                    battleUnitList.add(battleUnitState);
                    buildBattleFrame(BattleAction.BUFF, atkRise, buff.getSourceId(), buff.getTargetId(), battleUnitList);
                    break;
                }
                case 3: { // 加敏捷 10
                    int targetId = buff.getTargetId();
                    BattleUnit battleUnit = unitMap.get(targetId);
                    long agilityRise = 10L;
                    battleUnit.getAttrMap().merge(AttributeConst.AGILITY.getValue(), agilityRise, Long::sum);

                    // 构建战斗帧
                    List<BattleUnitState> battleUnitList = new ArrayList<>();
                    BattleUnitState battleUnitState = new BattleUnitState();
                    battleUnitState.setId(targetId);
                    battleUnitState.getAttrMap().put(AttributeConst.AGILITY.getValue(), agilityRise);
                    battleUnitList.add(battleUnitState);
                    buildBattleFrame(BattleAction.BUFF, agilityRise, buff.getSourceId(), buff.getTargetId(), battleUnitList);
                    break;
                }
                default: {
                    log.warn("用到了没有定义的buff效果,effectId={}", effectId);
                }
            }
        });
    }

    private void checkBuff() {
        Iterator<Buff> iterator = buffList.iterator();
        while (iterator.hasNext()) {
            Buff buff = iterator.next();
            if (round >= buff.getExpireRound()) {
                buffTriggerMap.get(buff.getTriggerType().getType()).remove(buff);
                iterator.remove();
            }
        }
    }

    private void initBattle() {
        this.round = 0;
        this.attackerWin = false;
        this.battleFrameList = new ArrayList<>();
        this.buffList = new ArrayList<>();
        this.buffTriggerMap = new HashMap<>();

        initBuff();
        buildBattleFrame(BattleAction.INIT, 0, 0, 0, new ArrayList<>());
    }

    private void initBuff() {
        attackerList.forEach(attacker -> {
            attacker.getBuffMap().forEach((buffId, buffLevel) -> addBuffToBattleField(buffId, buffLevel, attacker.getId(), attacker.getId()));
            attacker.getBuffMap().clear();
        });
        defenderList.forEach(defender -> {
            defender.getBuffMap().forEach((buffId, buffLevel) -> addBuffToBattleField(buffId, buffLevel, defender.getId(), defender.getId()));
            defender.getBuffMap().clear();
        });
    }

    private void addBuffToBattleField(int buffId, int buffLevel, int sourceId, int targetId) {
        Buff buff = buildBuff(buffId, buffLevel, sourceId, targetId);
        buffList.add(buff);
        buffTriggerMap.computeIfAbsent(buff.getTriggerType().getType(), k -> new ArrayList<>()).add(buff);
    }

    private Buff buildBuff(int buffId, int buffLevel, int sourceId, int targetId) {
        BuffConfig buffConfig = BuffConfigCache.getInstance().getBuffConfig(buffId, buffLevel);
        Buff buff = new Buff();
        buff.setId(buffId);
        buff.setLevel(buffLevel);
        buff.setSourceId(sourceId);
        buff.setTargetId(targetId);
        buff.setStartRound(1);
        buff.setExpireRound(1 + buffConfig.getEffectRound());
        buff.setTriggerType(TriggerType.getTriggerType(buffConfig.getTriggerType()));
        buff.setEffectId(buffConfig.getEffectId());
        return buff;
    }

    private void buildBattleFrame(BattleAction action, long actionParam, int castId, int targetId, List<BattleUnitState> battleUnitStateList) {
        BattleFrame battleFrame = new BattleFrame();
        battleFrame.setRound(round);
        battleFrame.setBattleAction(action);
        battleFrame.setActionParam(actionParam);
        battleFrame.setCastId(castId);
        battleFrame.setTargetId(targetId);
        battleFrame.setBattleUnitStateList(battleUnitStateList);
        battleFrameList.add(battleFrame);
    }

    public void printResult() {
        log.info("attackerWin: {}", attackerWin);
        log.info("unitMap: {}", unitMap);
        log.info("buffList: {}", buffList);
        log.info("result: {}", battleFrameList);
        log.info("result: {}", JSON.toJSONString(battleFrameList, SerializerFeature.DisableCircularReferenceDetect));
    }
}

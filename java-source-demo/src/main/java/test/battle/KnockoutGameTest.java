package test.battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by C on 2021/1/6.
 */
public class KnockoutGameTest {
    private static final Logger logger = LoggerFactory.getLogger(KnockoutGameTest.class);
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Set<Integer> winGroupIdSet = new HashSet<>();
        Map<Integer, Boolean> competitorWinMap = new HashMap<>();
        Map<Integer, Integer> competitorGroupMap = new HashMap<>();
        for (int i = 1; i <= 64; i ++) {
            competitorWinMap.put(i, i % 2 != 0);
            competitorGroupMap.put(i, (i - 1) / 2 + 1);
        }
        for (int i = 1; i <= 32; i ++) {
            winGroupIdSet.add(i);
        }

        int winNum = winGroupIdSet.size();
        while (winNum > 0) {
            competitorGroupMap = partition(winGroupIdSet, competitorWinMap, competitorGroupMap);
            if (winNum > 1) {
                winNum = fight(winGroupIdSet, competitorWinMap, competitorGroupMap);
            } else {
                winNum = 0;
            }
        }
    }

    private static Map<Integer, Integer> partition(Set<Integer> winGroupIdSet, Map<Integer, Boolean> competitorWinMap, Map<Integer, Integer> competitorGroupMap) {
        logger.info("winGroupIdSet={}", winGroupIdSet);
        logger.info("competitorWinMap={}", competitorWinMap);
        logger.info("competitorGroupMap={}", competitorGroupMap);

        Map<Integer, Integer> nextCompetitorGroupMap = new HashMap<>();
        int winNum = winGroupIdSet.size();
        competitorGroupMap.forEach((playerId, groupId) -> {
            int nextGroupId;
            if (winNum > 1) {
                if (winGroupIdSet.contains(groupId)) {
                    if (competitorWinMap.get(playerId)) {
                        nextGroupId = (groupId - 1) / 2 + 1;
                    } else {
                        nextGroupId = (groupId - 1) / 2 + 1 + ((groupId - 1) / winNum + winNum / 2);
                    }
                } else {
                    int base = (groupId - 1) / winNum * winNum;
                    int remainder = (groupId - 1) % winNum + 1;
                    if (competitorWinMap.get(playerId)) {
                        nextGroupId = (remainder - 1) / 2 + 1 + base;
                    } else {
                        nextGroupId = (remainder - 1) / 2 + 1 + ((remainder - 1) / winNum + winNum / 2) + base;
                    }
                }
            } else {
                if (competitorWinMap.get(playerId)) {
                    nextGroupId = groupId * 2 - 1;
                } else {
                    nextGroupId = groupId * 2;
                }
            }
            nextCompetitorGroupMap.put(playerId, nextGroupId);
        });
        logger.info("nextCompetitorGroupMap={}", nextCompetitorGroupMap);
        return nextCompetitorGroupMap;
    }

    private static int fight(Set<Integer> winGroupIdSet, Map<Integer, Boolean> competitorWinMap, Map<Integer, Integer> nextCompetitorGroupMap) {
        int winNum = winGroupIdSet.size() / 2;
        winGroupIdSet.clear();
        for (int i = 1; i <= winNum; i ++) {
            winGroupIdSet.add(i);
        }
        competitorWinMap.clear();
        Set<Integer> fightGroupIdSet = new HashSet<>();
        nextCompetitorGroupMap.forEach((playerId, groupId) -> {
            if (!fightGroupIdSet.contains(groupId)) {
                competitorWinMap.put(playerId, true);
                fightGroupIdSet.add(groupId);
            } else {
                competitorWinMap.put(playerId, false);
            }
        });
        return winNum;
    }
}

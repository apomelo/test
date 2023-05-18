package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 排序
 * @author C
 * @date 2023/5/18
 */
@Slf4j
public class AlgoSort {
    public static void main(String[] args) {
        // 合并区间
        log.info("MergeIntervals: {}", (Object) new MergeIntervals().merge(new int[][] {{1,3},{2,6},{8,10},{15,18}}));
        log.info("MergeIntervals: {}", (Object) new MergeIntervals().merge(new int[][] {{1,4},{4,5}}));
        log.info("MergeIntervals: {}", (Object) new MergeIntervals().merge(new int[][] {{2,3},{4,5},{1,6}}));
    }
}


/**
 * @lc app=leetcode.cn id=56 lang=java
 *
 * [56] 合并区间
 *
 * https://leetcode.cn/problems/merge-intervals/description/
 *
 * algorithms
 * Medium (49.34%)
 * Likes:    1897
 * Dislikes: 0
 * Total Accepted:    632.6K
 * Total Submissions: 1.3M
 * Testcase Example:  '[[1,3],[2,6],[8,10],[15,18]]'
 *
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi]
 * 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 *
 * 示例 1：
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 *
 * 示例 2：
 * 输入：intervals = [[1,4],[4,5]]
 * 输出：[[1,5]]
 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 *
 * 提示：
 * 1 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 10^4
 */
// @lc code=start
class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        intervals = Arrays.stream(intervals).sorted(Comparator.comparingInt(e -> e[0])).toArray(int[][]::new);
        List<int[]> res = new ArrayList<>();
        int[] merge = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (isOverlap(merge, intervals[i])) {
                merge = new int[] {Math.min(merge[0], intervals[i][0]), Math.max(merge[1], intervals[i][1])};
            } else {
                res.add(merge);
                merge = intervals[i];
            }
        }
        res.add(merge);
        return res.toArray(new int[0][]);
    }

    private boolean isOverlap(int[] a, int[] b) {
        return (a[0] >= b[0] && a[0] <= b[1])
                || (a[1] >= b[0] && a[1] <= b[1])
                || (b[0] >= a[0] && b[0] <= a[1])
                || (b[1] >= a[0] && b[1] <= a[1]);
    }
}
// @lc code=end

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
        // 插入区间
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3},{6,9}}, new int[] {2,5}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[] {4,8}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {}, new int[] {5,7}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,5}}, new int[] {2,3}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3}}, new int[] {2,7}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3}}, new int[] {0,5}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3}}, new int[] {0,0}));
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
        LinkedList<int[]> res = new LinkedList<>();
        // 按区间的 start 升序排列
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            // res 中最后一个元素的引用
            int[] last = res.getLast();
            if (curr[0] <= last[1]) {
                last[1] = Math.max(last[1], curr[1]);
            } else {
                // 处理下一个待合并区间
                res.add(curr);
            }
        }
        return res.toArray(new int[0][0]);
    }

    public int[][] merge2(int[][] intervals) {
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


/**
 * @lc app=leetcode.cn id=57 lang=java
 *
 * [57] 插入区间
 *
 * https://leetcode.cn/problems/insert-interval/description/
 *
 * algorithms
 * Medium (41.93%)
 * Likes:    705
 * Dislikes: 0
 * Total Accepted:    144.3K
 * Total Submissions: 344K
 * Testcase Example:  '[[1,3],[6,9]]\n[2,5]'
 *
 * 给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 *
 * 示例 1：
 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出：[[1,5],[6,9]]
 *
 * 示例 2：
 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出：[[1,2],[3,10],[12,16]]
 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
 *
 * 示例 3：
 * 输入：intervals = [], newInterval = [5,7]
 * 输出：[[5,7]]
 *
 * 示例 4：
 * 输入：intervals = [[1,5]], newInterval = [2,3]
 * 输出：[[1,5]]
 *
 * 示例 5：
 * 输入：intervals = [[1,5]], newInterval = [2,7]
 * 输出：[[1,7]]
 *
 * 提示：
 * 0 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= intervals[i][0] <= intervals[i][1] <= 10^5
 * intervals 根据 intervals[i][0] 按 升序 排列
 * newInterval.length == 2
 * 0 <= newInterval[0] <= newInterval[1] <= 10^5
 */
// @lc code=start
class InsertInterval {
    /**
     * 解法1
     * chatGPT生成
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> merged = new ArrayList<>();
        int n = intervals.length;
        int i = 0;

        // 添加所有在新区间之前的区间
        while (i < n && intervals[i][1] < newInterval[0]) {
            merged.add(intervals[i]);
            i++;
        }

        // 合并重叠的区间
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        merged.add(newInterval);

        // 添加剩余的区间
        while (i < n) {
            merged.add(intervals[i]);
            i++;
        }

        return merged.toArray(new int[merged.size()][]);
    }
}
// @lc code=end


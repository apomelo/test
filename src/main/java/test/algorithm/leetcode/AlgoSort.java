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
        // [56] 合并区间
        log.info("MergeIntervals: {}", (Object) new MergeIntervals().merge(new int[][] {{1,3},{2,6},{8,10},{15,18}}));
        log.info("MergeIntervals: {}", (Object) new MergeIntervals().merge(new int[][] {{1,4},{4,5}}));
        log.info("MergeIntervals: {}", (Object) new MergeIntervals().merge(new int[][] {{2,3},{4,5},{1,6}}));
        // [57] 插入区间
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3},{6,9}}, new int[] {2,5}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[] {4,8}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {}, new int[] {5,7}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,5}}, new int[] {2,3}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3}}, new int[] {2,7}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3}}, new int[] {0,5}));
        log.info("InsertInterval: {}", (Object) new InsertInterval().insert(new int[][] {{1,3}}, new int[] {0,0}));
        // [164] 最大间距
        log.info("MaximumGap: {}", new MaximumGap().maximumGap(new int[] {3,6,9,1}));
        log.info("MaximumGap: {}", new MaximumGap().maximumGap(new int[] {10}));
        log.info("MaximumGap: {}", new MaximumGap().maximumGap(new int[] {1,2,7,8,9}));
        // [179] 最大数
        log.info("LargestNumber: {}", new LargestNumber().largestNumber(new int[] {10,2}));
        log.info("LargestNumber: {}", new LargestNumber().largestNumber(new int[] {3,30,34,5,9}));
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

/**
 * @lc app=leetcode.cn id=164 lang=java
 *
 * [164] 最大间距
 *
 * https://leetcode.cn/problems/maximum-gap/description/
 *
 * algorithms
 * Medium (60.07%)
 * Likes:    592
 * Dislikes: 0
 * Total Accepted:    87.5K
 * Total Submissions: 145.7K
 * Testcase Example:  '[3,6,9,1]'
 *
 * 给定一个无序的数组 nums，返回 数组在排序之后，相邻元素之间最大的差值 。如果数组元素个数小于 2，则返回 0 。
 * 您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。
 *
 * 示例 1:
 * 输入: nums = [3,6,9,1]
 * 输出: 3
 * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
 *
 * 示例 2:
 * 输入: nums = [10]
 * 输出: 0
 * 解释: 数组元素个数小于 2，因此返回 0。
 *
 * 提示:
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 */
class MaximumGap {
    /**
     * 桶排序
     */
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        // 找到数组的最大值和最小值
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int minVal = Arrays.stream(nums).min().getAsInt();
        // 计算桶的大小和数量
        int bucketSize = Math.max(1, (maxVal - minVal) / (nums.length - 1));
        int bucketCount = (maxVal - minVal) / bucketSize + 1;

        // 创建桶数组
        Bucket[] buckets = new Bucket[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new Bucket();
        }

        // 将元素放入桶中并更新桶的最小值和最大值
        for (int num : nums) {
            int index = (num - minVal) / bucketSize;
            buckets[index].used = true;
            buckets[index].min = Math.min(buckets[index].min, num);
            buckets[index].max = Math.max(buckets[index].max, num);
        }

        int maxGap = 0;
        int prevMax = minVal;
        // 计算相邻非空桶之间的最大差值
        for (int i = 0; i < bucketCount; i++) {
            if (!buckets[i].used) {
                continue; // 跳过空桶
            }
            maxGap = Math.max(maxGap, buckets[i].min - prevMax);
            prevMax = buckets[i].max;
        }

        return maxGap;
    }

    class Bucket {
        boolean used;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
    }
}


/**
 * @lc app=leetcode.cn id=179 lang=java
 *
 * [179] 最大数
 *
 * https://leetcode.cn/problems/largest-number/description/
 *
 * algorithms
 * Medium (40.96%)
 * Likes:    1212
 * Dislikes: 0
 * Total Accepted:    211.5K
 * Total Submissions: 516.5K
 * Testcase Example:  '[10,2]'
 *
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 *
 * 示例 1：
 * 输入：nums = [10,2]
 * 输出："210"
 *
 * 示例 2：
 * 输入：nums = [3,30,34,5,9]
 * 输出："9534330"
 *
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 10^9
 */
class LargestNumber {
    public String largestNumber(int[] nums) {
        // 将整数数组转化为字符串数组
        String[] numStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numStrs[i] = String.valueOf(nums[i]);
        }

        // 自定义比较函数，将字符串拼接后比较
        Arrays.sort(numStrs, (a, b) -> (b + a).compareTo(a + b));

        // 如果排序后的第一个字符串是 "0"，说明所有数字都是 0，直接返回 "0"
        if (numStrs[0].equals("0")) {
            return "0";
        }

        // 将排序后的字符串数组拼接成最大的数字
        StringBuilder result = new StringBuilder();
        for (String numStr : numStrs) {
            result.append(numStr);
        }

        return result.toString();
    }
}

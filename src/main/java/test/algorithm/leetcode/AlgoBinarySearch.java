package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 二叉搜索
 * @author C
 * @date 2023/3/29
 */
@Slf4j
public class AlgoBinarySearch {
    public static void main(String[] args) {
        // [33] 搜索旋转排序数组
        log.info("SearchInRotatedSortedArray: {}", new SearchInRotatedSortedArray().search(new int[] {4,5,6,7,0,1,2}, 0));
        log.info("SearchInRotatedSortedArray: {}", new SearchInRotatedSortedArray().search(new int[] {4,5,6,7,0,1,2}, 3));
        log.info("SearchInRotatedSortedArray: {}", new SearchInRotatedSortedArray().search(new int[] {5,1,3}, 5));
        log.info("SearchInRotatedSortedArray: {}", new SearchInRotatedSortedArray().search(new int[] {4,5,6,7,8,1,2,3}, 8));
        // [34] 在排序数组中查找元素的第一个和最后一个位置
        log.info("FindFirstAndLastPositionOfElementInSortedArray: {}", new FindFirstAndLastPositionOfElementInSortedArray().searchRange(new int[] {5,7,7,8,8,10}, 8));
        log.info("FindFirstAndLastPositionOfElementInSortedArray: {}", new FindFirstAndLastPositionOfElementInSortedArray().searchRange(new int[] {5,7,7,8,8,10}, 6));
        log.info("FindFirstAndLastPositionOfElementInSortedArray: {}", new FindFirstAndLastPositionOfElementInSortedArray().searchRange(new int[] {}, 0));
        log.info("FindFirstAndLastPositionOfElementInSortedArray: {}", new FindFirstAndLastPositionOfElementInSortedArray().searchRange(new int[] {1}, 1));
        // [35] 搜索插入位置
        log.info("SearchInsertPosition: {}", new SearchInsertPosition().searchInsert(new int[] {1,3,5,6}, 5));
        log.info("SearchInsertPosition: {}", new SearchInsertPosition().searchInsert(new int[] {1,3,5,6}, 2));
        log.info("SearchInsertPosition: {}", new SearchInsertPosition().searchInsert(new int[] {1,3,5,6}, 7));
        log.info("SearchInsertPosition: {}", new SearchInsertPosition().searchInsert(new int[] {1,3,5,6}, 0));
        // [74] 搜索二维矩阵
        log.info("SearchA2dMatrix: {}", new SearchA2dMatrix().searchMatrix(new int[][] {{1,3,5,7},{10,11,16,20},{23,30,34,60}}, 3));
        log.info("SearchA2dMatrix: {}", new SearchA2dMatrix().searchMatrix(new int[][] {{1,3,5,7},{10,11,16,20},{23,30,34,60}}, 13));
        // [81] 搜索旋转排序数组 II
        log.info("SearchInRotatedSortedArrayII: {}", new SearchInRotatedSortedArrayII().search(new int[] {2,5,6,0,0,1,2}, 0));
        log.info("SearchInRotatedSortedArrayII: {}", new SearchInRotatedSortedArrayII().search(new int[] {2,5,6,0,0,1,2}, 3));
        // [153] 寻找旋转排序数组中的最小值
        log.info("FindMinimumInRotatedSortedArray: {}", new FindMinimumInRotatedSortedArray().findMin(new int[] {3,4,5,1,2}));
        log.info("FindMinimumInRotatedSortedArray: {}", new FindMinimumInRotatedSortedArray().findMin(new int[] {4,5,6,7,0,1,2}));
        log.info("FindMinimumInRotatedSortedArray: {}", new FindMinimumInRotatedSortedArray().findMin(new int[] {11,13,15,17}));
        log.info("FindMinimumInRotatedSortedArray: {}", new FindMinimumInRotatedSortedArray().findMin(new int[] {3,1,2}));
        // [154] 寻找旋转排序数组中的最小值 II
        log.info("FindMinimumInRotatedSortedArrayII: {}", new FindMinimumInRotatedSortedArrayII().findMin(new int[] {1,3,5}));
        log.info("FindMinimumInRotatedSortedArrayII: {}", new FindMinimumInRotatedSortedArrayII().findMin(new int[] {2,2,2,0,1}));
        log.info("FindMinimumInRotatedSortedArrayII: {}", new FindMinimumInRotatedSortedArrayII().findMin(new int[] {1,2,1}));
    }
}


/**
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 *
 * https://leetcode.cn/problems/search-in-rotated-sorted-array/description/
 *
 * algorithms
 * Medium (43.84%)
 * Likes:    2555
 * Dislikes: 0
 * Total Accepted:    709.5K
 * Total Submissions: 1.6M
 * Testcase Example:  '[4,5,6,7,0,1,2]\n0'
 *
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k],
 * nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始
 * 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 *
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 *
 * 示例 3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 *
 * 提示：
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 */
class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        int length = nums.length;
        // 定义左右指针
        int left = 0, right = length - 1;
        // 注意判断临界点, 是 < 还是 <=
        while (left <= right) {
            // 获取中间位置
            int mid = ((right - left) >> 1) + left;
            int leftNum = nums[left];
            int rightNum = nums[right];
            int midNum = nums[mid];
            // 找到就返回
            if (target == midNum) {
                return mid;
            }
            // 判断正序还是翻转了
            if (rightNum > leftNum) {
                // 正序指针位移比较简单
                if (target > midNum) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // 翻转后的位置判断要注意临界点和左右长度的两种情况
                if ((midNum < rightNum && target >= midNum && target <= rightNum)
                        || (midNum > rightNum && (target >= midNum || target <= rightNum))) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}


/**
 * @lc app=leetcode.cn id=34 lang=java
 *
 * [34] 在排序数组中查找元素的第一个和最后一个位置
 *
 * https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/
 *
 * algorithms
 * Medium (42.39%)
 * Likes:    2234
 * Dislikes: 0
 * Total Accepted:    760.4K
 * Total Submissions: 1.8M
 * Testcase Example:  '[5,7,7,8,8,10]\n8'
 *
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 *
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 *
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 *
 * 提示：
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums 是一个非递减数组
 * -10^9 <= target <= 10^9
 */
class FindFirstAndLastPositionOfElementInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int length = nums.length;
        // 二分法查找是否存在该 target
        int left = 0, right = length - 1;
        int mid = -1;
        boolean find = false;
        while (left <= right) {
            mid = ((right - left) >> 1) + left;
            int midNum = nums[mid];
            if (target == midNum) {
                find = true;
                break;
            }
            if (target > midNum) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 左右查找和该数值相等的索引
        left = -1;
        right = -1;
        if (find) {
            // 注意临界点
            for (left = mid; left > 0; ) {
                if (nums[left - 1] != nums[mid]) {
                    break;
                }
                left--;
            }
            // 注意临界点
            for (right = mid; right < length - 1; ) {
                if (nums[right + 1] != nums[mid]) {
                    break;
                }
                right++;
            }
        }

        return new int[] {left, right};
    }
}


/**
 * @lc app=leetcode.cn id=35 lang=java
 *
 * [35] 搜索插入位置
 *
 * https://leetcode.cn/problems/search-insert-position/description/
 *
 * algorithms
 * Easy (45.06%)
 * Likes:    1960
 * Dislikes: 0
 * Total Accepted:    1.1M
 * Total Submissions: 2.5M
 * Testcase Example:  '[1,3,5,6]\n5'
 *
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 * 示例 1:
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 *
 * 示例 2:
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 *
 * 示例 3:
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 *
 * 提示:
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 为 无重复元素 的 升序 排列数组
 * -10^4 <= target <= 10^4
 */
class SearchInsertPosition {
    public int searchInsert(int[] nums, int target) {
        int length = nums.length;
        int left = 0, right = length - 1;
        int mid = 0;
        while (left <= right) {
            mid = ((right - left) >> 1) + left;
            if (target == nums[mid]) {
                break;
            }
            if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 注意临界点
        return nums[mid] < target ? mid + 1 : mid;
    }
}


/**
 * @lc app=leetcode.cn id=74 lang=java
 *
 * [74] 搜索二维矩阵
 *
 * https://leetcode.cn/problems/search-a-2d-matrix/description/
 *
 * algorithms
 * Medium (48.68%)
 * Likes:    807
 * Dislikes: 0
 * Total Accepted:    314.9K
 * Total Submissions: 646.8K
 * Testcase Example:  '[[1,3,5,7],[10,11,16,20],[23,30,34,60]]\n3'
 *
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 *
 * 示例 1：
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * 输出：true
 *
 * 示例 2：
 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * 输出：false
 *
 * 提示：
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -104 <= matrix[i][j], target <= 104
 */
class SearchA2dMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) {
            return false;
        }
        int n = matrix[0].length;
        if (n == 0) {
            return false;
        }
        int i = m - 1;
        int j = 0;
        while (i >= 0 && j < n) {
            int curNum = matrix[i][j];
            if (curNum == target) {
                return true;
            } else if (curNum > target) {
                i--;
            } else {
                j++;
            }
        }
        return false;
    }
}


/**
 * @lc app=leetcode.cn id=81 lang=java
 *
 * [81] 搜索旋转排序数组 II
 *
 * https://leetcode.cn/problems/search-in-rotated-sorted-array-ii/description/
 *
 * algorithms
 * Medium (40.98%)
 * Likes:    717
 * Dislikes: 0
 * Total Accepted:    198.9K
 * Total Submissions: 485.3K
 * Testcase Example:  '[2,5,6,0,0,1,2]\n0'
 *
 * 已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，使数组变为 [nums[k],
 * nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始计数）。
 * 例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能变为 [4,5,6,6,7,0,1,2,4,4] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。如果 nums 中存在这个目标值
 * target ，则返回 true ，否则返回 false 。
 * 你必须尽可能减少整个操作步骤。
 *
 * 示例 1：
 * 输入：nums = [2,5,6,0,0,1,2], target = 0
 * 输出：true
 *
 * 示例 2：
 * 输入：nums = [2,5,6,0,0,1,2], target = 3
 * 输出：false
 *
 * 提示：
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 *
 * 进阶：
 * 这是 搜索旋转排序数组 的延伸题目，本题中的 nums  可能包含重复元素。
 * 这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？
 */
class SearchInRotatedSortedArrayII {
    public boolean search(int[] nums, int target) {
        int length = nums.length;
        // 定义左右指针
        int left = 0, right = length - 1;
        // 注意判断临界点, 是 < 还是 <=
        while (left <= right) {
            // 获取中间位置
            int mid = ((right - left) >> 1) + left;
            int leftNum = nums[left];
            int rightNum = nums[right];
            int midNum = nums[mid];
            // 找到就返回
            if (target == midNum) {
                return true;
            }
            // 判断正序还是翻转了
            if (rightNum > leftNum) {
                // 正序指针位移比较简单
                if (target > midNum) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else if (rightNum < leftNum) {
                // 翻转后的位置判断要注意临界点和左右长度的两种情况
                if ((midNum < rightNum && target >= midNum && target <= rightNum)
                        || (midNum > rightNum && (target >= midNum || target <= rightNum))) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (target == rightNum) {
                    return true;
                }
                left++;
                right--;
            }
        }
        return false;
    }
}


/**
 * @lc app=leetcode.cn id=153 lang=java
 *
 * [153] 寻找旋转排序数组中的最小值
 *
 * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/
 *
 * algorithms
 * Medium (57.14%)
 * Likes:    1033
 * Dislikes: 0
 * Total Accepted:    400.6K
 * Total Submissions: 701.1K
 * Testcase Example:  '[3,4,5,1,2]'
 *
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7]
 * 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
 *
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [3,4,5,1,2]
 * 输出：1
 * 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
 *
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2]
 * 输出：0
 * 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
 *
 * 示例 3：
 * 输入：nums = [11,13,15,17]
 * 输出：11
 * 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
 *
 * 提示：
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * nums 中的所有整数 互不相同
 * nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
 */
class FindMinimumInRotatedSortedArray {
    public int findMin(int[] nums) {
        // 判断不合法情况
        if (nums == null) {
            return 0;
        }
        // 定义左右指针
        int length = nums.length;
        int left = 0, right = length - 1;
        while (left < right) {
            // 因为 nums 中元素不重复，所以当 nums[left] < nums[right] 时表示该子段不包含翻转段
            if (nums[left] < nums[right]) {
                break;
            }
            // 找出中点（奇数是是中点，偶数时是中间两个数左边那个）
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[left]) {
                right = mid;
            } else {
                // 因为上面找中点的方式，剩两个数时会陷入死循环，这里需要 +1
                left = mid + 1;
            }
        }
        return nums[left];
    }
}


/**
 * @lc app=leetcode.cn id=154 lang=java
 *
 * [154] 寻找旋转排序数组中的最小值 II
 *
 * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array-ii/description/
 *
 * algorithms
 * Hard (52.41%)
 * Likes:    642
 * Dislikes: 0
 * Total Accepted:    180.5K
 * Total Submissions: 344.4K
 * Testcase Example:  '[1,3,5]'
 *
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,4,4,5,6,7]
 * 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,4]
 * 若旋转 7 次，则可以得到 [0,1,4,4,5,6,7]
 *
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * 给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 * 你必须尽可能减少整个过程的操作步骤。
 *
 * 示例 1：
 * 输入：nums = [1,3,5]
 * 输出：1
 *
 * 示例 2：
 * 输入：nums = [2,2,2,0,1]
 * 输出：0
 *
 * 提示：
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
 *
 * 进阶：这道题与 寻找旋转排序数组中的最小值 类似，但 nums 可能包含重复元素。允许重复会影响算法的时间复杂度吗？会如何影响，为什么？
 */
class FindMinimumInRotatedSortedArrayII {
    public int findMin(int[] nums) {
        // 判断不合法情况
        if (nums == null) {
            return 0;
        }
        // 定义左右指针
        int length = nums.length;
        int left = 0, right = length - 1;
        while (left < right) {
            // 当 nums[left] < nums[right] 时表示左边是正序
            if (nums[left] < nums[right]) {
                break;
            }
            // 因为 nums 中元素可重复，所以当 nums[left] == nums[right] 时需要移动一边指针
            if (nums[left] == nums[right]) {
                left++;
                continue;
            }
            // 找出中点（奇数是是中点，偶数时是中间两个数左边那个）
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[left]) {
                right = mid;
            } else {
                // 因为上面找中点的方式，剩两个数时会陷入死循环，这里需要 +1
                left = mid + 1;
            }
        }
        return nums[left];
    }
}

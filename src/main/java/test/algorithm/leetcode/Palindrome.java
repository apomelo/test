package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 回文数
 */
@Slf4j
public class Palindrome {
    public static void main(String[] args) {
        log.info("isPalindrome: {}", isPalindrome(-121));
        log.info("isPalindrome: {}", isPalindrome(121));
    }


    private static boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        return isPalindrome(s);
    }

    private static boolean isPalindrome(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i ++, j --) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}

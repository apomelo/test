package test.algorithm.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 正则匹配
 */
@Slf4j
public class RegularMatch {
    public static void main(String[] args) {
        log.info("{}", isMatch("aa", "a*"));
        log.info("{}", isMatch("aac", ".*"));
        log.info("{}", isMatch("aac", "aac"));
    }

    private static boolean isMatch(String s, String p) {

    }
}

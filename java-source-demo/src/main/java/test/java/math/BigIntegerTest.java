package test.java.math;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.java.lang.IntegerTest;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * @author C
 * @date 2022/6/29
 */
@Slf4j
public class BigIntegerTest {
    private final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    public static void main(String[] args) {
        encodeMulti();
        encodeMulti2();
    }

    private static void encodeMulti() {
        int max = 999999;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < max; i ++) {
            StringBuilder sb = new StringBuilder();
            sb.append(RandomUtils.nextInt(2, 10))
                    .append(RandomUtils.nextLong(999, 9999999999L))
                    .append(RandomUtils.nextLong(999, 9999999999L));
            BigInteger bigInteger = new BigInteger(sb.toString());
            String s = bigInteger.toString(Character.MAX_RADIX);
            set.add(s.substring(0, 1));
        }
        for (char c : digits) {
            if (!set.contains(String.valueOf(c))) {
                log.info("{}", c);
            }
        }
    }

    private static void encodeMulti2() {
        int max = 999999;
        Set<String> set = new HashSet<>();
        for (int i = 0; i < max; i ++) {
            StringBuilder sb = new StringBuilder();
            sb.append(RandomUtils.nextLong(999, 9999999999L))
                    .append(RandomUtils.nextLong(999, 9999999999L))
                    .append(RandomUtils.nextInt(2, 10));
            BigInteger bigInteger = new BigInteger(sb.toString());
            String s = bigInteger.toString(Character.MAX_RADIX);
            int length = s.length();
            set.add(s.substring(length - 1, length));
        }
        for (char c : digits) {
            if (!set.contains(String.valueOf(c))) {
                log.info("{}", c);
            }
        }
    }
}

package test.base.ienum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2019/7/5.
 */
public class EnumTest {
    private static final Logger logger = LoggerFactory.getLogger(EnumTest.class);
    public enum MyEnum implements IEnum {
        NULL(0),
        TEST1(1),
        TEST2(2);

        private int code;

        MyEnum(int code) {
            this.code = code;
        }

        @Override
        public Integer getCode() {
            return code;
        }
    }

    public static void main(String[] args) {
        int code = 1;
        MyEnum myEnum = EnumUtils.getByCode(MyEnum.class, code);
        logger.debug("{}", myEnum);
    }
}

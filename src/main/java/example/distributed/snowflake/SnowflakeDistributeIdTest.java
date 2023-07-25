package example.distributed.snowflake;

import lombok.extern.slf4j.Slf4j;

/**
 * @author C
 * @date 2023/7/25
 */
@Slf4j
public class SnowflakeDistributeIdTest {
    public static void main(String[] args) {
        SnowflakeDistributeId idWorker = new SnowflakeDistributeId(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            log.info(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}
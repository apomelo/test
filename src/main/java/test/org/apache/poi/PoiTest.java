package test.org.apache.poi;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created by C on 2020/4/7.
 */
public class PoiTest {
    private static final Logger logger = LoggerFactory.getLogger(PoiTest.class);

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        InputStream is1 = new FileInputStream("src/main/resources/data/test1.xls");
        List<A> aList1 = ExcelUtils.readExcel2Bean(is1, A.class, true);
        logger.info("{}", aList1);
        InputStream is2 = new FileInputStream("src/main/resources/data/test2.xlsx");
        List<A> aList2 = ExcelUtils.readExcel2Bean(is2, A.class, true);
        logger.info("{}", aList2);
        InputStream is3 = new FileInputStream("src/main/resources/data/test3.xlsx");
        List<A> aList3 = ExcelUtils.readExcel2Bean(is3, A.class, true);
        logger.info("{}", aList3);
    }
}

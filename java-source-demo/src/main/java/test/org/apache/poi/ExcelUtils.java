package test.org.apache.poi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 2019/4/3.
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * @Description 读取excel文件,将其转换为javabean
     * @param is 输入流
     * @param tClass 类
     * @param isIncludeFirst 是否包含第一行
     * @param <T> 泛型
     * @return java.util.List<T>
     */
    public static <T> List<T> readExcel2Bean(InputStream is, Class<T> tClass, boolean isIncludeFirst)
            throws IOException, IllegalAccessException, InstantiationException {
        List<List<String>> dataList = readExcel(is, 0);
        logger.info("{}", dataList);
        // 遍历数据到实体集合
        List<T> listBean = new ArrayList<>();
        Field[] fields = tClass.getDeclaredFields();
        int firstRow = isIncludeFirst ? 0 : 1;
        for (int i = firstRow; i < dataList.size(); i++) {
            T tBean = tClass.newInstance();
            List<String> listStr = dataList.get(i);
            for (int j = 0; j < listStr.size(); j++) {
                if (j >= fields.length) {
                    break;
                }
                Field field = fields[j];
                String dataStr = listStr.get(j);
                field.setAccessible(true);
                if (StringUtils.isNotEmpty(dataStr)) {
                    Class<?> type = field.getType();
                    if (type == String.class) {
                        field.set(tBean, dataStr);
                    } else if (type == Integer.class || type == int.class) {
                        field.set(tBean, Integer.parseInt(dataStr));
                    } else if (type == Double.class || type == double.class) {
                        field.set(tBean, Double.parseDouble(dataStr));
                    } else if (type == Float.class || type == float.class) {
                        field.set(tBean, Float.parseFloat(dataStr));
                    } else if (type == Long.class || type == long.class) {
                        field.set(tBean, Long.parseLong(dataStr));
                    } else if (type == Boolean.class || type == boolean.class) {
                        field.set(tBean, Boolean.parseBoolean(dataStr));
                    } else if (type == Short.class || type == short.class) {
                        field.set(tBean, Short.parseShort(dataStr));
                    } else if (type == Byte.class || type == byte.class) {
                        field.set(tBean, Byte.parseByte(dataStr));
                    } else if (type == Character.class || type == char.class) {
                        field.set(tBean, dataStr.charAt(0));
                    }
                }
            }
            listBean.add(tBean);
        }
        return listBean;
    }

    /**
     * Excel读取指定表，返回内容
     */
    private static List<List<String>> readExcel(InputStream is, int index) throws IOException {
        Workbook wb = WorkbookFactory.create(is);
        // 得到指定索引sheet
        Sheet sheet = wb.getSheetAt(index);
        // 得到Excel的行数
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        int totalRows = physicalNumberOfRows > 0 ? lastRowNum - firstRowNum + 1 : 0;
        // 得到Excel的列数
        Row row1 = sheet.getRow(firstRowNum);
        int firstCellNum = 0;
        int lastCellNum = 0;
        if (totalRows > 0 && row1 != null) {
            firstCellNum = row1.getFirstCellNum();
            lastCellNum = row1.getLastCellNum();
            logger.info("getFirstRowNum: {}", firstRowNum);
            logger.info("getLastRowNum: {}", lastRowNum);
            logger.info("getPhysicalNumberOfRows: {}", physicalNumberOfRows);
            logger.info("getFirstCellNum: {}", firstCellNum);
            logger.info("getLastCellNum: {}", lastCellNum);
            logger.info("getPhysicalNumberOfCells: {}", row1.getPhysicalNumberOfCells());
        }
        List<List<String>> dataList = new ArrayList<>();
        // 循环Excel的行
        for (int r = firstRowNum; r <= lastRowNum; r ++) {
            Row row = sheet.getRow(r);
            // 跳过空行
            if (row == null) {
                continue;
            }
            List<String> rowList = new ArrayList<>();
            // 循环Excel的列
            for (int c = firstCellNum; c <= lastCellNum; c++) {
                String cellValue = "";
                Cell cell = row.getCell(c);
                if (cell != null) {
                    HSSFDataFormatter hSSFDataFormatter = new HSSFDataFormatter();
                    cellValue = hSSFDataFormatter.formatCellValue(cell);
                }
                rowList.add(cellValue);
            }
            // 保存第r行的第c列
            dataList.add(rowList);
        }
        return dataList;
    }
}

package test.com.alibaba.fastjson;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Created by C on 2019/8/14.
 */
@lombok.Data
@ToString
@AllArgsConstructor
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class DataB {
    int testInt;
    String testString;
}

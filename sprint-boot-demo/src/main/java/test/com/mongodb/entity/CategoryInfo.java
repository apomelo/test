package test.com.mongodb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by C on 2019/1/12.
 */
@Data
@Document(collection = "category_info")
public class CategoryInfo {
    private String sourceValue;
    private String dbName;
}

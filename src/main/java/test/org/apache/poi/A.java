package test.org.apache.poi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by C on 2020/4/8.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class A {
    int level;
    int num;
    int a;
    String name;
}

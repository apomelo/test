package example.game.msg;

import lombok.Data;

import java.util.List;

/**
 * @author C
 * @date 2023/8/30
 */
@Data
public class FightBean {
    private int id;
    private List<AttrBean> attrBeanList;
    private List<BuffBean> buffBeanList;
}

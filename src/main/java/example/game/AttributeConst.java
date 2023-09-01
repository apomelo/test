package example.game;

/**
 * @author C
 * @date 2023/9/1
 */
public enum AttributeConst {
    HP(1),
    HP_MAX(2),
    ATK(3),
    AGILITY(4),
    ;
    private int value;
    AttributeConst(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

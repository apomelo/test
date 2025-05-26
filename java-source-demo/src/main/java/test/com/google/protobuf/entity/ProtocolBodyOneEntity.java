package test.com.google.protobuf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by C on 2019/6/10.
 */
@Data
@ToString
public class ProtocolBodyOneEntity {
    private Action action;
    private Corpus corpus;
    private int page;
    private List<Float> floatListTest;
    private List<Corpus> corpusListTest;
    private List<Action> actionListTest;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Action {
        private int type;
    }
    public enum Corpus {
        NIL(0),
        UNIVERSAL(1),
        WEB(2),
        IMAGES(3),
        LOCAL(4),
        NEWS(5),
        PRODUCTS(6),
        VIDEO(7);

        private int code;
        Corpus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
